package cn.dblearn.blog.manage.mall.controller;


import cn.dblearn.blog.common.mall.Constants;
import cn.dblearn.blog.common.mall.NewBeeMallCategoryLevelEnum;
import cn.dblearn.blog.common.mall.ServiceResultEnum;
import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.Result;
import cn.dblearn.blog.common.util.util.ResultGenerator;
import cn.dblearn.blog.entity.mall.GoodsCategory;
import cn.dblearn.blog.entity.mall.MallGoods;
import cn.dblearn.blog.manage.mall.service.BackMallCategoryService;
import cn.dblearn.blog.manage.mall.service.BackMallGoodsService;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
* <p>Title: </p>
* <p>Description: </p>
* @author: chenzicong
* @create: 2020/4/24 11:16
*/
@Controller
@RequestMapping("/mall/admin")
@Api(tags = "后台商品管理")
public class MallGoodsController {

    @Resource
    private BackMallGoodsService newBeeMallGoodsService;



    /**
     * 列表
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页面大小", paramType = "query", dataType = "int"),
    })
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.getNewBeeMallGoodsPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加商品")
    public Result save(@RequestBody MallGoods mallGoods) {
        if (StringUtils.isEmpty(mallGoods.getGoodsName())
                || StringUtils.isEmpty(mallGoods.getGoodsIntro())
                || StringUtils.isEmpty(mallGoods.getTag())
                || Objects.isNull(mallGoods.getOriginalPrice())
                || Objects.isNull(mallGoods.getGoodsCategoryId())
                || Objects.isNull(mallGoods.getSellingPrice())
                || Objects.isNull(mallGoods.getStockNum())
                || Objects.isNull(mallGoods.getGoodsSellStatus())
                || StringUtils.isEmpty(mallGoods.getGoodsCoverImg())
                || StringUtils.isEmpty(mallGoods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallGoodsService.saveNewBeeMallGoods(mallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("修改商品")
    public Result update(@RequestBody MallGoods mallGoods) {
        if (Objects.isNull(mallGoods.getGoodsId())
                || StringUtils.isEmpty(mallGoods.getGoodsName())
                || StringUtils.isEmpty(mallGoods.getGoodsIntro())
                || StringUtils.isEmpty(mallGoods.getTag())
                || Objects.isNull(mallGoods.getOriginalPrice())
                || Objects.isNull(mallGoods.getSellingPrice())
                || Objects.isNull(mallGoods.getGoodsCategoryId())
                || Objects.isNull(mallGoods.getStockNum())
                || Objects.isNull(mallGoods.getGoodsSellStatus())
                || StringUtils.isEmpty(mallGoods.getGoodsCoverImg())
                || StringUtils.isEmpty(mallGoods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallGoodsService.updateNewBeeMallGoods(mallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    @ApiOperation("商品详情")
    public Result info(@PathVariable("id") Long id) {
        MallGoods goods = newBeeMallGoodsService.getNewBeeMallGoodsById(id);
        if (goods == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(goods);
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (newBeeMallGoodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

}