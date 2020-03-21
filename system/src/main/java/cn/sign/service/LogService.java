package cn.sign.service;

import cn.sign.model.LogDO;
import cn.sign.model.PageDO;
import cn.sign.utils.Query;
import org.springframework.stereotype.Service;

@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
