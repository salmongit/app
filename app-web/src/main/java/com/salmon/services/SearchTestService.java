package com.salmon.services;

import com.salmon.entities.IndexModel;
import com.salmon.jpa.core.page.QueryResult;
import com.salmon.repositories.IndexModelRepositoy;

import java.util.List;
import java.util.Map;


public interface SearchTestService {

    QueryResult<IndexModel> searchText(String keyWord);

    IndexModelRepositoy getIndexModelRepositoy();


    List<IndexModel> getListModel(Map<String,Object> mapParam,Map<String,String> sortParam);
}
