package com.salmon.jpa.core.repositories.base;

import java.io.Serializable;
import java.util.Map;

public interface BatchHandlerRepository<T, Key extends Serializable> {
	/**
	 * 根据过滤条件，删除符合条件的记录。
	 * @param mapParam 过滤条件,建议使用LinkedHashMap类型，注意该条件不能包含导航属性查询
	 * @return 删除的记录行数
	 */
	Integer deleteByCondition(Map<String, Object> mapParam);
	/**
	 * 根据过滤条件mapParam，将符合条件的实体的属性统一更新为mapUpdateField中的Value。
	 * mapUpdateField中的key为当前实体的直接属性的属性名，value为要更新的值。
	 * @param mapParam 过滤条件,建议使用LinkedHashMap类型，注意该条件不能包含导航属性查询
	 * @param mapUpdateField
	 * @return
	 */
	Integer updateByCondition(Map<String, Object> mapParam, Map<String, Object> mapUpdateField);
	
	/**
	 * 根据过滤条件，删除符合条件的记录。
	 * @param mapParam 过滤条件,建议使用LinkedHashMap类型，该条件可以同查询一样包含关联查询
	 * @return 删除的记录行数
	 */
	Integer deleteByComplexCondition(Map<String, Object> mapParam);
	/**
	 * 根据过滤条件mapParam，将符合条件的实体的属性统一更新为mapUpdateField中的Value。
	 * mapUpdateField中的key为当前实体的直接属性的属性名，value为要更新的值。
	 * @param mapParam 过滤条件,建议使用LinkedHashMap类型，该条件可以同查询一样包含关联查询
	 * @param mapUpdateField
	 * @return
	 */
	Integer updateByComplexCondition(Map<String, Object> mapParam, Map<String, Object> mapUpdateField);
}
