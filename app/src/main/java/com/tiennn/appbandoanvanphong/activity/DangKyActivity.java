package com.tiennn.appbandoanvanphong.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tiennn.appbandoanvanphong.R;
import com.tiennn.appbandoanvanphong.ultit.CheckConnection;
import com.tiennn.appbandoanvanphong.ultit.Server;

import java.util.HashMap;
import java.util.Map;

public class DangKyActivity extends AppCompatActivity {
    EditText edttaikhoandangky, edtmatkhaudangky;
    Button btndangky;
    TextView tvloidangky,tvdacotaikhoan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        Anhxa();

        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = edttaikhoandangky.getText().toString().trim();
                String mk = edtmatkhaudangky.getText().toString().trim();

                boolean kiemtra = false;
                for (int i = 0 ; i < LoginActivity.mangnguoidung.size() ; i++){
                    if (tk.equals(LoginActivity.mangnguoidung.get(i).getTaikhoan()) == true){
                        CheckConnection.ShowToast_Short(DangKyActivity.this,"Tài khoản đã tồn tại");
                        kiemtra = true;
                    }
                }
                if (kiemtra == false){
                    DangKy(Server.Duongdandangkynguoidung);
                }


            }
        });

        tvdacotaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void DangKy(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")){
                    CheckConnection.ShowToast_Short(DangKyActivity.this,"Đăng ký thành công");
                    startActivity(new Intent(DangKyActivity.this,LoginActivity.class));
                }else {
                    CheckConnection.ShowToast_Short(DangKyActivity.this,"lỗi");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(DangKyActivity.this,"Lỗi server");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("taikhoan",edttaikhoandangky.getText().toString().trim());
                params.put("matkhau",edtmatkhaudangky.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Anhxa() {
        edttaikhoandangky = findViewById(R.id.edittexttaikhoandangky);
        edtmatkhaudangky = findViewById(R.id.edittextmatkhaudangky);
        btndangky = findViewById(R.id.buttondangky);
        tvloidangky = findViewById(R.id.textviewloidangky);
        tvdacotaikhoan = findViewById(R.id.textviewvelogin);
    }
}
