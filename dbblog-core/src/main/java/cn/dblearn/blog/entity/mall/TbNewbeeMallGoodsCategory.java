package cn.dblearn.blog.entity.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@ApiModel(value="cn-dblearn-blog-entity-mall-TbNewbeeMallGoodsCategory")
@Data
public class TbNewbeeMallGoodsCategory implements Serializable {
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
    * 父分类id
    */
    @ApiModelProperty(value="父分类id")
    private Long parentId;

    /**
    * 分类名称
    */
    @ApiModelProperty(value="分类名称")
    private String categoryName;

    /**
    * 排序值(字段越大越靠前)
    */
    @ApiModelProperty(value="排序值(字段越大越靠前)")
    private Integer categoryRank;

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
    * 修改时间
    */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;

    /**
    * 修改者id
    */
    @ApiModelProperty(value="修改者id")
    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}