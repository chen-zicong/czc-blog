package cn.dblearn.blog.manage.mall.service.impl;


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
import cn.dblearn.blog.manage.mall.service.BackMallCategoryService;
import cn.dblearn.blog.mapper.mall.GoodsCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class BackMallCategoryServiceImpl implements BackMallCategoryService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public PageResult getCategorisPage(PageQueryUtil pageUtil) {
        List<GoodsCategory> goodsCategories = goodsCategoryMapper.findGoodsCategoryList(pageUtil);
        int total = goodsCategoryMapper.getTotalGoodsCategories(pageUtil);
        PageResult pageResult = new PageResult(goodsCategories, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveCategory(GoodsCategory goodsCategory) {
        GoodsCategory temp = goodsCategoryMapper.selectByLevelAndName(goodsCategory.getCategoryLevel(), goodsCategory.getCategoryName());
        if (temp != null) {
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }
        if (goodsCategoryMapper.insertSelective(goodsCategory) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateGoodsCategory(GoodsCategory goodsCategory) {
        GoodsCategory temp = goodsCategoryMapper.selectByPrimaryKey(goodsCategory.getCategoryId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        GoodsCategory temp2 = goodsCategoryMapper.selectByLevelAndName(goodsCategory.getCategoryLevel(), goodsCategory.getCategoryName());
        if (temp2 != null && !temp2.getCategoryId().equals(goodsCategory.getCategoryId())) {
            //同名且不同id 不能继续修改
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }
        goodsCategory.setUpdateTime(LocalDateTime.now());
        if (goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public GoodsCategory getGoodsCategoryById(Long id) {
        return goodsCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除分类数据
        return goodsCategoryMapper.deleteBatch(ids) > 0;
    }


    @Override
    public List<GoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel) {
        return goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(parentIds, categoryLevel, 0);//0代表查询所有
    }


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
}
