package kelompok7.msibfinalproject2;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailBarang extends AppCompatActivity {
    private ImageView imageViewBarang;
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

        imageViewBarang = findViewById(R.id.imageViewBarang);
        tvNama = findViewById(R.id.tvNama);
        tvHarga = findViewById(R.id.tvHarga);
        tvStok = findViewById(R.id.tvStok);
        etJumlahBeli = findViewById(R.id.etJumlahBeli);
        btnTambah = findViewById(R.id.btnTambah);
        btnKurang = findViewById(R.id.btnKurang);
        btnBeliBarang = findViewById(R.id.btnBeliBarang);

        dbHelper = new DbHelper(this);

        // Ambil objek Barang dari Intent
        Intent intent = getIntent();
        if (intent.hasExtra("BARANG")) {
            barang = intent.getParcelableExtra("BARANG");

            // Periksa apakah objek Barang tidak null sebelum mengakses metodenya
            if (barang != null) {
                tampilkanDetailBarang();
            } else {
                // Handle kasus ketika objek Barang null
                // ...
            }
        } else {
            // Handle kasus ketika objek Barang tidak ditemukan dalam Intent
            // ...
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
        String namaBarang = barang.getNamabarang();
        // Mendapatkan data barang dari database atau sumber data Anda
        barang = dbHelper.getBarangById();

        // Menampilkan data barang
        tvNama.setText(barang.getNamabarang());
        tvHarga.setText("Harga: " + barang.getHargabarang());
        tvStok.setText("Stok: " + barang.getStokbarang());
        // Menggunakan Picasso untuk menampilkan gambar
        Picasso.get().load(barang.getPathGambar()).into(imageViewBarang);
    }

    private void konfirmasiBeli() {
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
    }

    private void beliBarang() {
        int jumlahPembelian = Integer.parseInt(etJumlahBeli.getText().toString());

        if (jumlahPembelian > 0 && jumlahPembelian <= barang.getStokbarang()) {
            // Proses pembelian, misalnya dengan menyimpan transaksi ke database atau server
            // Di sini, kita hanya menampilkan pesan pembelian berhasil
            Toast.makeText(this, "Pembelian berhasil: " + jumlahPembelian + " " + barang.getNamabarang(), Toast.LENGTH_SHORT).show();

            // Mengurangkan stok barang di database (contoh: menggunakan DBHelper)
            barang.kurangiStok(jumlahPembelian);
            dbHelper.updateBarang(barang);

            finish(); // Kembali ke activity sebelumnya
        } else {
            Toast.makeText(this, "Jumlah pembelian tidak valid", Toast.LENGTH_SHORT).show();
        }
    }
}