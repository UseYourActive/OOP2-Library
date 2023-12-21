package com.library.frontend.utils.colors;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class CustomHighlightingLoggerName extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        String loggerName = event.getLoggerName();
        return loggerName.contains("important") ? ANSIConstants.YELLOW_FG : ANSIConstants.DEFAULT_FG;
    }
}

