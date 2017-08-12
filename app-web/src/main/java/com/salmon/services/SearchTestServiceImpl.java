package com.salmon.services;

import com.salmon.entities.IndexModel;
import com.salmon.jpa.core.page.QueryResult;
import com.salmon.repositories.IndexModelRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class SearchTestServiceImpl implements SearchTestService {


    private IndexModelRepositoy indexModelRepositoy;

    public IndexModelRepositoy getIndexModelRepositoy() {
        return indexModelRepositoy;
    }
    @Autowired
    public void setIndexModelRepositoy(IndexModelRepositoy indexModelRepositoy) {
        this.indexModelRepositoy = indexModelRepositoy;
    }


    @Override
    public QueryResult<IndexModel> searchText(String keyWord){
        return indexModelRepositoy.searchTextPage(keyWord,0,2,"name");
    }

    @Override
    public List<IndexModel> getListModel(Map<String,Object> mapParam,Map<String,String> sortParam){
        return indexModelRepositoy.searchAll(mapParam,sortParam);
    }
}
