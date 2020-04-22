package cn.dblearn.blog.entity.mall.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品详情页VO
 */
@Data
public class NewBeeMallGoodsDetailVO implements Serializable {
    /**
     * 商品表主键id
     */
    @ApiModelProperty(value="商品表主键id")
    private Long goodsId;

    /**
     * 商品名
     */
    @ApiModelProperty(value="商品名")
    private String goodsName;

    /**
     * 商品简介
     */
    @ApiModelProperty(value="商品简介")
    private String goodsIntro;
    /**
     * 商品主图
     */
    @ApiModelProperty(value="商品主图")
    private String goodsCoverImg;
    /**
     * 商品轮播图
     */
    @ApiModelProperty(value="商品轮播图")
    private String[] goodsCarouselList;


    /**
     * 商品详情
     */
    @ApiModelProperty(value="商品详情")
    private String goodsDetailContent;

    /**
     * 商品价格
     */
    @ApiModelProperty(value="商品价格")
    private String originalPrice;

    /**
     * 商品实际售价
     */
    @ApiModelProperty(value="商品实际售价")

    private String sellingPrice;



}
