package codesmell.bookmarks.config;

import codesmell.bookmarks.service.BookmarkService;
import org.springframework.context.annotation.Bean;

public class BookmarkConfig {
    @Bean
    public BookmarkService buildBookmarkService() {
        return new BookmarkService();
    }
}
