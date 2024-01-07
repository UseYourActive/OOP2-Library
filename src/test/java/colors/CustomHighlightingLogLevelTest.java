package colors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import com.library.utils.colors.CustomHighlightingLogLevel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomHighlightingLogLevelTest {

    @Test
    void testGetForegroundColorCode_ErrorLevel() {
        // Create a mock LoggingEvent with ERROR level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.ERROR);

        // Create an instance of CustomHighlightingLogLevel
        CustomHighlightingLogLevel converter = new CustomHighlightingLogLevel();

        // Test with ERROR level
        assertEquals(ANSIConstants.RED_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_WarnLevel() {
        // Create a mock LoggingEvent with WARN level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.WARN);

        // Create an instance of CustomHighlightingLogLevel
        CustomHighlightingLogLevel converter = new CustomHighlightingLogLevel();

        // Test with WARN level
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_InfoLevel() {
        // Create a mock LoggingEvent with INFO level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.INFO);

        // Create an instance of CustomHighlightingLogLevel
        CustomHighlightingLogLevel converter = new CustomHighlightingLogLevel();

        // Test with INFO level
        assertEquals(ANSIConstants.GREEN_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_DebugLevel() {
        // Create a mock LoggingEvent with DEBUG level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.DEBUG);

        // Create an instance of CustomHighlightingLogLevel
        CustomHighlightingLogLevel converter = new CustomHighlightingLogLevel();

        // Test with DEBUG level
        assertEquals(ANSIConstants.BLUE_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_TraceLevel() {
        // Create a mock LoggingEvent with TRACE level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.TRACE);

        // Create an instance of CustomHighlightingLogLevel
        CustomHighlightingLogLevel converter = new CustomHighlightingLogLevel();

        // Test with TRACE level
        assertEquals(ANSIConstants.CYAN_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_DefaultLevel() {
        // Create a mock LoggingEvent with an unknown level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.ALL);

        // Create an instance of CustomHighlightingLogLevel
        CustomHighlightingLogLevel converter = new CustomHighlightingLogLevel();

        // Test with unknown level, should return default color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_NullLevel() {
        // Create a mock LoggingEvent with null level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(null);

        // Create an instance of CustomHighlightingLogLevel
        CustomHighlightingLogLevel converter = new CustomHighlightingLogLevel();

        // Test with null level, should return default color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_UpperCaseLevel() {
        // Create a mock LoggingEvent with an uppercase level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.WARN);

        // Create an instance of CustomHighlightingLogLevel
        CustomHighlightingLogLevel converter = new CustomHighlightingLogLevel();

        // Test with uppercase level, should return correct color
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_MixedCaseLevel() {
        // Create a mock LoggingEvent with a mixed-case level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.toLevel("WaRn"));

        // Create an instance of CustomHighlightingLogLevel
        CustomHighlightingLogLevel converter = new CustomHighlightingLogLevel();

        // Test with mixed-case level, should return correct color
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));
    }
}
