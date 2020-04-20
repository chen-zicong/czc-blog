package cn.dblearn.blog.entity.mall.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 订单详情页页面订单项VO
 */
public class NewBeeMallOrderItemVO implements Serializable {
    /**
     * 关联商品id
     */
    @ApiModelProperty(value="关联商品id")
    private Long goodsId;

    /**
     * 下单时商品的名称(订单快照)
     */
    @ApiModelProperty(value="下单时商品的名称(订单快照)")
    private String goodsName;

    /**
     * 下单时商品的主图(订单快照)
     */
    @ApiModelProperty(value="下单时商品的主图(订单快照)")
    private String goodsCoverImg;

    /**
     * 下单时商品的价格(订单快照)
     */
    @ApiModelProperty(value="下单时商品的价格(订单快照)")
    private Integer sellingPrice;

    /**
     * 数量(订单快照)
     */
    @ApiModelProperty(value="数量(订单快照)")
    private Integer goodsCount;

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

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }
}
