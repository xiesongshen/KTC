package com.xss.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author XSS
 * @date 2020/8/18
 * @desc 分页实体
 */
public class PageResult<T> implements Serializable {

    //总记录数
    private Long total;

    //当前页数据
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
