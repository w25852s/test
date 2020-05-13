package com.wangshuai.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangshuai.bean.*;
import com.wangshuai.client.*;
import com.wangshuai.enum1.ExceptionEnum;
import com.wangshuai.exception.LyException;
import com.wangshuai.pojo.Goods;
import com.wangshuai.pojo.GroupParam;
import com.wangshuai.pojo.GroupParamList;
import com.wangshuai.pojo.Param;
import com.wangshuai.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static com.wangshuai.utils.JsonUtils.nativeRead;

@Service
public class GoodService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private SpuClient spuClient;


    public Goods geGoods(Spu spu) {
        Goods goods = new Goods();
        Long brandId = spu.getBrandId();//品牌ID

        String all = "";
        all += spu.getTitle();//添加标题可搜索字段


        //所有能够查询的 信息
        List<String> names = categoryClient.findByCids(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3())).stream()
                .map(Category::getName).collect(Collectors.toList()); //搜索字段 添加 种类 字段

        Brand brand = brandClient.findBrandById(brandId);//搜索字段添加 品牌名称
        if(brand==null)
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);

        all += StringUtils.join(names, " ") + " " + brand.getName();
        List<Sku> skuList =null;
        try {
            skuList=  skuClient.findSkusById(spu.getId());
        }catch (Exception e){
            return null;
        }
        if(skuList==null)
            throw new LyException(ExceptionEnum.SKU_NOT_FOUND);
        //sku处理 只需要 id  title  price  image
        Set<Long> prices = new HashSet<>();
        List<Map<String, Object>> skuArr= new ArrayList<>();
        for (Sku s : skuList) {
            Map<String, Object> skuMap = new HashMap<>();
            skuMap.put("id", s.getId());
            skuMap.put("title", s.getTitle());
            skuMap.put("image", StringUtils.substringBefore(s.getImages(), ","));
            skuArr.add(skuMap);
            //价格set
            prices.add(s.getPrice());
        }
    


        //sku  json格式

        //
        List<SpecParams> specParams=null;
        try {
            specParams = specificationClient.findParamsByGid(null, spu.getCid3(), true);
        } catch (Exception e) {
            return null;
        }

        if(specParams==null)
            throw new LyException(ExceptionEnum.SPEC_PARAMS_NOT_FOUND);
        SpuDetail detail =null;
        try {
            detail= spuClient.getDetailById(spu.getId());
        if(detail==null)
            throw new LyException(ExceptionEnum.GOODS_DETAIL_NOT_FOUND);
    }catch (Exception e){
        return null;
    }

    //通用 规格参数
        String generigJson = detail.getGenericSpec();
        generigJson=StringUtils.substringAfter(generigJson, "[");
        generigJson = StringUtils.substringBeforeLast(generigJson, "]");
     /*   String[] testArr= generigJson.split(",\\{\"group");
        for (String s : testArr) {
            if(s.indexOf("{")!=0){
                s += "{";
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                GroupParam groupParam = mapper.readValue(s, GroupParam.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            GroupParam groupParam = mapper.readValue(generigJson, GroupParam.class);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        HashMap<String, Object> specs = new HashMap<>();
        generigJson=generigJson.replaceAll("\"group\":\"[\u4e00-\u9fa5]+\",", "");
       // generigJson=generigJson.replaceAll(",\"options\":\\[(\\w*|[\u4e00-\u9fa5]*)]", "");
        generigJson=generigJson.replaceAll("\\{\"params\":\\[\\{","");
     //   generigJson=generigJson.replaceAll("}]}","");
        ObjectMapper mapper = new ObjectMapper();
        String[] generigJsonArr=generigJson.split("},");
        for (String s : generigJsonArr) {
            s.replaceAll("]}", "");
            s.replaceAll("]", "");
            if(s.indexOf("{")!=0)
                s="{"+s;
            if(!s.endsWith("}"))
                s += "}";
            try {

                Param param = mapper.readValue(s, Param.class);
                if(param.getSearchable())
                    specs.put(param.getK(), param.getV());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //特有规格参数
        String specialJson = detail.getSpecialSpec();
        Map<String,List<String>> specialMap= JsonUtils.nativeRead(specialJson, new TypeReference<Map<String,List<String >>>() {
        });

    /*    generigJson="["+generigJson+"]";

        generigJson=generigJson.replaceAll("\"options\":\\[","\"options\":\\{");
        generigJson=generigJson.replaceAll("],","},");
     List<Map<String,List<Map<String,Object>>>> groupParams=  JsonUtils.nativeRead(generigJson, new TypeReference<List<Map<String,List<Map<String,Object>>>>>() {
        });*/
    /* for(Map<String,List<Map<String,Object>>>m :groupParams){
         for(Map<String,Object> m1:m.get("params")){
             if(m1.get("searchable").equals(true)){
                 specs.put(m1.get("k").toString(), m1.get("v"));
             }
         }
     }*/

        for (Map.Entry<String, List<String>> m : specialMap.entrySet()) {
            specs.put(m.getKey(), m.getValue());
        }
        goods.setBrandId(spu.getBrandId());
        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setAll(all);//所有 可以查询的 字符串集合
        goods.setSubTitle(spu.getSubTitle());
        goods.setSkus(JsonUtils.serialize(skuArr));//所有 sku json格式
        goods.setSpecs(specs);//所有规格参数的 json格式
        goods.setPrices(prices);
        return goods;
    }




}
