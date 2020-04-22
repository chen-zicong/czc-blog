package cn.dblearn.blog.portal.mall.service.impl;


import cn.dblearn.blog.common.mall.Constants;
import cn.dblearn.blog.common.mall.NewBeeMallCategoryLevelEnum;
import cn.dblearn.blog.common.mall.ServiceResultEnum;
import cn.dblearn.blog.common.util.util.BeanUtil;
import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.PageResult;
import cn.dblearn.blog.entity.mall.GoodsCategory;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallIndexCategoryVO;
import cn.dblearn.blog.entity.mall.vo.SearchPageCategoryVO;
import cn.dblearn.blog.entity.mall.vo.SecondLevelCategoryVO;
import cn.dblearn.blog.entity.mall.vo.ThirdLevelCategoryVO;
import cn.dblearn.blog.mapper.mall.GoodsCategoryMapper;
import cn.dblearn.blog.portal.mall.service.NewBeeMallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class NewBeeMallCategoryServiceImpl implements NewBeeMallCategoryService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;



    @Override
    public List<NewBeeMallIndexCategoryVO> getCategoriesForIndex() {
        List<NewBeeMallIndexCategoryVO> newBeeMallIndexCategoryVOS = new ArrayList<>();
        //获取一级分类的固定数量的数据
        List<GoodsCategory> firstLevelCategories = goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel(), Constants.INDEX_CATEGORY_NUMBER);
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            List<Long> firstLevelCategoryIds = firstLevelCategories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());
            //获取二级分类的数据
            List<GoodsCategory> secondLevelCategories = goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(firstLevelCategoryIds, NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel(), 0);
            if (!CollectionUtils.isEmpty(secondLevelCategories)) { ;
                //处理一级分类
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    //根据 parentId 将 thirdLevelCategories 分组
                    Map<Long, List<GoodsCategory>> secondLevelCategoryVOMap = secondLevelCategories.stream().collect(groupingBy(GoodsCategory::getParentId));
                    for (GoodsCategory firstCategory : firstLevelCategories) {
                        NewBeeMallIndexCategoryVO newBeeMallIndexCategoryVO = new NewBeeMallIndexCategoryVO();
                        BeanUtil.copyProperties(firstCategory, newBeeMallIndexCategoryVO);
                        //如果该一级分类下有数据则放入 newBeeMallIndexCategoryVOS 对象中
                        if (secondLevelCategoryVOMap.containsKey(firstCategory.getCategoryId())) {
                            //根据一级分类的id取出secondLevelCategoryVOMap分组中的二级级分类list
                            List<GoodsCategory> tempGoodsCategories = secondLevelCategoryVOMap.get(firstCategory.getCategoryId());

                            newBeeMallIndexCategoryVO.setSecondLevelCategoryVOS(BeanUtil.copyList(tempGoodsCategories, SecondLevelCategoryVO.class));
                            newBeeMallIndexCategoryVOS.add(newBeeMallIndexCategoryVO);
                        }
                    }
                }
            }

            return newBeeMallIndexCategoryVOS;
        } else {
            return null;
        }
    }

    @Override
    public SearchPageCategoryVO getCategoriesForSearch(Long categoryId) {
        SearchPageCategoryVO searchPageCategoryVO = new SearchPageCategoryVO();
        GoodsCategory thirdLevelGoodsCategory = goodsCategoryMapper.selectByPrimaryKey(categoryId);
        if (thirdLevelGoodsCategory != null && thirdLevelGoodsCategory.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            //获取当前三级分类的二级分类
            GoodsCategory secondLevelGoodsCategory = goodsCategoryMapper.selectByPrimaryKey(thirdLevelGoodsCategory.getParentId());
            if (secondLevelGoodsCategory != null && secondLevelGoodsCategory.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel()) {
                //获取当前二级分类下的三级分类List
                List<GoodsCategory> thirdLevelCategories = goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelGoodsCategory.getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel(), Constants.SEARCH_CATEGORY_NUMBER);
                searchPageCategoryVO.setCurrentCategoryName(thirdLevelGoodsCategory.getCategoryName());
                searchPageCategoryVO.setSecondLevelCategoryName(secondLevelGoodsCategory.getCategoryName());
                searchPageCategoryVO.setThirdLevelCategoryList(thirdLevelCategories);
                return searchPageCategoryVO;
            }
        }
        return null;
    }


}
