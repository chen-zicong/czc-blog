package cn.dblearn.blog.entity.user;

import lombok.Data;
import lombok.ToString;

/**
 * <p>Title: GitHubUserInfo</p>
 * <p>Description: </p>
 * <p>Company: ThinkMacro</p>
 *
 * @author: chenzicong
 * @create: 2020/4/19 19:53
 */
@Data
@ToString
public class GitHubUserInfo {


  /**
   * login : zicong-chen
   * id : 29993254
   * node_id : MDQ6VXNlcjI5OTkzMjU0
   * avatar_url : https://avatars2.githubusercontent.com/u/29993254?v=4
   * gravatar_id :
   * url : https://api.github.com/users/zicong-chen
   * html_url : https://github.com/zicong-chen
   * followers_url : https://api.github.com/users/zicong-chen/followers
   * following_url : https://api.github.com/users/zicong-chen/following{/other_user}
   * gists_url : https://api.github.com/users/zicong-chen/gists{/gist_id}
   * starred_url : https://api.github.com/users/zicong-chen/starred{/owner}{/repo}
   * subscriptions_url : https://api.github.com/users/zicong-chen/subscriptions
   * organizations_url : https://api.github.com/users/zicong-chen/orgs
   * repos_url : https://api.github.com/users/zicong-chen/repos
   * events_url : https://api.github.com/users/zicong-chen/events{/privacy}
   * received_events_url : https://api.github.com/users/zicong-chen/received_events
   * type : User
   * site_admin : false
   * name : chenzicong
   * company : null
   * blog :
   * location : null
   * email : null
   * hireable : null
   * bio : null
   * public_repos : 14
   * public_gists : 0
   * followers : 1
   * following : 7
   * created_at : 2017-07-08T02:51:09Z
   * updated_at : 2020-04-19T06:44:02Z
   */

  private String login;
  private Long id;
  private String node_id;
  private String avatar_url;
  private String gravatar_id;
  private String url;
  private String html_url;
  private String followers_url;
  private String following_url;
  private String gists_url;
  private String starred_url;
  private String subscriptions_url;
  private String organizations_url;
  private String repos_url;
  private String events_url;
  private String received_events_url;
  private String type;
  private Boolean site_admin;
  private String name;

  private String blog;

  private Integer public_repos;
  private Integer public_gists;
  private Integer followers;
  private Integer following;
  private String created_at;
  private String updated_at;


}
