package kelompok7.msibfinalproject2;

public class Barang {
    public long idbarang;
    public String namabarang;
    public int hargabarang;
    public int stokbarang;
    public String kategoribarang;
    public String pathGambar;

    public Barang(int idbarang, String namabarang, int hargabarang, int stokbarang, String kategoribarang, String pathGambarbarang) {
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

    public void kurangiStok(int jumlah) {
        if (jumlah > 0 && jumlah <= stokbarang) {
            stokbarang -= jumlah;
        } else {
            // Tambahkan logika atau penanganan kesalahan sesuai kebutuhan
            System.out.println("Jumlah pembelian tidak valid");
        }
    }
}
