package com.like.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.like.mall.common.utils.PageUtils;
import com.like.mall.product.entity.AttrGroupEntity;
import com.like.mall.product.vo.AttrGroupAndAttrVo;
import com.like.mall.product.vo.AttrGroupRelationVo;
import com.like.mall.product.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author like
 * @email 980650920@qq.com
 * @date 2020-10-25 11:35:49
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    Long delAttrAndAttrGroupRelation(List<AttrGroupRelationVo> vos);

    List<AttrGroupAndAttrVo> getAttrGroupAndAttrByCatId(Long catelogId);

    List<SpuItemAttrGroupVo> getBySpuId(@Param("spuId") Long spuId, Long catalogId);
}

