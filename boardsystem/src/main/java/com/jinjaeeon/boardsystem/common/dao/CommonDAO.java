package com.jinjaeeon.boardsystem.common.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonDAO {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	protected int insert(String id, Object param) {
		SqlSession session = sqlSessionFactory.openSession();
		int result = session.insert(id, param);
		session.commit();
		session.close();
		return result;
	}

	protected int update(String id, Object param) {
		SqlSession session = sqlSessionFactory.openSession();
		int result = session.update(id, param);
		session.commit();
		session.close();
		return result;
	}

	protected int delete(String id, Object param) {
		SqlSession session = sqlSessionFactory.openSession();
		int result = session.delete(id, param);
		session.commit();
		session.close();
		return result;
	}

	protected Object selectOne(String id, Object param) {
		SqlSession session = sqlSessionFactory.openSession();
		Object object = session.selectOne(id, param);		
		return object;
	}
	
	protected Object selectOne2(String id) {
		SqlSession session = sqlSessionFactory.openSession();
		Object object = session.selectOne(id);		
		return object;
	}

	protected List<Object> selectAll(String id, Object param) {
		SqlSession session = sqlSessionFactory.openSession();
		List<Object> list = session.selectList(id, param);
		return list;
	}

	protected List<Object> selectAll(String id) {
		SqlSession session = sqlSessionFactory.openSession();
		List<Object> list = session.selectList(id);
		return list;
	}
	
}