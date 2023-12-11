package kelompok7.msibfinalproject2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HalamanTambahStock extends AppCompatActivity implements BarangAdapter.OnItemClickListener {

    private Button btnstock;
    private Spinner spinnerKategori;
    private RecyclerView recyclerViewBarang;
    private BarangAdapter barangAdapter;
    private DbHelper dbHelper;

    private boolean isAdminMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_tambah_stock);

        dbHelper = new DbHelper(this);
        btnstock = findViewById(R.id.btn_stock);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        recyclerViewBarang = findViewById(R.id.recyclerViewBarangUser);
        Barang barang = getIntent().getParcelableExtra("BARANG");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.kategori_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);
        boolean isAdminMode = determineAdminMode();
        barangAdapter = new BarangAdapter(this, dbHelper.getAllBarang(), this);
        barangAdapter.setAdminMode(isAdminMode);
        recyclerViewBarang.setLayoutManager(new LinearLayoutManager(this));
        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                tampilkanBarang();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
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
    private boolean determineAdminMode() {
        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        String userRole = preferences.getString("user_role", "");
        return "admin".equals(userRole);
    }

    private void tampilkanBarang() {
        String kategori = spinnerKategori.getSelectedItem().toString();
        List<Barang> barangList;
        if ("All Product".equals(kategori)) {
            barangList = dbHelper.getAllBarang();
        } else {
            barangList = dbHelper.getBarangByKategori(kategori);
        }
        barangAdapter.updateData(barangList);
        recyclerViewBarang.setAdapter(barangAdapter);
        barangAdapter.setAdminMode(determineAdminMode());
    }
    @Override
    public void onItemClick(Barang barang) {
        if (barangAdapter.isAdminMode()) {
            Intent intent = new Intent(HalamanTambahStock.this, EditData.class);
            intent.putExtra("BARANG", barang);
            startActivityForResult(intent, 2);
        } else {
            Intent intent = new Intent(HalamanTambahStock.this, DetailBarang.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                tampilkanBarang();
            }
        }
    }

}
