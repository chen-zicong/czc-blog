package cn.dblearn.blog.manage.mall.controller;


import cn.dblearn.blog.common.mall.ServiceResultEnum;
import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.Result;
import cn.dblearn.blog.common.util.util.ResultGenerator;
import cn.dblearn.blog.entity.mall.MallOrder;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallOrderItemVO;
import cn.dblearn.blog.manage.mall.service.BackMallOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("mall/admin")
@Api(tags = "后台订单管理")
public class NewBeeMallOrderController {

    @Resource
    private BackMallOrderService newBeeMallOrderService;


    /**
     * 列表
     */
    @RequestMapping(value = "/orders/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页面大小", paramType = "query", dataType = "int"),
    })
    @ApiOperation("列表")
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallOrderService.getNewBeeMallOrdersPage(pageUtil));
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/orders/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("修改")
    public Result update(@RequestBody MallOrder mallOrder) {
        if (Objects.isNull(mallOrder.getTotalPrice())
                || Objects.isNull(mallOrder.getOrderId())
                || mallOrder.getOrderId() < 1
                || mallOrder.getTotalPrice().compareTo(BigDecimal.ZERO)<=0
                || StringUtils.isEmpty(mallOrder.getUserAddress())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallOrderService.updateOrderInfo(mallOrder);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/order-items/{id}")
    @ResponseBody
    @ApiOperation("详情")
    public Result info(@PathVariable("id") Long id) {
        List<NewBeeMallOrderItemVO> orderItems = newBeeMallOrderService.getOrderItems(id);
        if (!CollectionUtils.isEmpty(orderItems)) {
            return ResultGenerator.genSuccessResult(orderItems);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

    /**
     * 配货
     */
    @RequestMapping(value = "/orders/checkDone", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("配货")
    public Result checkDone(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallOrderService.checkDone(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 出库
     */
    @RequestMapping(value = "/orders/checkOut", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("出库")
    public Result checkOut(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallOrderService.checkOut(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 关闭订单
     */
    @RequestMapping(value = "/orders/close", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("关闭订单")
    public Result closeOrder(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallOrderService.closeOrder(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


}