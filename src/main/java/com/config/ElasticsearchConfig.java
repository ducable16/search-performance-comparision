package com.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.converter.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new LongToLocalDateConverter());
        converters.add(new LocalDateToLongConverter());
        converters.add(new LocalDateTimeToLongConverter());
        converters.add(new LongToLocalDateTimeConverter());
        converters.add(new IntegerToLocalDateConverter());
        return new ElasticsearchCustomConversions(converters);
    }
//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate(ElasticsearchClient elasticsearchClient) {
//        return new ElasticsearchTemplate(elasticsearchClient);
//    }
//
//    @Bean
//    public ElasticsearchClient elasticsearchClient() {
//        JacksonJsonpMapper mapper = new JacksonJsonpMapper();
//        mapper.objectMapper().registerModule(new JavaTimeModule());
//
//        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
//        ElasticsearchTransport transport = new RestClientTransport(restClient, mapper);
//        return new ElasticsearchClient(transport);
//    }


}
