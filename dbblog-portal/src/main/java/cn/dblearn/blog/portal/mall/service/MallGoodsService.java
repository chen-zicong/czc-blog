package cn.dblearn.blog.portal.mall.service;

import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.PageResult;
import cn.dblearn.blog.entity.mall.MallGoods;

public interface MallGoodsService {


    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    MallGoods getNewBeeMallGoodsById(Long id);



    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil);
}
