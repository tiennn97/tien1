package com.tiennn.appbandoanvanphong.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.tiennn.appbandoanvanphong.R;
import com.tiennn.appbandoanvanphong.adapter.LoaispAdapter;
import com.tiennn.appbandoanvanphong.adapter.SanphamAdapter;
import com.tiennn.appbandoanvanphong.model.Giohang;
import com.tiennn.appbandoanvanphong.model.Loaisp;
import com.tiennn.appbandoanvanphong.model.Sanpham;
import com.tiennn.appbandoanvanphong.ultit.CheckConnection;
import com.tiennn.appbandoanvanphong.ultit.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewtrangchinh;
    NavigationView navigationView;
    ListView listViewtrangchinh;

    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;


    int id = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";

    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;

    public static ArrayList<Giohang> manggiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Anhxa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
            GetDuLieuSPMoiNhat();
            CatchOnItemListView();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Không có kết nối mạng -_-");
            finish();
        }



    }

    private void CatchOnItemListView() {
        listViewtrangchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"k co mang");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DoAnActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"k co mang");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DoUongActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"k co mang");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MonAnUaThichActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"k co mang");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        Intent intent = new Intent(MainActivity.this,MonAnKhongThichActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        Intent intent5 = new Intent(MainActivity.this,LichAnActivity.class);
                        startActivity(intent5);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        Intent intent6 = new Intent(MainActivity.this,PhanHoiActivity.class);
                        startActivity(intent6);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void GetDuLieuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int ID = 0;
                    String Tensanpham = "";
                    Integer Giasanpham = 0;
                    String Hinhanhsanpham = "";
                    String Motasanpham = "";
                    int IDsanpham = 0;

                    for (int i = 0 ; i < response.length() ; i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensp");
                            Giasanpham = jsonObject.getInt("giasp");
                            Hinhanhsanpham = jsonObject.getString("hinhanhsp");
                            Motasanpham = jsonObject.getString("motasp");
                            IDsanpham = jsonObject.getInt("idsanpham");

                            mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                            sanphamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuLoaisp() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i = 0 ; i < response.length() ; i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));

                            loaispAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3,new Loaisp(0,"Món ăn ưa thích","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSok0hwouOrb688nfM0w_QZcCNirLYNlAUGMmH0-wRgJejBnS6h&s"));
                    mangloaisp.add(4,new Loaisp(0,"Món ăn không thích","https://png.pngtree.com/element_our/png_detail/20181229/sad-emoji-icon-design-vector-png_299786.jpg"));
                    mangloaisp.add(5,new Loaisp(0,"Lịch ăn","https://png.pngtree.com/element_our/png_detail/20181226/scheduleclassestimetableappointmentevent-glyph-icon--vec-png_284174.jpg"));
                    mangloaisp.add(6,new Loaisp(0,"Phản hồi","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTlp1sH4gZOdOHA2Uhmx4mskWdt8NTKjEYq6gVTClY58xi6LZL&s"));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://www.brandsvietnam.com/upload/forum2/2017/07/13020McDonalds8_1501001739.png");
        mangquangcao.add("http://www.chupsanpham.com.vn/uploads/2/1/6/8/21683184/_230641_orig.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/chaien-quan-184092.jpg");
        mangquangcao.add("https://quangcaotaybac.vn/wp-content/uploads/2019/06/bien-quang-cao-chu-hut-noi-nha-hang-imelda.jpg");

        for (int i = 0 ; i < mangquangcao.size() ; i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

            viewFlipper.setFlipInterval(5000);
            viewFlipper.setAutoStart(true);

            Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
            Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);

            viewFlipper.setInAnimation(animation_slide_in);
            viewFlipper.setOutAnimation(animation_slide_out);



        }
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                
            }
        });

    }

    private void Anhxa(){
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toobartrangchinh);
        viewFlipper = findViewById(R.id.viewfipper);
        recyclerViewtrangchinh = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.navigationview);
        listViewtrangchinh = findViewById(R.id.listviewtrangchinh);

        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang chính","https://png.pngtree.com/png-vector/20190321/ourlarge/pngtree-vector-homepage-icon-png-image_854926.jpg"));

        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewtrangchinh.setAdapter(loaispAdapter);

        mangsanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),mangsanpham);
        recyclerViewtrangchinh.setHasFixedSize(true);
        recyclerViewtrangchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewtrangchinh.setAdapter(sanphamAdapter);

        if (manggiohang != null){

        }else {
            manggiohang = new ArrayList<>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(intent);
                break;
            case R.id.menutimkiem:
                Intent intent1 = new Intent(getApplicationContext(),TimKiemActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
