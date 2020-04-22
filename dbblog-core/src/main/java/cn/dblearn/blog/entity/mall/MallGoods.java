package cn.dblearn.blog.entity.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@ApiModel(value = "cn-dblearn-blog-entity-mall-MallGoods")
@Data
public class MallGoods implements Serializable {
  /**
   * 商品表主键id
   */
  @ApiModelProperty(value = "商品表主键id")
  private Long goodsId;

  /**
   * 商品名
   */
  @ApiModelProperty(value = "商品名")
  private String goodsName;

  /**
   * 商品简介
   */
  @ApiModelProperty(value = "商品简介")
  private String goodsIntro;

  /**
   * 关联分类id
   */
  @ApiModelProperty(value = "关联分类id")
  private Long goodsCategoryId;

  /**
   * 商品主图
   */
  @ApiModelProperty(value = "商品主图")
  private String goodsCoverImg;

  /**
   * 商品轮播图
   */
  @ApiModelProperty(value = "商品轮播图")
  private String goodsCarousel;

  /**
   * 商品详情
   */
  @ApiModelProperty(value = "商品详情")
  private String goodsDetailContent;

  /**
   * 商品价格
   */
  @ApiModelProperty(value = "商品价格")
  private BigDecimal originalPrice;

  /**
   * 商品实际售价
   */
  @ApiModelProperty(value = "商品实际售价")
  private BigDecimal sellingPrice;

  /**
   * 商品库存数量
   */
  @ApiModelProperty(value = "商品库存数量")
  private Integer stockNum;

  /**
   * 商品标签
   */
  @ApiModelProperty(value = "商品标签")
  private String tag;

  /**
   * 商品上架状态 0-下架 1-上架
   */
  @ApiModelProperty(value = "商品上架状态 0-下架 1-上架")
  private Byte goodsSellStatus;

  /**
   * 添加者主键id
   */
  @ApiModelProperty(value = "添加者主键id")
  private Integer createUser;

  /**
   * 商品添加时间
   */
  @ApiModelProperty(value = "商品添加时间")
  private LocalDateTime createTime;

  /**
   * 修改者主键id
   */
  @ApiModelProperty(value = "修改者主键id")
  private Integer updateUser;

  /**
   * 商品修改时间
   */
  @ApiModelProperty(value = "商品修改时间")
  private LocalDateTime updateTime;

  private static final long serialVersionUID = 1L;
}