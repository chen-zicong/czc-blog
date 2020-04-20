package cn.dblearn.blog.entity.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@ApiModel(value="cn-dblearn-blog-entity-mall-TbNewbeeMallIndexConfig")
@Data
public class TbNewbeeMallIndexConfig implements Serializable {
    /**
    * 首页配置项主键id
    */
    @ApiModelProperty(value="首页配置项主键id")
    private Long configId;

    /**
    * 显示字符(配置搜索时不可为空，其他可为空)
    */
    @ApiModelProperty(value="显示字符(配置搜索时不可为空，其他可为空)")
    private String configName;

    /**
    * 1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
    */
    @ApiModelProperty(value="1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐")
    private Byte configType;

    /**
    * 商品id 默认为0
    */
    @ApiModelProperty(value="商品id 默认为0")
    private Long goodsId;

    /**
    * 点击后的跳转地址(默认不跳转)
    */
    @ApiModelProperty(value="点击后的跳转地址(默认不跳转)")
    private String redirectUrl;

    /**
    * 排序值(字段越大越靠前)
    */
    @ApiModelProperty(value="排序值(字段越大越靠前)")
    private Integer configRank;

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
    * 创建者id
    */
    @ApiModelProperty(value="创建者id")
    private Integer createUser;

    /**
    * 最新修改时间
    */
    @ApiModelProperty(value="最新修改时间")
    private LocalDateTime updateTime;

    /**
    * 修改者id
    */
    @ApiModelProperty(value="修改者id")
    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}