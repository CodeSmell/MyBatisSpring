package codesmell.bookmarks.dao;

import codesmell.bookmarks.Bookmark;
import codesmell.bookmarks.config.MyBatisConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MyBatisConfig.class })
public class BookmarkDaoInt {

    @Autowired
    private BookmarkDao dao;

    @Test
    public void test_dao_sql() {

        try {
            String bookmarkTitle = "Scrum Alliance";
            String bookmarkUrl = "https://www.scrumalliance.org/";
            
            // insert the row
            Bookmark bm = new Bookmark(null, bookmarkUrl, bookmarkTitle);
            assertTrue(dao.addBookmark(bm));
            assertNotNull(bm);
            assertNotNull(bm.getBookmarkId());
            
            Integer bookmarkId = bm.getBookmarkId();
            
            // retrieve it
            Bookmark result = dao.getBookmark(bookmarkId);
            assertNotNull(result);
            assertEquals(bookmarkId, result.getBookmarkId());
            assertEquals(bookmarkTitle, result.getBookmarkTitle());
            assertEquals(bookmarkUrl, result.getBookmarkUrl());
            
            // update it
            result.setBookmarkTitle("ScrumRocks!");
            assertTrue(dao.updateBookmark(result));
            assertNotNull(result);
            assertEquals(bookmarkId, result.getBookmarkId());
            assertEquals("ScrumRocks!", result.getBookmarkTitle());
            assertEquals(bookmarkUrl, result.getBookmarkUrl());

            // retrieve it again
            Bookmark updatedResult = dao.getBookmark(bookmarkId);
            assertNotNull(updatedResult);
            assertEquals(bookmarkId, updatedResult.getBookmarkId());
            assertEquals("ScrumRocks!", updatedResult.getBookmarkTitle());
            assertEquals(bookmarkUrl, updatedResult.getBookmarkUrl());
            
            // delete it
            assertTrue(dao.deleteBookmark(bookmarkId));
            
            // retrieve it one more time
            Bookmark deletedResult = dao.getBookmark(bookmarkId);
            assertNull(deletedResult);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void test_dao_bad_retrieve() {
        Bookmark result = dao.getBookmark(Integer.MAX_VALUE);
        assertNull(result);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void test_dao_bad_insert() {
        String bookmarkTitle = "Scrum Alliance";
        String bookmarkUrl = null;
        
        // insert the row
        Bookmark bm = new Bookmark(null, bookmarkUrl, bookmarkTitle);
        dao.addBookmark(bm);

        assertNotNull(bm);
        assertNotNull(bm.getBookmarkId());
    }

    @Test
    public void test_dao_bad_update() {
        assertFalse(dao.updateBookmark(null));
    }
    
    @Test
    public void test_dao_bad_delete() {
        assertFalse(dao.deleteBookmark(Integer.MAX_VALUE));
    }

}
