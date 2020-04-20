package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.entity.mall.TbNewbeeMallCarousel;

public interface TbNewbeeMallCarouselMapper {
    int deleteByPrimaryKey(Integer carouselId);

    int insert(TbNewbeeMallCarousel record);

    int insertSelective(TbNewbeeMallCarousel record);

    TbNewbeeMallCarousel selectByPrimaryKey(Integer carouselId);

    int updateByPrimaryKeySelective(TbNewbeeMallCarousel record);

    int updateByPrimaryKey(TbNewbeeMallCarousel record);
}