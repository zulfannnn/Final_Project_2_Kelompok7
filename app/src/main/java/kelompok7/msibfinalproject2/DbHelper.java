package kelompok7.msibfinalproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DatabaseFinalProject2";
    public static final int DATABASE_VERSION = 3;

    public static final String TABLE_REGISTER_USER = "registeruser";
    public static final String CO_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";


    public static final String TABLE_REGISTER_STAFF = "registerstaff";
    public static final String ID_STAFF="ID";
    public static final String NAME_STAFF="name";
    public static final String PASSWORD_STAFF="password";


    public static final String TABLE_NAME ="registeradmin";
    public static final String COL_1="ID";
    public static final String COL_2="name";
    public static final String COL_3="password";

    public static final String TABLE_BARANG = "tabelbarang";
    public static final String ID_BARANG = "idbarang";
    public static final String NAMA_BARANG = "namabarang";
    public static final String HARGA_BARANG = "hargabarang";
    public static final String STOK_BARANG = "stokbarang";
    public static final String KATEGORI_BARANG ="kategoribarang";
    public static final String GAMBAR_BARANG = "pathgambar";
    private Context mContext;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRegisterStaff = " CREATE TABLE " + TABLE_REGISTER_STAFF + "("
                + ID_STAFF + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_STAFF + " TEXT, "
                + PASSWORD_STAFF + " TEXT) ";

        String createUserTable = "CREATE TABLE " + TABLE_NAME + "("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_2 + " TEXT, " + COL_3 + " TEXT)";

        String createRegisterUserTable = "CREATE TABLE " + TABLE_REGISTER_USER + "("
                + CO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PASSWORD + " TEXT) ";

        String createBarangTable = "CREATE TABLE " + TABLE_BARANG + "(" +
                ID_BARANG + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAMA_BARANG + " TEXT, " +
                HARGA_BARANG + " INTEGER, " +
                STOK_BARANG + " INTEGER, " +
                KATEGORI_BARANG + " TEXT, " +
                GAMBAR_BARANG + " TEXT)";

        db.execSQL(createRegisterStaff);
        db.execSQL(createUserTable);
        db.execSQL(createRegisterUserTable);
        db.execSQL(createBarangTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER_STAFF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARANG);
        onCreate(db);
    }
    public boolean updateBarang(Barang barang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAMA_BARANG, barang.getNamabarang());
        values.put(HARGA_BARANG, barang.getHargabarang());
        values.put(STOK_BARANG, barang.getStokbarang());
        values.put(KATEGORI_BARANG, barang.getKategoribarang());
        String whereClause = ID_BARANG + " = ?";
        String[] whereArgs = {String.valueOf(barang.getIdbarang())};
        int rowsUpdated = db.update(TABLE_BARANG, values, whereClause, whereArgs);
        db.close();
        return rowsUpdated > 0;
    }
    private String saveImageToStorage(byte[] imageBytes) {
        try {
            File internalStorageDir = mContext.getApplicationContext().getFilesDir();
            String fileName = "image_" + System.currentTimeMillis() + ".png";
            File imageFile = new File(internalStorageDir, fileName);
            FileOutputStream fos = new FileOutputStream(imageFile);
            fos.write(imageBytes);
            fos.close();
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    public List<Barang> getAllBarang() {
        List<Barang> barangList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID_BARANG, NAMA_BARANG, HARGA_BARANG, STOK_BARANG, KATEGORI_BARANG, GAMBAR_BARANG};
        Cursor cursor = db.query(TABLE_BARANG, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idbarang = cursor.getInt(cursor.getColumnIndexOrThrow(ID_BARANG));
                String namabarang = cursor.getString(cursor.getColumnIndexOrThrow(NAMA_BARANG));
                int hargabarang = cursor.getInt(cursor.getColumnIndexOrThrow(HARGA_BARANG));
                int stokbarang = cursor.getInt(cursor.getColumnIndexOrThrow(STOK_BARANG));
                String kategoribarang = cursor.getString(cursor.getColumnIndexOrThrow(KATEGORI_BARANG));
                String pathGambarbarang = cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR_BARANG));
                Barang barang = new Barang(idbarang, namabarang, hargabarang, stokbarang, kategoribarang, pathGambarbarang);
                barangList.add(barang);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return barangList;
    }
    public Uri getGambarUriById(int idBarang) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {GAMBAR_BARANG};
        String selection = ID_BARANG + "=?";
        String[] selectionArgs = {String.valueOf(idBarang)};
        Cursor cursor = db.query(TABLE_BARANG, columns, selection, selectionArgs, null, null, null);
        Uri gambarUri = null;
        if (cursor.moveToFirst()) {
            String pathGambar = cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR_BARANG));
            if (!pathGambar.isEmpty()) {
                gambarUri = Uri.parse(pathGambar);
            }
        }
        cursor.close();
        db.close();
        return gambarUri;
    }
    public List<Barang> getBarangByKategori(String kategori) {
        List<Barang> barangList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID_BARANG, NAMA_BARANG, HARGA_BARANG, STOK_BARANG, KATEGORI_BARANG, GAMBAR_BARANG};
        String selection = KATEGORI_BARANG + " = ?";
        String[] selectionArgs = {kategori};
        Cursor cursor = db.query(TABLE_BARANG, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idbarang = cursor.getInt(cursor.getColumnIndexOrThrow(ID_BARANG));
                String namabarang = cursor.getString(cursor.getColumnIndexOrThrow(NAMA_BARANG));
                int hargabarang = cursor.getInt(cursor.getColumnIndexOrThrow(HARGA_BARANG));
                int stokbarang = cursor.getInt(cursor.getColumnIndexOrThrow(STOK_BARANG));
                String kategoribarang = cursor.getString(cursor.getColumnIndexOrThrow(KATEGORI_BARANG));
                String pathGambarbarang = cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR_BARANG));
                Barang barang = new Barang(idbarang, namabarang, hargabarang, stokbarang, kategoribarang, pathGambarbarang);
                barangList.add(barang);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return barangList;
    }
    public boolean addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        long newRowId = db.insert(TABLE_REGISTER_USER, null, values);
        db.close();
        return newRowId > 0;
    }
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {CO_ID};
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_REGISTER_USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    public long tambahStaff(String name, String password) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("password",password);
        long res = db.insert(TABLE_REGISTER_STAFF,null,contentValues);
        db.close();
        return res;
    }
    public boolean cekLoginStaff(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTER_STAFF +
                " WHERE name = ? AND password = ?", new String[]{name, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }
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
    public void tambahBarang(String namabarang, int hargabarang, int stokbarang, String kategoribarang, String pathGambarbarang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAMA_BARANG, namabarang);
        values.put(HARGA_BARANG, hargabarang);
        values.put(STOK_BARANG, stokbarang);
        values.put(KATEGORI_BARANG, kategoribarang);
        values.put(GAMBAR_BARANG, pathGambarbarang);
        if (!pathGambarbarang.isEmpty()) {
        } else {
        }
        db.insert(TABLE_BARANG, null, values);
        db.close();
    }
}
