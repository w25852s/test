package com.wangshuai.repository;

import com.wangshuai.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsRepository  extends ElasticsearchRepository<Goods,Long> {
}
