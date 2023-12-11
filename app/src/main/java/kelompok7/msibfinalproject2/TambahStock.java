package kelompok7.msibfinalproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TambahStock extends AppCompatActivity {
    private EditText etNama, etHarga, etStok;
    private Spinner spinnerKategori;
    private Button btnTambah;

    private DbHelper dbHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageProduct;
    private File tempDir;

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

        tempDir = new File(getExternalCacheDir(), "temp_images");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBarang();
            }
        });

        imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });
    }

    private void tambahBarang() {
        String namabarang = etNama.getText().toString();
        int hargabarang = Integer.parseInt(etHarga.getText().toString());
        int stokbarang = Integer.parseInt(etStok.getText().toString());
        String kategoribarang = spinnerKategori.getSelectedItem().toString();
        String pathGambarbarang = getImagePath();
        if (namabarang.isEmpty()) {
            Toast.makeText(this, "Nama barang tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (hargabarang <= 0) {
            Toast.makeText(this, "Harga barang harus lebih dari 0", Toast.LENGTH_SHORT).show();
            return;
        }
        if (stokbarang < 0) {
            Toast.makeText(this, "Stok barang tidak boleh kurang dari 0", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pathGambarbarang.isEmpty()) {
            Toast.makeText(this, "Harap pilih gambar barang", Toast.LENGTH_SHORT).show();
            return;
        }
        dbHelper.tambahBarang(namabarang, hargabarang, stokbarang, kategoribarang, pathGambarbarang);
        etNama.setText("");
        etHarga.setText("");
        etStok.setText("");
        imageProduct.setImageResource(R.drawable.ic_noimage);
        Toast.makeText(this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TambahStock.this, HalamanTambahStock.class);
        startActivity(intent);
    }
    private String getImagePath() {
        if (imageProduct.getDrawable() instanceof BitmapDrawable) {
            BitmapDrawable drawable = (BitmapDrawable) imageProduct.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            File tempImageFile = new File(tempDir, "temp_image.jpg");
            try {
                try (FileOutputStream fos = new FileOutputStream(tempImageFile)) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    return tempImageFile.getAbsolutePath();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            // Set the image using the Uri
            imageProduct.setImageURI(selectedImageUri);
        }
    }
}
