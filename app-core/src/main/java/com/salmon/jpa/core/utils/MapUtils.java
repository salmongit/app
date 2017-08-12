package com.salmon.jpa.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.salmon.jpa.core.utils.CriterionHelper.Operator;

public class MapUtils {
	private static Pattern patternDate = Pattern.compile("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$");
	private static Pattern patternDateTime = Pattern.compile("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$");
	
	public static Map<String, Object> parseMapForFilter(Map<String, Object> map){
		if(map == null) return null;
		map = filterMapEmptyValue(map);
		Object value = null;
		String vStr = "";
		for(String key : map.keySet()){
			value = map.get(key);
			if(value instanceof String){
				FilterKey fk = new FilterKey(key);
				vStr = (String)value;
				if (patternDate.matcher(vStr).matches()) {
					if (fk.getOperator() == Operator.LE || fk.getOperator() == Operator.TIMEEND) {
						value = DateUtils.parse(value + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
					} else {
						value = DateUtils.parse((String) value, "yyyy-MM-dd");
					}
					map.put(key, value);
				} else if (patternDateTime.matcher((String) value).matches()) {
					value = DateUtils.parse((String) value, "yyyy-MM-dd HH:mm:ss");
					map.put(key, value);
				} else if (fk.getOperator() == Operator.INCLUDE || fk.getOperator() == Operator.EXCLUDE) {
					value = ((String) value).split(",");
					map.put(key, value);
				}
			}
		}
		return map;
	}

	/**
	 * 过滤掉map中为空的那些数据
	 * @param map
	 * @return
	 */
	public static Map<String, Object> filterMapEmptyValue(Map<String, Object> map){
		List<String> emptyList = new ArrayList<String>();
		Object value = null;
		for(String key : map.keySet()){
			value = map.get(key);
			if(value == null){
				emptyList.add(key);
				continue;
			}
			if(value instanceof String && ((String) value).trim().equals("")){
				emptyList.add(key);
				continue;
			}
		}
		for(String key : emptyList){
			map.remove(key);
		}
		return map;
	}
}
