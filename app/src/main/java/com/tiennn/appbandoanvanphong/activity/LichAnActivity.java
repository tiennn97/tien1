package com.tiennn.appbandoanvanphong.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiennn.appbandoanvanphong.R;

public class LichAnActivity extends AppCompatActivity{
    Toolbar toolbarlichan;
    TextView tvthu2,tvthu3,tvthu4,tvthu5,tvthu6,tvthu7,tvcn;
    ImageView imgthu2,imgthu3,imgthu4,imgthu5,imgthu6,imgthu7,imgcn;

    String editext;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_an);

        Anhxa();
        ActionToolbar();
        EventImageEdit();

        sharedPreferences = getSharedPreferences("dataLichAn",MODE_PRIVATE);

        //Lấy dữ liệu trong sharedPreferences
        tvthu2.setText(sharedPreferences.getString("luu2",""));
        tvthu3.setText(sharedPreferences.getString("luu3",""));
        tvthu4.setText(sharedPreferences.getString("luu4",""));
        tvthu5.setText(sharedPreferences.getString("luu5",""));
        tvthu6.setText(sharedPreferences.getString("luu6",""));
        tvthu7.setText(sharedPreferences.getString("luu7",""));
        tvcn.setText(sharedPreferences.getString("luucn",""));

    }


    private void ActionToolbar() {
        setSupportActionBar(toolbarlichan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlichan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarlichan = findViewById(R.id.toolbarlichan);
        tvthu2 = findViewById(R.id.textviewthu2);
        tvthu3 = findViewById(R.id.textviewthu3);
        tvthu4 = findViewById(R.id.textviewthu4);
        tvthu5 = findViewById(R.id.textviewthu5);
        tvthu6 = findViewById(R.id.textviewthu6);
        tvthu7 = findViewById(R.id.textviewthu7);
        tvcn = findViewById(R.id.textviewcn);

        imgthu2 = findViewById(R.id.imagevieweditthu2);
        imgthu3 = findViewById(R.id.imagevieweditthu3);
        imgthu4 = findViewById(R.id.imagevieweditthu4);
        imgthu5 = findViewById(R.id.imagevieweditthu5);
        imgthu6 = findViewById(R.id.imagevieweditthu6);
        imgthu7 = findViewById(R.id.imagevieweditthu7);
        imgcn = findViewById(R.id.imageviewcn);
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

   private void EventImageEdit(){
       imgthu2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Edit2();
           }
       });
       imgthu3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Edit3();
           }
       });
       imgthu4.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Edit4();
           }
       });
       imgthu5.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Edit5();
           }
       });
       imgthu6.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Edit6();
           }
       });
       imgthu7.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Edit7();
           }
       });
       imgcn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Editcn();
           }
       });
   }


    private void Edit2(){
        final Dialog dialog = new Dialog(LichAnActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final EditText edit = dialog.findViewById(R.id.edittextdialog);
        Button btnluu = dialog.findViewById(R.id.buttondialogluu);
        Button btntrove = dialog.findViewById(R.id.buttondialogtrove);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editext = edit.getText().toString().trim();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("luu2",editext);
                editor.commit();

                tvthu2.setText(editext);

                dialog.cancel();
            }
        });

        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void Edit3(){
        final Dialog dialog = new Dialog(LichAnActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final EditText edit = dialog.findViewById(R.id.edittextdialog);
        Button btnluu = dialog.findViewById(R.id.buttondialogluu);
        Button btntrove = dialog.findViewById(R.id.buttondialogtrove);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editext = edit.getText().toString().trim();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("luu3",editext);
                editor.commit();

                tvthu3.setText(editext);

                dialog.cancel();
            }
        });

        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void Edit4(){
        final Dialog dialog = new Dialog(LichAnActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final EditText edit = dialog.findViewById(R.id.edittextdialog);
        Button btnluu = dialog.findViewById(R.id.buttondialogluu);
        Button btntrove = dialog.findViewById(R.id.buttondialogtrove);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editext = edit.getText().toString().trim();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("luu4",editext);
                editor.commit();

                tvthu4.setText(editext);

                dialog.cancel();
            }
        });

        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void Edit5(){
        final Dialog dialog = new Dialog(LichAnActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final EditText edit = dialog.findViewById(R.id.edittextdialog);
        Button btnluu = dialog.findViewById(R.id.buttondialogluu);
        Button btntrove = dialog.findViewById(R.id.buttondialogtrove);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editext = edit.getText().toString().trim();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("luu5",editext);
                editor.commit();

                tvthu5.setText(editext);

                dialog.cancel();
            }
        });

        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void Edit6(){
        final Dialog dialog = new Dialog(LichAnActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final EditText edit = dialog.findViewById(R.id.edittextdialog);
        Button btnluu = dialog.findViewById(R.id.buttondialogluu);
        Button btntrove = dialog.findViewById(R.id.buttondialogtrove);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editext = edit.getText().toString().trim();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("luu6",editext);
                editor.commit();

                tvthu6.setText(editext);

                dialog.cancel();
            }
        });

        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void Edit7(){
        final Dialog dialog = new Dialog(LichAnActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final EditText edit = dialog.findViewById(R.id.edittextdialog);
        Button btnluu = dialog.findViewById(R.id.buttondialogluu);
        Button btntrove = dialog.findViewById(R.id.buttondialogtrove);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editext = edit.getText().toString().trim();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("luu7",editext);
                editor.commit();

                tvthu7.setText(editext);

                dialog.cancel();
            }
        });

        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void Editcn(){
        final Dialog dialog = new Dialog(LichAnActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final EditText edit = dialog.findViewById(R.id.edittextdialog);
        Button btnluu = dialog.findViewById(R.id.buttondialogluu);
        Button btntrove = dialog.findViewById(R.id.buttondialogtrove);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editext = edit.getText().toString().trim();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("luucn",editext);
                editor.commit();

                tvcn.setText(editext);

                dialog.cancel();
            }
        });

        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
