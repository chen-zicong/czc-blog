package cn.dblearn.blog.portal.mall.service.impl;


import cn.dblearn.blog.common.mall.*;
import cn.dblearn.blog.common.util.util.BeanUtil;
import cn.dblearn.blog.common.util.util.NumberUtil;
import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.PageResult;
import cn.dblearn.blog.entity.mall.MallGoods;
import cn.dblearn.blog.entity.mall.MallOrder;
import cn.dblearn.blog.entity.mall.NewBeeMallOrderItem;
import cn.dblearn.blog.entity.mall.StockNumDTO;
import cn.dblearn.blog.entity.mall.vo.*;
import cn.dblearn.blog.mapper.mall.MallGoodsMapper;
import cn.dblearn.blog.mapper.mall.NewBeeMallOrderItemMapper;
import cn.dblearn.blog.mapper.mall.MallOrderMapper;
import cn.dblearn.blog.mapper.mall.NewBeeMallShoppingCartItemMapper;
import cn.dblearn.blog.portal.mall.service.MallOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class MallOrderServiceImpl implements MallOrderService {

  @Autowired
  private MallOrderMapper mallOrderMapper;
  @Autowired
  private NewBeeMallOrderItemMapper newBeeMallOrderItemMapper;
  @Autowired
  private NewBeeMallShoppingCartItemMapper newBeeMallShoppingCartItemMapper;
  @Autowired
  private MallGoodsMapper mallGoodsMapper;


  @Override
  public NewBeeMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
    MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
    if (mallOrder != null) {
      //todo 验证是否是当前userId下的订单，否则报错
      List<NewBeeMallOrderItem> orderItems = newBeeMallOrderItemMapper.selectByOrderId(mallOrder.getOrderId());
      //获取订单项数据
      if (!CollectionUtils.isEmpty(orderItems)) {
        List<NewBeeMallOrderItemVO> newBeeMallOrderItemVOS = BeanUtil.copyList(orderItems, NewBeeMallOrderItemVO.class);
        NewBeeMallOrderDetailVO newBeeMallOrderDetailVO = new NewBeeMallOrderDetailVO();
        BeanUtil.copyProperties(mallOrder, newBeeMallOrderDetailVO);
        newBeeMallOrderDetailVO.setOrderStatusString(NewBeeMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(newBeeMallOrderDetailVO.getOrderStatus()).getName());
        newBeeMallOrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(newBeeMallOrderDetailVO.getPayType()).getName());
        newBeeMallOrderDetailVO.setPayStatusString(PayStatusEnum.getPayStatusEnumByStatus(newBeeMallOrderDetailVO.getPayStatus()).getName());
        newBeeMallOrderDetailVO.setNewBeeMallOrderItemVOS(newBeeMallOrderItemVOS);
        newBeeMallOrderDetailVO.setTotalPrice(mallOrder.getTotalPrice().toString());
        return newBeeMallOrderDetailVO;
      }
    }
    return null;
  }

  @Override
  public MallOrder getNewBeeMallOrderByOrderNo(String orderNo) {
    return mallOrderMapper.selectByOrderNo(orderNo);
  }

  @Override
  public PageResult getMyOrders(PageQueryUtil pageUtil) {
    int total = mallOrderMapper.getTotalNewBeeMallOrders(pageUtil);
    List<MallOrder> mallOrders = mallOrderMapper.findNewBeeMallOrderList(pageUtil);
    List<NewBeeMallOrderListVO> orderListVOS = new ArrayList<>();
    if (total > 0) {
      //数据转换 将实体类转成vo
      orderListVOS = BeanUtil.copyList(mallOrders, NewBeeMallOrderListVO.class);
      //设置订单状态中文显示值
      for (NewBeeMallOrderListVO newBeeMallOrderListVO : orderListVOS) {
        newBeeMallOrderListVO.setOrderStatusString(NewBeeMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(newBeeMallOrderListVO.getOrderStatus()).getName());
      }
      List<Long> orderIds = mallOrders.stream().map(MallOrder::getOrderId).collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(orderIds)) {
        List<NewBeeMallOrderItem> orderItems = newBeeMallOrderItemMapper.selectByOrderIds(orderIds);
        Map<Long, List<NewBeeMallOrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(NewBeeMallOrderItem::getOrderId));
        for (NewBeeMallOrderListVO newBeeMallOrderListVO : orderListVOS) {
          //封装每个订单列表对象的订单项数据
          if (itemByOrderIdMap.containsKey(newBeeMallOrderListVO.getOrderId())) {
            List<NewBeeMallOrderItem> orderItemListTemp = itemByOrderIdMap.get(newBeeMallOrderListVO.getOrderId());
            //将NewBeeMallOrderItem对象列表转换成NewBeeMallOrderItemVO对象列表
            List<NewBeeMallOrderItemVO> newBeeMallOrderItemVOS = BeanUtil.copyList(orderItemListTemp, NewBeeMallOrderItemVO.class);
            newBeeMallOrderListVO.setNewBeeMallOrderItemVOS(newBeeMallOrderItemVOS);
          }
        }
      }
    }
    PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
    return pageResult;
  }

  @Override
  public String cancelOrder(String orderNo, Long userId) {
    MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
    if (mallOrder != null) {
      //todo 验证是否是当前userId下的订单，否则报错
      //todo 订单状态判断
      if (mallOrderMapper.closeOrder(Collections.singletonList(mallOrder.getOrderId()), NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0) {
        return ServiceResultEnum.SUCCESS.getResult();
      } else {
        return ServiceResultEnum.DB_ERROR.getResult();
      }
    }
    return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
  }

  @Override
  public String finishOrder(String orderNo, Long userId) {
    MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
    if (mallOrder != null) {
      //todo 验证是否是当前userId下的订单，否则报错
      //todo 订单状态判断
      mallOrder.setOrderStatus((byte) NewBeeMallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
      mallOrder.setUpdateTime(LocalDateTime.now());
      if (mallOrderMapper.updateByPrimaryKeySelective(mallOrder) > 0) {
        return ServiceResultEnum.SUCCESS.getResult();
      } else {
        return ServiceResultEnum.DB_ERROR.getResult();
      }
    }
    return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
  }

  @Override
  public String paySuccess(String orderNo, int payType) {
    MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
    if (mallOrder != null) {
      //todo 订单状态判断 非待支付状态下不进行修改操作
      mallOrder.setOrderStatus((byte) NewBeeMallOrderStatusEnum.OREDER_PAID.getOrderStatus());
      mallOrder.setPayType((byte) payType);
      mallOrder.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
      mallOrder.setPayTime(LocalDateTime.now());
      mallOrder.setUpdateTime(LocalDateTime.now());
      if (mallOrderMapper.updateByPrimaryKeySelective(mallOrder) > 0) {
        return ServiceResultEnum.SUCCESS.getResult();
      } else {
        return ServiceResultEnum.DB_ERROR.getResult();
      }
    }
    return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
  }


  @Override
  @Transactional
  public String saveOrder(NewBeeMallUserVO user, List<NewBeeMallShoppingCartItemVO> myShoppingCartItems) {
    List<Long> itemIdList = myShoppingCartItems.stream().map(NewBeeMallShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
    List<Long> goodsIds = myShoppingCartItems.stream().map(NewBeeMallShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
    List<MallGoods> mallGoods = mallGoodsMapper.selectByPrimaryKeys(goodsIds);
    //检查是否包含已下架商品
    List<MallGoods> goodsListNotSelling = mallGoods.stream()
            .filter(newBeeMallGoodsTemp -> newBeeMallGoodsTemp.getGoodsSellStatus() != Constants.SELL_STATUS_UP)
            .collect(Collectors.toList());
    if (!CollectionUtils.isEmpty(goodsListNotSelling)) {
      //goodsListNotSelling 对象非空则表示有下架商品
      NewBeeMallException.fail(goodsListNotSelling.get(0).getGoodsName() + "已下架，无法生成订单");
    }
    Map<Long, MallGoods> newBeeMallGoodsMap = mallGoods.stream().collect(Collectors.toMap(MallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
    //判断商品库存
    for (NewBeeMallShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
      //查出的商品中不存在购物车中的这条关联商品数据，直接返回错误提醒
      if (!newBeeMallGoodsMap.containsKey(shoppingCartItemVO.getGoodsId())) {
        NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
      }
      //存在数量大于库存的情况，直接返回错误提醒
      if (shoppingCartItemVO.getGoodsCount() > newBeeMallGoodsMap.get(shoppingCartItemVO.getGoodsId()).getStockNum()) {
        NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
      }
    }
    //删除购物项
    if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(goodsIds) && !CollectionUtils.isEmpty(mallGoods)) {
      if (newBeeMallShoppingCartItemMapper.deleteBatch(itemIdList) > 0) {
        List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);
        int updateStockNumResult = mallGoodsMapper.updateStockNum(stockNumDTOS);
        if (updateStockNumResult < 1) {
          NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
        }
        //生成订单号
        String orderNo = NumberUtil.genOrderNo();
        BigDecimal priceTotal = BigDecimal.ZERO;
        //保存订单
        MallOrder mallOrder = new MallOrder();
        mallOrder.setOrderNo(orderNo);
        mallOrder.setUserId(user.getUserId());
        mallOrder.setUserAddress(user.getAddress());
        //总价
        for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
          priceTotal.add(new BigDecimal(newBeeMallShoppingCartItemVO.getSellingPrice()).multiply(BigDecimal.valueOf(newBeeMallShoppingCartItemVO.getGoodsCount())));
        }
        if (priceTotal.compareTo(BigDecimal.ZERO) <=0) {
          NewBeeMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
        }
        mallOrder.setTotalPrice(priceTotal);
        //todo 订单body字段，用来作为生成支付单描述信息，暂时未接入第三方支付接口，故该字段暂时设为空字符串
        String extraInfo = "";
        mallOrder.setExtraInfo(extraInfo);
        //生成订单项并保存订单项纪录
        if (mallOrderMapper.insertSelective(mallOrder) > 0) {
          //生成所有的订单项快照，并保存至数据库
          List<NewBeeMallOrderItem> newBeeMallOrderItems = new ArrayList<>();
          for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
            NewBeeMallOrderItem newBeeMallOrderItem = new NewBeeMallOrderItem();
            //使用BeanUtil工具类将newBeeMallShoppingCartItemVO中的属性复制到newBeeMallOrderItem对象中
            BeanUtil.copyProperties(newBeeMallShoppingCartItemVO, newBeeMallOrderItem);
            //NewBeeMallOrderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以获取到
            newBeeMallOrderItem.setOrderId(mallOrder.getOrderId());
            newBeeMallOrderItems.add(newBeeMallOrderItem);
          }
          //保存至数据库
          if (newBeeMallOrderItemMapper.insertBatch(newBeeMallOrderItems) > 0) {
            //所有操作成功后，将订单号返回，以供Controller方法跳转到订单详情
            return orderNo;
          }
          NewBeeMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
        }
        NewBeeMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
      }
      NewBeeMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
    }
    NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
    return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
  }
}