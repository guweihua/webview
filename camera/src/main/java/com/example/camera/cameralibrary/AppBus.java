package com.example.camera.cameralibrary;

import com.squareup.otto.Bus;

/**
 * Created by shy on 2017/2/23.
 *
 */

public class AppBus extends Bus{

private static AppBus bus;

public static AppBus getInstance() {
        if (bus == null) {
        bus = new AppBus();
        }
        return bus;
        }


        }
