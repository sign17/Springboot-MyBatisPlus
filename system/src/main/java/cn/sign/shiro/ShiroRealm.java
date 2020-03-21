package cn.sign.shiro;

import cn.sign.model.UserToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

	/**
	 * description: 授权
	 * @param arg0
	 * @return org.apache.shiro.authz.AuthorizationInfo
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {

	    //获取权限，看系统需要，例如可以根据系统菜单划分权限
		Set<String> perms = null;

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	/**
	 * description: 认证
	 * @param token
	 * @return org.apache.shiro.authc.AuthenticationInfo
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());

		//根据用户名查找数据库是否存在用户，验证密码是否正确
        // TODO

        //验证成功
		UserToken userToken = new UserToken();
		userToken.setUserId(1);
		userToken.setUsername(username);
		userToken.setName("xxx");
		userToken.setPassword(password);
		return new SimpleAuthenticationInfo(userToken, password, getName());
	}

}
