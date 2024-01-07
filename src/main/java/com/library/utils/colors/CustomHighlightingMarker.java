package com.library.utils.colors;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.List;

/**
 * The {@code CustomHighlightingMarker} class is a Logback {@link
 * ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase} implementation that provides
 * custom highlighting for log messages based on the presence of a specific marker. If the log event
 * contains a marker with the name "highlight," the foreground color is customized; otherwise, the
 * default foreground color is used.
 * <p>
 * This class is designed to be used with the Logback logging framework to customize the visual
 * representation of log messages based on the presence of specific markers.
 * <p>
 * Example Configuration in Logback.xml:
 * <pre>
 * {@code
 * <conversionRule conversionWord="highlightMarker"
 *                 converterClass="com.library.frontend.utils.colors.CustomHighlightingMarker" />
 *
 * <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
 *     <encoder>
 *         <pattern>%highlightMarker(%-5level) [%logger{35}] - %msg%n</pattern>
 *     </encoder>
 * </appender>
 * }
 * </pre>
 * In this example, the log messages with the "highlight" marker will be highlighted with a yellow
 * foreground color.
 *
 * @see ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase
 * @see ch.qos.logback.classic.spi.ILoggingEvent
 * @see ch.qos.logback.core.pattern.color.ANSIConstants
 * @see org.slf4j.Marker
 * @see org.slf4j.MarkerFactory
 */
public class CustomHighlightingMarker extends ForegroundCompositeConverterBase<ILoggingEvent> {

    /**
     * Determines the foreground color code based on the presence of the "highlight" marker in the
     * log event. It returns the ANSI color code for the foreground color based on the marker's
     * presence.
     *
     * @param event The logging event containing information about the log message.
     * @return The ANSI color code for the foreground color based on the presence of the "highlight"
     * marker.
     */
    @Override
    public String getForegroundColorCode(ILoggingEvent event) {
        List<Marker> markerList = event.getMarkerList();

        return markerList != null && markerList.contains(MarkerFactory.getMarker("highlight")) ?
                ANSIConstants.YELLOW_FG : ANSIConstants.DEFAULT_FG;
    }
}
