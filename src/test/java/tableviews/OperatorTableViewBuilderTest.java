package tableviews;

import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.frontend.utils.tableviews.OperatorTableViewBuilder;
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
class OperatorTableViewBuilderTest {

    private TableViewBuilder<User> operatorTableViewBuilder;

    @BeforeEach
    void setUp() {
        operatorTableViewBuilder = new OperatorTableViewBuilder();
    }

    @Test
    void testCreateTableViewColumns() {
        TableView<User> tableView = new TableView<>();
        operatorTableViewBuilder.createTableViewColumns(tableView);

        assertEquals(2, tableView.getColumns().size(), "The table should have two columns");

        TableColumn<User, String> usernameColumn = (TableColumn<User, String>) tableView.getColumns().get(0);
        assertEquals("Username", usernameColumn.getText(), "Column header should be 'Username'");
        assertNotNull(usernameColumn.getCellFactory(), "Cell factory should not be null");

        TableColumn<User, Role> roleColumn = (TableColumn<User, Role>) tableView.getColumns().get(1);
        assertEquals("Role", roleColumn.getText(), "Column header should be 'Role'");
        assertNotNull(roleColumn.getCellFactory(), "Cell factory should not be null");
    }
}
