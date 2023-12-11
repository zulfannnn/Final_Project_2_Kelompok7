package kelompok7.msibfinalproject2;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import java.io.File;

public class DetailBarang extends AppCompatActivity {
    private ImageView ivGambar;
    private TextView tvNama, tvHarga, tvStok;
    private EditText etJumlahBeli;
    private Button btnTambah, btnKurang, btnBeliBarang;
    private DbHelper dbHelper;
    private Barang barang;
    private int jumlahBeli = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        ivGambar = findViewById(R.id.imageViewBarang);
        tvNama = findViewById(R.id.tvNama);
        tvHarga = findViewById(R.id.tvHarga);
        tvStok = findViewById(R.id.tvStok);
        etJumlahBeli = findViewById(R.id.etJumlahBeli);
        btnTambah = findViewById(R.id.btnTambah);
        btnKurang = findViewById(R.id.btnKurang);
        btnBeliBarang = findViewById(R.id.btnBeliBarang);
        dbHelper = new DbHelper(this);
        Intent intent = getIntent();
        if (intent != null) {
            barang = intent.getParcelableExtra("BARANG");
            if (barang != null) {
                tampilkanDetailBarang();
            } else {
            }
        }
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlahBeli++;
                etJumlahBeli.setText(String.valueOf(jumlahBeli));
            }
        });
        btnKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlahBeli > 1) {
                    jumlahBeli--;
                    etJumlahBeli.setText(String.valueOf(jumlahBeli));
                }
            }
        });
        btnBeliBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konfirmasiBeli();
            }
        });
    }
    private void tampilkanDetailBarang() {
        if (barang != null) {
            tvNama.setText(barang.getNamabarang());
            tvHarga.setText("Harga : Rp " + barang.getHargabarang());
            tvStok.setText("Stok : " + barang.getStokbarang());

            String pathGambar = barang.getPathGambar();
            if (pathGambar != null && !pathGambar.isEmpty()) {
                Picasso.get()
                        .load(new File(pathGambar))
                        .config(Bitmap.Config.RGB_565)
                        .into(ivGambar);
            } else {
                ivGambar.setImageResource(R.drawable.ic_noimage);
            }
        } else {
            Toast.makeText(this, "Objek Barang tidak valid", Toast.LENGTH_SHORT).show();
            finish(); // Selesaikan aktivitas jika objek Barang null
        }
    }
    private void konfirmasiBeli() {
        if (barang != null) {
            String namaBarang = barang.getNamabarang();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Konfirmasi Pembelian");
            builder.setMessage("Anda yakin ingin membeli " + jumlahBeli + " " + barang.getNamabarang() +
                    "?\nTotal Harga: " + (jumlahBeli * barang.getHargabarang()));
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    beliBarang();
                }
            });
            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            Toast.makeText(this, "Objek Barang tidak valid", Toast.LENGTH_SHORT).show();
        }
    }
    private void beliBarang() {
        if (barang != null) {
            int jumlahPembelian = Integer.parseInt(etJumlahBeli.getText().toString());
            if (jumlahPembelian > 0 && jumlahPembelian <= barang.getStokbarang()) {
                Toast.makeText(this, "Pembelian berhasil: " + jumlahPembelian + " " + barang.getNamabarang(), Toast.LENGTH_SHORT).show();
                barang.kurangiStok(jumlahPembelian);
                dbHelper.updateBarang(barang);

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);

                finish();
            } else {
                Toast.makeText(this, "Stok barang tidak mencukupi", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Objek Barang tidak valid", Toast.LENGTH_SHORT).show();
        }
    }
}