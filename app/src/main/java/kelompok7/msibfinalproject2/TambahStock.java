package kelompok7.msibfinalproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class TambahStock extends AppCompatActivity {
    private EditText etNama, etHarga, etStok;
    private Spinner spinnerKategori;
    private Button btnTambah;

    private DbHelper dbHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageProduct;
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_stock);


        dbHelper = new DbHelper(this);

        imageProduct = findViewById(R.id.imageProduct);
        etNama = findViewById(R.id.etNamaBarang);
        etHarga = findViewById(R.id.etHarga);
        etStok = findViewById(R.id.etStok);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        btnTambah = findViewById(R.id.btn_tambahbarang);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBarang();
            }
        });

        imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuka galeri untuk memilih gambar
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }
    private void tambahBarang() {
        String namabarang = etNama.getText().toString();
        int hargabarang = Integer.parseInt(etHarga.getText().toString());
        int stokbarang = Integer.parseInt(etStok.getText().toString());
        String kategoribarang = spinnerKategori.getSelectedItem().toString();
        String pathGambar = getPathGambar();


        // Validasi input
        if (namabarang.isEmpty() || hargabarang <= 0 || stokbarang < 0) {
            Toast.makeText(this, "Harap isi semua field dengan benar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simpan data ke database
        dbHelper.tambahBarang(namabarang, hargabarang, stokbarang, kategoribarang,pathGambar);

        // Reset input
        etNama.setText("");
        etHarga.setText("");
        etStok.setText("");
        imageProduct.setImageResource(R.drawable.ic_noimage);

        Toast.makeText(this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TambahStock.this, HalamanTambahStock.class);
        startActivity(intent);
    }
    private String getPathGambar() {
        // Mengambil path gambar dari ImageView
        if (imageProduct.getDrawable() instanceof BitmapDrawable) {
            // Jika ImageView berisi gambar
            BitmapDrawable drawable = (BitmapDrawable) imageProduct.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            // Simpan gambar ke penyimpanan internal atau eksternal dan dapatkan pathnya
            // ... (implementasi terserah Anda)
            // Misalnya, Anda dapat menyimpan gambar ke direktori aplikasi Anda.

            // Mengembalikan path gambar
            return imagePath;
        } else {
            // Jika ImageView kosong atau tidak berisi gambar
            return "";
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Mengatur gambar yang dipilih ke ImageView
            Uri uri = data.getData();
            imageProduct.setImageURI(uri);
        }
    }

}