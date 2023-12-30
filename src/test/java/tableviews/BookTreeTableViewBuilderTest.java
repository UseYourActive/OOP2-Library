package tableviews;

import com.library.database.entities.Author;
import com.library.database.entities.Book;
import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import com.library.frontend.utils.tableviews.BookTreeTableViewBuilder;
import com.library.frontend.utils.tableviews.TreeTableViewBuilder;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
class BookTreeTableViewBuilderTest {

    private TreeTableViewBuilder<Book> bookTreeTableViewBuilder;

    @BeforeEach
    void setUp() {
        bookTreeTableViewBuilder = new BookTreeTableViewBuilder();
    }

    @Test
    void testCreateTreeTableViewColumns() {
        TreeTableView<Book> treeTableView = new TreeTableView<>();
        bookTreeTableViewBuilder.createTreeTableViewColumns(treeTableView);

        assertEquals(6, treeTableView.getColumns().size(), "The tree table should have six columns");

        TreeTableColumn<Book, String> titleColumn = (TreeTableColumn<Book, String>) treeTableView.getColumns().get(0);
        assertEquals("Book", titleColumn.getText(), "Column header should be 'Book'");
        assertNotNull(titleColumn.getCellFactory(), "Cell factory should not be null");

        TreeTableColumn<Book, Author> authorColumn = (TreeTableColumn<Book, Author>) treeTableView.getColumns().get(1);
        assertEquals("Author", authorColumn.getText(), "Column header should be 'Author'");
        assertNotNull(authorColumn.getCellFactory(), "Cell factory should not be null");

        TreeTableColumn<Book, Genre> genreColumn = (TreeTableColumn<Book, Genre>) treeTableView.getColumns().get(2);
        assertEquals("Genre", genreColumn.getText(), "Column header should be 'Genre'");
        assertNotNull(genreColumn.getCellFactory(), "Cell factory should not be null");

        TreeTableColumn<Book, BookStatus> bookStatusColumn = (TreeTableColumn<Book, BookStatus>) treeTableView.getColumns().get(3);
        assertEquals("Status", bookStatusColumn.getText(), "Column header should be 'Status'");
        assertNotNull(bookStatusColumn.getCellFactory(), "Cell factory should not be null");

        TreeTableColumn<Book, Year> publishedYearColumn = (TreeTableColumn<Book, Year>) treeTableView.getColumns().get(4);
        assertEquals("Year", publishedYearColumn.getText(), "Column header should be 'Year'");
        assertNotNull(publishedYearColumn.getCellFactory(), "Cell factory should not be null");

        TreeTableColumn<Book, String> numberOfTimesUsedColumn = (TreeTableColumn<Book, String>) treeTableView.getColumns().get(5);
        assertEquals("Times used", numberOfTimesUsedColumn.getText(), "Column header should be 'Times used'");
        assertNotNull(numberOfTimesUsedColumn.getCellFactory(), "Cell factory should not be null");
    }
}
