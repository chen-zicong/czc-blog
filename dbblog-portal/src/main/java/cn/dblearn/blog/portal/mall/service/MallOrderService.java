package cn.dblearn.blog.portal.mall.service;

import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.PageResult;
import cn.dblearn.blog.entity.mall.MallOrder;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallOrderDetailVO;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallShoppingCartItemVO;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallUserVO;

import java.util.List;

public interface MallOrderService {


  /**
   * 获取订单详情
   *
   * @param orderNo
   * @param userId
   * @return
   */
  NewBeeMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

  /**
   * 获取订单详情
   *
   * @param orderNo
   * @return
   */
  MallOrder getNewBeeMallOrderByOrderNo(String orderNo);

  /**
   * 我的订单列表
   *
   * @param pageUtil
   * @return
   */
  PageResult getMyOrders(PageQueryUtil pageUtil);

  /**
   * 手动取消订单
   *
   * @param orderNo
   * @param userId
   * @return
   */
  String cancelOrder(String orderNo, Long userId);

  /**
   * 保存订单
   *
   * @param user
   * @param myShoppingCartItems
   * @return
   */
  String saveOrder(NewBeeMallUserVO user, List<NewBeeMallShoppingCartItemVO> myShoppingCartItems);

  /**
   * 确认收货
   *
   * @param orderNo
   * @param userId
   * @return
   */
  String finishOrder(String orderNo, Long userId);

  String paySuccess(String orderNo, int payType);

}
