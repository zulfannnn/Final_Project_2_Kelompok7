package kelompok7.msibfinalproject2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class HalamanMenu extends AppCompatActivity {

    private Spinner spinnerKategoriUser;
    private RecyclerView recyclerViewBarangUser;

    private DbHelper dbHelper;
    private List<Barang> listBarang;
    private BarangAdapter barangAdapter;

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

        // Mendapatkan data barang (listBarang) dari sumber data Anda
        listBarang = fetchData();

        // Membuat adapter dan menetapkannya pada RecyclerView
        barangAdapter = new BarangAdapter(this, listBarang);
        recyclerViewBarangUser.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBarangUser.setAdapter(barangAdapter);

        spinnerKategoriUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    }

    private void tampilkanBarang() {
        String kategori = spinnerKategoriUser.getSelectedItem().toString();
        List<Barang> barangList = dbHelper.getBarangByKategori(kategori);

        // Memperbarui adapter dengan data yang baru
        listBarang.clear(); // Menghapus data lama
        listBarang.addAll(barangList); // Menambahkan data baru
        barangAdapter.notifyDataSetChanged();
    }

    // Metode untuk mendapatkan data barang dari sumber data
    private ArrayList<Barang> fetchData() {
        // Implementasikan sesuai dengan kebutuhan Anda
        // Contoh sederhana: return data dari database atau sumber data lainnya
        return new ArrayList<>(dbHelper.getAllBarang());
    }
}

//public class HalamanMenu extends AppCompatActivity {
//
//    private Spinner spinnerKategoriUser;
//    public ListView recyclerView;
//
//    private DbHelper dbHelper;
//    private List<Barang> listBarang;
//    private BarangAdapter barangAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_halaman_menu);
//
//        dbHelper = new DbHelper(this);
//
//        spinnerKategoriUser = findViewById(R.id.spinnerKategoriUser);
//        recyclerView = findViewById(R.id.RecycleViewBarangUser);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this,
//                R.array.kategori_array,
//                android.R.layout.simple_spinner_item
//        );
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerKategoriUser.setAdapter(adapter);
//
//        //Mendapatkan data barang (listBarang) dari sumber data Anda
//        ArrayList<Barang> listBarang = fetchData();
//        //Membuat adapter dan menetapkannya pada ListView
//        BarangAdapter Adapter = new BarangAdapter(this, listBarang);
//        recyclerView.setAdapter(Adapter);
//
//        spinnerKategoriUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                tampilkanBarang();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Do nothing here
//            }
//        });
//
//        tampilkanBarang();
//    }
//    private void tampilkanBarang() {
//        String kategori = spinnerKategoriUser.getSelectedItem().toString();
//        List<Barang> barangList = dbHelper.getBarangByKategori(kategori);
//        BarangAdapter adapter = new BarangAdapter(this, barangList);
//        barangAdapter = new BarangAdapter(this, barangList);
//        recyclerView.setAdapter(barangAdapter);
//    }
//
//    // Metode untuk mendapatkan data barang dari sumber data
//    private ArrayList<Barang> fetchData() {
//        // Implementasikan sesuai dengan kebutuhan Anda
//        // Contoh sederhana: return data dari database atau sumber data lainnya
//        return (ArrayList<Barang>) dbHelper.getAllBarang();
//    }
//}


//        spinnerKategoriUser = findViewById(R.id.spinnerKategoriUser);
//        setupSpinner();
//
//        // Inisialisasi data barang (listBarang) dari database SQLite
//        listBarang = getDataFromDatabase();
//
//        // Inisialisasi adapter dan atur ke RecyclerView
//        barangAdapter = new BarangAdapter(listBarang, this);
//        recyclerView.setAdapter(barangAdapter);
//    }
//
//    private void setupSpinner() {
//        // Daftar kategori yang akan ditampilkan di Spinner
//        String[] kategoriArray = {"Semua Kategori", "Elektronik", "Apparels", "Book", "Smartwatch"};
//
//        // Buat ArrayAdapter untuk Spinner
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategoriArray);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerKategoriUser.setAdapter(spinnerAdapter);
//
//        // Tambahkan listener untuk menangkap perubahan kategori
//        spinnerKategoriUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                // Panggil metode untuk memperbarui RecyclerView berdasarkan kategori yang dipilih
//                updateRecyclerViewByCategory(kategoriArray[position]);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Do nothing here
//            }
//        });
//    }
//
//    private List<Barang> getDataFromDatabase() {
//        // Implementasikan logika Anda untuk mengambil data dari database SQLite berdasarkan kategori "Semua Kategori"
//        // ...
//
//        // Saat ini, kembalikan daftar dummy
//        return new ArrayList<>();
//    }
//
//    private void updateRecyclerViewByCategory(String selectedCategory) {
//        // Implementasikan logika Anda untuk mengambil data dari database SQLite berdasarkan kategori yang dipilih
//        // ...
//
//        // Saat ini, kembalikan daftar dummy
//        List<Barang> filteredList = new ArrayList<>();
//        for (Barang barang : listBarang) {
//            if (selectedCategory.equals("Semua Kategori") || barang.getKategoribarang().equals(selectedCategory)) {
//                filteredList.add(barang);
//            }
//        }
//
//        // Perbarui adapter dengan data yang difilter
////        barangAdapter.setData(filteredList);
//        barangAdapter.notifyDataSetChanged();
//    }
//}

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this,
//                R.array.kategori_array,
//                android.R.layout.simple_spinner_item
//        );
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerKategoriUser.setAdapter(adapter);
//
//        // Inisialisasi data barang (listBarang) dari database SQLite
//        listBarang = getDataFromDatabase();
//
//        // Inisialisasi adapter dan atur ke RecyclerView
//        barangAdapter = new BarangAdapter(listBarang, this);
//        recyclerView.setAdapter(barangAdapter);
//
////        // Inisialisasi data barang (listBarang) dari database SQLite
////        listBarang = getDataFromDatabase();
////
////        // Inisialisasi adapter dan atur ke RecyclerView
////        barangAdapter = new BarangAdapter(listBarang, this);
////        recyclerView.setAdapter(barangAdapter);
//
////         Mendapatkan data barang (listBarang) dari sumber data Anda
////        ArrayList<Barang> listBarang = fetchData();
////         Membuat adapter dan menetapkannya pada ListView
////        barangAdapter = new BarangAdapter(this, listBarang);
////        recyclerView.setAdapter(barangAdapter);
////        BarangAdapter Adapter = new BarangAdapter(this, listBarang);
////        recyclerView.setAdapter(Adapter);
//
//        spinnerKategoriUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                tampilkanBarang();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Do nothing here
//            }
//        });
//
////        tampilkanBarang();
//    }
//
//    private List<Barang> getDataFromDatabase() {
//
//    }
//
////    private void tampilkanBarang() {
////        String kategori = spinnerKategoriUser.getSelectedItem().toString();
////        List<Barang> barangList = dbHelper.getBarangByKategori(kategori);
////        BarangAdapter adapter = new BarangAdapter(this, barangList);
////        barangAdapter = new BarangAdapter(this, barangList);
////        recyclerView.setAdapter(barangAdapter);
//    }
//    // Metode untuk mendapatkan data barang dari sumber data
//    private ArrayList<Barang> fetchData() {
//        // Implementasikan sesuai dengan kebutuhan Anda
//        // Contoh sederhana: return data dari database atau sumber data lainnya
//        return (ArrayList<Barang>) dbHelper.getAllBarang();
//    }
