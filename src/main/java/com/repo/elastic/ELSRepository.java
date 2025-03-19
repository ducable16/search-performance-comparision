package com.repo.elastic;
import com.entity.ELSUser;
import com.entity.SQLUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ELSRepository extends ElasticsearchRepository<ELSUser, String> {
}
