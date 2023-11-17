package kelompok7.msibfinalproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DatabaseFinalProject2";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_REGISTERUSER = "registeruser";
    public static final String CO_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";


    public static final String TABLE_NAME ="registerstaff";
    public static final String COL_1="ID";
    public static final String COL_2="name";
    public static final String COL_3="password";

    public static final String TABEL_BARANG = "tabelbarang";
    public static final String ID_BARANG = "idbarang";
    public static final String NAMA_BARANG = "namabarang";
    public static final String HARGA_BARANG = "hargabarang";
    public static final String STOK_BARANG = "stokbarang";
    public static final String KATEGORI_BARANG ="kategoribarang";
    public static final String GAMBAR_BARANG ="pathgambar";
    public int idbarang;


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void updateBarang(Barang barang) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STOK_BARANG, barang.getStokbarang());

        // Menentukan baris yang akan diupdate berdasarkan ID
        String whereClause = ID_BARANG + " = ?";
        String[] whereArgs = {String.valueOf(barang.getIdbarang())};

        // Melakukan proses update
        db.update(TABEL_BARANG, values, whereClause, whereArgs);

        db.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String tabel1 = "CREATE TABLE " + TABLE_NAME + "("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_2 + " TEXT, " + COL_3 + " TEXT)";

        String tabel2 = "CREATE TABLE " + TABLE_REGISTERUSER + "("
                + CO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PASSWORD + " TEXT) ";

        String CREATE_TABLE_BARANG = "CREATE TABLE " + TABEL_BARANG + "(" +
                ID_BARANG + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAMA_BARANG + " TEXT, " +
                HARGA_BARANG + " INTEGER, " +
                STOK_BARANG + " INTEGER, " +
                KATEGORI_BARANG + " TEXT, " +
                GAMBAR_BARANG + " TEXT)";

        db.execSQL(tabel1);
        db.execSQL(tabel2);
        db.execSQL(CREATE_TABLE_BARANG);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTERUSER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_BARANG);
        onCreate(db);
    }

    //Registrasi&Login User
    public boolean addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long newRowId = db.insert(TABLE_REGISTERUSER, null, values);
        db.close();

        return newRowId > 0;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {CO_ID};
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_REGISTERUSER, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    //tambah staff
    public long tambahStaff(String name, String password) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",name);
        contentValues.put("password",password);
        long res = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        return res;
    }

    //login admin
    public Boolean User(String name, String password) {
        String[] columns = { COL_1 };
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " AND " + COL_3 + "=?";
        String[] selectionArgs = { name, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    //tambah barang
    public void tambahBarang(String namabarang, int hargabarang, int stokbarang, String kategoribarang, String pathGambar) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAMA_BARANG, namabarang);
        values.put(HARGA_BARANG, hargabarang);
        values.put(STOK_BARANG, stokbarang);
        values.put(KATEGORI_BARANG, kategoribarang);
        values.put(GAMBAR_BARANG, pathGambar);

        db.insert(TABEL_BARANG, null, values);
        db.close();
    }
    public List<Barang> getBarangByKategori(String kategori) {
        List<Barang> barangList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query;
        String[] selectionArgs;

        if ("All Product".equals(kategori)) {
            query = "SELECT * FROM " + TABEL_BARANG;
            selectionArgs = null;
        } else {
            query = "SELECT * FROM " + TABEL_BARANG + " WHERE " + KATEGORI_BARANG + " = ?";
            selectionArgs = new String[]{kategori};
        }
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                int idbarang = cursor.getInt(cursor.getColumnIndexOrThrow(ID_BARANG));
                String namabarang = cursor.getString(cursor.getColumnIndexOrThrow(NAMA_BARANG));
                int hargabarang = cursor.getInt(cursor.getColumnIndexOrThrow(HARGA_BARANG));
                int stokbarang = cursor.getInt(cursor.getColumnIndexOrThrow(STOK_BARANG));
                String kategorigambar = cursor.getString(cursor.getColumnIndexOrThrow(KATEGORI_BARANG));
                String pathGambarbarang = cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR_BARANG));

                Barang barang = new Barang(idbarang, namabarang, hargabarang, stokbarang, kategorigambar, pathGambarbarang);
                barangList.add(barang);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return barangList;
    }
    public List<Barang> getAllBarang() {
        List<Barang> listBarang = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ID_BARANG,
                NAMA_BARANG,
                HARGA_BARANG,
                STOK_BARANG,
                KATEGORI_BARANG,
                GAMBAR_BARANG
                // Tambahkan kolom lainnya sesuai kebutuhan
        };

        Cursor cursor = db.query(
                TABEL_BARANG,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idbarang = cursor.getInt(cursor.getColumnIndexOrThrow(ID_BARANG));
                String namabarang = cursor.getString(cursor.getColumnIndexOrThrow(NAMA_BARANG));
                int hargabarang = cursor.getInt(cursor.getColumnIndexOrThrow(HARGA_BARANG));
                int stokbarang = cursor.getInt(cursor.getColumnIndexOrThrow(STOK_BARANG));
                String kategoribarang = cursor.getString(cursor.getColumnIndexOrThrow(KATEGORI_BARANG));
                String pathGambarbarang = cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR_BARANG));

                Barang barang = new Barang(idbarang, namabarang, hargabarang, stokbarang, kategoribarang, pathGambarbarang);
                listBarang.add(barang);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return listBarang;
    }
    public Barang getBarangById() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ID_BARANG,
                NAMA_BARANG,
                HARGA_BARANG,
                STOK_BARANG,
                KATEGORI_BARANG,
                GAMBAR_BARANG
                // Tambahkan kolom lainnya sesuai kebutuhan
        };

        String selection = ID_BARANG + " = ?";
        String[] selectionArgs = {String.valueOf(idbarang)};

        Cursor cursor = db.query(
                TABEL_BARANG,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Barang barang = null;

        if (cursor != null && cursor.moveToFirst()) {
            String namabarang = cursor.getString(cursor.getColumnIndexOrThrow(NAMA_BARANG));
            int hargabarang = cursor.getInt(cursor.getColumnIndexOrThrow(HARGA_BARANG));
            int stokbarang = cursor.getInt(cursor.getColumnIndexOrThrow(STOK_BARANG));
            String kategoribarang = cursor.getString(cursor.getColumnIndexOrThrow(KATEGORI_BARANG));
            String pathGambarbarang = cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR_BARANG));

            // Inisialisasi objek barang
            barang = new Barang(idbarang, namabarang, hargabarang, stokbarang, kategoribarang, pathGambarbarang);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return barang;
    }
}
