package com.library.frontend.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.pattern.LevelConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ColoredLevelConverter extends LevelConverter {

    @Override
    public String convert(ILoggingEvent event) {
        return switch (event.getLevel().toInt()) {
            case Level.ERROR_INT -> "\033[31mERROR\033[0m";
            case Level.WARN_INT -> "\033[33mWARN\033[0m";
            case Level.INFO_INT -> "\033[32mINFO\033[0m";
            case Level.DEBUG_INT -> "\033[34mDEBUG\033[0m";
            case Level.TRACE_INT -> "\033[36mTRACE\033[0m";
            default -> super.convert(event);
        };
    }
}
