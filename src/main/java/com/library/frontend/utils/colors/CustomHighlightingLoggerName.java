package com.library.frontend.utils.colors;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * The {@code CustomHighlightingLoggerName} class is a Logback {@link
 * ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase} implementation that provides
 * custom highlighting for the logger name in log messages. The logger name is highlighted in yellow
 * when it contains the substring "important," and it uses the default foreground color otherwise.
 * <p>
 * This class is designed to be used with the Logback logging framework to customize the visual
 * representation of log messages based on the logger name.
 * <p>
 * Example Configuration in Logback.xml:
 * <pre>
 * {@code
 * <conversionRule conversionWord="highlightLoggerName"
 *                 converterClass="com.library.frontend.utils.colors.CustomHighlightingLoggerName" />
 *
 * <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
 *     <encoder>
 *         <pattern>%highlightLoggerName(%-5level) [%logger{35}] - %msg%n</pattern>
 *     </encoder>
 * </appender>
 * }
 * </pre>
 * In this example, the logger name will be highlighted in yellow if it contains the substring "important."
 *
 * @see ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase
 * @see ch.qos.logback.classic.spi.ILoggingEvent
 * @see ch.qos.logback.core.pattern.color.ANSIConstants
 */
public class CustomHighlightingLoggerName extends ForegroundCompositeConverterBase<ILoggingEvent> {

    /**
     * Determines the foreground color code based on the logger name. If the logger name contains
     * the substring "important," it returns the ANSI color code for yellow; otherwise, it returns
     * the ANSI color code for the default foreground color.
     *
     * @param event The logging event containing information about the log message.
     * @return The ANSI color code for the foreground color.
     */
    @Override
    public String getForegroundColorCode(ILoggingEvent event) {
        String loggerName = event.getLoggerName();

        return (loggerName != null && loggerName.contains("important")) ? ANSIConstants.YELLOW_FG : ANSIConstants.DEFAULT_FG;
    }
}


