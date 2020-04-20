package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.entity.mall.TbNewbeeMallOrderItem;

public interface TbNewbeeMallOrderItemMapper {
    int deleteByPrimaryKey(Long orderItemId);

    int insert(TbNewbeeMallOrderItem record);

    int insertSelective(TbNewbeeMallOrderItem record);

    TbNewbeeMallOrderItem selectByPrimaryKey(Long orderItemId);

    int updateByPrimaryKeySelective(TbNewbeeMallOrderItem record);

    int updateByPrimaryKey(TbNewbeeMallOrderItem record);
}