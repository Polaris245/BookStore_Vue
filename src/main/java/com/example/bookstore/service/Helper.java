package com.example.bookstore.service;

import com.example.bookstore.pojo.Page;

import java.util.Map;

public class Helper {

    public static boolean isLegal(Map<String, String> maps){
        for (Map.Entry<String, String> entry : maps.entrySet()){
                if (entry.getValue() == null || entry.getValue().equals(""))
                    return false;
        }
        return true;
    }

    public static Integer pageHelper(Page page, int pageTotalCount, String no) {
        int pageNo, pageTotal, begin, pageSize = page.getPageSize();
        try {
            if (no == null)
                pageNo = 1;
            else
                pageNo = Integer.parseInt(no);
            if (pageNo < 1) pageNo = 1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        page.setPageTotalCount(pageTotalCount);
        pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0)
            pageTotal += 1;
        page.setPageTotal(pageTotal);
        if (pageNo > pageTotal && pageTotal != 0)
            pageNo = pageTotal;
        page.setPageNo(pageNo);
        begin = (pageNo - 1) * pageSize;
        return begin;
    }
}
