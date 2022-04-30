package com.svo.svo.other;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

public class PagedList<T> {
    private List<T> list;
    private int total;

    public PagedList() {
    }

    public PagedList(List<T> list, int total) {
        this.list = list;
        this.total = total;
    }

    public PagedList(List<T> list, int total, int start, int limit) {
        this.list = this.paginateList(list, start, limit);
        this.total = total;
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException var3) {
            return var3.getMessage();
        }
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> paginateList(List<T> sourceList, int page, int pageSize) {
        if (pageSize >= 0 && page >= 0) {
            int fromIndex = page * pageSize;
            return sourceList != null && sourceList.size() >= fromIndex ? sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size())) : Collections.emptyList();
        } else {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }
    }
}