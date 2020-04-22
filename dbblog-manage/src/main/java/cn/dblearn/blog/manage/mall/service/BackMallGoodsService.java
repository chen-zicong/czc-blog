package cn.dblearn.blog.manage.mall.service;

import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.PageResult;
import cn.dblearn.blog.entity.mall.MallGoods;

public interface BackMallGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveNewBeeMallGoods(MallGoods goods);



    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateNewBeeMallGoods(MallGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    MallGoods getNewBeeMallGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids, int sellStatus);


}
