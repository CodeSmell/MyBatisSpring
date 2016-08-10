package codesmell.bookmarks.service;

import codesmell.bookmarks.dao.BookmarkDao;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;

/**
 * 
 * see MyBatisConfig class in DAO module
 *
 */
public class MockDatabaseConfig {

    @Mock
    BookmarkDao mockDAO;

    public MockDatabaseConfig() {
        MockitoAnnotations.initMocks(this);
    }

    @Bean
    public BookmarkDao buildBookmarkDao() throws Exception {
        return mockDAO;
    }
}