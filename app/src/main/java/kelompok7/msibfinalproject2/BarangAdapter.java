package kelompok7.msibfinalproject2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {

    private List<Barang> barangList;
    private Context context;

    public BarangAdapter(Context context, List<Barang> barangList) {
        this.context = context;
        this.barangList = barangList;
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

        if (barang.getPathGambar() != null && !barang.getPathGambar().isEmpty()) {
            Glide.with(context)
                    .load(barang.getPathGambar())
                    .placeholder(R.drawable.ic_noimage)
                    .into(holder.imageView);
        }

        holder.buttonLihatDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailBarang.class);
                intent.putExtra("ID_BARANG", barang.getIdbarang());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvHarga, tvStok, tvKategori;
        ImageView imageView;
        Button buttonLihatDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.TV_NamaCamera);
            tvHarga = itemView.findViewById(R.id.TV_HargaCamera);
            tvStok = itemView.findViewById(R.id.TV_StokCamera);
            tvKategori = itemView.findViewById(R.id.TV_Kategori);
            imageView = itemView.findViewById(R.id.imageView);
            buttonLihatDetail = itemView.findViewById(R.id.btn_lihatdetail);
        }
    }
}
