package com.jinjaeeon.boardsystem;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DBTests {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSessionFactory sqlSession;
	
	@Test
    public void testConnection() throws Exception{
        Connection conn = dataSource.getConnection(); 
        System.err.println("DataSource : "+dataSource);
        System.err.println("Connection : "+conn);
        conn.close(); 
    }

	@Test
	public void testSqlSession() { 
		System.err.println("sqlSession : " + sqlSession);
	}
	
	
}
