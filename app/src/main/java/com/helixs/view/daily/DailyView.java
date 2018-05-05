package com.helixs.view.daily;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.helixs.model.DailyWeatherRes;
import com.helixs.tools.BaseViewModel;
import com.helixs.tools.R;
import com.helixs.ui.main.binding.DailyBinding;

/**
 * Created by helixs on 2017/3/7.
 */

public class DailyView extends LinearLayout {
    private DailyBinding dailyBinding;

    public DailyView(Context context) {
        this(context, null);

    }

    public DailyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DailyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DailyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        dailyBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_card, this, true);
        dailyBinding.setInfo(new DailyModel());
    }

    public void setData(DailyWeatherRes.DailyBean dailyBean) {
        dailyBinding.getInfo().load(dailyBean);
    }

    public static class DailyModel extends BaseViewModel {
        public ObservableField<String> date = new ObservableField<>("暂无数据");
        public ObservableField<String> dayWeather = new ObservableField<>("暂无数据");
        public ObservableField<String> nightWeather = new ObservableField<>("暂无数据");
        public ObservableField<String> temperatureHigh = new ObservableField<>("暂无数据");
        public ObservableField<String> temperatureLow = new ObservableField<>("暂无数据");
        public ObservableField<String> precipitationProbability = new ObservableField<>("暂无数据");
        public ObservableField<String> direction = new ObservableField<>("暂无数据");
        public ObservableField<String> directionDegree = new ObservableField<>("暂无数据");
        public ObservableField<String> windSpeed = new ObservableField<>("暂无数据");
        public ObservableField<String> windScale = new ObservableField<>("暂无数据");

        public void load(DailyWeatherRes.DailyBean dailyBean) {
            if (dailyBean != null) {
                date.set(dailyBean.date);
                dayWeather.set(dailyBean.text_day);
                nightWeather.set(dailyBean.text_night);
                temperatureHigh.set(dailyBean.high);
                temperatureLow.set(dailyBean.low);
                precipitationProbability.set(dailyBean.precip);
                direction.set(dailyBean.wind_direction);
                directionDegree.set(dailyBean.wind_direction_degree);
                windSpeed.set(dailyBean.wind_speed);
                windScale.set(dailyBean.wind_scale);
            }
        }
    }
}
