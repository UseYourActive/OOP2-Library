package tableviews;

import com.library.database.entities.Reader;
import com.library.frontend.utils.tableviews.ReaderTableViewBuilder;
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
class ReaderTableViewBuilderTest {

    private TableViewBuilder<Reader> readerTableViewBuilder;

    @BeforeEach
    void setUp() {
        readerTableViewBuilder = new ReaderTableViewBuilder();
    }

    @Test
    void testCreateTableViewColumns() {
        TableView<Reader> tableView = new TableView<>();
        readerTableViewBuilder.createTableViewColumns(tableView);

        assertEquals(3, tableView.getColumns().size(), "The table should have three columns");

        TableColumn<Reader, String> firstNameColumn = (TableColumn<Reader, String>) tableView.getColumns().get(0);
        assertEquals("First Name", firstNameColumn.getText(), "Column header should be 'First Name'");
        assertNotNull(firstNameColumn.getCellFactory(), "Cell factory should not be null");

        TableColumn<Reader, String> lastNameColumn = (TableColumn<Reader, String>) tableView.getColumns().get(1);
        assertEquals("Last Name", lastNameColumn.getText(), "Column header should be 'Last Name'");
        assertNotNull(lastNameColumn.getCellFactory(), "Cell factory should not be null");

        TableColumn<Reader, String> emailColumn = (TableColumn<Reader, String>) tableView.getColumns().get(2);
        assertEquals("Email", emailColumn.getText(), "Column header should be 'Email'");
        assertNotNull(emailColumn.getCellFactory(), "Cell factory should not be null");
    }
}
