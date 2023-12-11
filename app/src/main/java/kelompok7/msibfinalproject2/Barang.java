package kelompok7.msibfinalproject2;

import android.os.Parcel;
import android.os.Parcelable;

public class Barang implements Parcelable {
    public long idbarang;
    public String namabarang;
    public int hargabarang;
    public int stokbarang;
    public String kategoribarang;
    public String pathGambar;

    public Barang(long idbarang, String namabarang, int hargabarang, int stokbarang, String kategoribarang, String pathGambarbarang) {
        this.idbarang = idbarang;
        this.namabarang = namabarang;
        this.hargabarang = hargabarang;
        this.stokbarang = stokbarang;
        this.kategoribarang = kategoribarang;
        this.pathGambar = pathGambarbarang;
    }
    public long getIdbarang() {
        return idbarang;
    }

    public void setIdbarang(long idbarang) {
        this.idbarang = idbarang;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public int getHargabarang() {
        return hargabarang;
    }

    public void setHargabarang(int hargabarang) {
        this.hargabarang = hargabarang;
    }

    public int getStokbarang() {
        return stokbarang;
    }

    public void setStokbarang(int stokbarang) {
        this.stokbarang = stokbarang;
    }

    public String getKategoribarang() {
        return kategoribarang;
    }

    public void setKategoribarang(String kategoribarang) {
        this.kategoribarang = kategoribarang;
    }

    public String getPathGambar() {
        return pathGambar;
    }

    public void setPathGambar(String pathGambar) {
        this.pathGambar = pathGambar;
    }

    public void kurangiStok(int jumlah) throws IllegalArgumentException {
        if (jumlah > 0 && jumlah <= stokbarang) {
            stokbarang -= jumlah;
        } else {
            throw new IllegalArgumentException("Jumlah pembelian tidak valid");
        }
    }
    protected Barang(Parcel in) {
        idbarang = in.readLong();
        namabarang = in.readString();
        hargabarang = in.readInt();
        stokbarang = in.readInt();
        kategoribarang = in.readString();
        pathGambar = in.readString();
    }
    public static final Parcelable.Creator<Barang> CREATOR = new Parcelable.Creator<Barang>() {
        @Override
        public Barang createFromParcel(Parcel in) {
            return new Barang(in);
        }

        @Override
        public Barang[] newArray(int size) {
            return new Barang[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idbarang);
        dest.writeString(namabarang);
        dest.writeInt(hargabarang);
        dest.writeInt(stokbarang);
        dest.writeString(kategoribarang);
        dest.writeString(pathGambar);
    }
}

