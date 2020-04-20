package cn.dblearn.blog.auth.constant;

import lombok.Data;

/**
 * <p>Title: GitHubRequestUrl</p>
 * <p>Description: </p>
 * <p>Company: ThinkMacro</p>
 *
 * @author: chenzicong
 * @create: 2020/4/19 19:35
 */

public class GitHubRequestUrl {

  private static final String BASE_URL = "https://api.github.com";

  public static final String TOKEN = "https://github.com/login/oauth/access_token";

  public static final String USER_INFO = BASE_URL +"/user";
}
