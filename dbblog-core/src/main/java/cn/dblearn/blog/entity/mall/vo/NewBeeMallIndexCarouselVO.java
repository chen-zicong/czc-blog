package cn.dblearn.blog.entity.mall.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 首页轮播图VO
 */
public class NewBeeMallIndexCarouselVO implements Serializable {

    /**
     * 轮播图
     */
    @ApiModelProperty(value="轮播图")
    private String carouselUrl;

    /**
     * 点击后的跳转地址(默认不跳转)
     */
    @ApiModelProperty(value="点击后的跳转地址(默认不跳转)")
    private String redirectUrl;

    public String getCarouselUrl() {
        return carouselUrl;
    }

    public void setCarouselUrl(String carouselUrl) {
        this.carouselUrl = carouselUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
