package com.helixs.api;

import com.helixs.model.DailyWeatherRes;
import com.helixs.model.IPRes;
import com.helixs.net.AppNetCallBack;
import com.helixs.net.Net;
import com.helixs.model.NowWeatherRes;

import java.util.Map;

/**
 * Created by helixs on 2017/2/17.
 */

public class Api {

    private static final String THINK_PAGE_URL = "https://api.thinkpage.cn/v3/weather/";
    private static final String IP_ADRESS = "http://ip.chinaz.com/getip.aspx";

    public static void getNowWeatherForGPS(String location, AppNetCallBack<NowWeatherRes> netCallBack) {
        Map<String, String> params = Net.thinkPageParams();
        params.put("location", location);
        Net.get(THINK_PAGE_URL + ThinkPageApiInterface.GET_NOW_WEATHER.api, params, NowWeatherRes
                .class).send(netCallBack);
    }

    public static void getIp(AppNetCallBack<IPRes> netCallBack) {
        Net.get(IP_ADRESS, null, IPRes
                .class).send(netCallBack);
    }

    public static void getDailyWeatherFor(String location, AppNetCallBack<DailyWeatherRes> netCallBack) {
        Map<String, String> params = Net.thinkPageParams();
        params.put("location", location);
        Net.get(THINK_PAGE_URL + ThinkPageApiInterface.GET_DAILY_WEATHER.api, params, DailyWeatherRes
                .class).send(netCallBack);
    }
}
