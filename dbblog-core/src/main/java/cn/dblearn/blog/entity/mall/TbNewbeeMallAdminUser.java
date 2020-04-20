package cn.dblearn.blog.entity.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@ApiModel(value="cn-dblearn-blog-entity-mall-TbNewbeeMallAdminUser")
@Data
public class TbNewbeeMallAdminUser implements Serializable {
    /**
    * 管理员id
    */
    @ApiModelProperty(value="管理员id")
    private Integer adminUserId;

    /**
    * 管理员登陆名称
    */
    @ApiModelProperty(value="管理员登陆名称")
    private String loginUserName;

    /**
    * 管理员登陆密码
    */
    @ApiModelProperty(value="管理员登陆密码")
    private String loginPassword;

    /**
    * 管理员显示昵称
    */
    @ApiModelProperty(value="管理员显示昵称")
    private String nickName;

    /**
    * 是否锁定 0未锁定 1已锁定无法登陆
    */
    @ApiModelProperty(value="是否锁定 0未锁定 1已锁定无法登陆")
    private Byte locked;

    private static final long serialVersionUID = 1L;
}