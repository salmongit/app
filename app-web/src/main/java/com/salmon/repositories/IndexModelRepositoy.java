package com.salmon.repositories;

import com.salmon.entities.IndexModel;
import com.salmon.jpa.core.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface IndexModelRepositoy extends BaseRepository<IndexModel,Long> {

    @Query("select u from IndexModel u where u.name like ?1 or u.name like ?2")
    List<IndexModel> findByName(String name1,String name2);

}
