package com.salmon.jpa.core.tree;

import java.io.Serializable;
import java.util.List;

public interface ITree extends Serializable {
    String getId();
    String getParentId();
    List<ITree> getChildren();
    void setChildren(List<ITree> children);
}
