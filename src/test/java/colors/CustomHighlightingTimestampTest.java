package colors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import com.library.frontend.utils.colors.CustomHighlightingTimestamp;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomHighlightingTimestampTest {
    @Test
    void testGetForegroundColorCode_ErrorLevel() {
        // Create a mock LoggingEvent with error level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.ERROR);

        // Create an instance of CustomHighlightingTimestamp
        CustomHighlightingTimestamp converter = new CustomHighlightingTimestamp();

        // Test with error level, should return red foreground color
        assertEquals(ANSIConstants.RED_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_WarnLevel() {
        // Create a mock LoggingEvent with warn level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.WARN);

        // Create an instance of CustomHighlightingTimestamp
        CustomHighlightingTimestamp converter = new CustomHighlightingTimestamp();

        // Test with warn level, should return yellow foreground color
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_OtherLevels() {
        // Create a mock LoggingEvent with a level other than error or warn
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.INFO);

        // Create an instance of CustomHighlightingTimestamp
        CustomHighlightingTimestamp converter = new CustomHighlightingTimestamp();

        // Test with other levels, should return magenta foreground color
        assertEquals(ANSIConstants.MAGENTA_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_NullLevel() {
        // Create a mock LoggingEvent with null level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(null);

        // Create an instance of CustomHighlightingTimestamp
        CustomHighlightingTimestamp converter = new CustomHighlightingTimestamp();

        // Test with null level, should return magenta foreground color (default)
        assertEquals(ANSIConstants.MAGENTA_FG, converter.getForegroundColorCode(loggingEvent));
    }
}
