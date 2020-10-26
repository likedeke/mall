package com.like.mall.product.controller;

import com.like.mall.common.utils.R;
import com.like.mall.product.entity.CategoryEntity;
import com.like.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 商品三级分类
 *  使用地方：
 *      商品系统-分类维护
 * @author like
 * @email 980650920@qq.com
 * @date 2020-10-25 11:35:49
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询分类中的所有信息，并组成的tree
     */
    @RequestMapping("/list/tree")
    public R list(@RequestParam Map<String, Object> params) {
        List<CategoryEntity> entities = categoryService.listWithTree();

        return R
                .ok()
                .put("data", entities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R
                .ok()
                .put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] catIds) {
        // 1.删除标签：检查是否被引用
        categoryService.removeMenu(Arrays.asList(catIds));
        return R.ok();
    }

}
