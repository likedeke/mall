package com.like.mall.authserver.controller;

import cn.hutool.core.bean.BeanUtil;
import com.like.mall.authserver.Vo.UserLoginVo;
import com.like.mall.authserver.Vo.UserRegisterVo;
import com.like.mall.authserver.feign.MemberFeignService;
import com.like.mall.authserver.feign.SmsFeignService;
import com.like.mall.common.constant.AuthConstant;
import com.like.mall.common.utils.R;
import com.like.mall.common.vo.MemberVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.like.mall.common.constant.AuthConstant.LOGIN_USER;
import static com.like.mall.common.constant.AuthConstant.goIndex;

/**
 * @author like
 * @date 2020-12-14 18:37
 * @contactMe 980650920@qq.com
 * @description
 */
@Controller
public class LoginController {

    @Autowired
    private SmsFeignService smsFeignService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/sms/sendSms")
    @ResponseBody
    public R sendSms(@RequestParam("mobile") String mobile) {
        // 1.接口防刷
        String s = redisTemplate.opsForValue().get(AuthConstant.SMS_CODE_CACHE_PREFIX + mobile);

        // 60s内不能在次发送
        if (s != null && System.currentTimeMillis() - Long.parseLong(s.split("_")[1]) < 60000) {
            return R.error();
        }

        // 生成验证码
        String code = UUID.randomUUID().toString().substring(0, 5) + "_" + System.currentTimeMillis();

        // 2.验证码再次校验  sms:code:13789983260,12345   10min有效期
        redisTemplate.opsForValue().set(AuthConstant.SMS_CODE_CACHE_PREFIX + mobile, code, 10, TimeUnit.MINUTES);
        return smsFeignService.sendSms(mobile, code.substring(0, 5));
    }

    /**
     * 注册用户
     *
     * @return {@link String}
     */
    @PostMapping("/register")
    public String register(@Valid UserRegisterVo vo, BindingResult result) {

        if (result.hasErrors()) {
           return  "redirect:http://localhost:7777/reg.html";
        } else {
            // 1.校验验证码
            String code = vo.getCode();
            String mobile = vo.getMobile();
            String redisCode = redisTemplate.opsForValue().get(AuthConstant.SMS_CODE_CACHE_PREFIX + mobile);
            if (StringUtils.isNoneBlank(redisCode) && code.equals(redisCode)) {
                return  "redirect:http://localhost:7777/reg.html";
            }else {
                // 2.验证码正确
                redisTemplate.delete(AuthConstant.SMS_CODE_CACHE_PREFIX + mobile);      // 删除验证码
                // 3.调用用户服务保存用户信息
                memberFeignService.register(vo);
            }
        }
        // 重定向到登录页面
        return "redirect:/login.html";
    }

    @PostMapping("/login")
    public String login(UserLoginVo vo, HttpSession session) {

        // 远程
        R login = memberFeignService.login(vo);
        if (login == null || login.getCode() != 0) {
            return "redirect:/login.html";
        }
        MemberVo loginUser = BeanUtil.toBean(login.get(LOGIN_USER), MemberVo.class);
        session.setAttribute(LOGIN_USER,loginUser);
        return  goIndex;
    }

    @GetMapping("/login.html")
    public String loginPage(HttpSession session) {
        if (session.getAttribute(LOGIN_USER) != null) {
            return goIndex;
        }
        return "login";
    }

    @Autowired
    private MemberFeignService memberFeignService;
}
