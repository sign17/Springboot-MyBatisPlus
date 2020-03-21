package cn.sign.service;

import cn.sign.dao.BaseDao;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

public class BaseService<T> extends ServiceImpl<BaseDao<T>, T> {

    public T findOne(Wrapper<T> queryWrapper) {
        List<T> list = list(queryWrapper);
        return list.stream().findFirst().orElse(null);
    }

    public Boolean remove(Integer id) {
        return removeById(id);
    }
}
