package cn.dblearn.blog.entity.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@ApiModel(value="cn-dblearn-blog-entity-mall-NewBeeMallOrderItem")
@Data
public class NewBeeMallOrderItem implements Serializable {
    /**
    * 订单关联购物项主键id
    */
    @ApiModelProperty(value="订单关联购物项主键id")
    private Long orderItemId;

    /**
    * 订单主键id
    */
    @ApiModelProperty(value="订单主键id")
    private Long orderId;

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

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}