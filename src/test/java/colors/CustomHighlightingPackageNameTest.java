package colors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import com.library.frontend.utils.colors.CustomHighlightingPackageName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomHighlightingPackageNameTest {

    @Test
    void testGetForegroundColorCode_ErrorLevel() {
        // Create a mock LoggingEvent with ERROR level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.ERROR);

        // Create an instance of CustomHighlightingPackageName
        CustomHighlightingPackageName converter = new CustomHighlightingPackageName();

        // Test with ERROR level, should return red foreground color
        assertEquals(ANSIConstants.RED_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_WarnLevel() {
        // Create a mock LoggingEvent with WARN level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.WARN);

        // Create an instance of CustomHighlightingPackageName
        CustomHighlightingPackageName converter = new CustomHighlightingPackageName();

        // Test with WARN level, should return yellow foreground color
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_InfoLevel() {
        // Create a mock LoggingEvent with INFO level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.INFO);

        // Create an instance of CustomHighlightingPackageName
        CustomHighlightingPackageName converter = new CustomHighlightingPackageName();

        // Test with INFO level, should return cyan foreground color
        assertEquals(ANSIConstants.CYAN_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_NullLevel() {
        // Create a mock LoggingEvent with null level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(null);

        // Create an instance of CustomHighlightingPackageName
        CustomHighlightingPackageName converter = new CustomHighlightingPackageName();

        // Test with null level, should return cyan foreground color
        assertEquals(ANSIConstants.CYAN_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_CustomLevel() {
        // Create a mock LoggingEvent with a custom level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Level customLevel = Level.toLevel("CUSTOM");
        Mockito.when(loggingEvent.getLevel()).thenReturn(customLevel);

        // Create an instance of CustomHighlightingPackageName
        CustomHighlightingPackageName converter = new CustomHighlightingPackageName();

        // Test with a custom level, should return cyan foreground color
        assertEquals(ANSIConstants.CYAN_FG, converter.getForegroundColorCode(loggingEvent));
    }
}
