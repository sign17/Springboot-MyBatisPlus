package cn.sign.service;

import cn.sign.model.Student;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TestService extends IService<Student>{
	
    Page<Student> list(JSONObject params);

    void save(JSONObject data, Boolean isAdd);

    Student findOne(Wrapper<Student> queryWrapper);

    Boolean remove(Integer id);
}
