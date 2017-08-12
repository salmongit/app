package com.salmon.jpa.core.utils;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 依据FromPath获取Path<?>对象
 * @author GONGYAN
 *
 * @param <T>
 */
public class FromPathHelper<T> {
	private FromPath rootFromPath;
	
	public FromPath getRootFromPath() {
		return rootFromPath;
	}
	
	public FromPathHelper(Root<T> root){
		this.rootFromPath = new FromPath(root);
	}
	
	public static enum FromJoinType{
		FL{
			@Override
			public Path<?> buildCurrentFrom(Path<?> from, String tableProperty) {
				return (From<?, ?>)((From<?, ?>)from).fetch(tableProperty, JoinType.LEFT);
			}
		},FI{
			@Override
			public Path<?> buildCurrentFrom(Path<?> from, String tableProperty) {
				return (From<?, ?>)((From<?, ?>)from).fetch(tableProperty, JoinType.INNER);
			}
		},L{
			@Override
			public Path<?> buildCurrentFrom(Path<?> from, String tableProperty) {
				return ((From<?, ?>)from).join(tableProperty, JoinType.LEFT);
			}
		},I{
			@Override
			public Path<?> buildCurrentFrom(Path<?> from, String tableProperty) {
				return ((From<?, ?>)from).join(tableProperty, JoinType.INNER);
			}
		},P{
			@Override
			public Path<?> buildCurrentFrom(Path<?> path, String tableProperty) {
				return path.get(tableProperty);
			}
		};
		public abstract Path<?> buildCurrentFrom(Path<?> from, String tableProperty);
		
		public static FromJoinType getFromJoinTypeByStrIgnoreCase(String type){
			if(type == null || type.trim().equals(""))return null;
			for(FromJoinType fjt : FromJoinType.values()){
				if(type.equalsIgnoreCase(fjt.toString())){
					return fjt;
				}
			}
			return null;
		}
	}
	
	public Path<?> getFromByListJoinInfo(List<JoinInfo> joinInfos){
		if(joinInfos == null || joinInfos.size()<1){
			return rootFromPath.getCurrentValue();
		}
		
		String tableProperty;
		FromJoinType fromJoinType;
		FromPath resultFromPath = rootFromPath;
		
		int i = 0;
		for(JoinInfo ji: joinInfos){
			tableProperty = ji.getTableProperty();
			fromJoinType = ji.getFromJoinType();
			if(resultFromPath.containsKey(tableProperty)){
				resultFromPath = resultFromPath.get(tableProperty);
				i++;
			}
			else{
				break;
			}
		}
		JoinInfo ji = null;
		Path<?> currentFrom = null;
		FromPath currentFromPath = null;
		while(i < joinInfos.size()){
			ji = joinInfos.get(i++);
			tableProperty = ji.getTableProperty();
			fromJoinType = ji.getFromJoinType();
			currentFrom = fromJoinType.buildCurrentFrom(resultFromPath.getCurrentValue(), tableProperty);
			currentFromPath = new FromPath();
			currentFromPath.setCurrentValue(currentFrom);
			resultFromPath.put(tableProperty, currentFromPath);
			resultFromPath = currentFromPath;
		}
		return resultFromPath.getCurrentValue();
	}
}
