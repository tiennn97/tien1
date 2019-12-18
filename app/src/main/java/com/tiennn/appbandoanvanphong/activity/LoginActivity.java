package com.tiennn.appbandoanvanphong.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tiennn.appbandoanvanphong.R;
import com.tiennn.appbandoanvanphong.model.Nguoidung;
import com.tiennn.appbandoanvanphong.ultit.CheckConnection;
import com.tiennn.appbandoanvanphong.ultit.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText edttaikhoan, edtmatkhau;
    Button btnlogin;
    TextView tvdangkitaikhoanmoi,tvloi;
    CheckBox checkBox;

    SharedPreferences sharedPreferences;

    public static ArrayList<Nguoidung> mangnguoidung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Anhxa();
        GetDuLieu();

        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckDulieu();
            }
        });

        tvdangkitaikhoanmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,DangKyActivity.class);
                startActivity(intent);
            }
        });

        //lay gia tri sharedPreferences
        edttaikhoan.setText(sharedPreferences.getString("taikhoan",""));
        edtmatkhau.setText(sharedPreferences.getString("matkhau",""));
        checkBox.setChecked(sharedPreferences.getBoolean("checked",false));

    }

    private void CheckDulieu() {
        String tk = edttaikhoan.getText().toString();
        String mk = edtmatkhau.getText().toString();


        for (int i = 0; i < mangnguoidung.size(); i++) {
            if (tk.equals(mangnguoidung.get(i).getTaikhoan()) == true && mk.equals(mangnguoidung.get(i).getMatkhau()) == true) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                if (checkBox.isChecked()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("taikhoan",tk);
                    editor.putString("matkhau",mk);
                    editor.putBoolean("checked",true);
                    editor.commit();
                }
                startActivity(intent);
            } else {
                tvloi.setText("Bạn hay kiểm tra lại tài khoản or mật khẩu");
            }
        }
    }

    private void GetDuLieu() {
        final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdannguoidung, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int id = 0;
                String Taikhoan = "";
                String Matkhau = "";

                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Taikhoan = jsonObject.getString("taikhoan");
                            Matkhau = jsonObject.getString("matkhau");
                            mangnguoidung.add(new Nguoidung(id, Taikhoan, Matkhau));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Anhxa() {
        edttaikhoan = findViewById(R.id.edittexttaikhoan);
        edtmatkhau = findViewById(R.id.edittextmatkhau);
        btnlogin = findViewById(R.id.buttonlogin);
        tvdangkitaikhoanmoi = findViewById(R.id.textviewdangkytaikhoanmoi);
        tvloi = findViewById(R.id.textviewloi);
        checkBox = findViewById(R.id.checkbox);

        mangnguoidung = new ArrayList<>();
    }
}
