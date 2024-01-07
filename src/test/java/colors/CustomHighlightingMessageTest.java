package colors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import com.library.utils.colors.CustomHighlightingMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomHighlightingMessageTest {
    @Test
    void testGetForegroundColorCode_ErrorLevel() {
        // Create a mock LoggingEvent with ERROR level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.ERROR);

        // Create an instance of CustomHighlightingMessage
        CustomHighlightingMessage converter = new CustomHighlightingMessage();

        // Test with ERROR level, should return red foreground color
        assertEquals(ANSIConstants.RED_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_WarnLevel() {
        // Create a mock LoggingEvent with WARN level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.WARN);

        // Create an instance of CustomHighlightingMessage
        CustomHighlightingMessage converter = new CustomHighlightingMessage();

        // Test with WARN level, should return yellow foreground color
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_InfoLevel() {
        // Create a mock LoggingEvent with INFO level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.INFO);

        // Create an instance of CustomHighlightingMessage
        CustomHighlightingMessage converter = new CustomHighlightingMessage();

        // Test with INFO level, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_NullLevel() {
        // Create a mock LoggingEvent with null level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(null);

        // Create an instance of CustomHighlightingMessage
        CustomHighlightingMessage converter = new CustomHighlightingMessage();

        // Test with null level, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_CustomLevel() {
        // Create a mock LoggingEvent with a custom level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Level customLevel = Level.toLevel("CUSTOM");
        Mockito.when(loggingEvent.getLevel()).thenReturn(customLevel);

        // Create an instance of CustomHighlightingMessage
        CustomHighlightingMessage converter = new CustomHighlightingMessage();

        // Test with a custom level, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_DebugLevel() {
        // Create a mock LoggingEvent with DEBUG level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.DEBUG);

        // Create an instance of CustomHighlightingMessage
        CustomHighlightingMessage converter = new CustomHighlightingMessage();

        // Test with DEBUG level, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_TraceLevel() {
        // Create a mock LoggingEvent with TRACE level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.TRACE);

        // Create an instance of CustomHighlightingMessage
        CustomHighlightingMessage converter = new CustomHighlightingMessage();

        // Test with TRACE level, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }
}
