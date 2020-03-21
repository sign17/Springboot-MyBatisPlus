package cn.sign.dao;

import cn.sign.model.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDao extends BaseDao<Student> {

}
