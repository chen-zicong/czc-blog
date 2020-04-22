package cn.dblearn.blog.portal.mall.service;


import cn.dblearn.blog.entity.mall.NewBeeMallShoppingCartItem;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallShoppingCartItemVO;

import java.util.List;

public interface NewBeeMallShoppingCartService {

    /**
     * 保存商品至购物车中
     *
     * @param newBeeMallShoppingCartItem
     * @return
     */
    String saveNewBeeMallCartItem(NewBeeMallShoppingCartItem newBeeMallShoppingCartItem);

    /**
     * 修改购物车中的属性
     *
     * @param newBeeMallShoppingCartItem
     * @return
     */
    String updateNewBeeMallCartItem(NewBeeMallShoppingCartItem newBeeMallShoppingCartItem);



    /**
     * 删除购物车中的商品
     *
     * @param newBeeMallShoppingCartItemId
     * @return
     */
    Boolean deleteById(Long newBeeMallShoppingCartItemId);

    /**
     * 获取我的购物车中的列表数据
     *
     * @param newBeeMallUserId
     * @return
     */
    List<NewBeeMallShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId);
}
