package com.example.bookstore.service;

import com.example.bookstore.dao.BookDao;
import com.example.bookstore.dao.OrderDao;
import com.example.bookstore.dao.OrderItemDao;
import com.example.bookstore.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;

    public String createOrder(Cart cart, String username) {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        String orderId = System.currentTimeMillis() + "" + username;
        Timestamp date = new Timestamp(Calendar.getInstance().getTime().getTime());
        Order order = new Order(orderId, date, cart.getTotalPrice(), 0, username);
        try {
            orderDao.add(order);
            for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet())
            {
                CartItem cartItem = entry.getValue();
                OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId, cartItem.getImgPath());
                Book book = bookDao.searchById(cartItem.getId());
                int num = book.getStock() - cartItem.getCount();
                if (num >= 0)
                {
                    book.setSales(book.getSales() + cartItem.getCount());
                    book.setStock(num);
                    bookDao.update(book);
                    orderItemDao.add(orderItem);
                }
                else
                    return null;
            }
            cart.clear();
            dataSourceTransactionManager.commit(transactionStatus);
            return orderId;
        } catch (Exception e) {
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transactionStatus);
            return null;
        }
    }

    public boolean delete(String orderId) {
        if (orderId == null) return false;
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            orderItemDao.delete(orderId);
            orderDao.delete(orderId);
            dataSourceTransactionManager.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transactionStatus);
            return false;
        }
    }

    public Page page(String no, String username) {
        Page page = new Page();
        int pageTotalCount = orderDao.pageCount(username);
        Integer begin = Helper.pageHelper(page, pageTotalCount, no);
        if (begin == null) return null;
        List<Order> orders = orderDao.page(username, begin, page.getPageSize());
        page.setItems(orders);
        return page;
    }

    public Page pageAll(String no) {
        Page page = new Page();
        int pageTotalCount = orderDao.pageAllCount();
        Integer begin = Helper.pageHelper(page, pageTotalCount, no);
        if (begin == null) return null;
        List<Order> orders = orderDao.pageAll(begin, page.getPageSize());
        page.setItems(orders);
        return page;
    }

    public boolean updateStatus(String orderId, String str) {
        if (orderId == null) return false;
        try {
            int status = Integer.parseInt(str);
            status++;
            return orderDao.updateStatus(orderId, status);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Page itemsPage(String no, String orderId) {
        Page page = new Page();
        int pageTotalCount = orderItemDao.pageCount(orderId);
        Integer begin = Helper.pageHelper(page, pageTotalCount, no);
        if (begin == null) return null;
        List<OrderItem> orderItems = orderItemDao.page(orderId, begin, page.getPageSize());
        page.setItems(orderItems);
        return page;
    }

    public List<OrderItem> searchAll(String orderId) {
        return orderItemDao.searchAll(orderId);
    }
    public int getStatus(String orderId) {
        return orderDao.getStatus(orderId);
    }
}
