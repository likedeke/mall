package com.like.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.like.mall.common.utils.PageUtils;
import com.like.mall.product.entity.CategoryEntity;
import com.like.mall.product.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author like
 * @email 980650920@qq.com
 * @date 2020-10-25 11:35:49
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenu(List<Long> asList);

    /**
     * 找到catelogId的完整路径
     * 【父/子/孙】
     * @param catelogId
     * @return
     */
    Long[] findCatelogPath(Long catelogId);

    void updateDetail(CategoryEntity category);

    List<CategoryEntity> getLevelFirstCategory();

    Map<String, List<Catelog2Vo>> getCatalogJson();
}

