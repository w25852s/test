package com.wangshuai.service.imp;

import com.github.pagehelper.PageHelper;
import com.netflix.discovery.converters.Auto;
import com.wangshuai.bean.*;
import com.wangshuai.dao.*;
import com.wangshuai.enum1.ExceptionEnum;
import com.wangshuai.exception.LyException;
import com.wangshuai.service.IBrandService;
import com.wangshuai.service.ICategoryService;
import com.wangshuai.service.IGoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsService  implements IGoodsService {

    @Autowired

    private ISpuDao spuDao;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IBrandService brandService;

    @Autowired
    private ISkuDao skuDao;

    @Autowired
    private IStockDao stockDao;

    @Autowired
    private ISpuDetailDao spuDetailDao;

    @Override
    public List<Spu> findByPage(String key, Boolean saleable, Integer page, Integer rows) {
        //排序
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }

        if (saleable!=null&&saleable==false) {
            criteria.andEqualTo("saleable",saleable);
        }

        //排序
        example.setOrderByClause(" last_update_time  DESC  ");
        List<Spu> spus = spuDao.selectByExample(example);
        if (spus == null) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        //根据三级 cid 获取category
        loadBrandNameAndCategoryName(spus);

        return spus;
    }

    @Override
    @Transactional
    public void savGood(Spu spu) {
        //添加Spu
        spu.setId(null);
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());

        int row = spuDao.insert(spu);
        if(row!=1){
            throw new LyException(ExceptionEnum.GOOD_SAVE_FAILURE);
        }

        //detail
        SpuDetail detail = spu.getSpuDetail();
        detail.setSpuId(spu.getId());
        row = spuDetailDao.insert(detail);
        if(row!=1){
            throw new LyException(ExceptionEnum.GOOD_SAVE_FAILURE);
        }
        detail.setSpuId(spu.getId());
        List<Stock> stocks = new ArrayList<>();
        List<Sku> skus = spu.getSkus();
        for (Sku sku : skus) {
            sku.setId(null);
            sku.setSpuId(spu.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());

            row = skuDao.insert(sku);
            if(row!=1){
                throw new LyException(ExceptionEnum.GOOD_SAVE_FAILURE);
            }
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stocks.add(stock);
        }
         stockDao.insertList(stocks);

    }

    @Override
    public SpuDetail findDetailById(Long id) {
        SpuDetail spuDetail = spuDetailDao.selectByPrimaryKey(id);
        if(spuDetail==null)
            throw new LyException(ExceptionEnum.GOODS_DETAIL_NOT_FOUND);
        return spuDetail;
    }

    @Override
    public List<Sku> findSkusById(Long id) {
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skus = skuDao.select(sku);
        if (skus == null) {
            throw new LyException(ExceptionEnum.SKU_NOT_FOUND);
        }
        //查询每个sku的库存
        List<Long> skuIDList = skus.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stocks = stockDao.selectByIdList(skuIDList);
        if (stocks == null) {
            throw new LyException(ExceptionEnum.STOCK_NOT_FOUND);
        }
        Map<Long, Integer> stockMap = stocks.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
       /* for (Sku s : skus) {
            s.setStock(stockMap.get(s.getId()));
        }*/

       skus.forEach(s ->s.setStock(stockMap.get(s.getId())));

        return skus;
    }

    private void loadBrandNameAndCategoryName(List<Spu> spus) {
        for (Spu spu : spus) {
            List<String> names = categoryService.findByCids(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3())).stream()
                    .map(c -> c.getName()).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names, "/"));

            //获取BrandName
            Brand brand = brandService.findByBid(spu.getBrandId());
        }


    }
}
