package codesmell.bookmarks.service;

import codesmell.bookmarks.Bookmark;
import codesmell.bookmarks.config.BookmarkConfig;
import codesmell.bookmarks.dao.BookmarkDao;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BookmarkConfig.class, MockDatabaseConfig.class })
public class BookmarkServiceTest {

    @Autowired
    BookmarkDao mockDao;
    
    @Autowired
    BookmarkService bmSvc;

    @After
    public void cleanup() {
        Mockito.reset(mockDao);
    }
    
    @Test(expected = RuntimeException.class)
    public void test_add_with_exception() {
        Mockito.when(mockDao.addBookmark(Mockito.any(Bookmark.class))).thenThrow(new RuntimeException("test"));

        Bookmark bm = new Bookmark(null, "Kanban", "https://en.wikipedia.org/wiki/Kanban");
        bmSvc.addBookmark(bm);
        
        Mockito.verify(mockDao, Mockito.times(1)).addBookmark(Mockito.any(Bookmark.class));
        Mockito.verify(mockDao, Mockito.times(0)).updateBookmark(Mockito.any(Bookmark.class));
        Mockito.verify(mockDao, Mockito.times(0)).deleteBookmark(Mockito.anyInt());
        Mockito.verify(mockDao, Mockito.times(0)).getBookmark(Mockito.anyInt());
    }
    
    @Test
    public void test_add_success() {
        Mockito.when(mockDao.addBookmark(Mockito.any(Bookmark.class))).thenAnswer(new AddBookMarkAnswer());

        Bookmark bm = new Bookmark(null, "Kanban", "https://en.wikipedia.org/wiki/Kanban");
        Integer id = bmSvc.addBookmark(bm);
        assertNotNull(id);
        assertEquals(new Integer(1), id);
        
        Mockito.verify(mockDao, Mockito.times(1)).addBookmark(Mockito.any(Bookmark.class));
        Mockito.verify(mockDao, Mockito.times(0)).updateBookmark(Mockito.any(Bookmark.class));
        Mockito.verify(mockDao, Mockito.times(0)).deleteBookmark(Mockito.anyInt());
        Mockito.verify(mockDao, Mockito.times(0)).getBookmark(Mockito.anyInt());
    }
    
    @Test
    public void test_update_success() {
        Mockito.when(mockDao.updateBookmark(Mockito.any(Bookmark.class))).thenReturn(true);

        Bookmark bm = new Bookmark(new Integer(100), "Kanban", "https://en.wikipedia.org/wiki/Kanban");
        boolean isSuccess = bmSvc.updateBookmark(bm);
        assertTrue(isSuccess);
        
        Mockito.verify(mockDao, Mockito.times(0)).addBookmark(Mockito.any(Bookmark.class));
        Mockito.verify(mockDao, Mockito.times(1)).updateBookmark(Mockito.any(Bookmark.class));
        Mockito.verify(mockDao, Mockito.times(0)).deleteBookmark(Mockito.anyInt());
        Mockito.verify(mockDao, Mockito.times(0)).getBookmark(Mockito.anyInt());
    }
    
    @Test
    public void test_update_bad_data() {

        Bookmark bm = new Bookmark(null, "Kanban", "https://en.wikipedia.org/wiki/Kanban");
        boolean isSuccess = bmSvc.updateBookmark(bm);
        assertFalse(isSuccess);
        
        Mockito.verify(mockDao, Mockito.times(0)).addBookmark(Mockito.any(Bookmark.class));
        Mockito.verify(mockDao, Mockito.times(0)).updateBookmark(Mockito.any(Bookmark.class));
        Mockito.verify(mockDao, Mockito.times(0)).deleteBookmark(Mockito.anyInt());
        Mockito.verify(mockDao, Mockito.times(0)).getBookmark(Mockito.anyInt());
    }

    private class AddBookMarkAnswer implements Answer<Boolean> {
        public Boolean answer(InvocationOnMock invocation) throws Throwable {
            Bookmark bm = invocation.getArgumentAt(0, Bookmark.class);
            bm.setBookmarkId(new Integer(1));
            return true;
        }
    }
}
