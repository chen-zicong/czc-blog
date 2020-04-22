package cn.dblearn.blog.portal.mall.service;



import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.PageResult;
import cn.dblearn.blog.entity.mall.GoodsCategory;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallIndexCategoryVO;
import cn.dblearn.blog.entity.mall.vo.SearchPageCategoryVO;

import java.util.List;

public interface NewBeeMallCategoryService {


    /**
     * 返回分类数据(首页调用)
     *
     * @return
     */
    List<NewBeeMallIndexCategoryVO> getCategoriesForIndex();

    /**
     * 返回分类数据(搜索页调用)
     *
     * @param categoryId
     * @return
     */
    SearchPageCategoryVO getCategoriesForSearch(Long categoryId);


}
