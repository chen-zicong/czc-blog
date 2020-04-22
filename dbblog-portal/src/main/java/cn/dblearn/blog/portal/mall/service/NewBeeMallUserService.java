package cn.dblearn.blog.portal.mall.service;



import cn.dblearn.blog.common.util.util.PageQueryUtil;
import cn.dblearn.blog.common.util.util.PageResult;
import cn.dblearn.blog.entity.mall.MallUser;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallUserVO;

import javax.servlet.http.HttpSession;

public interface NewBeeMallUserService {


    /**
     * 用户注册
     *
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);

    /**
     * 登录
     *
     * @param loginName
     * @param passwordMD5
     * @param httpSession
     * @return
     */
    String login(String loginName, String passwordMD5, HttpSession httpSession);

    /**
     * 用户信息修改并返回最新的用户信息
     *
     * @param mallUser
     * @return
     */
    NewBeeMallUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession);

    NewBeeMallUserVO detail(Long id);


}
