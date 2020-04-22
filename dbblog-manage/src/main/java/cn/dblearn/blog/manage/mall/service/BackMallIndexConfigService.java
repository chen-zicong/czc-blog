package cn.dblearn.blog.manage.mall.service;



import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.PageResult;
import cn.dblearn.blog.entity.mall.IndexConfig;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallIndexConfigGoodsVO;

import java.util.List;

public interface BackMallIndexConfigService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getConfigsPage(PageQueryUtil pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);



    Boolean deleteBatch(Long[] ids);
}
