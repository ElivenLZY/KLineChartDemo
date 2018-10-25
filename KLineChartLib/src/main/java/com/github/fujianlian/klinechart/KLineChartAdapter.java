package com.github.fujianlian.klinechart;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据适配器
 * Created by tifezh on 2016/6/18.
 */
public class KLineChartAdapter extends BaseKLineChartAdapter {

    private List<KLineEntity> datas = new ArrayList<>();

    public KLineChartAdapter() {

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public String getDate(int position) {
        return datas.get(position).Date;
    }

    /**
     * 全部替换为新的数据
     */
    public void replaceData(List<KLineEntity> data) {
        synchronized (this) {
            datas.clear();
            if (data != null && data.size() > 0) datas.addAll(data);
            DataHelper.calculate(datas, 0, datas.size(), false);
        }
        notifyDataSetChanged();

    }

    /**
     * 向右边头部添加新数据
     */
    public void addHeaderData(List<KLineEntity> data) {
        if (data == null) data = new ArrayList<>();
        synchronized (this) {
            int oldDataSize = getCount();
            datas.addAll(data);
            DataHelper.calculate(datas, oldDataSize, datas.size(), false);
        }
        notifyDataSetChanged();

    }

    /**
     * 向左边尾部添加旧数据
     */
    public void addFooterData(List<KLineEntity> data) {
        if (data == null) data = new ArrayList<>();
        synchronized (this) {
            datas.addAll(0, data);
            DataHelper.calculate(datas, 0, data.size(), true);
        }
        notifyDataSetChanged();
    }

    /**
     * 改变某个点的值
     *
     * @param position 索引值
     */
    public void changeItem(int position, KLineEntity data) {
        synchronized (this) {
            datas.set(position, data);
        }
        notifyDataSetChanged();
    }

    /**
     * 数据清除
     */
    public void clearData() {
        synchronized (this) {
            datas.clear();
        }
        notifyDataSetChanged();
    }
}
