package kelompok7.msibfinalproject2;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.Manifest;

public class HalamanMenu extends AppCompatActivity {

    private Spinner spinnerKategoriUser;
    private RecyclerView recyclerViewBarangUser;
    private DbHelper dbHelper;
    private List<Barang> listBarang;
    private BarangAdapter barangAdapter;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int KODE_AKTIVITAS_PEMBELIAN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_menu);
        dbHelper = new DbHelper(this);
        spinnerKategoriUser = findViewById(R.id.spinnerKategoriUser);
        recyclerViewBarangUser = findViewById(R.id.recyclerViewBarangUser);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.kategori_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategoriUser.setAdapter(adapter);
        barangAdapter = new BarangAdapter(this, listBarang, new BarangAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Barang barang) {
                Intent intent = new Intent(HalamanMenu.this, DetailBarang.class);
                intent.putExtra("BARANG", barang);
                startActivityForResult(intent, 3);
            }
        });
        recyclerViewBarangUser.setAdapter(barangAdapter);
        recyclerViewBarangUser.setLayoutManager(new LinearLayoutManager(this));
        spinnerKategoriUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                tampilkanBarang();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        tampilkanBarang();
        if (checkStoragePermission()) {
        } else {
            requestStoragePermission();
        }
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
    }
    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
    private void handleItemClick(Barang barang) {
        Intent intent = new Intent(this, DetailBarang.class);
        intent.putExtra("BARANG", barang); // barang harus merupakan objek Parcelable
        startActivityForResult(intent, KODE_AKTIVITAS_PEMBELIAN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                tampilkanBarang();
            }
        }
    }
    private void tampilkanBarang() {
        String kategori = spinnerKategoriUser.getSelectedItem().toString();

        if ("All Product".equals(kategori)) {
            listBarang = fetchData();
        } else {
            listBarang = dbHelper.getBarangByKategori(kategori);
        }
        barangAdapter.updateData(listBarang);
        barangAdapter.notifyDataSetChanged();
    }
    private ArrayList<Barang> fetchData() {
        return new ArrayList<>(dbHelper.getAllBarang());
    }
}
