package cn.dblearn.blog.portal.mall.controller;


import cn.dblearn.blog.common.mall.Constants;
import cn.dblearn.blog.common.mall.NewBeeMallException;
import cn.dblearn.blog.common.mall.ServiceResultEnum;
import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.Result;
import cn.dblearn.blog.common.util.util.ResultGenerator;
import cn.dblearn.blog.entity.mall.MallOrder;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallOrderDetailVO;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallShoppingCartItemVO;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallUserVO;
import cn.dblearn.blog.portal.mall.service.MallOrderService;
import cn.dblearn.blog.portal.mall.service.NewBeeMallShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "订单相关接口")
public class OrderController {

    @Resource
    private NewBeeMallShoppingCartService newBeeMallShoppingCartService;
    @Resource
    private MallOrderService mallOrderService;

    @GetMapping("/orders/{orderNo}")
    @ApiOperation("订单详情")
    public Result<NewBeeMallOrderDetailVO> orderDetailPage(HttpServletRequest request, @PathVariable("orderNo") String orderNo, HttpSession httpSession) {

        NewBeeMallOrderDetailVO orderDetailVO = mallOrderService.getOrderDetailByOrderNo(orderNo, 7L);
        if (orderDetailVO == null) {
           return ResultGenerator.genFailResult("不存在的订单");
        }

        return ResultGenerator.genSuccessResult(orderDetailVO);
    }

    @GetMapping("/orders")
    @ApiOperation("订单列表")
    public Result orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        params.put("userId", user.getUserId());
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("orderPageResult", mallOrderService.getMyOrders(pageUtil));
        request.setAttribute("path", "orders");
        return ResultGenerator.genSuccessResult(mallOrderService.getMyOrders(pageUtil));

    }

    @GetMapping("/saveOrder")
    @ApiOperation("生成订单")
    public Result<String> saveOrder(HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<NewBeeMallShoppingCartItemVO> myShoppingCartItems = newBeeMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (StringUtils.isEmpty(user.getAddress().trim())) {
            //无收货地址
            NewBeeMallException.fail(ServiceResultEnum.NULL_ADDRESS_ERROR.getResult());
        }
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物车中无数据则跳转至错误页
            NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        }
        //保存订单并返回订单号
        String saveOrderResult = mallOrderService.saveOrder(user, myShoppingCartItems);
        //跳转到订单详情页
        return ResultGenerator.genSuccessResult(saveOrderResult);
    }

    @PutMapping("/orders/{orderNo}/cancel")
    @ApiOperation("取消订单")
    public Result cancelOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String cancelOrderResult = mallOrderService.cancelOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PutMapping("/orders/{orderNo}/finish")
    @ApiOperation("交易成功")
    @ResponseBody
    public Result finishOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String finishOrderResult = mallOrderService.finishOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    @GetMapping("/selectPayType")
    public String selectPayType(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MallOrder mallOrder = mallOrderService.getNewBeeMallOrderByOrderNo(orderNo);
        //todo 判断订单userId
        //todo 判断订单状态
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", mallOrder.getTotalPrice());
        return "mall/pay-select";
    }

    @GetMapping("/payPage")
    public String payOrder(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession, @RequestParam("payType") int payType) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MallOrder mallOrder = mallOrderService.getNewBeeMallOrderByOrderNo(orderNo);
        //todo 判断订单userId
        //todo 判断订单状态
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", mallOrder.getTotalPrice());
        if (payType == 1) {
            return "mall/alipay";
        } else {
            return "mall/wxpay";
        }
    }

    @GetMapping("/paySuccess")
    @ResponseBody
    public Result paySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("payType") int payType) {
        String payResult = mallOrderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }

}
