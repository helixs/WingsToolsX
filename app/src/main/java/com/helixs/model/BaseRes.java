package com.helixs.model;

import java.io.Serializable;

/**
 * Created by helixs on 2017/2/21.
 */

public abstract class BaseRes implements Serializable {

	public String status;
	public String status_code;

	public String getStatus() {
		if (status_code != null) {
			switch (status_code) {
				case "AP010001":
					return "API请求参数错误";
				case "AP010002":
					return "没有权限访问这个API接口。在此查看你有权访问的API接口。";
				case "AP010003":
					return " API密钥key错误。";
				case "AP010004":
					return " 签名错误。";
				case "AP010005 ":
					return "你请求的API不存在。";
				case "AP010006":
					return " 没有权限访问这个地点。";
				case "AP010007":
					return "JSONP请求需要使用签名验证方式";
				case "AP010008":
					return " 没有绑定域名。在此绑定域名。";
				case "AP010009":
					return " API请求的user -agent与你设置的不一致。";
				case "AP010010":
					return " 没有这个地点。";
				case "AP010011":
					return " 无法查找到制定IP地址对应的城市。";
				case "AP010012":
					return " 你的服务已经过期。在此续费。";
				case "AP010013":
					return "访问量余额不足。联系客服购买更多访问量。";
				case "AP010014":
					return "免费用户超过了每小时访问量额度。一小时后自动恢复。";
				case "AP010015":
					return " 暂不支持该城市的车辆限行信息。";
				case "AP100001":
					return " 系统内部错误：数据缺失。";
				case "AP100002":
					return " 系统内部错误：数据错误。";
				case "AP100003":
					return " 系统内部错误：服务内部错误。";


			}
		}
		return "未获取到状态";
	}
	public static class LocationBean {
		/**
		 * id : WX4FBXXFKE4F
		 * name : 北京
		 * country : CN
		 * path : 北京,北京,中国
		 * timezone : Asia/Shanghai
		 * timezone_offset : +08:00
		 */
		/**
		 * 地区id
		 */
		public String id;
		/**
		 * name
		 */
		public String name;
		public String country;
		/**
		 * 地区
		 */
		public String path;
		/**
		 * 时区
		 */
		public String timezone;
		/**
		 * 时区差
		 */
		public String timezone_offset;

		@Override
		public String toString() {
			return "LocationBean{" +
					"id='" + id + '\'' +
					", name='" + name + '\'' +
					", country='" + country + '\'' +
					", path='" + path + '\'' +
					", timezone='" + timezone + '\'' +
					", timezone_offset='" + timezone_offset + '\'' +
					'}';
		}
	}


}
