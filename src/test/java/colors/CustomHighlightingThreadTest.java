package colors;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import com.library.utils.colors.CustomHighlightingThread;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomHighlightingThreadTest {
    @Test
    void testGetForegroundColorCode_BackgroundThread() {
        // Create a mock LoggingEvent with a thread name containing "background"
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getThreadName()).thenReturn("background-thread");

        // Create an instance of CustomHighlightingThread
        CustomHighlightingThread converter = new CustomHighlightingThread();

        // Test with a background thread, should return blue foreground color
        assertEquals(ANSIConstants.BLUE_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_NonBackgroundThread() {
        // Create a mock LoggingEvent with a thread name not containing "background"
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getThreadName()).thenReturn("foreground-thread");

        // Create an instance of CustomHighlightingThread
        CustomHighlightingThread converter = new CustomHighlightingThread();

        // Test with a non-background thread, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_NullThreadName() {
        // Create a mock LoggingEvent with a null thread name
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getThreadName()).thenReturn(null);

        // Create an instance of CustomHighlightingThread
        CustomHighlightingThread converter = new CustomHighlightingThread();

        // Test with a null thread name, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }
}
