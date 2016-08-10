package codesmell.bookmarks.dao;

import codesmell.bookmarks.Bookmark;

import java.util.Collection;

public interface BookmarkDao {
    Collection<Bookmark> getAllBookmarks();
    
    Bookmark getBookmark(Integer id);
    
    boolean addBookmark(Bookmark bm);
    
    boolean updateBookmark(Bookmark bm);
    
    boolean deleteBookmark(Integer id);
}
