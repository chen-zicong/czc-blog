package cn.dblearn.blog.portal.mall.service.impl;


import cn.dblearn.blog.common.mall.Constants;
import cn.dblearn.blog.common.mall.ServiceResultEnum;
import cn.dblearn.blog.common.util.util.BeanUtil;
import cn.dblearn.blog.entity.mall.MallGoods;
import cn.dblearn.blog.entity.mall.NewBeeMallShoppingCartItem;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallShoppingCartItemVO;
import cn.dblearn.blog.mapper.mall.MallGoodsMapper;
import cn.dblearn.blog.mapper.mall.NewBeeMallShoppingCartItemMapper;
import cn.dblearn.blog.portal.mall.service.NewBeeMallShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NewBeeMallShoppingCartServiceImpl implements NewBeeMallShoppingCartService {

    @Autowired
    private NewBeeMallShoppingCartItemMapper newBeeMallShoppingCartItemMapper;

    @Autowired
    private MallGoodsMapper mallGoodsMapper;

    //todo 修改session中购物项数量

    @Override
    public String saveNewBeeMallCartItem(NewBeeMallShoppingCartItem newBeeMallShoppingCartItem) {
        NewBeeMallShoppingCartItem temp = newBeeMallShoppingCartItemMapper.selectByUserIdAndGoodsId(newBeeMallShoppingCartItem.getUserId(), newBeeMallShoppingCartItem.getGoodsId());
        if (temp != null) {
            //已存在则修改该记录
            //todo count = tempCount + 1
            temp.setGoodsCount(newBeeMallShoppingCartItem.getGoodsCount());
            return updateNewBeeMallCartItem(temp);
        }
        MallGoods mallGoods = mallGoodsMapper.selectByPrimaryKey(newBeeMallShoppingCartItem.getGoodsId());
        //商品为空
        if (mallGoods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = newBeeMallShoppingCartItemMapper.selectCountByUserId(newBeeMallShoppingCartItem.getUserId()) + 1;
        //超出单个商品的最大数量
        if (newBeeMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        //保存记录
        if (newBeeMallShoppingCartItemMapper.insertSelective(newBeeMallShoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateNewBeeMallCartItem(NewBeeMallShoppingCartItem newBeeMallShoppingCartItem) {
        NewBeeMallShoppingCartItem newBeeMallShoppingCartItemUpdate = newBeeMallShoppingCartItemMapper.selectByPrimaryKey(newBeeMallShoppingCartItem.getCartItemId());
        if (newBeeMallShoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        //超出单个商品的最大数量
        if (newBeeMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //todo 数量相同不会进行修改
        //todo userId不同不能修改
        newBeeMallShoppingCartItemUpdate.setGoodsCount(newBeeMallShoppingCartItem.getGoodsCount());
        newBeeMallShoppingCartItemUpdate.setUpdateTime(LocalDateTime.now());
        //修改记录
        if (newBeeMallShoppingCartItemMapper.updateByPrimaryKeySelective(newBeeMallShoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }



    @Override
    public Boolean deleteById(Long newBeeMallShoppingCartItemId) {
        //todo userId不同不能删除
        return newBeeMallShoppingCartItemMapper.deleteByPrimaryKey(newBeeMallShoppingCartItemId) > 0;
    }

    @Override
    public List<NewBeeMallShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId) {
        List<NewBeeMallShoppingCartItemVO> newBeeMallShoppingCartItemVOS = new ArrayList<>();
        List<NewBeeMallShoppingCartItem> newBeeMallShoppingCartItems = newBeeMallShoppingCartItemMapper.selectByUserId(newBeeMallUserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(newBeeMallShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> newBeeMallGoodsIds = newBeeMallShoppingCartItems.stream().map(NewBeeMallShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<MallGoods> mallGoods = mallGoodsMapper.selectByPrimaryKeys(newBeeMallGoodsIds);
            Map<Long, MallGoods> newBeeMallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(mallGoods)) {
                newBeeMallGoodsMap = mallGoods.stream().collect(Collectors.toMap(MallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (NewBeeMallShoppingCartItem newBeeMallShoppingCartItem : newBeeMallShoppingCartItems) {
                NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO = new NewBeeMallShoppingCartItemVO();
                BeanUtil.copyProperties(newBeeMallShoppingCartItem, newBeeMallShoppingCartItemVO);
                if (newBeeMallGoodsMap.containsKey(newBeeMallShoppingCartItem.getGoodsId())) {
                    MallGoods mallGoodsTemp = newBeeMallGoodsMap.get(newBeeMallShoppingCartItem.getGoodsId());
                    newBeeMallShoppingCartItemVO.setGoodsCoverImg(mallGoodsTemp.getGoodsCoverImg());
                    String goodsName = mallGoodsTemp.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    newBeeMallShoppingCartItemVO.setGoodsName(goodsName);
                    newBeeMallShoppingCartItemVO.setSellingPrice(mallGoodsTemp.getSellingPrice().toString());
                    newBeeMallShoppingCartItemVOS.add(newBeeMallShoppingCartItemVO);
                }
            }
        }
        return newBeeMallShoppingCartItemVOS;
    }
}
