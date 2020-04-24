package cn.dblearn.blog.auth;


import org.apache.shiro.authc.AuthenticationToken;

/**
* <p>Title: </p>
* <p>Description: </p>
* @author: chenzicong
* @create: 2020/4/24 11:14
*/
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
