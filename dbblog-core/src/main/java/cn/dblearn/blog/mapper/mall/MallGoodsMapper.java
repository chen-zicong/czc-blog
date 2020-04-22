package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.common.util.util.PageQueryUtil;import cn.dblearn.blog.entity.mall.MallGoods;import cn.dblearn.blog.entity.mall.StockNumDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import java.util.List;
@Mapper
public interface MallGoodsMapper {
  int deleteByPrimaryKey(Long goodsId);

  int insert(MallGoods record);

  int insertSelective(MallGoods record);

  MallGoods selectByPrimaryKey(Long goodsId);

  int updateByPrimaryKeySelective(MallGoods record);

  int updateByPrimaryKey(MallGoods record);

  int updateByPrimaryKeyWithBLOBs(MallGoods record);

  List<MallGoods> findNewBeeMallGoodsList(PageQueryUtil pageUtil);

  int getTotalNewBeeMallGoods(PageQueryUtil pageUtil);

  List<MallGoods> selectByPrimaryKeys(List<Long> goodsIds);

  List<MallGoods> findNewBeeMallGoodsListBySearch(PageQueryUtil pageUtil);

  int getTotalNewBeeMallGoodsBySearch(PageQueryUtil pageUtil);

  int batchInsert(@Param("mallGoodsList") List<MallGoods> mallGoodsList);

  int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

  int batchUpdateSellStatus(@Param("orderIds") Long[] orderIds, @Param("sellStatus") int sellStatus);
}