package com.tiennn.appbandoanvanphong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tiennn.appbandoanvanphong.R;
import com.tiennn.appbandoanvanphong.activity.GiohangActivity;
import com.tiennn.appbandoanvanphong.activity.MainActivity;
import com.tiennn.appbandoanvanphong.model.Giohang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arraygiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arraygiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus,btnvalues,btnplus;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengiohang = view.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang = view.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang = view.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus = view.findViewById(R.id.buttonminus);
            viewHolder.btnvalues = view.findViewById(R.id.buttovaluse);
            viewHolder.btnplus = view.findViewById(R.id.buttonplus);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();

        }
        Giohang giohang = (Giohang) getItem(i);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp()) + " Đ");
        Picasso.with(context).load(giohang.getHinhsp())
                .placeholder(R.drawable.wait)
                .error(R.drawable.error)
                .into(viewHolder.imggiohang);
        viewHolder.btnvalues.setText(giohang.getSoluongsp()+"");
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if (sl >= 10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if (sl <= 1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if (sl >= 1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }

        final Button btncong = viewHolder.btnplus;
        final Button btntru = viewHolder.btnminus;
        final Button btn = viewHolder.btnvalues;
        final TextView txt = viewHolder.txtgiagiohang;

        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(btn.getText().toString()) + 1;
                int slht = MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                btn.setText(slmoinhat +"");
                long giamoinhat = (giaht * slmoinhat) /slht;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txt.setText(decimalFormat.format(giamoinhat) + " Đ");

                GiohangActivity.EventUtil();
                if (slmoinhat > 9){
                    btncong.setVisibility(View.INVISIBLE);
                    btntru.setVisibility(View.VISIBLE);
                    btn.setText(String.valueOf(slmoinhat));
                }else if (slmoinhat >= 2){
                    btntru.setVisibility(View.VISIBLE);
                    btncong.setVisibility(View.VISIBLE);
                }else if (slmoinhat < 2){
                    btntru.setVisibility(View.INVISIBLE);
                    btncong.setVisibility(View.VISIBLE);
                }
            }
        });
        btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(btn.getText().toString()) - 1;
                int slht = MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                btn.setText(slmoinhat +"");
                long giamoinhat = (giaht * slmoinhat) /slht;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txt.setText(decimalFormat.format(giamoinhat) + " Đ");

                GiohangActivity.EventUtil();
                if (slmoinhat < 2){
                    btncong.setVisibility(View.VISIBLE);
                    btntru.setVisibility(View.INVISIBLE);
                    btn.setText(String.valueOf(slmoinhat));
                }else if (slmoinhat >= 2){
                    btntru.setVisibility(View.VISIBLE);
                    btncong.setVisibility(View.VISIBLE);
                }else if (slmoinhat > 9){
                    btntru.setVisibility(View.VISIBLE);
                    btncong.setVisibility(View.INVISIBLE);
                }
            }
        });
        return view;
    }
}
