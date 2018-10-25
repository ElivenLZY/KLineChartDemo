package com.lzy.klinechartdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.blankj.utilcode.util.ThreadUtils;
import com.github.fujianlian.klinechart.KLineChartAdapter;
import com.github.fujianlian.klinechart.KLineChartView;
import com.github.fujianlian.klinechart.KLineEntity;
import com.github.fujianlian.klinechart.formatter.DateFormatter;
import com.lzy.klinechartdemo.widget.KLineMenuView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lzy
 * create at 2018/10/25 14:13
 **/
public class KLineActivity extends AppCompatActivity {

    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.tv_ratio)
    TextView tvRatio;
    @BindView(R.id.tv_high)
    TextView tvHigh;
    @BindView(R.id.tv_low)
    TextView tvLow;
    @BindView(R.id.tv_24hour)
    TextView tv24hour;
    @BindView(R.id.tab_kline)
    KLineMenuView tabKline;
    @BindView(R.id.kLineChartView)
    KLineChartView kLineChartView;

    private List<String> tabTitles = Arrays.asList("1分", "5分", "15分", "30分", "1时", "1天");

    private List<String> tabParams = Arrays.asList("1min", "5min", "15min", "30min", "60min", "1day");

    private String coinCode;

    private int defaultIndex = 0;

    private KLineChartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kline);
        ButterKnife.bind(this);
        coinCode = tabParams.get(defaultIndex);
        tabKline.setData(tabTitles);
        tabKline.setOnTabSelectListener(new KLineMenuView.OnTabSelectListener() {
            @Override
            public void onTabClick(int position) {
                defaultIndex = position;
                loadData();
            }
        });
        initKLineView();
        loadData();
    }

    private void initKLineView() {
        adapter = new KLineChartAdapter();
        kLineChartView.setAdapter(adapter);
        kLineChartView.setDateTimeFormatter(new DateFormatter());
        kLineChartView.setGridRows(4);
        kLineChartView.setGridColumns(4);
        kLineChartView.setSelectedYLineColor(getResources().getColor(R.color.chart_sel_y_color));
        kLineChartView.setSelectedYLineWidth(getResources().getDimension(R.dimen.dp_6));
    }

    private void loadData() {
        kLineChartView.justShowLoading();
        ThreadUtils.executeByCached(new ThreadUtils.Task<List<KLineEntity>>() {

            @Nullable
            @Override
            public List<KLineEntity> doInBackground() throws Throwable {
                return DataHelper.getData(tabParams.get(defaultIndex));
            }

            @Override
            public void onSuccess(@Nullable List<KLineEntity> result) {
                setData(result);
            }

            @Override
            public void onCancel() {
                kLineChartView.hideLoading();
            }

            @Override
            public void onFail(Throwable t) {
                kLineChartView.hideLoading();
            }
        });
    }

    private void setData(List<KLineEntity> kLineList) {
        if (adapter.getCount() == 0) {
            kLineChartView.startAnimation();
        }
        adapter.replaceData(kLineList);
        kLineChartView.refreshEnd();
    }

}
