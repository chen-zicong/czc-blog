package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.entity.mall.TbNewbeeMallIndexConfig;

public interface TbNewbeeMallIndexConfigMapper {
    int deleteByPrimaryKey(Long configId);

    int insert(TbNewbeeMallIndexConfig record);

    int insertSelective(TbNewbeeMallIndexConfig record);

    TbNewbeeMallIndexConfig selectByPrimaryKey(Long configId);

    int updateByPrimaryKeySelective(TbNewbeeMallIndexConfig record);

    int updateByPrimaryKey(TbNewbeeMallIndexConfig record);
}