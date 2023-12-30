package colors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import com.library.frontend.utils.colors.CustomHighlightingLoggerName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomHighlightingLoggerNameTest {

    @Test
    void testGetForegroundColorCode() {
        // Create a mock LoggingEvent
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);

        // Create an instance of CustomHighlightingLoggerName
        CustomHighlightingLoggerName converter = new CustomHighlightingLoggerName();

        // Test with logger name containing "important"
        Mockito.when(loggingEvent.getLoggerName()).thenReturn("com.library.important.Class");
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));

        // Test with logger name not containing "important"
        Mockito.when(loggingEvent.getLoggerName()).thenReturn("com.library.normal.Class");
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_LoggerNameContainsImportant() {
        // Create a mock LoggingEvent
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);

        // Create an instance of CustomHighlightingLoggerName
        CustomHighlightingLoggerName converter = new CustomHighlightingLoggerName();

        // Test with logger name containing "important"
        Mockito.when(loggingEvent.getLoggerName()).thenReturn("com.library.important.Class");
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_LoggerNameDoesNotContainImportant() {
        // Create a mock LoggingEvent
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);

        // Create an instance of CustomHighlightingLoggerName
        CustomHighlightingLoggerName converter = new CustomHighlightingLoggerName();

        // Test with logger name not containing "important"
        Mockito.when(loggingEvent.getLoggerName()).thenReturn("com.library.normal.Class");
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_NullLoggerName() {
        // Create a mock LoggingEvent
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);

        // Create an instance of CustomHighlightingLoggerName
        CustomHighlightingLoggerName converter = new CustomHighlightingLoggerName();

        // Test with null logger name
        Mockito.when(loggingEvent.getLoggerName()).thenReturn(null);
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_EmptyLoggerName() {
        // Create a mock LoggingEvent
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);

        // Create an instance of CustomHighlightingLoggerName
        CustomHighlightingLoggerName converter = new CustomHighlightingLoggerName();

        // Test with empty logger name
        Mockito.when(loggingEvent.getLoggerName()).thenReturn("");
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_LevelIsError() {
        // Create a mock LoggingEvent
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);

        // Create an instance of CustomHighlightingLoggerName
        CustomHighlightingLoggerName converter = new CustomHighlightingLoggerName();

        // Test with logger name containing "important" and log level is ERROR
        Mockito.when(loggingEvent.getLoggerName()).thenReturn("com.library.important.Class");
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.ERROR);
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));
    }
}
