package com.salmon.jpa.core.repositories.base;

import com.salmon.jpa.core.page.QueryResult;
import com.salmon.jpa.core.utils.FromPathHelper;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface SearchableRepository<T, Key extends Serializable> {
	/**
	 * 
	 * @param mapParam 过滤条件,建议使用LinkedHashMap类型
	 * @return
	 */
	Long searchTotalCount(Map<String, Object> mapParam);

	/**
	 * 根据过滤条件获取分页数据
	 * @param mapParam 过滤条件,建议使用LinkedHashMap类型
	 * @param mapSort 排序 key为列对应的属性名，value为desc或asc,建议使用LinkedHashMap类型
	 * @param limit 每页显示条数
	 * @param start 基于0为第一行的从第几条开始
	 * @return
	 */
	QueryResult<T> searchPage(Map<String, Object> mapParam, Map<String, String> mapSort, Integer limit, Long start);

	/**
	 * 根据过滤条件获取分页数据，此处传入QueryResult 实现类，来扩展返回接口的具体实现，这样可以针对不同的平台进行渲染结果，例如 （DOJO ExtJS） 某个具体的实现
	 * @param mapParam 过滤条件,建议使用LinkedHashMap类型
	 * @param mapSort 排序 key为列对应的属性名，value为desc或asc,建议使用LinkedHashMap类型
	 * @param limit 每页显示条数
	 * @param start 基于0为第一行的从第几条开始
	 * @param pv 实现QueryResult接口的具体类
	 * @return
	 */
	QueryResult<T> searchPage(Map<String, Object> mapParam, Map<String, String> mapSort, Integer limit, Long start,QueryResult<T> pv);

	/**
	 * 根据过滤条件获取所有数据 此处返回的是ExtJS需要的格式
	 * @param mapParam 过滤条件,建议使用LinkedHashMap类型
	 * @param mapSort 排序 key为列对应的属性名，value为desc或asc,建议使用LinkedHashMap类型
	 * @return
	 */
	List<T> searchAll(Map<String, Object> mapParam, Map<String, String> mapSort);
	/**
	 * 根据条件获取ID
	 * @param mapParam
	 * @return
	 */
	List<Key> searchAllIds(Map<String, Object> mapParam);
	Predicate getPredicate(CriteriaBuilder cb, final FromPathHelper<T> fromPathHelper, Map<String, Object> mapParam);
	List<Sort> getSort(LinkedHashMap<String, String> sortMap);
	List<Order> getOrders(Root<T> root, Map<String, String> sortMap);
	CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder cb);
	Root<T> getRoot(CriteriaQuery<?> cq);
}
