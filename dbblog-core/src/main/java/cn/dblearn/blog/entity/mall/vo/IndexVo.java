package cn.dblearn.blog.entity.mall.vo;

import cn.dblearn.blog.common.mall.Constants;
import cn.dblearn.blog.common.mall.IndexConfigTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * <p>Title: IndexVo</p>
 * <p>Description: </p>
 * <p>Company: ThinkMacro</p>
 *
 * @author: chenzicong
 * @create: 2020/4/20 13:08
 */
@Data
public class IndexVo {

  List<NewBeeMallIndexCarouselVO> carousels;
  List<NewBeeMallIndexConfigGoodsVO> hotGoodses;
  List<NewBeeMallIndexConfigGoodsVO> newGoodses ;
  List<NewBeeMallIndexConfigGoodsVO> recommendGoodses;
  List<NewBeeMallIndexCategoryVO> categories;
}
