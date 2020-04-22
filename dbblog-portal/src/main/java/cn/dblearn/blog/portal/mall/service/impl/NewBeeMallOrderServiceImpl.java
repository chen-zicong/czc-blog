package cn.dblearn.blog.portal.mall.service.impl;


import cn.dblearn.blog.common.mall.*;
import cn.dblearn.blog.common.util.util.BeanUtil;
import cn.dblearn.blog.common.util.util.NumberUtil;
import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.PageResult;
import cn.dblearn.blog.entity.mall.NewBeeMallGoods;
import cn.dblearn.blog.entity.mall.NewBeeMallOrder;
import cn.dblearn.blog.entity.mall.NewBeeMallOrderItem;
import cn.dblearn.blog.entity.mall.StockNumDTO;
import cn.dblearn.blog.entity.mall.vo.*;
import cn.dblearn.blog.mapper.mall.NewBeeMallGoodsMapper;
import cn.dblearn.blog.mapper.mall.NewBeeMallOrderItemMapper;
import cn.dblearn.blog.mapper.mall.NewBeeMallOrderMapper;
import cn.dblearn.blog.mapper.mall.NewBeeMallShoppingCartItemMapper;
import cn.dblearn.blog.portal.mall.service.NewBeeMallOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class NewBeeMallOrderServiceImpl implements NewBeeMallOrderService {

    @Autowired
    private NewBeeMallOrderMapper newBeeMallOrderMapper;
    @Autowired
    private NewBeeMallOrderItemMapper newBeeMallOrderItemMapper;
    @Autowired
    private NewBeeMallShoppingCartItemMapper newBeeMallShoppingCartItemMapper;
    @Autowired
    private NewBeeMallGoodsMapper newBeeMallGoodsMapper;





    @Override
    public NewBeeMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (newBeeMallOrder != null) {
            //todo 验证是否是当前userId下的订单，否则报错
            List<NewBeeMallOrderItem> orderItems = newBeeMallOrderItemMapper.selectByOrderId(newBeeMallOrder.getOrderId());
            //获取订单项数据
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<NewBeeMallOrderItemVO> newBeeMallOrderItemVOS = BeanUtil.copyList(orderItems, NewBeeMallOrderItemVO.class);
                NewBeeMallOrderDetailVO newBeeMallOrderDetailVO = new NewBeeMallOrderDetailVO();
                BeanUtil.copyProperties(newBeeMallOrder, newBeeMallOrderDetailVO);
                newBeeMallOrderDetailVO.setOrderStatusString(NewBeeMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(newBeeMallOrderDetailVO.getOrderStatus()).getName());
                newBeeMallOrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(newBeeMallOrderDetailVO.getPayType()).getName());
                newBeeMallOrderDetailVO.setNewBeeMallOrderItemVOS(newBeeMallOrderItemVOS);
                return newBeeMallOrderDetailVO;
            }
        }
        return null;
    }

    @Override
    public NewBeeMallOrder getNewBeeMallOrderByOrderNo(String orderNo) {
        return newBeeMallOrderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = newBeeMallOrderMapper.getTotalNewBeeMallOrders(pageUtil);
        List<NewBeeMallOrder> newBeeMallOrders = newBeeMallOrderMapper.findNewBeeMallOrderList(pageUtil);
        List<NewBeeMallOrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            //数据转换 将实体类转成vo
            orderListVOS = BeanUtil.copyList(newBeeMallOrders, NewBeeMallOrderListVO.class);
            //设置订单状态中文显示值
            for (NewBeeMallOrderListVO newBeeMallOrderListVO : orderListVOS) {
                newBeeMallOrderListVO.setOrderStatusString(NewBeeMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(newBeeMallOrderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = newBeeMallOrders.stream().map(NewBeeMallOrder::getOrderId).collect(Collectors.toList());
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
        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (newBeeMallOrder != null) {
            //todo 验证是否是当前userId下的订单，否则报错
            //todo 订单状态判断
            if (newBeeMallOrderMapper.closeOrder(Collections.singletonList(newBeeMallOrder.getOrderId()), NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String finishOrder(String orderNo, Long userId) {
        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (newBeeMallOrder != null) {
            //todo 验证是否是当前userId下的订单，否则报错
            //todo 订单状态判断
            newBeeMallOrder.setOrderStatus((byte) NewBeeMallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
            newBeeMallOrder.setUpdateTime(LocalDateTime.now());
            if (newBeeMallOrderMapper.updateByPrimaryKeySelective(newBeeMallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String paySuccess(String orderNo, int payType) {
        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (newBeeMallOrder != null) {
            //todo 订单状态判断 非待支付状态下不进行修改操作
            newBeeMallOrder.setOrderStatus((byte) NewBeeMallOrderStatusEnum.OREDER_PAID.getOrderStatus());
            newBeeMallOrder.setPayType((byte) payType);
            newBeeMallOrder.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            newBeeMallOrder.setPayTime(LocalDateTime.now());
            newBeeMallOrder.setUpdateTime(LocalDateTime.now());
            if (newBeeMallOrderMapper.updateByPrimaryKeySelective(newBeeMallOrder) > 0) {
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
        List<NewBeeMallGoods> newBeeMallGoods = newBeeMallGoodsMapper.selectByPrimaryKeys(goodsIds);
        //检查是否包含已下架商品
        List<NewBeeMallGoods> goodsListNotSelling = newBeeMallGoods.stream()
                .filter(newBeeMallGoodsTemp -> newBeeMallGoodsTemp.getGoodsSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(goodsListNotSelling)) {
            //goodsListNotSelling 对象非空则表示有下架商品
            NewBeeMallException.fail(goodsListNotSelling.get(0).getGoodsName() + "已下架，无法生成订单");
        }
        Map<Long, NewBeeMallGoods> newBeeMallGoodsMap = newBeeMallGoods.stream().collect(Collectors.toMap(NewBeeMallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
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
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(goodsIds) && !CollectionUtils.isEmpty(newBeeMallGoods)) {
            if (newBeeMallShoppingCartItemMapper.deleteBatch(itemIdList) > 0) {
                List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);
                int updateStockNumResult = newBeeMallGoodsMapper.updateStockNum(stockNumDTOS);
                if (updateStockNumResult < 1) {
                    NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                //生成订单号
                String orderNo = NumberUtil.genOrderNo();
                int priceTotal = 0;
                //保存订单
                NewBeeMallOrder newBeeMallOrder = new NewBeeMallOrder();
                newBeeMallOrder.setOrderNo(orderNo);
                newBeeMallOrder.setUserId(user.getUserId());
                newBeeMallOrder.setUserAddress(user.getAddress());
                //总价
                for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
                    priceTotal += newBeeMallShoppingCartItemVO.getGoodsCount() * newBeeMallShoppingCartItemVO.getSellingPrice();
                }
                if (priceTotal < 1) {
                    NewBeeMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                newBeeMallOrder.setTotalPrice(priceTotal);
                //todo 订单body字段，用来作为生成支付单描述信息，暂时未接入第三方支付接口，故该字段暂时设为空字符串
                String extraInfo = "";
                newBeeMallOrder.setExtraInfo(extraInfo);
                //生成订单项并保存订单项纪录
                if (newBeeMallOrderMapper.insertSelective(newBeeMallOrder) > 0) {
                    //生成所有的订单项快照，并保存至数据库
                    List<NewBeeMallOrderItem> newBeeMallOrderItems = new ArrayList<>();
                    for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
                        NewBeeMallOrderItem newBeeMallOrderItem = new NewBeeMallOrderItem();
                        //使用BeanUtil工具类将newBeeMallShoppingCartItemVO中的属性复制到newBeeMallOrderItem对象中
                        BeanUtil.copyProperties(newBeeMallShoppingCartItemVO, newBeeMallOrderItem);
                        //NewBeeMallOrderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以获取到
                        newBeeMallOrderItem.setOrderId(newBeeMallOrder.getOrderId());
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