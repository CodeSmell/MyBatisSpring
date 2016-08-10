package codesmell.bookmarks.service;

import codesmell.bookmarks.Bookmark;
import codesmell.bookmarks.dao.BookmarkDao;
import com.mysql.cj.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * simple service to show how to test with mocking
 */
public class BookmarkService {
    @Autowired
    BookmarkDao dao;

    public Integer addBookmark(Bookmark bm) {
        Integer newId = null;
        if (bm != null) {
            if (!StringUtils.isNullOrEmpty(bm.getBookmarkTitle()) && !StringUtils.isNullOrEmpty(bm.getBookmarkUrl())) {
                dao.addBookmark(bm);
                newId = bm.getBookmarkId();
            }
        }
        return newId;
    }
    
    public boolean updateBookmark(Bookmark bm) {
        boolean isOK = false;
        if (bm != null) {
            if (bm.getBookmarkId() != null && 
                !StringUtils.isNullOrEmpty(bm.getBookmarkTitle()) && 
                !StringUtils.isNullOrEmpty(bm.getBookmarkUrl())) {
                isOK = dao.updateBookmark(bm);
            }
        }
        return isOK;
    }
}
