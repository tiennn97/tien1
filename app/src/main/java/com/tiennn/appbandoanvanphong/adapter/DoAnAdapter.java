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

public class DoAnAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraydoan;

    public DoAnAdapter(Context context, ArrayList<Sanpham> arraydoan) {
        this.context = context;
        this.arraydoan = arraydoan;
    }

    @Override
    public int getCount() {
        return arraydoan.size();
    }

    @Override
    public Object getItem(int i) {
        return arraydoan.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttendoan,txtgiadoan,txtmotadoan;
        public ImageView imgdoan;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_doan,null);
            viewHolder.txttendoan = view.findViewById(R.id.textviewdoan);
            viewHolder.txtgiadoan = view.findViewById(R.id.textviewgiadoan);
            viewHolder.txtmotadoan = view.findViewById(R.id.textviewmotadoan);
            viewHolder.imgdoan = view.findViewById(R.id.imageviewdoan);

            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttendoan.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadoan.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham())+ "Đ");
        viewHolder.txtmotadoan.setMaxLines(2);
        viewHolder.txtmotadoan.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotadoan.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.wait)
                .error(R.drawable.error)
                .into(viewHolder.imgdoan);
        return view;
    }
}
