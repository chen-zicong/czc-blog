package cn.dblearn.blog.portal.mall.service;



import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.PageResult;
import cn.dblearn.blog.entity.mall.Carousel;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallIndexCarouselVO;

import java.util.List;

public interface NewBeeMallCarouselService {


    /**
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    List<NewBeeMallIndexCarouselVO> getCarouselsForIndex(int number);
}
