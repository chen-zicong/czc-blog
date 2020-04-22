package cn.dblearn.blog.auth.controller;

import cn.dblearn.blog.common.constants.RedisKeyConstants;
import cn.dblearn.blog.common.mall.NewBeeMallException;
import cn.dblearn.blog.common.util.RedisUtils;
import cn.dblearn.blog.entity.mall.MallUser;
import cn.dblearn.blog.entity.sys.SysUserToken;
import cn.dblearn.blog.mapper.mall.MallUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: BaseController</p>
 * <p>Description: </p>
 * <p>Company: ThinkMacro</p>
 *
 * @author: chenzicong
 * @create: 2020/4/21 17:49
 */

public class BaseController {

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private RedisUtils redisUtils;

  @Autowired
  private MallUserMapper mallUserMapper;

  public MallUser getUser() {
    //从header中获取token
    String token = request.getHeader("token");

    //如果header中不存在token，则从参数中获取token
    if (StringUtils.isEmpty(token)) {
      token = request.getParameter("token");
    }
    if (token == null) {
      throw new NewBeeMallException("未携带Token ");
    }

    String userId=redisUtils.get(RedisKeyConstants.USER_TOKEN+token);
    if(StringUtils.isEmpty(userId)){
      throw new NewBeeMallException("无效的token ");
    }
    MallUser mallUser = mallUserMapper.selectByPrimaryKey(Long.parseLong(userId));
    if(mallUser==null){
      throw new NewBeeMallException("用户不存在");
    }


    return mallUser;



  }
}
