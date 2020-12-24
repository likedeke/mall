package com.like.mall.cart.service;

import com.like.mall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

/**
 * @author like
 * @date 2020-12-23 20:49
 * @contactMe 980650920@qq.com
 * @description
 */
public interface CartService {

    public void addToCart(String skuId, Integer num) throws ExecutionException, InterruptedException;

    /**
     * 获取购物车中的购物项
     *
     * @param skuId sku id
     * @return {@link CartItem}
     */
    CartItem getCartItem(String skuId);
}
