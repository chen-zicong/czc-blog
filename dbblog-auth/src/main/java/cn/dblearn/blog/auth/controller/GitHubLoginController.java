package cn.dblearn.blog.auth.controller;

import cn.dblearn.blog.auth.config.GitHubConfig;
import cn.dblearn.blog.auth.constant.GitHubRequestUrl;
import cn.dblearn.blog.auth.service.SysUserTokenService;
import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.util.JsonUtils;
import cn.dblearn.blog.common.util.util.JsonUtil;
import cn.dblearn.blog.entity.mall.MallUser;
import cn.dblearn.blog.entity.user.GitHubUserInfo;
import cn.dblearn.blog.entity.user.TokenInfo;
import cn.dblearn.blog.mapper.mall.MallUserMapper;
import cn.hutool.Hutool;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: OauthController</p>
 * <p>Description: </p>
 * <p>Company: ThinkMacro</p>
 *
 * @author: chenzicong
 * @create: 2020/4/19 19:12
 */
@Controller
@Slf4j
@RequestMapping("github")
public class GitHubLoginController {
  @Resource
  private GitHubConfig gitHubConfig;

  @Resource
  private MallUserMapper mallUserMapper;

  @Resource
  private SysUserTokenService sysUserTokenService;

  @RequestMapping("/github")
  public Map githubRedirect(Map map) {
    log.info(map.toString());
    return map;
  }


  @GetMapping("redirect")
  public String getToken(String code, ModelMap modelMap) throws JsonProcessingException {
//    String tokenBody = HttpUtil.get(GitHubRequestUrl.TOKEN
//            + "?client_id=" + gitHubConfig.getClientId()
//            + "&client_secret=" + gitHubConfig.getClientSecret()
//            + "&code=" + code
//    );
//
//    String token = tokenBody.split("&")[0].replace( "access_token=", "");
//
//    String userInfoBody = HttpUtil.get(GitHubRequestUrl.USER_INFO
//            + "?access_token=" + token);
//
//
//    GitHubUserInfo userInfo = JsonUtils.toObj(userInfoBody, GitHubUserInfo.class);
//    MallUser mallUser = new MallUser();
//
//
//    if (mallUserMapper.selectByPrimaryKey(userInfo.getId()) == null) {
//      mallUser.setNickName(userInfo.getLogin());
//      mallUser.setUserId(userInfo.getId());
//      mallUserMapper.insertSelective(mallUser);
//
//    } else {
//      mallUser.setNickName(userInfo.getLogin());
//      mallUser.setUserId(userInfo.getId());
//      mallUserMapper.updateByPrimaryKeySelective(mallUser);
//    }
//
//    String tokenForever = sysUserTokenService.createTokenForever(mallUser.getUserId());
//
//    TokenInfo tokenInfo = new TokenInfo();
//    tokenInfo.setAvatar_url(userInfo.getAvatar_url());
//    tokenInfo.setName(userInfo.getLogin());
//    tokenInfo.setToken(tokenForever);
    TokenInfo tokenInfo = new TokenInfo();
    tokenInfo.setToken("66d0395c132ac572f40d2629c4ca5efb");
    tokenInfo.setName("蔡楠");
    tokenInfo.setAvatar_url("https://c.disquscdn.com/uploads/users/32825/5551/avatar92.jpg?1555061962");

    modelMap.addAttribute("tokenInfo", JsonUtil.objToJson(tokenInfo));

    return "login";
  }


}
