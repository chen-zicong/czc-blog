package cn.dblearn.blog.entity.mall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>Title: CategoryWithGoods</p>
 * <p>Description: </p>
 * <p>Company: ThinkMacro</p>
 *
 * @author: chenzicong
 * @create: 2020/4/23 14:43
 */
@Data
public class CategoryWithGoods {
  /**
   * 分类id
   */
  @ApiModelProperty(value="分类id")
  private Long categoryId;

  /**
   * 分类级别(1-一级分类 2-二级分类 3-三级分类)
   */
  @ApiModelProperty(value="分类级别(1-一级分类 2-二级分类 3-三级分类)")
  private Byte categoryLevel;

  /**
   * 分类名称
   */
  @ApiModelProperty(value="分类名称")
  private String categoryName;


  List<NewBeeMallSearchGoodsVO> Goodses;

}
