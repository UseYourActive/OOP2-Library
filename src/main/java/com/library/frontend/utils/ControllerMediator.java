package com.library.frontend.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ControllerMediator {
    private static ControllerMediator instance;
    private Object globalData;

    private ControllerMediator() {
        this.globalData = "Default Global Data";
    }

    public static synchronized ControllerMediator getInstance() {
        if (instance == null) {
            instance = new ControllerMediator();
        }
        return instance;
    }
}
