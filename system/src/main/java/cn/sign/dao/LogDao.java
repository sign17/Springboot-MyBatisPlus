package cn.sign.dao;

import java.util.List;
import java.util.Map;

import cn.sign.model.LogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 */
@Mapper
public interface LogDao {

	LogDO get(Long id);
	
	List<LogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(LogDO log);
	
	int update(LogDO log);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
