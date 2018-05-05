package com.helixs.api;

/**
 * Created by helixs on 2017/2/20.
 */

public enum ThinkPageApiInterface {

    GET_NOW_WEATHER("now.json", "获取指定城市的天气实况"),
    GET_DAILY_WEATHER("daily.json", "未来三天天气预报"),;


    public final String api;
    public final String info;

    ThinkPageApiInterface(String api, String info) {
        this.api = api;
        this.info = info;
    }
}
