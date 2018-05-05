package com.helixs.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.helixs.api.Api;
import com.helixs.base.BaseActivity;
import com.helixs.model.DailyWeatherRes;
import com.helixs.model.NowWeatherRes;
import com.helixs.net.AppNetCallBack;
import com.helixs.tools.AppPrefs;
import com.helixs.tools.BaseViewModel;
import com.helixs.tools.CollectionUtil;
import com.helixs.tools.Pop;
import com.helixs.tools.R;
import com.helixs.ui.main.binding.MainBinding;
import com.helixs.view.daily.DailyView;
import com.helixs.weather.App;
import com.helixs.weather.AppLocation;
import com.helixs.weather.LocationBroadcastReciver;

import java.util.List;


public class WeatherActivity extends BaseActivity {


    private MainBinding mMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initToolbar(true);
        mMainBinding.setInfo(new MainInfo());
        mMainBinding.getInfo().load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_city,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_city_name){
            Pop.toast("请选择地区");
        }
        return super.onOptionsItemSelected(item);
    }

    @BindingAdapter("datas")
    public static void setAdapter(LinearLayout linearLayout, List<DailyWeatherRes.DailyBean> lists) {
        if (linearLayout != null && !CollectionUtil.isEmpty(lists)) {
            int childCount = linearLayout.getChildCount();
            if (childCount != lists.size()) {
                linearLayout.removeAllViews();

                for (DailyWeatherRes.DailyBean dailyBean : lists) {
                    DailyView dailyView = new DailyView(linearLayout.getContext());
                    dailyView.setData(dailyBean);
                    dailyView.setTag(dailyBean.date);
                    LinearLayout.LayoutParams  layoutParams =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    linearLayout.addView(dailyView,layoutParams);

                }
            } else {
                for (int i = 0; i < lists.size(); i++) {
                    DailyView productView = (DailyView) linearLayout.getChildAt(i);
                    DailyWeatherRes.DailyBean dailyBean = lists.get(i);
                    productView.setData(dailyBean);
                    productView.setTag(dailyBean.date);
                }
            }
        }
    }

    public static class MainInfo extends BaseViewModel {
        public ObservableField<String> path = new ObservableField<>("暂无数据");
        public ObservableField<String> country = new ObservableField<>("暂无数据");
        public ObservableField<String> timezone = new ObservableField<>("暂无数据");
        public ObservableField<String> timeOffset = new ObservableField<>("暂无数据");
        public ObservableField<String> nowWeather = new ObservableField<>("暂无数据");
        public ObservableField<String> temperature = new ObservableField<>("暂无数据");
        public ObservableField<String> nowLastUpdate = new ObservableField<>("暂无数据");
        public ObservableField<String> dayLastUpdate = new ObservableField<>("暂无数据");
        public ObservableField<List<DailyWeatherRes.DailyBean>> dailys = new ObservableField<>();


        private BroadcastReceiver location = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String ip = intent.getStringExtra(LocationBroadcastReciver.IP_ADRESS);
                requstNow(ip);
                requestDaily(ip);
            }
        };

        public void load() {
            IntentFilter dynamicFilter = new IntentFilter();
            dynamicFilter.addAction(LocationBroadcastReciver.ACTION);//添加动态广博Action
            App.getContext().registerReceiver(location, dynamicFilter);
            Pop.toast("加载中");
            if (TextUtils.isEmpty(AppPrefs.get().ipAdressPref.get())) {
                AppLocation.getIpAdress();
            } else {
                requstNow(AppPrefs.get().ipAdressPref.get());
                requestDaily(AppPrefs.get().ipAdressPref.get());
            }

        }

        private void requstNow(String ipAdress) {

            Api.getNowWeatherForGPS(ipAdress, new AppNetCallBack<NowWeatherRes>() {
                @Override
                public void ok(NowWeatherRes nowWeatherRes) {
                    Pop.toast("NowWeatherRes，ok");
                    if (nowWeatherRes != null && !CollectionUtil.isEmpty(nowWeatherRes.results)) {
                        NowWeatherRes.ResultsBean resultsBean = nowWeatherRes.results.get(0);
                        path.set(resultsBean.location.path);
                        timezone.set(resultsBean.location.timezone);
                        timeOffset.set(resultsBean.location.timezone_offset);
                        country.set(resultsBean.location.country);
                        nowWeather.set(resultsBean.now.text);
                        temperature.set(resultsBean.now.temperature);
                        nowLastUpdate.set(resultsBean.last_update);


                    } else {
                        Pop.toast("数据为空");
                    }
                }

                @Override
                public void netFailure() {
                    Pop.toast("请求实时天气数据发生错误");
                    super.netFailure();
                }

                @Override
                public void onCallBack() {
                    super.onCallBack();
                }
            });
        }

        private void requestDaily(String ip) {
            Api.getDailyWeatherFor(ip, new AppNetCallBack<DailyWeatherRes>() {
                @Override
                public void ok(DailyWeatherRes dailyWeatherRes) {
                    Pop.toast("DailyWeatherRes，ok");
                    if (dailyWeatherRes != null && !CollectionUtil.isEmpty(dailyWeatherRes.results)) {
                        DailyWeatherRes.ResultsBean resultsBean = dailyWeatherRes.results.get(0);
                        dayLastUpdate.set(resultsBean.last_update);
                        if (!CollectionUtil.isEmpty(resultsBean.daily)) {
                            dailys.set(resultsBean.daily);
                        }
                    }
                }

                @Override
                public void netFailure() {
                    Pop.toast("请求未来天气数据发生错误");
                    super.netFailure();
                }
            });
        }
    }
}
