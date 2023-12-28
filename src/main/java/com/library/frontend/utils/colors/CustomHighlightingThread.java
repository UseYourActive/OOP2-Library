package com.library.frontend.utils.colors;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class CustomHighlightingThread extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        String threadName = event.getThreadName();
        return threadName.contains("background") ? ANSIConstants.BLUE_FG : ANSIConstants.DEFAULT_FG;
    }
}

