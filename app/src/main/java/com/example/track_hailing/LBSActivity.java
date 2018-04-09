package com.example.track_hailing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LBSActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    //AMap是地图对象
    private AMap aMap;
    private MapView mapView;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;


    private DrawerLayout mDrawerLayout;
    private ImageButton createBtn;
    private TextView topLineTv,et_name, now, future, direct, time, time2, reduce, add, tv_num, add_order;
    public static int screenW, screenH;
    private TopMiddlePopup middlePopup;
    private ArrayList<String> items = new ArrayList<String>();
    private List<Car> carList = new ArrayList<Car>();
    private LinearLayout linearLayout, linear_time, direct_linear_1, direct_linear_2, linear_order;
    private EditText search_City,origin,destination;
    private FloatingActionButton fab;
    private FrameLayout frame_float, linear_price;


    private double mEndLng = 0.0;
    private double mEndLat = 0.0;


    private String originDate, destinationDate;

    private String iCity,iStreet,iDistrict,iStreet_Num;

    private boolean KeyTrue;


    private Myapplication myapplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_lbs);

        //初始化
        //getScreenPixels();
        //setItemsName();

        //用户界面
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_location:
                        Toast.makeText(LBSActivity.this, "location", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_call:
                        Toast.makeText(LBSActivity.this, "15967186973", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_mail:
                        Toast.makeText(LBSActivity.this, "837468381@qq.com", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_friends:
                        Toast.makeText(LBSActivity.this, "you are my friend", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_task:
                        Toast.makeText(LBSActivity.this, "Orders", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LBSActivity.this,OrderActivity.class));
                        break;
                    case R.id.nav_store:
                        Toast.makeText(LBSActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
                return true;
            }
        });


        //初始化
        createBtn = (ImageButton) findViewById(R.id.spinner);
        //topLineTv = (TextView) findViewById(R.id.rule_line_tv);
        et_name = (TextView) findViewById(R.id.et_name);
        //linearLayout = (LinearLayout) findViewById(R.id.linear1);
        //search_City = (EditText) findViewById(R.id.keyword_edittext);
        origin = (EditText) findViewById(R.id.origin);
        destination = (EditText) findViewById(R.id.destination);
        now = (TextView) findViewById(R.id.now);
        future = (TextView) findViewById(R.id.future);
        linear_time = (LinearLayout) findViewById(R.id.linear_time);
        time = (TextView) findViewById(R.id.time);
        time2 = (TextView) findViewById(R.id.time2);
        direct = (TextView) findViewById(R.id.direct);
        reduce = (TextView) findViewById(R.id.tv_reduce);
        add = (TextView) findViewById(R.id.tv_add);
        tv_num = (TextView) findViewById(R.id.tv_num);
        add_order = (TextView) findViewById(R.id.add_order);
        direct_linear_1 = (LinearLayout) findViewById(R.id.direct_linear_1);
        direct_linear_2 = (LinearLayout) findViewById(R.id.direct_linear_2);
        linear_order = (LinearLayout) findViewById(R.id.linear_order);
        frame_float = (FrameLayout) findViewById(R.id.frame_float);
        linear_price = (FrameLayout) findViewById(R.id.linear_price);


        myapplication = (Myapplication) getApplication();
        et_name.setText(myapplication.getCity());


        fab = (FloatingActionButton) findViewById(R.id.fab);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        time.setText(simpleDateFormat.format(date));

        //设置弹窗以及监听器
        //setPopup(0);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LBSActivity.this, SearchActivity.class));
                //middlePopup.show(topLineTv);
                //linearLayout.setVisibility(View.VISIBLE);
                //et_name.setText(middlePopup.getCity());
            }
        });


        origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myapplication.setChoose(true);
                startActivity(new Intent(LBSActivity.this, PoiKeywordSearchActivity.class));
            }
        });

        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myapplication.setChoose(false);
                startActivity(new Intent(LBSActivity.this, PoiKeywordSearchActivity.class));
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开高德地图，进行导航
                mEndLat = myapplication.getLat();
                mEndLng = myapplication.getLon();
                if (mEndLng != 0.0 && mEndLat != 0.0) {
                    //移动APP调起Android高德地图方式
                    Intent intent = new Intent("android.intent.action.VIEW"
                            , android.net.Uri.parse("androidamap://navi?sourceApplication=税源地图&lat="
                            + mEndLat + "&lon=" + mEndLng + "&dev=0&style=2"));
                    intent.setPackage("com.autonavi.minimap");
                    if (isInstallByread("com.autonavi.minimap")) {
                        startActivity(intent); // 启动调用
                    }
                }else {
                    ToastUtil.show(getBaseContext(),"终点坐标不明确，请确认");
                }
            }
        });

        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// HH:mm:ss
                //获取当前时间
                Date date = new Date(System.currentTimeMillis());
                linear_order.setVisibility(View.VISIBLE);
                linear_price.setVisibility(View.VISIBLE);
                linear_time.setVisibility(View.VISIBLE);
                time.setEnabled(false);
                time.setText(simpleDateFormat.format(date));
                direct_linear_1.setVisibility(View.GONE);
                direct_linear_2.setVisibility(View.GONE);
                frame_float.setVisibility(View.GONE);
            }
        });

        future.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_order.setVisibility(View.VISIBLE);
                linear_price.setVisibility(View.VISIBLE);
                linear_time.setVisibility(View.VISIBLE);
                time.setEnabled(true);
                time.setText("取车时间");
                direct_linear_1.setVisibility(View.GONE);
                direct_linear_2.setVisibility(View.GONE);
                frame_float.setVisibility(View.GONE);
            }
        });

        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_order.setVisibility(View.GONE);
                linear_price.setVisibility(View.GONE);
                linear_time.setVisibility(View.GONE);
                direct_linear_1.setVisibility(View.VISIBLE);
                direct_linear_2.setVisibility(View.VISIBLE);
                frame_float.setVisibility(View.VISIBLE);
            }
        });

        //参考https://blog.csdn.net/u013148839/article/details/52191183
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog=   new DateDialog(LBSActivity.this,"获取当前时间日期", DateDialog.MODE_1, new DateDialog.InterfaceDateDialog() {
                    @Override
                    public void getTime(String dateTime) {
                        Toast.makeText(LBSActivity.this,dateTime,Toast.LENGTH_LONG).show();
                        originDate = dateTime;
                        time.setText(dateTime);
                    }
                } );
                dateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dateDialog.show();
            }
        });
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog=   new DateDialog(LBSActivity.this,"获取当前时间日期", DateDialog.MODE_1, new DateDialog.InterfaceDateDialog() {
                    @Override
                    public void getTime(String dateTime) {
                        Toast.makeText(LBSActivity.this,dateTime,Toast.LENGTH_LONG).show();
                        destinationDate = dateTime;
                        time2.setText(dateTime);
                    }
                } );
                dateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dateDialog.show();
            }
        });

        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = Integer.parseInt(tv_num.getText().toString());
                temp--;
                if(temp > 0){
                    tv_num.setText(temp + "");
                }else{
                    Toast.makeText(LBSActivity.this,"购买数量必须大于0",Toast.LENGTH_LONG).show();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = Integer.parseInt(tv_num.getText().toString());
                temp++;
                if(temp < 31){
                    tv_num.setText(temp + "");
                }else{
                    Toast.makeText(LBSActivity.this,"租赁天数不得超过一个月",Toast.LENGTH_LONG).show();
                }
            }
        });

        add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestMethod_Add();
            }
        });





        initCars();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.car_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        CarAdapter adapter = new CarAdapter(carList);
        recyclerView.setAdapter(adapter);



        /*middlePopup.getMyLv().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                et_name.setText(items.get(i));
                linearLayout.setVisibility(View.GONE);
                middlePopup.dismiss();
            }
        });*/

        /*search_City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                middlePopup.show(topLineTv);
            }
        });*/

        //顶部menu
        TopBar topBar = (TopBar) findViewById(R.id.topbar);
        topBar.setOnLeftAndRightClickListener(new TopBar.OnLeftAndRightClickListener() {
            @Override
            public void OnLeftButtonClick() {
                finish();//左边按钮实现的功能逻辑
            }

            @Override
            public void OnRightButtonClick() {
                //右边按钮实现的功能逻辑
                mDrawerLayout.openDrawer(GravityCompat.END);
            }
        });



        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            //设置显示定位按钮 并且可以点击
            UiSettings settings = aMap.getUiSettings();
            aMap.setLocationSource(this);//设置了定位的监听
            // 是否显示定位按钮
            settings.setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        }
        //开始定位
        location();

    }



    private void initCars() {
        for (int i = 0; i < 1; i++) {
            Car racing_bike = new Car("跑车");
            carList.add(racing_bike);
            Car truck = new Car("卡车");
            carList.add(truck);
            Car private_car = new Car("私家车");
            carList.add(private_car);
            Car van = new Car("厢式货车");
            carList.add(van);
            Car crane = new Car("起重机");
            carList.add(crane);
            Car tractor = new Car("拖拉机");
            carList.add(tractor);
            Car off_road_vehicle = new Car("越野车");
            carList.add(off_road_vehicle);
        }
    }

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    @Override
    protected void onResume() {
        super.onResume();
        myapplication = (Myapplication) getApplication();

        et_name.setText(myapplication.getCity());
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        origin.setText(myapplication.getOrigin());
        destination.setText(myapplication.getDestination());

        //mapView.onResume();
    }

    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName)
                .exists();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                iCity = aMapLocation.getCity();//城市信息
                iDistrict = aMapLocation.getDistrict();//城区信息
                iStreet = aMapLocation.getStreet();//街道信息
                iStreet_Num = aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码

                origin.setText(iCity + ""
                        + iDistrict + ""
                        + iStreet + ""
                        + iStreet_Num);

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);
                    //添加图钉
                    //  aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }







    //访问数据库，进行insert操作
    private void sendRequestMethod_Add() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //这里的nameSpace用webserver上的nameSpace
                String nameSpace = "http://localhost/";
                String serviceURL = "http://192.168.43.90/Service.asmx";
                String methodName = "AddOrder";
                HashMap<String, Object> UserIdex= new HashMap<String, Object>();
                UserIdex.put("OrId", "000002");
                UserIdex.put("OrName", "卡车");
                UserIdex.put("OrPrice", 300);
                UserIdex.put("OrDayNum", Integer.parseInt(tv_num.getText().toString()));
                UserIdex.put("OrOriginDate", originDate);
                UserIdex.put("OrDestinateDate", destinationDate);
                UserIdex.put("OrCarAddress", "浙江工商大学");
                UserIdex.put("OrStatus", "未完成");
                UserIdex.put("UId", myapplication.getId());
                try{
                    KeyTrue = Boolean.parseBoolean(SoapObjectUser.call(serviceURL, nameSpace, methodName, UserIdex).toString());
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        showResponse();
    }

    private void showResponse(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (KeyTrue) {

                    Toast.makeText(LBSActivity.this, "订单生成成功",
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LBSActivity.this,OrderActivity.class));
                } else {
                    Toast.makeText(LBSActivity.this, "订单生成失败",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    /**
     * 设置弹窗
     *
     * @param type
     */
    /*private void setPopup(int type) {
        middlePopup = new TopMiddlePopup(LBSActivity.this, screenW, screenH,
                onItemClickListener, items, type);
    }*/

    /**
     * 设置弹窗内容
     *
     * @return
     */
    /*private void setItemsName() {
        items.add("企业客户");
        items.add("集团客户");
        items.add("公海客户");
    }*/



    /**
     * 弹窗点击事件
     */
    /*private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            linearLayout.setVisibility(View.GONE);
            middlePopup.dismiss();
        }
    };*/

    /**
     * 获取屏幕的宽和高
     */
    /*public void getScreenPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;
    }*/

}
