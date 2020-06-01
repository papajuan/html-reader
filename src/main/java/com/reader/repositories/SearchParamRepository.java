package com.reader.repositories;

import com.reader.entities.SearchParam;
import com.reader.entities.SearchParamId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author papajuan
 * @date 6/1/2020
 **/
@Repository
public interface SearchParamRepository extends CrudRepository<SearchParam, SearchParamId> {

    @Query("select param.tag from SearchParam param where param.channel.id = :id")
    public Iterable<String> findAllByChannel(@Param("id") int id);
}
