package com.summation.dailysnap.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 설정 파일임을 알려주는 어노테이션
@Configuration
// MyBatis의 DAO(Mapper)를 찾아서 연결해주는 설정
@MapperScan(basePackages = "com.summation.dailysnap.dao")
// 트랜잭션(데이터베이스 작업을 안전하게 처리하는 기능)을 활성화
@EnableTransactionManagement
public class DBconfig {

    /**
     * 데이터베이스와 연결된 MyBatis의 핵심 객체를 만드는 설정
     * (MyBatis가 SQL을 실행하려면 이게 꼭 필요함)
     *
     * @param dataSource 데이터베이스 연결 정보
     * @return SqlSessionFactory (MyBatis의 핵심 설정을 가진 객체)
     * @throws Exception 설정이 잘못되면 오류 발생
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        // SqlSessionFactoryBean: MyBatis 설정을 도와주는 클래스
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        // 데이터베이스 연결 정보 설정
        sessionFactory.setDataSource(dataSource);

        // MyBatis Mapper XML 파일 위치 지정
        // Mapper XML은 SQL이 적혀 있는 파일임 (예: SELECT, INSERT 같은 쿼리)
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));

        // MyBatis 설정 완료 후 객체 반환
        return sessionFactory.getObject();
    }

    /**
     * SQL 실행과 결과 관리를 도와주는 객체를 설정
     * (SqlSessionFactory로 만든 설정을 사용해서 SQL 실행을 도와줌)
     *
     * @param sqlSessionFactory SqlSessionFactory (위에서 만든 설정 객체)
     * @return SqlSessionTemplate (SQL 실행과 관리 도구)
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        // SqlSessionTemplate: SQL 실행을 쉽게 관리해주는 도구
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}