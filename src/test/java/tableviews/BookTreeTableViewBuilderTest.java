package tableviews;

import com.library.database.entities.Book;
import com.library.utils.tableviews.BookTreeTableViewBuilder;
import com.library.utils.tableviews.TreeTableViewBuilder;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

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

        assertColumn(treeTableView, 0, "Book", Book::getTitle);
        assertColumn(treeTableView, 1, "Author", Book::getAuthor);
        assertColumn(treeTableView, 2, "Genre", Book::getGenre);
        assertColumn(treeTableView, 3, "Year", Book::getPublishYear);
        assertColumn(treeTableView, 4, "Status", Book::getBookStatus);
        assertColumn(treeTableView, 5, "Times used", Book::getNumberOfTimesUsed);
    }

    private <S, T> void assertColumn(TreeTableView<S> treeTableView, int columnIndex, String header, java.util.function.Function<Book, T> valueExtractor) {
        TreeTableColumn<S, T> column = (TreeTableColumn<S, T>) treeTableView.getColumns().get(columnIndex);
        assertEquals(header, column.getText(), "Column header should be '" + header + "'");
        assertNotNull(column.getCellFactory(), "Cell factory should not be null");
    }
}
