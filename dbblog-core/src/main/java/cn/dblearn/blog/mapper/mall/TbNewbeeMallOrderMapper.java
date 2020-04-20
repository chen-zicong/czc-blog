package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.entity.mall.TbNewbeeMallOrder;

public interface TbNewbeeMallOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(TbNewbeeMallOrder record);

    int insertSelective(TbNewbeeMallOrder record);

    TbNewbeeMallOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(TbNewbeeMallOrder record);

    int updateByPrimaryKey(TbNewbeeMallOrder record);
}