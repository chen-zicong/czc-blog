package cn.dblearn.blog.portal.mall.controller;


import cn.dblearn.blog.common.mall.Constants;
import cn.dblearn.blog.common.mall.IndexConfigTypeEnum;
import cn.dblearn.blog.common.util.util.Result;
import cn.dblearn.blog.common.util.util.ResultGenerator;
import cn.dblearn.blog.entity.mall.vo.IndexVo;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallIndexCarouselVO;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallIndexCategoryVO;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallIndexConfigGoodsVO;
import cn.dblearn.blog.portal.mall.service.NewBeeMallCarouselService;
import cn.dblearn.blog.portal.mall.service.NewBeeMallCategoryService;
import cn.dblearn.blog.portal.mall.service.NewBeeMallIndexConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "首页数据")
public class lIndexController {

  @Resource
  private NewBeeMallCarouselService newBeeMallCarouselService;

  @Resource
  private NewBeeMallIndexConfigService newBeeMallIndexConfigService;

  @Resource
  private NewBeeMallCategoryService newBeeMallCategoryService;

  @GetMapping({"/index"})
  @ApiOperation(value = "首页数据")
  public Result<IndexVo> indexPage() {
    List<NewBeeMallIndexCategoryVO> categories = newBeeMallCategoryService.getCategoriesForIndex();
    if (CollectionUtils.isEmpty(categories)) {
      return ResultGenerator.genFailResult("无数据");
    }
    List<NewBeeMallIndexCarouselVO> carousels = newBeeMallCarouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
    List<NewBeeMallIndexConfigGoodsVO> hotGoodses = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
    List<NewBeeMallIndexConfigGoodsVO> newGoodses = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
    List<NewBeeMallIndexConfigGoodsVO> recommendGoodses = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Constants.INDEX_GOODS_RECOMMOND_NUMBER);


    IndexVo indexVo = new IndexVo();
    indexVo.setCarousels(carousels);
    indexVo.setCategories(categories);
    indexVo.setHotGoodses(hotGoodses);
    indexVo.setRecommendGoodses(recommendGoodses);
    indexVo.setNewGoodses(newGoodses);

    return ResultGenerator.genSuccessResult(indexVo);
  }
}
