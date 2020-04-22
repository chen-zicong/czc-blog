package cn.dblearn.blog.entity.mall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 订单详情页页面VO
 */
@Data
public class NewBeeMallOrderDetailVO implements Serializable {

    /**
     * 订单号
     */
    @ApiModelProperty(value="订单号")
    private String orderNo;

    /**
     * 订单总价
     */
    @ApiModelProperty(value="订单总价")
    private String totalPrice;

    /**
     * 支付状态:0.未支付,1.支付成功,-1:支付失败
     */
    @ApiModelProperty(value="支付状态:0.未支付,1.支付成功,-1:支付失败")
    private Byte payStatus;

    private String payStatusString;

    /**
     * 0.无 1.支付宝支付 2.微信支付
     */
    @ApiModelProperty(value="0.无 1.支付宝支付 2.微信支付")
    private Byte payType;


    private String payTypeString;

    /**
     * 支付时间
     */
    @ApiModelProperty(value="支付时间")
    private LocalDateTime payTime;

    /**
     * 订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
     */
    @ApiModelProperty(value="订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭")
    private Byte orderStatus;
    private String orderStatusString;
    /**
     * 收货人收货地址
     */
    @ApiModelProperty(value="收货人收货地址")
    private String userAddress;


    private Date createTime;

    private List<NewBeeMallOrderItemVO> newBeeMallOrderItemVOS;


}
