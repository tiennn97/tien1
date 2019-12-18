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
import com.tiennn.appbandoanvanphong.model.Loaisp;
import com.tiennn.appbandoanvanphong.model.Sanpham;
import com.tiennn.appbandoanvanphong.ultit.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimKiemAdapter extends BaseAdapter {
    Context context;
    List<Sanpham> arraytimkiem;
    ArrayList<Sanpham> arrayList;


    public TimKiemAdapter(Context context, List<Sanpham> arraytimkiem) {
        this.context = context;
        this.arraytimkiem = arraytimkiem;

    }

    @Override
    public int getCount() {
        return arraytimkiem.size();
    }

    @Override
    public Object getItem(int i) {
        return arraytimkiem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttentimkiem,txtgiatimkiem,txtmotatimkiem;
        public ImageView imgtimkiem;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_timkiem,null);

            viewHolder.txttentimkiem = view.findViewById(R.id.textviewtentimkiem);
            viewHolder.txtgiatimkiem = view.findViewById(R.id.textviewgiatimkiem);
            viewHolder.txtmotatimkiem = view.findViewById(R.id.textviewmotatimkiem);
            viewHolder.imgtimkiem = view.findViewById(R.id.imageviewtimkiem);

            view.setTag(viewHolder);

        }else {
            viewHolder =(ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttentimkiem.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiatimkiem.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham())+ "Đ");
        viewHolder.txtmotatimkiem.setMaxLines(2);
        viewHolder.txtmotatimkiem.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotatimkiem.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.wait)
                .error(R.drawable.error)
                .into(viewHolder.imgtimkiem);

        return view;
    }

    public void filer(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        arrayList = new ArrayList<>();
        arrayList.addAll(arraytimkiem);
        arraytimkiem.clear();

        for (Sanpham sanpham: arrayList) {
            if (sanpham.getTensanpham().toLowerCase(Locale.getDefault()).contains(charText)){
                arraytimkiem.add(sanpham);
            }
        }
        notifyDataSetChanged();
    }
}
