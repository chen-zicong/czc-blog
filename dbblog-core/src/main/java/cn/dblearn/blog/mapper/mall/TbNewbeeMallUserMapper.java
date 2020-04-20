package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.entity.mall.TbNewbeeMallUser;

public interface TbNewbeeMallUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(TbNewbeeMallUser record);

    int insertSelective(TbNewbeeMallUser record);

    TbNewbeeMallUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(TbNewbeeMallUser record);

    int updateByPrimaryKey(TbNewbeeMallUser record);
}