package cn.dblearn.blog.auth.config;

/**
 * <p>Title: GitHubConfig</p>
 * <p>Description: </p>
 * <p>Company: ThinkMacro</p>
 *
 * @author: chenzicong
 * @create: 2020/4/19 19:39
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "github")
@EnableConfigurationProperties(GitHubConfig.class)
@Configuration
@Data
public class GitHubConfig {
  private String clientId;
  private String clientSecret;
}
