package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.entity.mall.NewBeeMallShoppingCartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface NewBeeMallShoppingCartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);

    int insert(NewBeeMallShoppingCartItem record);

    int insertSelective(NewBeeMallShoppingCartItem record);

    NewBeeMallShoppingCartItem selectByPrimaryKey(Long cartItemId);

    NewBeeMallShoppingCartItem selectByUserIdAndGoodsId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("goodsId") Long goodsId);

    List<NewBeeMallShoppingCartItem> selectByUserId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("number") int number);

    int selectCountByUserId(Long newBeeMallUserId);

    int updateByPrimaryKeySelective(NewBeeMallShoppingCartItem record);

    int updateByPrimaryKey(NewBeeMallShoppingCartItem record);

    int deleteBatch(List<Long> ids);
}