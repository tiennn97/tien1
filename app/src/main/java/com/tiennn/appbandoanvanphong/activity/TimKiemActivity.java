package com.tiennn.appbandoanvanphong.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tiennn.appbandoanvanphong.R;
import com.tiennn.appbandoanvanphong.adapter.DoAnAdapter;
import com.tiennn.appbandoanvanphong.adapter.TimKiemAdapter;
import com.tiennn.appbandoanvanphong.model.Loaisp;
import com.tiennn.appbandoanvanphong.model.Sanpham;
import com.tiennn.appbandoanvanphong.ultit.CheckConnection;
import com.tiennn.appbandoanvanphong.ultit.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimKiemActivity extends AppCompatActivity {
    Toolbar toolbartimkiem;
    ListView lvtimkiem;
    private SearchView searchView;
    TimKiemAdapter timKiemAdapter;
    ArrayList<Sanpham> mangtimkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        Anhxa();
        ActionToolbar();
        GetDuLieu();
        Search();
    }

    public void Search() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                timKiemAdapter.filer(newText.trim());
                return false;
            }
        });
    }

    private void GetDuLieu() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdantimkiem, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int id = 0;
                String Tendoan ="";
                int Giadoan = 0;
                String Hinhanhdoan = "";
                String Motadoan = "";
                int Idspdoan = 0;

                if (response != null){
                    for (int i = 0 ; i < response.length() ; i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendoan = jsonObject.getString("tensp");
                            Giadoan = jsonObject.getInt("giasp");
                            Hinhanhdoan = jsonObject.getString("hinhanhsp");
                            Motadoan = jsonObject.getString("motasp");
                            Idspdoan = jsonObject.getInt("idsanpham");
                            mangtimkiem.add(new Sanpham(id , Tendoan , Giadoan , Hinhanhdoan , Motadoan , Idspdoan));

                            timKiemAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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

    private void Anhxa() {
        toolbartimkiem = findViewById(R.id.toolbartimkiem);
        lvtimkiem = findViewById(R.id.listviewtimkiem);
        mangtimkiem = new ArrayList<>();
        timKiemAdapter = new TimKiemAdapter(getApplicationContext(),mangtimkiem);
        lvtimkiem.setAdapter(timKiemAdapter);

        searchView = findViewById(R.id.search);
    }
    private void ActionToolbar() {
        setSupportActionBar(toolbartimkiem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbartimkiem.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
