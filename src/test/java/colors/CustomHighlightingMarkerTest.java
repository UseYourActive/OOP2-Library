package colors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import com.library.utils.colors.CustomHighlightingMarker;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomHighlightingMarkerTest {

    @Test
    void testGetForegroundColorCode_WithHighlightMarker() {
        // Create a mock LoggingEvent with a marker named "highlight"
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Marker highlightMarker = MarkerFactory.getMarker("highlight");
        List<Marker> markerList = Collections.singletonList(highlightMarker);
        Mockito.when(loggingEvent.getMarkerList()).thenReturn(markerList);

        // Create an instance of CustomHighlightingMarker
        CustomHighlightingMarker converter = new CustomHighlightingMarker();

        // Test with the "highlight" marker, should return yellow foreground color
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_WithoutHighlightMarker() {
        // Create a mock LoggingEvent without the "highlight" marker
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        List<Marker> markerList = Collections.emptyList();
        Mockito.when(loggingEvent.getMarkerList()).thenReturn(markerList);

        // Create an instance of CustomHighlightingMarker
        CustomHighlightingMarker converter = new CustomHighlightingMarker();

        // Test without the "highlight" marker, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_NullMarkerList() {
        // Create a mock LoggingEvent with null marker list
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getMarkerList()).thenReturn(null);

        // Create an instance of CustomHighlightingMarker
        CustomHighlightingMarker converter = new CustomHighlightingMarker();

        // Test with null marker list, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_MultipleMarkers_WithHighlightMarker() {
        // Create a mock LoggingEvent with multiple markers including "highlight"
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Marker highlightMarker = MarkerFactory.getMarker("highlight");
        Marker otherMarker = MarkerFactory.getMarker("other");
        List<Marker> markerList = Arrays.asList(otherMarker, highlightMarker);
        Mockito.when(loggingEvent.getMarkerList()).thenReturn(markerList);

        // Create an instance of CustomHighlightingMarker
        CustomHighlightingMarker converter = new CustomHighlightingMarker();

        // Test with the "highlight" marker, should return yellow foreground color
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_MultipleMarkers_WithoutHighlightMarker() {
        // Create a mock LoggingEvent with multiple markers but without "highlight"
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Marker otherMarker = MarkerFactory.getMarker("other");
        List<Marker> markerList = Collections.singletonList(otherMarker);
        Mockito.when(loggingEvent.getMarkerList()).thenReturn(markerList);

        // Create an instance of CustomHighlightingMarker
        CustomHighlightingMarker converter = new CustomHighlightingMarker();

        // Test without the "highlight" marker, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_NullMarkerName() {
        // Create a mock LoggingEvent with a marker with null name
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);

        // Create a marker with an empty string (null-like behavior)
        Marker nullNameMarker = MarkerFactory.getMarker("");
        List<Marker> markerList = Collections.singletonList(nullNameMarker);
        Mockito.when(loggingEvent.getMarkerList()).thenReturn(markerList);

        // Create an instance of CustomHighlightingMarker
        CustomHighlightingMarker converter = new CustomHighlightingMarker();

        // Test with a marker with null-like name, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_HighlightMarkerPresent() {
        // Create a mock LoggingEvent with the "highlight" marker
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Marker highlightMarker = MarkerFactory.getMarker("highlight");
        List<Marker> markerList = Collections.singletonList(highlightMarker);
        Mockito.when(loggingEvent.getMarkerList()).thenReturn(markerList);

        // Create an instance of CustomHighlightingMarker
        CustomHighlightingMarker converter = new CustomHighlightingMarker();

        // Test with the "highlight" marker present, should return yellow foreground color
        assertEquals(ANSIConstants.YELLOW_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_HighlightMarkerNotPresent() {
        // Create a mock LoggingEvent without the "highlight" marker
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        List<Marker> markerList = Collections.emptyList();
        Mockito.when(loggingEvent.getMarkerList()).thenReturn(markerList);

        // Create an instance of CustomHighlightingMarker
        CustomHighlightingMarker converter = new CustomHighlightingMarker();

        // Test without the "highlight" marker, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_CustomMarkerPresent() {
        // Create a mock LoggingEvent with a custom marker
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Marker customMarker = MarkerFactory.getMarker("custom");
        List<Marker> markerList = Collections.singletonList(customMarker);
        Mockito.when(loggingEvent.getMarkerList()).thenReturn(markerList);

        // Create an instance of CustomHighlightingMarker
        CustomHighlightingMarker converter = new CustomHighlightingMarker();

        // Test with a custom marker, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_NullLevel() {
        // Create a mock LoggingEvent with null level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(null);

        // Create an instance of CustomHighlightingMarker
        CustomHighlightingMarker converter = new CustomHighlightingMarker();

        // Test with null level, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }

    @Test
    void testGetForegroundColorCode_CustomLevel() {
        // Create a mock LoggingEvent with a custom level
        ILoggingEvent loggingEvent = Mockito.mock(ILoggingEvent.class);
        Mockito.when(loggingEvent.getLevel()).thenReturn(Level.DEBUG);

        // Create an instance of CustomHighlightingMarker
        CustomHighlightingMarker converter = new CustomHighlightingMarker();

        // Test with a custom level, should return default foreground color
        assertEquals(ANSIConstants.DEFAULT_FG, converter.getForegroundColorCode(loggingEvent));
    }
}
