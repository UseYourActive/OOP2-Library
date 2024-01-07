package com.library.utils.colors;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * The {@code CustomHighlightingThread} class is a Logback {@link
 * ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase} implementation that provides
 * custom highlighting for log messages based on the thread name. Log messages associated with
 * threads containing the term "background" in their names will be highlighted with a specific
 * foreground color, while messages from other threads will use the default foreground color.
 * <p>
 * This class is designed to be used with the Logback logging framework to customize the visual
 * representation of log messages based on the names of the threads associated with them.
 * <p>
 * Example Configuration in Logback.xml:
 * <pre>
 * {@code
 * <conversionRule conversionWord="highlightThread"
 *                 converterClass="com.library.frontend.utils.colors.CustomHighlightingThread" />
 *
 * <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
 *     <encoder>
 *         <pattern>%highlightThread(%-5level) [%thread] - %msg%n</pattern>
 *     </encoder>
 * </appender>
 * }
 * </pre>
 * In this example, log messages from threads with the term "background" in their names will be
 * highlighted with a blue foreground color, and messages from other threads will use the default
 * foreground color.
 *
 * @see ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase
 * @see ch.qos.logback.classic.spi.ILoggingEvent
 * @see ch.qos.logback.core.pattern.color.ANSIConstants
 */
public class CustomHighlightingThread extends ForegroundCompositeConverterBase<ILoggingEvent> {

    /**
     * Determines the foreground color code based on the thread name of the log event. It returns
     * the ANSI color code for the foreground color based on the presence of the term "background"
     * in the thread name.
     *
     * @param event The logging event containing information about the log message.
     * @return The ANSI color code for the foreground color based on the thread name.
     */
    @Override
    public String getForegroundColorCode(ILoggingEvent event) {
        String threadName = event.getThreadName();

        if (threadName == null) {
            return ANSIConstants.DEFAULT_FG;
        }

        return threadName.contains("background") ? ANSIConstants.BLUE_FG : ANSIConstants.DEFAULT_FG;
    }
}
