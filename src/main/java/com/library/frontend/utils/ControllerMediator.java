package com.library.frontend.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ControllerMediator {
    private static Object globalData="";

    public static Object getGlobalData() {
        return globalData;
    }

    public static void setGlobalData(Object globalData) {
        ControllerMediator.globalData = globalData;
    }
}
