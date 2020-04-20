package cn.dblearn.blog.entity.mall.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 首页配置商品VO
 */
public class NewBeeMallIndexConfigGoodsVO implements Serializable {

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
     * 商品实际售价
     */
    @ApiModelProperty(value="商品实际售价")
    private Integer sellingPrice;

    /**
     * 商品标签
     */
    @ApiModelProperty(value="商品标签")
    private String tag;


    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro;
    }

    public String getGoodsCoverImg() {
        return goodsCoverImg;
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        this.goodsCoverImg = goodsCoverImg;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
