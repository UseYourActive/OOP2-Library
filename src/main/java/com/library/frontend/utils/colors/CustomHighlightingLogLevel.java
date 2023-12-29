package com.library.frontend.utils.colors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * The {@code CustomHighlightingLogLevel} class is a Logback {@link
 * ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase} implementation that provides
 * custom highlighting for log levels in log messages. The foreground color of the log level is
 * customized based on the severity of the log event.
 * <p>
 * This class is designed to be used with the Logback logging framework to customize the visual
 * representation of log messages based on their severity levels.
 * <p>
 * Example Configuration in Logback.xml:
 * <pre>
 * {@code
 * <conversionRule conversionWord="highlightLogLevel"
 *                 converterClass="com.library.frontend.utils.colors.CustomHighlightingLogLevel" />
 *
 * <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
 *     <encoder>
 *         <pattern>%highlightLogLevel(%-5level) [%logger{35}] - %msg%n</pattern>
 *     </encoder>
 * </appender>
 * }
 * </pre>
 * In this example, the log levels will be highlighted with different colors based on their severity.
 *
 * @see ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase
 * @see ch.qos.logback.classic.spi.ILoggingEvent
 * @see ch.qos.logback.classic.Level
 * @see ch.qos.logback.core.pattern.color.ANSIConstants
 */
public class CustomHighlightingLogLevel extends ForegroundCompositeConverterBase<ILoggingEvent> {

    /**
     * Determines the foreground color code based on the severity level of the log event. It returns
     * the ANSI color code corresponding to the severity level.
     *
     * @param event The logging event containing information about the log message.
     * @return The ANSI color code for the foreground color based on the log level.
     */
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        return switch (level.toInt()) {
            case Level.ERROR_INT -> ANSIConstants.RED_FG;
            case Level.WARN_INT -> ANSIConstants.YELLOW_FG;
            case Level.INFO_INT -> ANSIConstants.GREEN_FG;
            case Level.DEBUG_INT -> ANSIConstants.BLUE_FG;
            case Level.TRACE_INT -> ANSIConstants.CYAN_FG;
            default -> ANSIConstants.DEFAULT_FG;
        };
    }
}
