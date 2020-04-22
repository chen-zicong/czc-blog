package cn.dblearn.blog.mapper.mall;

import cn.dblearn.blog.common.util.util.PageQueryUtil;import cn.dblearn.blog.entity.mall.MallUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import java.util.List;
@Mapper
public interface MallUserMapper {
  int deleteByPrimaryKey(Long userId);

  int insert(MallUser record);

  int insertSelective(MallUser record);

  MallUser selectByPrimaryKey(Long userId);

  int updateByPrimaryKeySelective(MallUser record);

  int updateByPrimaryKey(MallUser record);

  MallUser selectByLoginName(String loginName);

  MallUser selectByLoginNameAndPasswd(@Param("loginName") String loginName, @Param("password") String password);

  List<MallUser> findMallUserList(PageQueryUtil pageUtil);

  int getTotalMallUsers(PageQueryUtil pageUtil);

  int lockUserBatch(@Param("ids") Integer[] ids, @Param("lockStatus") int lockStatus);
}