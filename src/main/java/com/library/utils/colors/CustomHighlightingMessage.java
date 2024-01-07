package com.library.utils.colors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * The {@code CustomHighlightingMessage} class is a Logback {@link
 * ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase} implementation that provides
 * custom highlighting for log messages based on their log levels. Log messages with error or
 * warning levels are highlighted with specific foreground colors, while messages with other levels
 * use the default foreground color.
 * <p>
 * This class is designed to be used with the Logback logging framework to customize the visual
 * representation of log messages based on their log levels.
 * <p>
 * Example Configuration in Logback.xml:
 * <pre>
 * {@code
 * <conversionRule conversionWord="highlightMessage"
 *                 converterClass="com.library.frontend.utils.colors.CustomHighlightingMessage" />
 *
 * <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
 *     <encoder>
 *         <pattern>%highlightMessage(%-5level) [%logger{35}] - %msg%n</pattern>
 *     </encoder>
 * </appender>
 * }
 * </pre>
 * In this example, log messages with the "ERROR" level will be highlighted with a red foreground
 * color, and messages with the "WARN" level will be highlighted with a yellow foreground color.
 *
 * @see ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase
 * @see ch.qos.logback.classic.spi.ILoggingEvent
 * @see ch.qos.logback.core.pattern.color.ANSIConstants
 */
public class CustomHighlightingMessage extends ForegroundCompositeConverterBase<ILoggingEvent> {

    /**
     * Determines the foreground color code based on the log level of the log event. It returns the
     * ANSI color code for the foreground color based on the log level.
     *
     * @param event The logging event containing information about the log message.
     * @return The ANSI color code for the foreground color based on the log level.
     */
    @Override
    public String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();

        if (level != null) {
            return switch (level.toInt()) {
                case Level.ERROR_INT -> ANSIConstants.RED_FG;
                case Level.WARN_INT -> ANSIConstants.YELLOW_FG;
                default -> ANSIConstants.DEFAULT_FG;
            };
        }

        return ANSIConstants.DEFAULT_FG;
    }
}
