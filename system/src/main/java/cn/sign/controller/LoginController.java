package cn.sign.controller;

import cn.sign.annotation.log.Log;
import cn.sign.model.LoginToken;
import cn.sign.utils.MD5Utils;
import cn.sign.utils.R;
import cn.sign.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("sys")
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * description: 登录
     * @param request
     * @param username 账号
     * @param password 密码
     * @return R
     */
    @Log("登录")
    @PostMapping("/login")
    public R login(
            HttpServletRequest request,
            @RequestParam(value = "username",required = true) String username,
            @RequestParam(value = "password",required = true) String password,
            @RequestParam(value = "loginType", required = true) String loginType) {

        //加密密码
        password = MD5Utils.encrypt(password);
        LoginToken loginToken  = new LoginToken(username, password, loginType);
        Subject subject = SecurityUtils.getSubject();
        try {
            //调用login方法进入realm
            subject.login(loginToken);
            return R.ok("登录成功");
        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error(e.getMessage());
        } catch (AuthenticationException e) {
            return R.error("账号或验证码不正确");
        }
    }

    /**
     * description: 退出登录
     * @param
     * @return R
     */
    @Log("退出登录")
    @RequestMapping("/logout")
    public R logout() {
        ShiroUtils.logout();
        return R.ok("退出登录");
    }

}
