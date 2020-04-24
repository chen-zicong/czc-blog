package cn.dblearn.blog.portal.mall.controller;


import cn.dblearn.blog.common.mall.Constants;
import cn.dblearn.blog.common.mall.IndexConfigTypeEnum;
import cn.dblearn.blog.common.util.util.*;
import cn.dblearn.blog.entity.mall.vo.*;
import cn.dblearn.blog.portal.mall.service.MallGoodsService;
import cn.dblearn.blog.portal.mall.service.NewBeeMallCarouselService;
import cn.dblearn.blog.portal.mall.service.NewBeeMallCategoryService;
import cn.dblearn.blog.portal.mall.service.NewBeeMallIndexConfigService;
import cn.hutool.core.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "首页数据")
public class lIndexController {

  @Resource
  private NewBeeMallCarouselService newBeeMallCarouselService;

  @Resource
  private NewBeeMallIndexConfigService newBeeMallIndexConfigService;

  @Resource
  private NewBeeMallCategoryService newBeeMallCategoryService;

  @Resource
  private MallGoodsService mallGoodsService;

  @GetMapping({"/index"})
  @ApiOperation(value = "首页数据")
  public Result<IndexVo> indexPage() {
    List<NewBeeMallIndexCategoryVO> categories = newBeeMallCategoryService.getCategoriesForIndex();
    if (CollectionUtils.isEmpty(categories)) {
      return ResultGenerator.genFailResult("无数据");
    }
    List<NewBeeMallIndexCarouselVO> carousels = newBeeMallCarouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);





    List<CategoryWithGoods> categoryWithGoods = BeanUtil.copyList(categories, CategoryWithGoods.class);
    Map<String, Object> querys = new HashMap<>();
    querys.put("page", 1);
    querys.put("limit", 8);

    for (CategoryWithGoods categoryWithGood : categoryWithGoods) {
      querys.put("goodsCategoryId", categoryWithGood.getCategoryId());
      PageResult pageResult = mallGoodsService.searchNewBeeMallGoods(new PageQueryUtil(querys));
      categoryWithGood.setGoodses((List<NewBeeMallSearchGoodsVO>) pageResult.getList());
    }

    IndexVo indexVo = new IndexVo();
    indexVo.setCarousels(carousels);
    //  indexVo.setCategories(categories);
    indexVo.setCategoryWithGoods(categoryWithGoods);
    return ResultGenerator.genSuccessResult(indexVo);
  }
}
