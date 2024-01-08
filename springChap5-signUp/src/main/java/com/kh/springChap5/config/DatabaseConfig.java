package com.kh.springChap5.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

// DB에 테이블이 없을 경우 테이블 생성
@Configuration
public class DatabaseConfig {
	/*
	 DataSourceInitializer 객체는 DB 초기화를 수행할 수 있는 데이터 소스와 Populator 설정해주는 것이 작성되어 있음.
	 ResourceDatabasePopulator : DB를 초기화 하는 데 사용
	 addScript : DB를 초기화하고 추가할 스크립트 파일 추가
	 sql/create-members-table.sql : sql에 추가할 DDL문을 작성해서 넣어준 것
	 */
	@Bean // SQL을 관리해줌. 
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator());
		return initializer;
	}
	private ResourceDatabasePopulator databasePopulator() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		//if () {
			populator.addScript(new ClassPathResource("sql/create-members-table.sql"));
		//}
		return populator;
	}
}
