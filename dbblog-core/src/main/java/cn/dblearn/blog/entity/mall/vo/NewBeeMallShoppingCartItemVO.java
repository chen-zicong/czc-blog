package cn.dblearn.blog.entity.mall.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车页面购物项VO
 */
@Data
public class NewBeeMallShoppingCartItemVO implements Serializable {

    private Long cartItemId;

    private Long goodsId;

    private Integer goodsCount;

    private String goodsName;

    private String goodsCoverImg;

    private String sellingPrice;


}
