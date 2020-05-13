package com.wangshuai;

import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import com.wangshuai.bean.Spu;
import com.wangshuai.client.SpuClient;
import com.wangshuai.pojo.Goods;
import com.wangshuai.repository.GoodsRepository;
import com.wangshuai.service.GoodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository repository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private GoodService goodService;

    @Test
    public void createIndex() {
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);


    }

    @Test
    public void putIndex() {
        int page=1;
        int rows=100;
        List<Goods> goods = new ArrayList<>();
        int size=0;
        do {


            PageInfo<Spu> pages = spuClient.findByPage(null, true, page, rows);
            if (pages == null) {
                break;
            }
            List<Spu> list = pages.getList();
            for (Spu spu : list) {
                Goods good = goodService.geGoods(spu);
                if (good == null) {
                    continue;
                }
                goods.add(good);
            }

            page++;
            size=list.size();


        }while(size==100);

        repository.saveAll(goods);

    }
}
