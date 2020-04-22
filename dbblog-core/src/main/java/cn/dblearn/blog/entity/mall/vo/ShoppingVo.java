package cn.dblearn.blog.entity.mall.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>Title: ShoppingVo</p>
 * <p>Description: </p>
 * <p>Company: ThinkMacro</p>
 *
 * @author: chenzicong
 * @create: 2020/4/21 16:17
 */
@Data
public class ShoppingVo {

  List<NewBeeMallShoppingCartItemVO> myShoppingCartItems;
  int itemsTotal = 0;
  int priceTotal = 0;
}
