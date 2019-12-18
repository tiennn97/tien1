package com.tiennn.appbandoanvanphong.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tiennn.appbandoanvanphong.R;
import com.tiennn.appbandoanvanphong.adapter.DoAnAdapter;
import com.tiennn.appbandoanvanphong.model.Giohang;
import com.tiennn.appbandoanvanphong.model.Sanpham;
import com.tiennn.appbandoanvanphong.ultit.CheckConnection;
import com.tiennn.appbandoanvanphong.ultit.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoAnActivity extends AppCompatActivity {
    Toolbar toolbardoan;
    ListView lvdoan;
    DoAnAdapter doAnAdapter;
    ArrayList<Sanpham> mangdoan;

    int iddoan = 0;
    int page = 1;

    View footerview;
    boolean isLoading = false;
    boolean limitdata = false;
    mHandler mHandler;

    int id = 0;
    String Tendoan ="";
    int Giadoan = 0;
    String Hinhanhdoan = "";
    String Motadoan = "";
    int Idspdoan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_an);

        Anhxa();
        GetIdloaisp();
        ActionToolbar();
        GetData(page);
        LoadMoreData();

        registerForContextMenu(lvdoan); // khai báo context menu
    }



    private void LoadMoreData() {
        lvdoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham",mangdoan.get(i));
                startActivity(intent);
            }
        });

        lvdoan.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if (FirstItem + VisibleItem == TotalItem && TotalItem != 0 && isLoading == false && limitdata == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandoan+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response != null && response.length() != 2){ //response luôn trả về {} -> luôn có 2 phần tử
                    lvdoan.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i <jsonArray.length() ; i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendoan = jsonObject.getString("tensp");
                            Giadoan = jsonObject.getInt("giasp");
                            Hinhanhdoan = jsonObject.getString("hinhanhsp");
                            Motadoan = jsonObject.getString("motasp");
                            Idspdoan = jsonObject.getInt("idsanpham");

                            mangdoan.add(new Sanpham(id , Tendoan , Giadoan , Hinhanhdoan , Motadoan , Idspdoan));
                            doAnAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitdata = true;
                    lvdoan.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Hết");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(iddoan));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardoan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardoan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdloaisp() {
        iddoan = getIntent().getIntExtra("idloaisanpham",-1);
    }

    private void Anhxa() {
        toolbardoan = findViewById(R.id.toolbardoan);
        lvdoan = findViewById(R.id.listviewdoan);
        mangdoan = new ArrayList<>();
        doAnAdapter = new DoAnAdapter(getApplicationContext(),mangdoan);
        lvdoan.setAdapter(doAnAdapter);

        // gắn layout progressbar
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);

        mHandler = new mHandler();

    }

    public class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvdoan.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1); //obtainMessage de lien ket Thread voi handler
            mHandler.sendMessage(message);
            super.run();
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Sanpham f = mangdoan.get(info.position);

        menu.setHeaderTitle(f.getTensanpham());


        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * Xử lý lựa chọn trên CONTEXT MENU
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int idMenu = item.getItemId();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Sanpham f = mangdoan.get(info.position);


        switch (idMenu){
            case R.id.menu_themvaogiohang:

                String t = f.getTensanpham();
                String h = f.getHinhanhsanpham();
                int iddd = f.getID();
                int giaa = f.getGiasanpham();

                if (MainActivity.manggiohang.size() > 0){
                    int sl = 1;
                    boolean exists = false;
                    for (int i = 0 ; i < MainActivity.manggiohang.size() ; i++){
                        if (MainActivity.manggiohang.get(i).getIdsp() == iddd){
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl);
                            if (MainActivity.manggiohang.get(i).getSoluongsp() >= 10){
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                                Toast.makeText(this, "Số lượng món " + f.getTensanpham() + " đã đạt max 10", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(this, "Thêm thành công vào giỏ hàng", Toast.LENGTH_SHORT).show();
                            }
                            MainActivity.manggiohang.get(i).setGiasp(giaa * MainActivity.manggiohang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int soluong = 1;
                        long Giamoi = soluong * giaa;
                        MainActivity.manggiohang.add(new Giohang(iddd,t,Giamoi,h,soluong));
                    }
                }else {
                    int soluong = 1;
                    long Giamoi = soluong * giaa;
                    MainActivity.manggiohang.add(new Giohang(iddd,t,Giamoi,h,soluong));
                }
                break;
            case R.id.menu_themvaodsthich:
                Toast.makeText(this, "ĐẶT MÓN CHO GRAB " + f.getTensanpham(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_themvaodskhongthich:
                Toast.makeText(this, "ĐẶT MÓN CHO " + f.getTensanpham(), Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onContextItemSelected(item);
    }

}
