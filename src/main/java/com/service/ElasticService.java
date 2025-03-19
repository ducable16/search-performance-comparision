package com.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.entity.ELSUser;
import com.entity.Location;
import com.entity.SQLUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repo.elastic.ELSRepository;
import com.repo.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ElasticService {

    @Autowired
    ELSRepository elsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    public boolean initialSyncToElasticsearch() {
        int pageSize = 10000;
        int pageNumber = 860;
        Page<SQLUser> page;

        do {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            List<ELSUser> list = new ArrayList<>();
            page = userRepository.findAll(pageable); // đọc từ PostgreSQL
            page.getContent().forEach(u -> {
                ELSUser elsUser = new ELSUser(u);
                list.add(elsUser);
            });
            elsRepository.saveAll(list);
            pageNumber++;
            System.out.println(pageNumber);
        } while (!page.isLast());
        return true;
    }

    public List<ELSUser> search(Double longitude, Double latitude, Double distanceInMeters, Integer gender, Integer interestedInGender, Integer[] cityCodes, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight, Integer minHeight, Integer maxHeight, Integer minExp, Integer maxExp) {


        List<Query> filters = new ArrayList<>();

        if(latitude != null && longitude != null) {
            if(distanceInMeters != null) {
                filters.add(new Query.Builder()
                        .geoDistance(g -> g
                                .field("location")
                                .distance(distanceInMeters.toString() + "m")
                                .location(loc -> loc.latlon(l -> l.lat(latitude).lon(longitude)))
                        )
                        .build()
                );
            }
        }
        if (gender != null) {
            filters.add(new Query.Builder()
                    .term(t -> t.field("gender").value(gender))
                    .build());
        }
        if (interestedInGender != null) {
            filters.add(new Query.Builder()
                    .term(t -> t.field("interestedInGender").value(FieldValue.of(interestedInGender)))
                    .build());
        }
        if (cityCodes != null && cityCodes.length > 0) {
            filters.add(new Query.Builder()
                    .terms(t -> t.field("cityCode")
                            .terms(val -> val.value(
                                    Arrays.stream(cityCodes)
                                            .map(FieldValue::of)
                                            .toList()
                            ))
                    )
                    .build());
        }
        if (minAge != null) {
            filters.add(new Query.Builder()
                    .range(r -> r.number(nr -> nr
                            .field("age")
                            .gte(Double.valueOf(minAge)))
                    )
                    .build());
        }

        if (maxAge != null) {
            filters.add(new Query.Builder()
                    .range(r -> r.number(nr -> nr
                            .field("age")
                            .lte(Double.valueOf(maxAge)))
                    )
                    .build());
        }

        if (minWeight != null) {
            filters.add(new Query.Builder()
                    .range(r -> r.number(nr -> nr
                            .field("weight")
                            .gte(Double.valueOf(minWeight)))
                    )
                    .build());
        }

        if (maxWeight != null) {
            filters.add(new Query.Builder()
                    .range(r -> r.number(nr -> nr
                            .field("weight")
                            .lte(Double.valueOf(maxWeight)))
                    )
                    .build());
        }
        if (minHeight != null) {
            filters.add(new Query.Builder()
                    .range(r -> r.number(nr -> nr
                            .field("height")
                            .gte(Double.valueOf(minHeight)))
                    )
                    .build());
        }

        if (maxHeight != null) {
            filters.add(new Query.Builder()
                    .range(r -> r.number(nr -> nr
                            .field("height")
                            .lte(Double.valueOf(maxHeight)))
                    )
                    .build());
        }
        if (minExp != null) {
            filters.add(new Query.Builder()
                    .range(r -> r.number(nr -> nr
                            .field("expirience")
                            .gte(Double.valueOf(minExp)))
                    )
                    .build());
        }
        if (maxExp != null) {
            filters.add(new Query.Builder()
                    .range(r -> r.number(nr -> nr
                            .field("expirience")
                            .lte(Double.valueOf(maxExp)))
                    )
                    .build());
        }


        Query query = new Query.Builder()
                .bool(b -> b.filter(filters))
                .build();

        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(query)
                .withMaxResults(20)
                .build();
        SearchHits<ELSUser> searchHits = elasticsearchOperations.search(nativeQuery, ELSUser.class);

        System.out.println(nativeQuery.getQuery());
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

    }



}
