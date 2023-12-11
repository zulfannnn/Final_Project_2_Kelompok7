package kelompok7.msibfinalproject2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {
    private List<Barang> barangList;
    private Context context;
    private boolean isAdminMode = false;
    public interface OnItemClickListener {
        void onItemClick(Barang barang);
    }
    private OnItemClickListener itemClickListener;
    public interface OnEditItemClickListener {
        void onEditItemClick(Barang barang);
    }
    private OnEditItemClickListener editItemClickListener;
    public BarangAdapter(Context context, List<Barang> barangList, OnItemClickListener listener) {
        this.context = context;
        this.barangList = barangList;
        this.itemClickListener = listener;
        this.editItemClickListener = editItemClickListener;
        this.isAdminMode = false;
    }
    public void updateData(List<Barang> newData) {
        barangList = new ArrayList<>(newData);
        notifyDataSetChanged();
    }
    public void setAdminMode(boolean isAdminMode) {
        this.isAdminMode = isAdminMode;
        notifyDataSetChanged();
    }
    public boolean isAdminMode() {
        return isAdminMode;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_barang, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Barang barang = barangList.get(position);

        holder.tvNama.setText(barang.getNamabarang());
        holder.tvHarga.setText(String.valueOf(barang.getHargabarang()));
        holder.tvStok.setText(String.valueOf(barang.getStokbarang()));
        holder.tvKategori.setText(barang.getKategoribarang());
        String pathGambar = barang.getPathGambar();
        if (pathGambar != null && !pathGambar.isEmpty()) {
            Picasso.get()
                    .load(new File(pathGambar))
                    .config(Bitmap.Config.RGB_565)
                    .into(holder.tvImage);
        } else {
            holder.tvImage.setImageResource(R.drawable.ic_noimage);
        }
        if (isAdminMode) {
            holder.buttonLihatDetail.setText("Edit Data");
        } else {
            holder.buttonLihatDetail.setText("Lihat Detail");
        }
        holder.buttonLihatDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(barang);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return barangList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvHarga, tvStok, tvKategori;
        ImageView tvImage;
        Button buttonLihatDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.TV_NamaCamera);
            tvHarga = itemView.findViewById(R.id.TV_HargaCamera);
            tvStok = itemView.findViewById(R.id.TV_StokCamera);
            tvKategori = itemView.findViewById(R.id.TV_Kategori);
            tvImage = itemView.findViewById(R.id.TV_gambar);
            buttonLihatDetail = itemView.findViewById(R.id.btn_lihatdetail);
        }
    }
}
