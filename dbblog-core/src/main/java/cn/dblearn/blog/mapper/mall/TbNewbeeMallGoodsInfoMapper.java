package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.entity.mall.TbNewbeeMallGoodsInfo;

public interface TbNewbeeMallGoodsInfoMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(TbNewbeeMallGoodsInfo record);

    int insertSelective(TbNewbeeMallGoodsInfo record);

    TbNewbeeMallGoodsInfo selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(TbNewbeeMallGoodsInfo record);

    int updateByPrimaryKey(TbNewbeeMallGoodsInfo record);
}