package cn.dblearn.blog.entity.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@ApiModel(value="cn-dblearn-blog-entity-mall-TbNewbeeMallUser")
@Data
public class TbNewbeeMallUser implements Serializable {
    /**
    * 用户主键id
    */
    @ApiModelProperty(value="用户主键id")
    private Long userId;

    /**
    * 用户昵称
    */
    @ApiModelProperty(value="用户昵称")
    private String nickName;

    /**
    * 登陆名称(默认为手机号)
    */
    @ApiModelProperty(value="登陆名称(默认为手机号)")
    private String loginName;

    /**
    * MD5加密后的密码
    */
    @ApiModelProperty(value="MD5加密后的密码")
    private String passwordMd5;

    /**
    * 个性签名
    */
    @ApiModelProperty(value="个性签名")
    private String introduceSign;

    /**
    * 收货地址
    */
    @ApiModelProperty(value="收货地址")
    private String address;

    /**
    * 注销标识字段(0-正常 1-已注销)
    */
    @ApiModelProperty(value="注销标识字段(0-正常 1-已注销)")
    private Byte isDeleted;

    /**
    * 锁定标识字段(0-未锁定 1-已锁定)
    */
    @ApiModelProperty(value="锁定标识字段(0-未锁定 1-已锁定)")
    private Byte lockedFlag;

    /**
    * 注册时间
    */
    @ApiModelProperty(value="注册时间")
    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}