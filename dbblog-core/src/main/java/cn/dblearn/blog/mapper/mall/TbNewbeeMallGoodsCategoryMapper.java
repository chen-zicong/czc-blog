package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.entity.mall.TbNewbeeMallGoodsCategory;

public interface TbNewbeeMallGoodsCategoryMapper {
    int deleteByPrimaryKey(Long categoryId);

    int insert(TbNewbeeMallGoodsCategory record);

    int insertSelective(TbNewbeeMallGoodsCategory record);

    TbNewbeeMallGoodsCategory selectByPrimaryKey(Long categoryId);

    int updateByPrimaryKeySelective(TbNewbeeMallGoodsCategory record);

    int updateByPrimaryKey(TbNewbeeMallGoodsCategory record);
}