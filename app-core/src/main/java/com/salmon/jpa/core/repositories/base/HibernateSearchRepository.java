package com.salmon.jpa.core.repositories.base;

import com.salmon.jpa.core.page.QueryResult;

import java.util.List;

/**
 * 使用Hibernate Search做全文检索使用到的接口
 * @param <T>
 */
public interface HibernateSearchRepository<T> {

    /**
     * 根据提供的搜素字段以及搜素内容进行全文检索，并反悔搜索到的实体类列表
     * @param searchString 要搜索的内容
     * @param field 对哪个字段进行搜素
     * @return
     */
    List<T> searchText(String searchString,String... field);

    /**
     * 根据提供的搜索关键字进行分页搜索
     * @param searchString 要搜索的内容
     * @param startIndex 起始位置 从0开始
     * @param limit 每页显示的条数
     * @param field 要搜索的字段
     * @return
     */
    QueryResult<T> searchTextPage(String searchString,Integer startIndex, Integer limit,String... field);
}
