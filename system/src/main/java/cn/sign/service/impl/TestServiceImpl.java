package cn.sign.service.impl;

import cn.sign.dao.TestDao;
import cn.sign.model.Student;
import cn.sign.service.BaseService;
import cn.sign.service.TestService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl extends BaseService<Student> implements TestService {
	@Autowired
	private TestDao testDao;

	@Override
	public Page<Student> list(JSONObject params) {

//		List<Student> list = list();

		Integer page = params.getInteger("page");
		Integer size = params.getInteger("size");
		QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
		page = page==null ? 0:page;
		size = size==null ? 10:size;
        queryWrapper.orderByDesc("id");
		Page<Student> pageList = page(new Page<>(page,size),queryWrapper);

		return pageList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(JSONObject data, Boolean isAdd) {
	    Student stu = new Student();
	    stu.setId(1);
	    stu.setName("测试");
	    updateById(stu);
	}

}
