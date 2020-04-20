package cn.dblearn.blog.auth.controller;

import cn.dblearn.blog.auth.config.GitHubConfig;
import cn.dblearn.blog.auth.constant.GitHubRequestUrl;
import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.util.JsonUtils;
import cn.dblearn.blog.entity.user.GitHubUserInfo;
import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
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
@RestController
@Slf4j
@RequestMapping("/oAuth")
public class OauthLoginController {
  @Resource
  private GitHubConfig gitHubConfig;

  @RequestMapping("/github")
  public Map githubRedirect(Map map) {
    log.info(map.toString());
    return map;
  }


  @GetMapping("login")
  public Result getToken(String code) {
    String tokenBody = HttpRequest.get(GitHubRequestUrl.TOKEN
            + "?client_id=" + gitHubConfig.getClientId()
            + "&client_secret=" + gitHubConfig.getClientSecret()
            + "&code=" + code
    ).execute().body();
    if (!tokenBody.startsWith("access_token")) {
      return Result.error("用户信息错误");
    }
    String token = tokenBody.split("&")[0].replace("access_token=", "");

    String userInfoBody = HttpRequest.get(GitHubRequestUrl.USER_INFO
            + "?access_token=" + token).execute().body();

    GitHubUserInfo userInfo = JsonUtils.toObj(userInfoBody, GitHubUserInfo.class);
    log.info(userInfo.toString());
    return Result.ok().put("userInfo",userInfo);
  }
}
