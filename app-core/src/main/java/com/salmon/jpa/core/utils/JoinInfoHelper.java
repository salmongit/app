package com.salmon.jpa.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要目的是根据joinInfos字符串，例如:FL@people.FI@gradeType
 * 解析成为List<JoinInfo>
 * @author GONGYAN
 *
 */
public class JoinInfoHelper {
	/**
	 * 用来分割导航JOIN中的各个表的分隔符
	 */
	public static final String JOIN_TABLE_SPLIT="\\.";
	/**
	 * 用来分割导航JOIN中的各个表的JOIN TYPE和Table Name的分隔符
	 * JOIN Type分为五种：
	 * FI、I、FL、L、P
	 * 其中F代表fetch；I代表Inner Join；L代表Left Join;P代表笛卡尔积
	 * 格式如：L@people.FI@type
	 */
	public static final String JOIN_TYPE_NAME_SPLIT="@";
	
	public static boolean isJoinStr(String str){
		if(str == null) return false;
		return str.contains(JOIN_TABLE_SPLIT) || str.contains(JOIN_TYPE_NAME_SPLIT);
	}
	
	public static List<JoinInfo> buildJoinInfosByStr(String joinInfos){
		if(joinInfos == null || joinInfos.trim().equals(""))
			return null;
		List<JoinInfo> lsJoinInfo = new ArrayList<JoinInfo>();
		String[] joins = joinInfos.split(JOIN_TABLE_SPLIT);
		JoinInfo ji = null;
		for(String join : joins){
			ji = new JoinInfo(join);
			lsJoinInfo.add(ji);
		}
		return lsJoinInfo;
	}
	
}
