package com.salmon.jpa.core.page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * 分页统一接口
 * @param <T>
 */
public interface QueryResult<T> extends Serializable {

    Pageable getPageable(Sort sort);
    String toJsonString();
    void setContent(List<T> resultList);
    List<T> getContent();
    /**
     * 是否进行分页，是否满足分页的条件
     * @return
     */
    boolean pageable();
    /**
     * 满足条件的所有记录的总行数
     * @return
     */
    Long getTotalRow();
    /**
     * 基于0为第一行的本页实际的第一行在所有记录中的行号
     * @return
     */
    Long getBeginRow();
    /**
     * 每页行数，不论本页实际条数为多少，返回分页的每页记录数
     * @return
     */
    Integer getPageRow();
}

