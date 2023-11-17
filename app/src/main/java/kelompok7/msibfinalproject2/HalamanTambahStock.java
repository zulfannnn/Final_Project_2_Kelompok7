package kelompok7.msibfinalproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;



public class HalamanTambahStock extends AppCompatActivity {

    private Button btnstock;
    private Spinner spinnerKategori;
    private RecyclerView recyclerViewBarang;

    private DbHelper dbHelper;
    private BarangAdapter barangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_tambah_stock);

        dbHelper = new DbHelper(this);
        btnstock = findViewById(R.id.btn_stock);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        recyclerViewBarang = findViewById(R.id.recyclerViewBarangUser); // Menggunakan RecyclerView

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.kategori_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);

        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                tampilkanBarang();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        tampilkanBarang();

        btnstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HalamanTambahStock.this, TambahStock.class);
                startActivity(intent);
            }
        });
    }

    private void tampilkanBarang() {
        String kategori = spinnerKategori.getSelectedItem().toString();
        List<Barang> barangList = dbHelper.getBarangByKategori(kategori);

        // Membuat adapter dan menetapkannya pada RecyclerView
        barangAdapter = new BarangAdapter(this, barangList);
        recyclerViewBarang.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBarang.setAdapter(barangAdapter);
    }
}

