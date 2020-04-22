package cn.dblearn.blog.manage.mall.service;

import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.PageResult;
import cn.dblearn.blog.entity.mall.MallOrder;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallOrderItemVO;

import java.util.List;

public interface BackMallOrderService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallOrdersPage(PageQueryUtil pageUtil);

    /**
     * 订单信息修改
     *
     * @param mallOrder
     * @return
     */
    String updateOrderInfo(MallOrder mallOrder);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);



    List<NewBeeMallOrderItemVO> getOrderItems(Long id);
}
