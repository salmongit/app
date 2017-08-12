package com.salmon.jpa.core.utils;

import com.salmon.jpa.core.tree.ITree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtils {

    public static List<ITree> getTreeList(List<? extends ITree> list) {
    	List<ITree> parent = new ArrayList<ITree>();
		
		Map<Object, ITree> map = new HashMap<Object, ITree>();
		for (ITree tree : list) {
			map.put(tree.getId(), tree);
		}
		
		for (ITree tree : list) {
			if (null != tree.getParentId() && map.containsKey(tree.getParentId()) && map.containsKey(tree.getId())) {
				ITree temp = map.get(tree.getParentId());
				if(temp.getChildren()==null){
					List<ITree> children = new ArrayList<ITree>();	
					children.add(tree);
					temp.setChildren(children);
				}else
				    temp.getChildren().add(tree);
			} else {
				parent.add(tree);
			}
		}
		
		return parent;
    }
    
    public static ITree getTree(List<? extends ITree> list) {
    	List<ITree> result = getTreeList(list);
		return result.get(0);
    }
}
