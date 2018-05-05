package com.helixs.model;

import java.util.List;

/**
 * Created by helixs on 2017/2/21.
 */

public class NowWeatherRes extends BaseRes {

	public List<ResultsBean> results;


	public static class ResultsBean {
		/**
		 * location : {"id":"WX4FBXXFKE4F","name":"北京","country":"CN","path":"北京,北京,中国","timezone":"Asia/Shanghai","timezone_offset":"+08:00"}
		 * now : {"text":"雨夹雪","code":"20","temperature":"-1"}
		 * last_update : 2017-02-21T14:45:00+08:00
		 */

		public LocationBean location;
		public NowBean now;
		/**
		 * 最后一次更新时间
		 */
		public String last_update;




		public static class NowBean {
			/**
			 * text : 雨夹雪
			 * code : 20
			 * temperature : -1
			 */
			/**
			 * 天气现象文字
			 */
			public String text;
			/**
			 * 天气现象代码
			 */
			public String code;
			/**
			 * 温度，单位为c摄氏度或f华氏度
			 */
			public String temperature;

			@Override
			public String toString() {
				return "NowBean{" +
						"text='" + text + '\'' +
						", code='" + code + '\'' +
						", temperature='" + temperature + '\'' +
						'}';
			}
		}

		@Override
		public String toString() {
			return "ResultsBean{" +
					"location=" + location +
					", now=" + now +
					", last_update='" + last_update + '\'' +
					'}';
		}
	}

	@Override
	public String toString() {
		return "NowWeatherRes{" +
				"results=" + results +
				'}';
	}
}
