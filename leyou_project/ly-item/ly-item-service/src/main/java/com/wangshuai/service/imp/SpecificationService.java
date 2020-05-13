package com.wangshuai.service.imp;

import com.wangshuai.bean.SpecGroup;
import com.wangshuai.bean.SpecParams;
import com.wangshuai.dao.ISpecGroupDao;
import com.wangshuai.dao.ISpecParamsDao;
import com.wangshuai.enum1.ExceptionEnum;
import com.wangshuai.exception.LyException;
import com.wangshuai.service.ISpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class SpecificationService  implements ISpecificationService {

    @Autowired
    private ISpecGroupDao specGroupDao;

    @Autowired
    private ISpecParamsDao specParamsDao;
    @Override
    public List<SpecGroup> findSpecByCid(Integer cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(Long.valueOf(cid));
        List<SpecGroup> groups = specGroupDao.select(specGroup);
        if (CollectionUtils.isEmpty(groups)) {
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        log.info(groups.toString());
        return groups;
    }

    @Override
    public List<SpecParams> findParams(Long gid,Long cid,Boolean searching) {
        SpecParams specParams = new SpecParams();
        specParams.setGroupId(gid);
        specParams.setCid(cid);
        specParams.setSearching(searching);
        List<SpecParams> params = specParamsDao.select(specParams);
        if (CollectionUtils.isEmpty(params)) {
            log.error("没查到参数");
            throw new LyException(ExceptionEnum.SPEC_PARAMS_NOT_FOUND);
        }
        return params;
    }

    @Override
    @Transactional
    public void saveParam(SpecParams specParams) {
        int row = specParamsDao.insert(specParams);
        if (row != 1) {
            throw new LyException(ExceptionEnum.PARAMS_SAVE_FAILURE);
        }
    }

    @Override
    public void saveSpecGroup(SpecGroup specGroup) {
        specGroup.setId(null);
        int row = specGroupDao.insert(specGroup);
        if (row != 1) {
            throw new LyException(ExceptionEnum.SPEC_GROUP_SAVE_FAILURE);
        }

    }


}
