package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.entity.mall.TbNewbeeMallAdminUser;

public interface TbNewbeeMallAdminUserMapper {
    int deleteByPrimaryKey(Integer adminUserId);

    int insert(TbNewbeeMallAdminUser record);

    int insertSelective(TbNewbeeMallAdminUser record);

    TbNewbeeMallAdminUser selectByPrimaryKey(Integer adminUserId);

    int updateByPrimaryKeySelective(TbNewbeeMallAdminUser record);

    int updateByPrimaryKey(TbNewbeeMallAdminUser record);
}