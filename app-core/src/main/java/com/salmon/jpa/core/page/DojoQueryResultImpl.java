package com.salmon.jpa.core.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salmon.jpa.core.utils.JacksonUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class DojoQueryResultImpl<T> implements QueryResult<T> {

    private String identifier = "id";

    private String label;

    private Integer curPage;
    private Integer pageSize;

    @JsonProperty("numRows")
    private Long totalRow;
    @JsonProperty("items")
    private List<T> content;

    public DojoQueryResultImpl(Long totalRow, Long start, Integer pageRow){

    }

    @Override
    public Pageable getPageable(Sort sort) {
        return new PageRequest(curPage, pageSize, sort);
    }

    @Override
    public String toJsonString() {
        return JacksonUtils.obj2json(this);
    }

    @Override
    public void setContent(List<T> resultList) {
        this.content = resultList;
    }

    @Override
    public List<T> getContent() {
        return this.content;
    }

    @Override
    public boolean pageable() {
        return totalRow != null;
    }

    @Override
    public Long getTotalRow() {
        return this.totalRow;
    }

    @Override
    public Long getBeginRow() {
        return null;
    }

    @Override
    public Integer getPageRow() {
        return null;
    }
}
