package com.wangshuai.service.imp;

import com.github.pagehelper.PageHelper;
import com.wangshuai.bean.Brand;
import com.wangshuai.bean.PageRequest;
import com.wangshuai.dao.IBrandDao;
import com.wangshuai.enum1.ExceptionEnum;
import com.wangshuai.exception.LyException;
import com.wangshuai.service.IBrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class BrandService  implements IBrandService  {

    @Autowired
    private IBrandDao brandDao;
    @Override
    public List<Brand> findPage(PageRequest pageRequest) throws  LyException {
       PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        Example example = new Example(Brand.class);
       if(StringUtils.isNotBlank(pageRequest.getKey())){
           Example.Criteria criteria = example.createCriteria();
           criteria.orLike("id", "%"+pageRequest.getKey()+"%");
           criteria.orLike("name", "%"+pageRequest.getKey()+"%");
           criteria.orEqualTo("letter", pageRequest.getKey().toUpperCase());
       }
        if (StringUtils.isNotBlank(pageRequest.getSortBy())) {
            example.setOrderByClause(pageRequest.getSortBy()+(pageRequest.isDes()?" desc":" asc"));
        }
        List<Brand> brands = brandDao.selectByExample(example);
        if (CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brands;
    }

    @Override
    public void saveBrand(Brand brand, List<Long> cids) {
        brand.setId(null);

        int count = brandDao.insert(brand);
        if (count != 1) {
            throw new LyException(ExceptionEnum.BRAND_SAVE_FAILURE);
        }
        for (Long cid : cids) {
            count = brandDao.saveBrand(cid, brand.getId());
            if (count != 1) {
                throw new LyException(ExceptionEnum.BRAND_SAVE_FAILURE);
            }
        }



    }

    @Override
    public Brand findByBid(Long bid) {
        Brand brand = brandDao.selectByPrimaryKey(bid);
        if (brand == null) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);

        }
        return brand;
    }

    @Override
    public List<Brand> findByCid(Long cid) {
        List<Brand> brands = brandDao.findBrandByCid(cid);
        if (CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brands;
    }
}
