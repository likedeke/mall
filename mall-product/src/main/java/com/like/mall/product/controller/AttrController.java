package com.like.mall.product.controller;

import com.like.mall.common.utils.PageUtils;
import com.like.mall.common.utils.R;
import com.like.mall.product.entity.AttrEntity;
import com.like.mall.product.service.AttrService;
import com.like.mall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 商品属性
 *
 * @author like
 * @email 980650920@qq.com
 * @date 2020-10-25 11:35:49
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr){
//        attrService.save(attr);
        attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		AttrEntity attr = attrService.getById(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrEntity attr){
		attrService.updateById(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
