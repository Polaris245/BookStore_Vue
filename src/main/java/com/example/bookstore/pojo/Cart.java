package com.example.bookstore.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private BigDecimal totalPrice;
    private int totalCount;
    private String lastName;
    private Map<Integer,CartItem> items = new LinkedHashMap<Integer,CartItem>();

    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (Map.Entry<Integer,CartItem> entry : items.entrySet())
            totalCount += entry.getValue().getCount();
        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        for (Map.Entry<Integer,CartItem> entry : items.entrySet())
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        return totalPrice;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    public Cart( Map<Integer, CartItem> items) {
        this.items = items;
        this.totalCount = getTotalCount();
        this.totalPrice = getTotalPrice();
    }
    public Cart() {
        this.totalCount = getTotalCount();
        this.totalPrice = getTotalPrice();
    }

    public void add(CartItem cartItem)
    {
        CartItem item = items.get(cartItem.getId());
        if (item == null)
            items.put(cartItem.getId(),cartItem);
        else {
            item.setCount(item.getCount()+1);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
        this.totalCount = getTotalCount();
        this.totalPrice = getTotalPrice();
    }
    public void delete(Integer id) {
        items.remove(id);
        this.totalCount = getTotalCount();
        this.totalPrice = getTotalPrice();
    }
    public void clear() {
        items.clear();
        this.totalCount = getTotalCount();
        this.totalPrice = getTotalPrice();
    }
    public void updateCount(Integer id, Integer count)
    {
        CartItem cartItem = items.get(id);
        cartItem.setCount(count);
        cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        this.totalCount = getTotalCount();
        this.totalPrice = getTotalPrice();
    }
}
