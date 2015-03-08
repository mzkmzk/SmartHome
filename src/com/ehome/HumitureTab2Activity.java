package com.ehome;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;

public class HumitureTab2Activity extends AbstractDemoChart{
	  private static final long HOUR = 3600*1000;

	  private static final long DAY = HOUR * 24;

	  private static final int HOURS = 24;

	@Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      String[] titles = new String[] { "空气质量指数（AQI）" };
      
      long now = Math.round(new Date().getTime() / DAY) * DAY;
      
      List<Date[]> x = new ArrayList<Date[]>();
      
      for (int i = 0; i < titles.length; i++) {
        Date[] dates = new Date[HOURS];
        for (int j = 0; j < HOURS; j++) {
          dates[j] = new Date(now - (HOURS - j) * HOUR);
        }
        x.add(dates);
      }
      
//      double[] panLimits={x.get(0)[0].getTime(), x.get(0)[HOURS - 1].getTime()};
      
      List<double[]> values = new ArrayList<double[]>();

      values.add(new double[] { 61,58,59,60,66,63,61,62,68,68,62,64,80,93,110,121,129,128,133,103,87,79,62,52 });//每个序列中点的Y坐标
      
     /* values.add(new double[] { 1.9, 1.2, 0.9, 0.5, 0.1, -0.5, -0.6, MathHelper.NULL_VALUE,
          MathHelper.NULL_VALUE, -1.8, -0.3, 1.4, 3.4, 4.9, 7.0, 6.4, 3.4, 2.0, 1.5, 0.9, -0.5,
          MathHelper.NULL_VALUE, -1.9, -2.5, -4.3 });*/

     // int[] colors = new int[] { Color.GREEN, Color.BLUE };
      int[] colors = new int[] { Color.GREEN };
      //PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND };
      
      PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };//每个序列中点的形状设置
      
      XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);//调用AbstractDemoChart中的方法设置renderer.  
      int length = renderer.getSeriesRendererCount();
     
      for (int i = 0; i < length; i++) {
        ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);//设置图上的点为实心  
      }
      
      setChartSettings(renderer, "空气质量统计图", "HOUR ", "",x.get(0)[0].getTime(), x.get(0)[HOURS - 1].getTime(), 0, 140, Color.WHITE, Color.WHITE);
      
      renderer.setXLabels(10);//设置x轴显示10个点,根据setChartSettings的最大值和最小值自动计算点的间隔  
      renderer.setYLabels(10);
      renderer.setShowGrid(true);//是否显示网格
      renderer.setXLabelsAlign(Align.CENTER);
      renderer.setYLabelsAlign(Align.RIGHT);//刻度线与刻度标注之间的相对位置关系
//      renderer.setPanLimits(panLimits);
      
      renderer.setZoomButtonsVisible(true);//是否显示放大缩小按钮  
      
      View view = ChartFactory.getTimeChartView(this, buildDateDataset(titles, x, values), renderer, "h:mm a"); //Type.STACKED
      view.setBackgroundColor(Color.BLACK);
      setContentView(view);
  }

}
