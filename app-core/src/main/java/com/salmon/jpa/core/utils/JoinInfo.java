package com.salmon.jpa.core.utils;

import com.salmon.jpa.core.utils.FromPathHelper.FromJoinType;

public class JoinInfo {
	private FromJoinType fromJoinType;
	private String tableProperty;
	
	public FromJoinType getFromJoinType() {
		return fromJoinType;
	}
	public void setFromJoinType(FromJoinType fromJoinType) {
		this.fromJoinType = fromJoinType;
	}
	public String getTableProperty() {
		return tableProperty;
	}
	public void setTableProperty(String tableProperty) {
		this.tableProperty = tableProperty;
	}
	
	/**
	 * 根据类似于FL@people这种字符串分解为当前类的格式
	 * @param singleJoin
	 */
	public JoinInfo(String singleJoin){
		String[] join = singleJoin.split(JoinInfoHelper.JOIN_TYPE_NAME_SPLIT);
		if(join.length !=2){
			throw new IllegalArgumentException("构造JoinInfo的构造函数参数'"+singleJoin+"'不符合格式要求,例如'FI@people'。");
		}
		this.fromJoinType = FromJoinType.getFromJoinTypeByStrIgnoreCase(join[0]);
		if(fromJoinType == null){
			throw new IllegalArgumentException("构造JoinInfo的构造函数参数'"+singleJoin+"'不符合格式要求,其JoinType部分不在'FI','FL','I','L','P'这五种之内。");
		}
		this.tableProperty = join[1];
		if(this.tableProperty == null || this.tableProperty.trim().equals("")){
			throw new IllegalArgumentException("构造JoinInfo的构造函数参数'"+singleJoin+"'不符合格式要求,其tableProperty部分为空。");
		}
	}
}
