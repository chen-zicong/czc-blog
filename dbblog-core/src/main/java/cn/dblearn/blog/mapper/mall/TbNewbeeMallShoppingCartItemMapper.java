package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.entity.mall.TbNewbeeMallShoppingCartItem;

public interface TbNewbeeMallShoppingCartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);

    int insert(TbNewbeeMallShoppingCartItem record);

    int insertSelective(TbNewbeeMallShoppingCartItem record);

    TbNewbeeMallShoppingCartItem selectByPrimaryKey(Long cartItemId);

    int updateByPrimaryKeySelective(TbNewbeeMallShoppingCartItem record);

    int updateByPrimaryKey(TbNewbeeMallShoppingCartItem record);
}