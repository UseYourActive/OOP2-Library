package tableviews;

import com.library.database.entities.Book;
import com.library.database.enums.BookStatus;
import com.library.frontend.utils.tableviews.BookTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
class BookTableViewBuilderTest {

    private TableViewBuilder<Book> bookTableViewBuilder;

    @BeforeEach
    void setUp() {
        bookTableViewBuilder = new BookTableViewBuilder();
    }

    @Test
    void testCreateTableViewColumns() {
        TableView<Book> tableView = new TableView<>();
        bookTableViewBuilder.createTableViewColumns(tableView);

        assertEquals(3, tableView.getColumns().size(), "The table should have three columns");

        TableColumn<Book, Long> idColumn = (TableColumn<Book, Long>) tableView.getColumns().get(0);
        assertEquals("Id", idColumn.getText(), "Column header should be 'Id'");
        assertNotNull(idColumn.getCellFactory(), "Cell factory should not be null");

        TableColumn<Book, Integer> timesUsedColumn = (TableColumn<Book, Integer>) tableView.getColumns().get(1);
        assertEquals("Times used", timesUsedColumn.getText(), "Column header should be 'Times used'");
        assertNotNull(timesUsedColumn.getCellFactory(), "Cell factory should not be null");

        TableColumn<Book, BookStatus> statusColumn = (TableColumn<Book, BookStatus>) tableView.getColumns().get(2);
        assertEquals("Status", statusColumn.getText(), "Column header should be 'Status'");
        assertNotNull(statusColumn.getCellFactory(), "Cell factory should not be null");
    }
}
