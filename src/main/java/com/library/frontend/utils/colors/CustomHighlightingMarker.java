package com.library.frontend.utils.colors;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.List;

public class CustomHighlightingMarker extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        List<Marker> markerList = event.getMarkerList();
        return markerList != null && markerList.contains(MarkerFactory.getMarker("highlight")) ? ANSIConstants.YELLOW_FG : ANSIConstants.DEFAULT_FG;
    }
}
