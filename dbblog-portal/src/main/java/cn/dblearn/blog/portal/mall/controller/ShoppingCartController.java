package cn.dblearn.blog.portal.mall.controller;


import cn.dblearn.blog.auth.controller.BaseController;
import cn.dblearn.blog.common.mall.Constants;
import cn.dblearn.blog.common.mall.ServiceResultEnum;
import cn.dblearn.blog.common.util.util.Result;
import cn.dblearn.blog.common.util.util.ResultGenerator;
import cn.dblearn.blog.entity.mall.NewBeeMallShoppingCartItem;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallShoppingCartItemVO;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallUserVO;
import cn.dblearn.blog.entity.mall.vo.ShoppingVo;
import cn.dblearn.blog.portal.mall.service.NewBeeMallShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ShoppingCartController  extends BaseController {

  @Resource
  private NewBeeMallShoppingCartService newBeeMallShoppingCartService;

  @GetMapping("/shop-cart")
  public Result<ShoppingVo> cartListPage(HttpServletRequest request,
                                         HttpSession httpSession) {

    int itemsTotal = 0;
    BigDecimal priceTotal = BigDecimal.ZERO;
    List<NewBeeMallShoppingCartItemVO> myShoppingCartItems = newBeeMallShoppingCartService.getMyShoppingCartItems(getUser().getUserId());
    if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
      //购物项总数
      itemsTotal = myShoppingCartItems.stream().mapToInt(NewBeeMallShoppingCartItemVO::getGoodsCount).sum();

      //总价
      for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
        priceTotal.add(BigDecimal.valueOf(newBeeMallShoppingCartItemVO.getGoodsCount()).multiply(new BigDecimal(newBeeMallShoppingCartItemVO.getSellingPrice())));
      }

    }
    ShoppingVo shoppingVo = new ShoppingVo();
    shoppingVo.setMyShoppingCartItems(myShoppingCartItems);
    shoppingVo.setItemsTotal(itemsTotal);
    shoppingVo.setPriceTotal(priceTotal.toString());

    return ResultGenerator.genSuccessResult(shoppingVo);
  }

  @PostMapping("/shop-cart")
  @ResponseBody
  public Result saveNewBeeMallShoppingCartItem(@RequestBody NewBeeMallShoppingCartItem newBeeMallShoppingCartItem) {
    newBeeMallShoppingCartItem.setUserId(getUser().getUserId());
    //todo 判断数量
    String saveResult = newBeeMallShoppingCartService.saveNewBeeMallCartItem(newBeeMallShoppingCartItem);
    //添加成功
    if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
      return ResultGenerator.genSuccessResult();
    }
    //添加失败
    return ResultGenerator.genFailResult(saveResult);
  }

  @PutMapping("/shop-cart")
  @ResponseBody
  public Result updateNewBeeMallShoppingCartItem(@RequestBody NewBeeMallShoppingCartItem newBeeMallShoppingCartItem,
                                                 HttpSession httpSession) {

    newBeeMallShoppingCartItem.setUserId(getUser().getUserId());
    //todo 判断数量
    String updateResult = newBeeMallShoppingCartService.updateNewBeeMallCartItem(newBeeMallShoppingCartItem);
    //修改成功
    if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
      return ResultGenerator.genSuccessResult();
    }
    //修改失败
    return ResultGenerator.genFailResult(updateResult);
  }

  @DeleteMapping("/shop-cart/{newBeeMallShoppingCartItemId}")
  @ResponseBody
  public Result updateNewBeeMallShoppingCartItem(@PathVariable("newBeeMallShoppingCartItemId") Long newBeeMallShoppingCartItemId,
                                                 HttpSession httpSession) {
    Boolean deleteResult = newBeeMallShoppingCartService.deleteById(newBeeMallShoppingCartItemId);
    //删除成功
    if (deleteResult) {
      return ResultGenerator.genSuccessResult();
    }
    //删除失败
    return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
  }


}
