package cn.dblearn.blog.portal.mall.controller;


import cn.dblearn.blog.common.mall.Constants;
import cn.dblearn.blog.common.mall.NewBeeMallException;
import cn.dblearn.blog.common.mall.ServiceResultEnum;
import cn.dblearn.blog.common.util.util.*;
import cn.dblearn.blog.entity.mall.MallGoods;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallGoodsDetailVO;
import cn.dblearn.blog.entity.mall.vo.SearchPageCategoryVO;
import cn.dblearn.blog.portal.mall.service.NewBeeMallCategoryService;
import cn.dblearn.blog.portal.mall.service.MallGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Api(tags = "商品")
public class GoodsController {

  @Resource
  private MallGoodsService mallGoodsService;
  @Resource
  private NewBeeMallCategoryService newBeeMallCategoryService;

  @GetMapping("/search")
  @ApiOperation("搜索")
  @ResponseBody
  @ApiImplicitParams({
          @ApiImplicitParam(name = "categoryId", value = "分类id", paramType = "query", dataType = "int"),
          @ApiImplicitParam(name = "page", value = "当前页", paramType = "query", dataType = "int"),
          @ApiImplicitParam(name = "limit", value = "页面大小", paramType = "query", dataType = "int"),
  })
  public Result searchPage( Map<String, Object> params, HttpServletRequest request) {
    if (StringUtils.isEmpty(params.get("page"))) {
      params.put("page", 1);
    }
    params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
    //封装分类数据
    if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
      Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
      SearchPageCategoryVO searchPageCategoryVO = newBeeMallCategoryService.getCategoriesForSearch(categoryId);
      if (searchPageCategoryVO != null) {
        request.setAttribute("goodsCategoryId", categoryId);
        request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
      }
    }

    //封装商品数据
    PageQueryUtil pageUtil = new PageQueryUtil(params);
    PageResult pageResult = mallGoodsService.searchNewBeeMallGoods(pageUtil);
    return ResultGenerator.genSuccessResult(pageResult);
  }

  @GetMapping("/goods/detail/{goodsId}")
  @ResponseBody
  @ApiOperation(value = "商品详情")
  public Result<NewBeeMallGoodsDetailVO> detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
    if (goodsId < 1) {
      return ResultGenerator.genFailResult("无数据");
    }
    MallGoods goods = mallGoodsService.getNewBeeMallGoodsById(goodsId);
    if (goods == null) {
      NewBeeMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
    }
    if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
      NewBeeMallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
    }
    NewBeeMallGoodsDetailVO goodsDetailVO = new NewBeeMallGoodsDetailVO();
    BeanUtil.copyProperties(goods, goodsDetailVO);
    goodsDetailVO.setOriginalPrice(goods.getOriginalPrice().toString());
    goodsDetailVO.setSellingPrice(goods.getSellingPrice().toString());
    goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));

    return ResultGenerator.genSuccessResult(goodsDetailVO);
  }

}
