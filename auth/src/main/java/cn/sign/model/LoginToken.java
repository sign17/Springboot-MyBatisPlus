package cn.sign.model;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
public class LoginToken extends UsernamePasswordToken {
    private String loginType;

    public LoginToken(String username, String password, String loginType) {
        super(username,password);
        this.loginType = loginType;
    }
}
