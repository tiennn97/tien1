package com.tiennn.appbandoanvanphong.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tiennn.appbandoanvanphong.R;
import com.tiennn.appbandoanvanphong.model.Sanpham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DoUongAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraydouong;

    public DoUongAdapter(Context context, ArrayList<Sanpham> arraydouong) {
        this.context = context;
        this.arraydouong = arraydouong;
    }

    @Override
    public int getCount() {
        return arraydouong.size();
    }

    @Override
    public Object getItem(int i) {
        return arraydouong.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttendouong,txtgiadouong,txtmotadouong;
        public ImageView imgdouong;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DoUongAdapter.ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_douong,null);
            viewHolder.txttendouong = view.findViewById(R.id.textviewdouong);
            viewHolder.txtgiadouong = view.findViewById(R.id.textviewgiadouong);
            viewHolder.txtmotadouong = view.findViewById(R.id.textviewmotadouong);
            viewHolder.imgdouong = view.findViewById(R.id.imageviewdouong);

            view.setTag(viewHolder);

        }else {
            viewHolder = (DoUongAdapter.ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttendouong.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadouong.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham())+ "Đ");
        viewHolder.txtmotadouong.setMaxLines(2);
        viewHolder.txtmotadouong.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotadouong.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.wait)
                .error(R.drawable.error)
                .into(viewHolder.imgdouong);
        return view;
    }
}
