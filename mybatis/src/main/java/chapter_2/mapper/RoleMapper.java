package chapter_2.mapper;

import chapter_2.po.Role;

public interface RoleMapper {
    //接口的方法要和XML映射文件的id保持一致
    public Role getRole(Long id);
    public int deleteRole(Long id);
    public int insertRole(Role role);
}
