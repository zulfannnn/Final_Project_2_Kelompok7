package kelompok7.msibfinalproject2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.List;

public class EditData extends AppCompatActivity {
    private EditText etNama, etHarga, etStok;
    private Spinner spinnerKategori;
    private Button btnSimpan;
    private ImageView ivGambar;
    private DbHelper dbHelper;
    private Barang barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        etNama = findViewById(R.id.etNamaBarangEdit);
        etHarga = findViewById(R.id.etHargaBarangEdit);
        etStok = findViewById(R.id.etStokBarangEdit);
        spinnerKategori = findViewById(R.id.spinnerKategoriEdit);
        btnSimpan = findViewById(R.id.btn_editbarangEdit);
        ivGambar = findViewById(R.id.imageProductEdit);
        dbHelper = new DbHelper(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.kategori_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);
        Intent intent = getIntent();
        if (intent != null) {
            barang = intent.getParcelableExtra("BARANG");
            if (barang != null) {
                etNama.setText(barang.getNamabarang());
                etHarga.setText(String.valueOf(barang.getHargabarang()));
                etStok.setText(String.valueOf(barang.getStokbarang()));
                int spinnerPosition = adapter.getPosition(barang.getKategoribarang());
                spinnerKategori.setSelection(spinnerPosition);
                String pathGambar = barang.getPathGambar();
                if (pathGambar != null && !pathGambar.isEmpty()) {
                    Picasso.get()
                            .load(new File(pathGambar))
                            .config(Bitmap.Config.RGB_565)
                            .into(ivGambar);
                }
            } else {
            }
            btnSimpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    simpanPerubahan();
                }
            });
        }
    }
    private void simpanPerubahan() {
        String namaBarang = etNama.getText().toString().trim();
        int hargaBarang = Integer.parseInt(etHarga.getText().toString());
        int stokBarang = Integer.parseInt(etStok.getText().toString());
        String kategoriBarang = spinnerKategori.getSelectedItem().toString().trim();
        Log.d("EditData", "Nama Barang: " + barang.namabarang + ", Harga Barang: " + barang.hargabarang +
                ", Stok Barang: " + barang.stokbarang + ", Kategori Barang: " + barang.kategoribarang);
        barang.setNamabarang(namaBarang);
        barang.setHargabarang(hargaBarang);
        barang.setStokbarang(stokBarang);
        barang.setKategoribarang(kategoriBarang);
        boolean isSuccess = dbHelper.updateBarang(barang);
        if (isSuccess) {
            Toast.makeText(EditData.this, "Perubahan berhasil disimpan", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(EditData.this, "Gagal menyimpan perubahan", Toast.LENGTH_SHORT).show();
        }
    }
}
