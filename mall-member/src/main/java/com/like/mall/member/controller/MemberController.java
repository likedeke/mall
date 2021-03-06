package com.like.mall.member.controller;

import com.like.mall.common.utils.PageUtils;
import com.like.mall.common.utils.R;
import com.like.mall.member.entity.MemberEntity;
import com.like.mall.member.feign.CouponFeignService;
import com.like.mall.member.service.MemberService;
import com.like.mall.member.vo.UserLoginVo;
import com.like.mall.member.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 会员
 *
 * @author like
 * @email 980650920@qq.com
 * @date 2020-10-25 12:51:23
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    // 远程调用服务
    @Autowired
    private CouponFeignService couponFeignService;

    // 调运远程方法
    @RequestMapping("/coupon")
    public R testCoupon() {
        R r = couponFeignService.memberCoupons();
        return r;
    }

    @PostMapping("/login")
    public R login(@RequestBody UserLoginVo vo) {
        MemberEntity member = memberService.login(vo);
        if (member == null) {
            return R.error("登录失败");
        }
        return R.ok().put("loginUser",member);
    }


    /**
     * 注册
     *
     * @return {@link R}
     */
    @PostMapping("/register")
    public R register(@RequestBody UserRegisterVo vo) {
        try {
            memberService.register(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R
                .ok()
                .put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R
                .ok()
                .put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
