package com.wangshuai.service.imp;


import com.wangshuai.bean.Category;
import com.wangshuai.dao.ICategoryDao;
import com.wangshuai.enum1.ExceptionEnum;
import com.wangshuai.exception.LyException;
import com.wangshuai.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;


import java.util.Arrays;
import java.util.List;
@Service
public class CategoryServiceImp implements ICategoryService {
    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public List<Category> findCategoryByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);

        List<Category> categories = categoryDao.select(category);
        if (categories == null) {
            throw new LyException(ExceptionEnum.SPEC_PARAMS_NOT_FOUND);
        }
        return categories;
    }

    @Override
    public List<Category> findByCids(List<Long> cids) {
        List<Category> categories = categoryDao.selectByIdList(cids);
        if (categories == null) {
            throw new LyException(ExceptionEnum.SPEC_PARAMS_NOT_FOUND);
        }
        return categories;
    }

    @Override
    public List<Category> findByParams(Long gid, Long cid, Boolean searching) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if (gid != null) {
            criteria.andEqualTo("group_id",gid);
        }
        if (cid != null) {
            criteria.andEqualTo("cid", cid);
        }
        if (searching != null) {
            criteria.andEqualTo("searching", searching);
        }

        return null;
    }
}
