package cn.dblearn.blog.entity.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@ApiModel(value="cn-dblearn-blog-entity-mall-TbNewbeeMallShoppingCartItem")
@Data
public class TbNewbeeMallShoppingCartItem implements Serializable {
    /**
    * 购物项主键id
    */
    @ApiModelProperty(value="购物项主键id")
    private Long cartItemId;

    /**
    * 用户主键id
    */
    @ApiModelProperty(value="用户主键id")
    private Long userId;

    /**
    * 关联商品id
    */
    @ApiModelProperty(value="关联商品id")
    private Long goodsId;

    /**
    * 数量(最大为5)
    */
    @ApiModelProperty(value="数量(最大为5)")
    private Integer goodsCount;

    /**
    * 删除标识字段(0-未删除 1-已删除)
    */
    @ApiModelProperty(value="删除标识字段(0-未删除 1-已删除)")
    private Byte isDeleted;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    /**
    * 最新修改时间
    */
    @ApiModelProperty(value="最新修改时间")
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}