package com.ehome;


import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;

public class HumitureTabActivity extends AbstractDemoChart{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		String[] titles = new String[] { "温度  ℃" };
	    
		List<double[]> x = new ArrayList<double[]>();
	    for (int i = 0; i < titles.length; i++) {
	      x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
	    }
	    
	    List<double[]> values = new ArrayList<double[]>();
	    values.add(new double[] { 32, 30, 33, 33, 32, 30, 34, 31, 33, 31, 32,27 });
	    
	    int[] colors = new int[] { Color.RED, Color.YELLOW };
	    
	    PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT };
	    
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(2);
	    setRenderer(renderer, colors, styles);
	    
	    int length = renderer.getSeriesRendererCount();
	    for (int i = 0; i < length; i++) {
	      XYSeriesRenderer r = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
	      r.setLineWidth(3f);
	    }
	    
	    setChartSettings(renderer, "温湿度统计图", "Day", "", 1, 12, 0, 35,Color.LTGRAY, Color.LTGRAY);
	    
	    renderer.setChartTitleTextSize(30);//设置整个图表标题文字的大小
		renderer.setAxisTitleTextSize(25);//设置轴标题文字的大小

	    renderer.setXLabels(12);  //设置X轴显示的刻度标签的个数
	    renderer.setYLabels(10);
	    
	    renderer.setShowGrid(true);  //设置是否在图表中显示网格
	    
	    renderer.setXLabelsAlign(Align.RIGHT);  //设置刻度线与X轴之间的相对位置关系
	    renderer.setYLabelsAlign(Align.RIGHT);	
	    
	    renderer.setZoomButtonsVisible(true);	//设置可以缩放
	    
	    renderer.setMargins(new int[] { 60, 30, 20, 30 });//设置图表的外边框(上/左/下/右)
	    renderer.setPanLimits(new double[] { 1, 20, 1, 35 });//设置拉动的范围
	    renderer.setZoomLimits(new double[] { 30, 20, 1, 100 });//设置缩放的范围

//	    renderer.setRange(newdouble[]{0d, 5d, 0d, 100d}); //设置chart的视图范围
	    renderer.setFitLegend(true);// 调整合适的位置
//	    renderer.setClickEnabled(true);//设置是否可以滑动及放大缩小;
	    
	    renderer.setZoomRate(1.05f);
	    
	    renderer.setLabelsColor(Color.WHITE);
	    renderer.setXLabelsColor(Color.WHITE);
	    renderer.setYLabelsColor(0, colors[0]);
	    renderer.setYLabelsColor(1, colors[1]);

	    renderer.setYTitle("", 1);
	    renderer.setYAxisAlign(Align.RIGHT, 1);
	    renderer.setYLabelsAlign(Align.LEFT, 1);

	    XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
	    values.clear();
	    values.add(new double[] { 67, 67, 70, 69, 71, 73, 64,63, 61, 65, 65, 71 });
	    addXYSeries(dataset, new String[] { "相对空气湿度 %" }, x, values, 1);

	    View view = ChartFactory.getCubeLineChartView(this, dataset, renderer, 0.3f); //Type.STACKED
	    view.setBackgroundColor(Color.BLACK);
	    setContentView(view);
	    
	}
	
}
