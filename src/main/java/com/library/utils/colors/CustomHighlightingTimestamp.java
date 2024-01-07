package com.library.utils.colors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * The {@code CustomHighlightingTimestamp} class is a Logback {@link
 * ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase} implementation that provides
 * custom highlighting for log messages based on their timestamp and log level. Log messages with
 * error level (ERROR) will be highlighted with a red foreground color, warning level (WARN) with
 * yellow, and other levels with magenta.
 * <p>
 * This class is designed to be used with the Logback logging framework to customize the visual
 * representation of log messages based on their timestamps and levels.
 * <p>
 * Example Configuration in Logback.xml:
 * <pre>
 * {@code
 * <conversionRule conversionWord="highlightTimestamp"
 *                 converterClass="com.library.frontend.utils.colors.CustomHighlightingTimestamp" />
 *
 * <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
 *     <encoder>
 *         <pattern>%highlightTimestamp(%-5level) [%thread] - %msg%n</pattern>
 *     </encoder>
 * </appender>
 * }
 * </pre>
 * In this example, log messages with error level will be highlighted with a red foreground color,
 * warning level with yellow, and other levels with magenta.
 *
 * @see ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase
 * @see ch.qos.logback.classic.spi.ILoggingEvent
 * @see ch.qos.logback.core.pattern.color.ANSIConstants
 */
public class CustomHighlightingTimestamp extends ForegroundCompositeConverterBase<ILoggingEvent> {

    /**
     * Determines the foreground color code based on the timestamp and log level of the log event.
     * It returns the ANSI color code for the foreground color based on the log level.
     *
     * @param event The logging event containing information about the log message.
     * @return The ANSI color code for the foreground color based on the log level.
     */
    @Override
    public String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();

        return (level != null) ? switch (level.toInt()) {
            case Level.ERROR_INT -> ANSIConstants.RED_FG;
            case Level.WARN_INT -> ANSIConstants.YELLOW_FG;
            default -> ANSIConstants.MAGENTA_FG;
        } : ANSIConstants.MAGENTA_FG;
    }
}
