package codesmell.bookmarks.config;

import codesmell.bookmarks.dao.BookmarkDao;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    /**
     * Create the DataSource
     */
    // TODO: get configuration from properties
    @Bean
    public DataSource buildDataSource() throws Exception {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/bookmark?useLegacyDatetimeCode=false&serverTimezone=UTC");
        ds.setUsername("yoda");
        ds.setPassword("jedi123");
        return ds;
    }

    /**
     * create the MyBatis SQL Session Factory
     */
    @Bean
    public SqlSessionFactory buildSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(this.buildDataSource());
        sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return (SqlSessionFactory) sqlSessionFactory.getObject();
    }
    
    /**
     * create the MyBatis SQL Session Template
     */
    @Bean
    public SqlSessionTemplate buildSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(this.buildSqlSessionFactory());
    }

    /**
     * create the Bookmark DAO
     */
    @Bean
    public BookmarkDao buildBookmarkDao() throws Exception {
        return this.buildSqlSessionTemplate().getMapper(BookmarkDao.class);
    }
}
