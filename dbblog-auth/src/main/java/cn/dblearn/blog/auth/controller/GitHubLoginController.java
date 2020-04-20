package cn.dblearn.blog.auth.controller;

import cn.dblearn.blog.auth.config.GitHubConfig;
import cn.dblearn.blog.auth.constant.GitHubRequestUrl;
import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.util.JsonUtils;
import cn.dblearn.blog.entity.user.GitHubUserInfo;
import cn.hutool.Hutool;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

  @RequestMapping("/github")
  public Map githubRedirect(Map map) {
    log.info(map.toString());
    return map;
  }


  @GetMapping("redirect")
  public String getToken(String code, ModelMap modelMap) {
    String tokenBody = HttpUtil.get(GitHubRequestUrl.TOKEN
            + "?client_id=" + gitHubConfig.getClientId()
            + "&client_secret=" + gitHubConfig.getClientSecret()
            + "&code=" + code
    );

    String token = tokenBody.split("&")[0].replace("access_token=", "");

    String userInfoBody = HttpUtil.get(GitHubRequestUrl.USER_INFO
            + "?access_token=" + token);


    GitHubUserInfo userInfo = JsonUtils.toObj(userInfoBody, GitHubUserInfo.class);


    log.info(userInfo.toString());

    modelMap.addAttribute("token",userInfo.getId()+"");
    return "login";
  }

  @GetMapping("login")
  public void  login(){


  }
}
