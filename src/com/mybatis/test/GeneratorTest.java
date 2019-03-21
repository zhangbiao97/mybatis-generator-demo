package com.mybatis.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.mybatis.dao.EmployeeMapper;
import com.mybatis.entity.Employee;
import com.mybatis.entity.EmployeeExample;
import com.mybatis.entity.EmployeeExample.Criteria;

/**
 *
 * @author mengbing
 * @date 2019年3月21日 下午2:42:49
 * 
 */
class GeneratorTest {

	@Test
	public void generatorTest() throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("generator.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}

	@Test
	public void test01() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			// 查询全部
			/*
			 * List<Employee> list = employeeMapper.selectByExample(null);
			 * list.forEach(System.out::println);
			 */

			// 条件查询
			EmployeeExample example = new EmployeeExample();
			Criteria criteria = example.createCriteria();
			criteria.andLastNameLike("%z%");
			criteria.andGenderEqualTo(0);
			List<Employee> list = employeeMapper.selectByExample(example);
			list.forEach(System.out::println);
		} finally {
			openSession.close();
		}
	}

}
