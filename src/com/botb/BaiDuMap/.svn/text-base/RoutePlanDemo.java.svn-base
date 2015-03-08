package com.botb.BaiDuMap;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.botb.BaiDuMap.MapControlDemo;
import com.botb.activity.R;
/**
 * 此demo用来展示如何进行驾车、步行、公交路线搜索并在地图使用RouteOverlay、TransitOverlay绘制
 * 同时展示如何进行节点浏览并弹出泡泡
 *
 */
public class RoutePlanDemo extends Activity {

	//UI相关
	Button mBtnDrive = null;	// 驾车搜索
	Button mBtnTransit = null;	// 公交搜索
	Button mBtnWalk = null;	// 步行搜索
	//Button mBtnCusRoute = null; //自定义路线
	//Button mBtnCusIcon = null ; //自定义起终点图标
	
	//浏览路线节点相关
	Button mBtnPre = null;//上一个节点
	Button mBtnNext = null;//下一个节点
	int nodeIndex = -2;//节点索引,供浏览节点时使用
	MKRoute route = null;//保存驾车/步行路线数据的变量，供浏览节点时使用
	TransitOverlay transitOverlay = null;//保存公交路线图层数据的变量，供浏览节点时使用
	RouteOverlay routeOverlay = null; 
	boolean useDefaultIcon = false;
	int searchType = -1;//记录搜索的类型，区分驾车/步行和公交
	private PopupOverlay   pop  = null;//弹出泡泡图层，浏览节点时使用
	private TextView  popupText = null;//泡泡view
	private View viewCache = null;
	private int a=0;
	//地图相关，使用继承MapView的MyRouteMapView目的是重写touch事件实现泡泡处理
	//如果不处理touch事件，则无需继承，直接使用MapView即可
	MapView mMapView = null;	// 地图View
	//搜索相关
	MKSearch mSearch = null;	// 搜索模块，也可去掉地图模块独立使用
	
	// 定位相关
		LocationClient mLocClient;
		LocationData locData = null;
		public MyLocationListenner myListener = new MyLocationListenner();
		
		//定位图层
		locationOverlay myLocationOverlay = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        DemoApplication app = (DemoApplication)this.getApplication();
		setContentView(R.layout.routeplan);
		CharSequence titleLable="路线规划功能";
        setTitle(titleLable);
		//初始化地图
        mMapView = (MapView)findViewById(R.id.bmapView);
        mMapView.setBuiltInZoomControls(false);
        mMapView.getController().setZoom(12);
        mMapView.getController().enableClick(true);

        //初始化按键
        mBtnDrive = (Button)findViewById(R.id.drive);
        mBtnTransit = (Button)findViewById(R.id.transit);
        mBtnWalk = (Button)findViewById(R.id.walk);
        mBtnPre = (Button)findViewById(R.id.pre);
        mBtnNext = (Button)findViewById(R.id.next);
       // mBtnCusIcon = (Button)findViewById(R.id.customicon);
        mBtnPre.setVisibility(View.INVISIBLE);
		mBtnNext.setVisibility(View.INVISIBLE);
        //按键点击事件
        OnClickListener clickListener = new OnClickListener(){
			public void onClick(View v) {
				//发起搜索
				SearchButtonProcess(v);
			}
        };
        OnClickListener nodeClickListener = new OnClickListener(){
			public void onClick(View v) {
				//浏览路线节点
				nodeClick(v);
			}
        };


        //定位初始化
        mLocClient = new LocationClient( this );
        locData = new LocationData();
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
       
        
        mBtnDrive.setOnClickListener(clickListener); 
        mBtnTransit.setOnClickListener(clickListener); 
        mBtnWalk.setOnClickListener(clickListener);
        mBtnPre.setOnClickListener(nodeClickListener);
        mBtnNext.setOnClickListener(nodeClickListener);
       // mBtnCusRoute.setOnClickListener(customClickListener);
      //  mBtnCusIcon.setOnClickListener(changeRouteIconListener);
        //创建 弹出泡泡图层
        createPaopao();
       
        //地图点击事件处理
        mMapView.regMapTouchListner(new MKMapTouchListener(){

			@Override
			public void onMapClick(GeoPoint point) {
			  //在此处理地图点击事件 
			  //消隐pop
			  if ( pop != null ){
				  pop.hidePop();
			  }
			}

			@Override
			public void onMapDoubleClick(GeoPoint point) {
				
			}

			@Override
			public void onMapLongClick(GeoPoint point) {
				
			}
        	
        });
        // 初始化搜索模块，注册事件监听
        mSearch = new MKSearch();
        mSearch.init(app.mBMapManager, new MKSearchListener(){

			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
				//起点或终点有歧义，需要选择具体的城市列表或地址列表
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//遍历所有地址
				//	ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
				//	ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
				//	ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
				//	ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(RoutePlanDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
			
				searchType = 0;
			    routeOverlay = new RouteOverlay(RoutePlanDemo.this, mMapView);
			    // 此处仅展示一个方案作为示例
			    routeOverlay.setData(res.getPlan(0).getRoute(0));
			    //清除其他图层
			    mMapView.getOverlays().clear();
			    //添加路线图层
			    mMapView.getOverlays().add(routeOverlay);
			    //执行刷新使生效
			    mMapView.refresh();
			    // 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
			    mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
			    //移动地图到起点
			    mMapView.getController().animateTo(res.getStart().pt);
			    //将路线数据保存给全局变量
			    route = res.getPlan(0).getRoute(0);
			    //重置路线节点索引，节点浏览时使用
			    nodeIndex = -1;
			    mBtnPre.setVisibility(View.VISIBLE);
				mBtnNext.setVisibility(View.VISIBLE);
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
				//起点或终点有歧义，需要选择具体的城市列表或地址列表
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//遍历所有地址
//					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				if (error != 0 || res == null) {
					Toast.makeText(RoutePlanDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				
				searchType = 1;
				transitOverlay = new TransitOverlay (RoutePlanDemo.this, mMapView);
			    // 此处仅展示一个方案作为示例
			    transitOverlay.setData(res.getPlan(0));
			  //清除其他图层
			    mMapView.getOverlays().clear();
			  //添加路线图层
			    mMapView.getOverlays().add(transitOverlay);
			  //执行刷新使生效
			    mMapView.refresh();
			    // 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
			    mMapView.getController().zoomToSpan(transitOverlay.getLatSpanE6(), transitOverlay.getLonSpanE6());
			  //移动地图到起点
			    mMapView.getController().animateTo(res.getStart().pt);
			  //重置路线节点索引，节点浏览时使用
			    nodeIndex = 0;
			    mBtnPre.setVisibility(View.VISIBLE);
				mBtnNext.setVisibility(View.VISIBLE);
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
				//起点或终点有歧义，需要选择具体的城市列表或地址列表
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//遍历所有地址
//					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				if (error != 0 || res == null) {
					Toast.makeText(RoutePlanDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}

				searchType = 2;
				routeOverlay = new RouteOverlay(RoutePlanDemo.this, mMapView);
			    // 此处仅展示一个方案作为示例
				routeOverlay.setData(res.getPlan(0).getRoute(0));
				//清除其他图层
			    mMapView.getOverlays().clear();
			  //添加路线图层
			    mMapView.getOverlays().add(routeOverlay);
			  //执行刷新使生效
			    mMapView.refresh();
			    // 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
			    mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
			  //移动地图到起点
			    mMapView.getController().animateTo(res.getStart().pt);
			    //将路线数据保存给全局变量
			    route = res.getPlan(0).getRoute(0);
			    //重置路线节点索引，节点浏览时使用
			    nodeIndex = -1;
			    mBtnPre.setVisibility(View.VISIBLE);
				mBtnNext.setVisibility(View.VISIBLE);
			    
			}
			public void onGetAddrResult(MKAddrInfo res, int error) {
			}
			public void onGetPoiResult(MKPoiResult res, int arg1, int arg2) {
			}
			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

			@Override
			public void onGetPoiDetailSearchResult(int type, int iError) {
			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult result, int type,
					int error) {
				
			}
        });
        mLocClient.requestLocation();
        Log.i("12345",locData.latitude+""+locData.longitude);
        //定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
		//设置定位数据
	    myLocationOverlay.setData(locData);
	    //添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		//修改定位数据后刷新图层生效
		mMapView.refresh();
       // mMapView.getController().animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
	}
	//继承MyLocationOverlay重写dispatchTap实现点击处理
  	public class locationOverlay extends MyLocationOverlay{

  		public locationOverlay(MapView mapView) {
  			super(mapView);
  		}
  		
  	}
	
	
	/**
	 * 发起路线规划搜索示例
	 * @param v
	 */
	void SearchButtonProcess(View v) {
		//重置浏览节点的路线数据
		route = null;
		routeOverlay = null;
		transitOverlay = null; 
		mBtnPre.setVisibility(View.INVISIBLE);
		mBtnNext.setVisibility(View.INVISIBLE);
		// 处理搜索按钮响应
		//EditText editSt = (EditText)findViewById(R.id.start);
		
		// 对起点终点的name进行赋值，也可以直接对坐标赋值，赋值坐标则将根据坐标进行搜索
		MKPlanNode stNode = new MKPlanNode();
		//stNode.name = editSt.getText().toString();
		Bundle extras = getIntent().getExtras();
		
		
		//设置终点...............................................开始
		double  dimensionality= Double.parseDouble("38.889464");
		double  longitude= Double.parseDouble("117.164693");
		//设置终点...............................................结束
		int dimensionality_i = (int)(dimensionality*1000000);
		int longitude_i = (int)(longitude*1000000);
		Log.i("12345",dimensionality_i+"      "+longitude_i);
		
		//获取当前位置........................................................
		stNode.pt=new GeoPoint((int)(locData.latitude*1000000),(int)(locData.longitude*1000000));
		MKPlanNode enNode = new MKPlanNode();
		//enNode.name = editEn.getText().toString();
		  
		Log.i("12345",(int)(locData.latitude*1000000)+""+(int)(locData.longitude*1000000));
		enNode.pt=new GeoPoint(dimensionality_i,longitude_i);
 
		
		

		double distance = DistanceUtil.getDistance(new GeoPoint((int)(locData.latitude*1000000),(int)(locData.longitude*1000000)), new GeoPoint(dimensionality_i,longitude_i));
		
		// 实际使用中请对起点终点城市进行正确的设定
		if (mBtnDrive.equals(v)) {
			mSearch.drivingSearch("", stNode, "", enNode);
		} else if (mBtnTransit.equals(v)) {
			mSearch.transitSearch("天津", stNode,enNode);
		} else if (mBtnWalk.equals(v)) {
			mSearch.walkingSearch(null, stNode, null, enNode);
		} 
	}
	/**
	 * 节点浏览示例
	 * @param v
	 */
	public void nodeClick(View v){
		viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupText =(TextView) viewCache.findViewById(R.id.textcache);
		if (searchType == 0 || searchType == 2){
			//驾车、步行使用的数据结构相同，因此类型为驾车或步行，节点浏览方法相同
			if (nodeIndex < -1 || route == null || nodeIndex >= route.getNumSteps())
				return;
			
			//上一个节点
			if (mBtnPre.equals(v) && nodeIndex > 0){
				//索引减
				nodeIndex--;
				//移动到指定索引的坐标
				mMapView.getController().animateTo(route.getStep(nodeIndex).getPoint());
				//弹出泡泡
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						route.getStep(nodeIndex).getPoint(),
						5);
			}
			//下一个节点
			if (mBtnNext.equals(v) && nodeIndex < (route.getNumSteps()-1)){
				//索引加
				nodeIndex++;
				//移动到指定索引的坐标
				mMapView.getController().animateTo(route.getStep(nodeIndex).getPoint());
				//弹出泡泡
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						route.getStep(nodeIndex).getPoint(),
						5);
			}
		}
		if (searchType == 1){
			//公交换乘使用的数据结构与其他不同，因此单独处理节点浏览
			if (nodeIndex < -1 || transitOverlay == null || nodeIndex >= transitOverlay.getAllItem().size())
				return;
			
			//上一个节点
			if (mBtnPre.equals(v) && nodeIndex > 1){
				//索引减
				nodeIndex--;
				//移动到指定索引的坐标
				mMapView.getController().animateTo(transitOverlay.getItem(nodeIndex).getPoint());
				//弹出泡泡
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						transitOverlay.getItem(nodeIndex).getPoint(),
						5);
			}
			//下一个节点
			if (mBtnNext.equals(v) && nodeIndex < (transitOverlay.getAllItem().size()-2)){
				//索引加
				nodeIndex++;
				//移动到指定索引的坐标
				mMapView.getController().animateTo(transitOverlay.getItem(nodeIndex).getPoint());
				//弹出泡泡
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						transitOverlay.getItem(nodeIndex).getPoint(),
						5);
			}
		}
		
	}
	/**
	 * 创建弹出泡泡图层
	 */
	public void createPaopao(){
		
        //泡泡点击响应回调
        PopupClickListener popListener = new PopupClickListener(){
			@Override
			public void onClickedPopup(int index) {
				Log.v("click", "clickapoapo");
			}
        };
        pop = new PopupOverlay(mMapView,popListener);
	}
	
	
	/**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            locData.accuracy = location.getRadius();
            locData.direction = location.getDerect();
            if(a==0){
                a++;            	
            	mMapView.getController().animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
            }
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
	

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }
    @Override
    protected void onDestroy() {
    	//退出时销毁定位
        if (mLocClient != null)
            mLocClient.stop();
        mMapView.destroy();
        super.onDestroy();
    }
	
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mMapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	mMapView.onRestoreInstanceState(savedInstanceState);
    }
}
