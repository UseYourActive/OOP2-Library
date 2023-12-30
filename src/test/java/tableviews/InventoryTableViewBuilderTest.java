package tableviews;

import com.library.database.entities.Author;
import com.library.database.entities.BookInventory;
import com.library.database.enums.Genre;
import com.library.frontend.utils.tableviews.InventoryTableViewBuilder;
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
class InventoryTableViewBuilderTest {

    private TableViewBuilder<BookInventory> inventoryTableViewBuilder;

    @BeforeEach
    void setUp() {
        inventoryTableViewBuilder = new InventoryTableViewBuilder();
    }

    @Test
    void testCreateTableViewColumns() {
        TableView<BookInventory> tableView = new TableView<>();
        inventoryTableViewBuilder.createTableViewColumns(tableView);

        assertEquals(3, tableView.getColumns().size(), "The table should have three columns");

        TableColumn<BookInventory, String> titleColumn = (TableColumn<BookInventory, String>) tableView.getColumns().get(0);
        assertEquals("Title", titleColumn.getText(), "Column header should be 'Title'");
        assertNotNull(titleColumn.getCellFactory(), "Cell factory should not be null");

        TableColumn<BookInventory, Author> authorColumn = (TableColumn<BookInventory, Author>) tableView.getColumns().get(1);
        assertEquals("Author", authorColumn.getText(), "Column header should be 'Author'");
        assertNotNull(authorColumn.getCellFactory(), "Cell factory should not be null");

        TableColumn<BookInventory, Genre> genreColumn = (TableColumn<BookInventory, Genre>) tableView.getColumns().get(2);
        assertEquals("Genre", genreColumn.getText(), "Column header should be 'Genre'");
        assertNotNull(genreColumn.getCellFactory(), "Cell factory should not be null");
    }
}

