package cn.dblearn.blog.portal.mall.controller;

import cn.dblearn.blog.auth.controller.BaseController;
import cn.dblearn.blog.common.mall.Constants;
import cn.dblearn.blog.common.mall.ServiceResultEnum;
import cn.dblearn.blog.common.util.util.MD5Util;
import cn.dblearn.blog.common.util.util.Result;
import cn.dblearn.blog.common.util.util.ResultGenerator;
import cn.dblearn.blog.entity.mall.MallUser;
import cn.dblearn.blog.entity.mall.vo.NewBeeMallUserVO;
import cn.dblearn.blog.portal.mall.service.NewBeeMallUserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PersonalController extends BaseController {

  @Resource
  private NewBeeMallUserService newBeeMallUserService;

  @PostMapping("/personal/updateInfo")
  @ResponseBody
  public Result updateInfo(@RequestBody MallUser mallUser, HttpSession httpSession) {
    MallUser user = getUser();
    user.setPhone(mallUser.getPhone());
    user.setAddress(mallUser.getAddress());
    NewBeeMallUserVO mallUserTemp = newBeeMallUserService.updateUserInfo(mallUser, httpSession);
    if (mallUserTemp == null) {
      Result result = ResultGenerator.genFailResult("修改失败");
      return result;
    } else {
      //返回成功
      Result result = ResultGenerator.genSuccessResult(mallUserTemp);
      return result;
    }
  }


  @PostMapping("/personal/detail")
  @ResponseBody
  public Result detail() {
    MallUser user = getUser();
    NewBeeMallUserVO mallUserTemp = newBeeMallUserService.detail(user.getUserId());
    return ResultGenerator.genSuccessResult(mallUserTemp);

  }
}
