package com.example.track_hailing;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private List<Orders> itemList1 = new ArrayList<Orders>();

    private Myapplication myapplication;

    public String UserId;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_order);

        //得到UserId这个整个app的全局变量
        myapplication = (Myapplication) getApplication();
        UserId = myapplication.getId();


        //用户界面
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_location:
                        Toast.makeText(OrderActivity.this, "location", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_call:
                        Toast.makeText(OrderActivity.this, "15967186973", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_mail:
                        Toast.makeText(OrderActivity.this, "837468381@qq.com", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_friends:
                        Toast.makeText(OrderActivity.this, "you are my friend", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_task:
                        Toast.makeText(OrderActivity.this, "Orders", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OrderActivity.this,OrderActivity.class));
                        break;
                    case R.id.nav_store:
                        Toast.makeText(OrderActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
                return true;
            }
        });

        sendRequestMethod();

        //适配器，listview
        ListView listView1 = (ListView) findViewById(R.id.order_list);
        OrdersAdapter adapter = new OrdersAdapter(OrderActivity.this, R.layout.order_item, itemList1);

        listView1.setAdapter(adapter);


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

    }


    private void sendRequestMethod() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //这里的nameSpace用webserver上的nameSpace
                String nameSpace = "http://localhost/";
                String serviceURL = "http://192.168.43.90/Service.asmx";
                String methodName = "UserId2Orders";
                //存储网络请求时的参数信息
                HashMap<String, Object> UserIdex= new HashMap<String, Object>();
                UserIdex.put("UserId", UserId);
                try{
                    SoapObject result = (SoapObject)SoapObjectUser.call(serviceURL, nameSpace, methodName, UserIdex);
                    SoapObject child = (SoapObject)((SoapObject)result.getProperty(1)).getProperty(0);
                    for(int i=0;i<child.getPropertyCount();i++) {
                        SoapObject item = (SoapObject) child.getProperty(i);
                        String[] temp = item.getProperty("OrOriginDate").toString().split("T");
                        String[] temp2 = temp[1].split("\\+");
                        String[] temp3 = item.getProperty("OrDestinateDate").toString().split("T");
                        String[] temp4 = temp3[1].split("\\+");
                        Orders orders = new Orders(item.getProperty("OrId").toString(),
                                item.getProperty("OrName").toString(),
                                Double.parseDouble(item.getProperty("OrPrice").toString()),
                                Integer.parseInt(item.getProperty("OrDayNum").toString()),
                                temp[0] + " " + temp2[0],
                                temp3[0] + " " + temp4[0],
                                item.getProperty("OrCarAddress").toString(),
                                item.getProperty("OrStatus").toString());

                        //将得到的信息加入到list中
                        itemList1.add(orders);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
