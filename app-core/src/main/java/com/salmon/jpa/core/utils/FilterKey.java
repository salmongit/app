package com.salmon.jpa.core.utils;

import com.salmon.jpa.core.utils.CriterionHelper.Operator;
import java.util.List;
import java.util.Map;

/**
 * 将FilterKey分解为三部分
 * 1、operator
 * 2、joinInfo
 * 3、propertyName
 */
public class FilterKey {
	/**
	 * 参数对应的key，通过该字符进行分割为三部分：
	 * 1、判断操作符，比如EQ(=),LT(<),INCLUDE(in)...
	 * 2、导航JOIN，比如L@people.R@type,将会转换为 Left join people on ... Right join type on...
	 * 3、条件属性，比如name，最终在where条件中增加type.name = value
	 * 完整的例子例如：EQ_L@people.R@classType_name
	 * 其中注意：
	 * 1、如果条件属性是查询的根类的属性，则不需要指定导航Join
	 * 2、可以只有导航JOIN部分：FL@people，这样之代表查询时，将people同时fetch出来，相当于fecth Left Join people on ...
	 * 3、判断操作符省略时，默认为EQ
	 */
	public static final String PARAM_KEY_SPLIT = "_";
	
	private Operator operator;
	private List<JoinInfo> joinInfos;
	private String propertyName;
	
	public Operator getOperator() {
		return operator;
	}
	public void setOperatorByStr(String operator) {
		this.operator = Operator.getOperatorByStrIgnoreCase(operator);
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public List<JoinInfo> getJoinStr() {
		return joinInfos;
	}
	public void setJoinStr(String joinStr) {
		this.joinInfos = JoinInfoHelper.buildJoinInfosByStr(joinStr);
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}


	/**
	 * @param key 条件命令
	 */
	public FilterKey(String key){
		if(key == null || key.trim().equals("")) return;
		if(key.startsWith(PARAM_KEY_SPLIT))
			key = key.substring(1);
		String[] keyInfo = key.split(PARAM_KEY_SPLIT);

		if(keyInfo.length == 3){
			if(!JoinInfoHelper.isJoinStr(keyInfo[1]) || JoinInfoHelper.isJoinStr(keyInfo[0]) || JoinInfoHelper.isJoinStr(keyInfo[2])){
				throw new IllegalArgumentException("条件过滤标识符" + key + "不符合规范要求。");
			}
			this.setOperatorByStr(keyInfo[0]);
			this.setJoinStr(keyInfo[1]);
			this.setPropertyName(keyInfo[2]);
			if(this.getOperator() == null){
				throw new IllegalArgumentException("条件过滤标识符" + key + "不符合规范要求。");
			}
		}
		else if(keyInfo.length == 2){
			if(JoinInfoHelper.isJoinStr(keyInfo[0])){
				this.setOperatorByStr("EQ");
				this.setJoinStr(keyInfo[0]);
				this.setPropertyName(keyInfo[1]);
			}
			else if(!JoinInfoHelper.isJoinStr(keyInfo[0]) && !JoinInfoHelper.isJoinStr(keyInfo[1])){
				this.setOperatorByStr(keyInfo[0]);
				this.setJoinStr(null);
				this.setPropertyName(keyInfo[1]);
			}
			else {
				throw new IllegalArgumentException("条件过滤标识符" + key + "不符合规范要求。");
			}
			if(this.getOperator() == null){
				throw new IllegalArgumentException("条件过滤标识符" + key + "不符合规范要求。");
			}
		}
		else if(keyInfo.length == 1){
			if(JoinInfoHelper.isJoinStr(keyInfo[0])){
				this.setOperator(null);
				this.setJoinStr(keyInfo[0]);
				this.setPropertyName(null);
			}
			else{
				this.setOperatorByStr("EQ");
				this.setJoinStr(null);
				this.setPropertyName(keyInfo[0]);
			}
		}
		else{
			throw new IllegalArgumentException("条件过滤标识符" + key + "不符合规范要求。");
		}
	}
	
	public static boolean isContainsJoin(Map<String, ?> filterMap){
		if(filterMap == null) return false;
		for(String key : filterMap.keySet()){
			if(JoinInfoHelper.isJoinStr(key)){
				return true;
			}
		}
		return false;
	}
}
