package com.helixs.model;

import java.util.List;

/**
 * Created by helixs on 2017/3/7.
 */

public class DailyWeatherRes extends BaseRes {

    public List<ResultsBean> results;


    public static class ResultsBean {
        /**
         * location : {"id":"WX4FBXXFKE4F","name":"北京","country":"CN","path":"北京,北京,中国","timezone":"Asia/Shanghai","timezone_offset":"+08:00"}
         * daily : [{"date":"2017-03-07","text_day":"晴","code_day":"0","text_night":"晴","code_night":"0","high":"9","low":"0","precip":"","wind_direction":"北","wind_direction_degree":"0","wind_speed":"15","wind_scale":"3"},{"date":"2017-03-08","text_day":"晴","code_day":"0","text_night":"晴","code_night":"0","high":"13","low":"0","precip":"","wind_direction":"北","wind_direction_degree":"0","wind_speed":"15","wind_scale":"3"},{"date":"2017-03-09","text_day":"晴","code_day":"0","text_night":"晴","code_night":"0","high":"15","low":"2","precip":"","wind_direction":"南","wind_direction_degree":"180","wind_speed":"10","wind_scale":"2"}]
         * last_update : 2017-03-07T11:00:00+08:00
         */
        public LocationBean location;
        public String last_update;
        public List<DailyBean> daily;


    }
    /**
     * 返回指定days天数的结果
     */
    public static class DailyBean {
        /**
         * date :
         * text_day :
         * code_day :
         * text_night :
         * code_night :
         * high :
         * low :
         * precip :
         * wind_direction :
         * wind_direction_degree :
         * wind_speed :
         * wind_scale :
         */

        /**
         * 日期 2017-03-07
         */
        public String date;
        /**
         *白天天气现象文字 晴
         */
        public String text_day;
        /**
         * 白天天气现象代码 0
         */
        public String code_day;
        /**
         * 晚间天气现象文字 晴
         */
        public String text_night;
        /**
         * 晚间天气现象代码 0
         */
        public String code_night;
        /**
         * 当天最高温度 9
         */
        public String high;
        /**
         * 当天最低温度 0
         */
        public String low;
        /**
         * 降水概率，范围0~100，单位百分比
         */
        public String precip;
        /**
         * 风向文字 北
         */
        public String wind_direction;
        /**
         * 风向角度，范围0~360 0
         */
        public String wind_direction_degree;
        /**
         * 风速，单位km/h（当unit=c时）、mph（当unit=f时） 15
         */
        public String wind_speed;
        /**
         * 风力等级  3
         */
        public String wind_scale;

    }
}
