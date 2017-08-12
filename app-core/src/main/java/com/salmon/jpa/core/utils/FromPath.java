package com.salmon.jpa.core.utils;

import javax.persistence.criteria.Path;
import java.util.HashMap;

public class FromPath extends HashMap<String, FromPath>{

	private static final long serialVersionUID = 5360727813934543063L;

	public FromPath(){
		
	}
	
	public FromPath(Path<?> currentValue){
		this.currentValue = currentValue;
	}
	
	private Path<?> currentValue;

	public Path<?> getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Path<?> currentValue) {
		this.currentValue = currentValue;
	}
	
}
