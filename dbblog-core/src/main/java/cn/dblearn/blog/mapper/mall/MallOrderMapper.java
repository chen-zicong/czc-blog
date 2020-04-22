package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.entity.mall.MallOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import java.util.List;
@Mapper
public interface MallOrderMapper {
  int deleteByPrimaryKey(Long orderId);

  int insert(MallOrder record);

  int insertSelective(MallOrder record);

  MallOrder selectByPrimaryKey(Long orderId);

  int updateByPrimaryKeySelective(MallOrder record);

  int updateByPrimaryKey(MallOrder record);

  MallOrder selectByOrderNo(String orderNo);

  List<MallOrder> findNewBeeMallOrderList(PageQueryUtil pageUtil);

  int getTotalNewBeeMallOrders(PageQueryUtil pageUtil);

  List<MallOrder> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

  int checkOut(@Param("orderIds") List<Long> orderIds);

  int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

  int checkDone(@Param("orderIds") List<Long> asList);
}