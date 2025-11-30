import java.util.ArrayList;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

class Menu {
    String nama;
    int harga;
    String kategori; // makanan atau minuman

    // constructor untuk membuat object menu baru
    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    // setter ubah harga di menu admin
    public void setHarga(int harga) {
        this.harga = harga;
    }
}

public class Main {
    static ArrayList<Menu> daftarMenu = new ArrayList<>();
    
    // scanner untuk input pengguna
    static Scanner scanner = new Scanner(System.in);
    
    // formatter untuk menampilkan mata uang Rupiah
    static NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public static void main(String[] args) {
        // inisialisasi menu default
        inisialisasiMenu();

        int pilihan;

        do {
            tampilkanMenuUtama();
            while (!scanner.hasNextInt()) {
                System.out.println("Input tidak valid. Masukkan angka (0-2):");
                scanner.next(); // buang input salah
            }
            pilihan = scanner.nextInt();
            scanner.nextLine(); // membersihkan newline

            // struktur Keputusan switch case
            switch (pilihan) {
                case 1:
                    menuPelanggan();
                    break;
                case 2:
                    menuAdmin();
                    break;
                case 0:
                    System.out.println("Terima kasih telah menggunakan aplikasi.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihan != 0);

        scanner.close();
    }

    /**
     * mengisi daftarMenu dengan data awal (minimal 4 makanan, 4 minuman)
     */
    public static void inisialisasiMenu() {
        // makanan
        daftarMenu.add(new Menu("Nasi Goreng", 25000, "Makanan"));
        daftarMenu.add(new Menu("Mie Ayam", 35000, "Makanan"));
        daftarMenu.add(new Menu("Ayam Bakar", 30000, "Makanan"));
        daftarMenu.add(new Menu("Sate Ayam", 22000, "Makanan"));
        // minuman
        daftarMenu.add(new Menu("Es Teh Manis", 8000, "Minuman"));
        daftarMenu.add(new Menu("Jus Alpukat", 15000, "Minuman"));
        daftarMenu.add(new Menu("Es Jeruk", 10000, "Minuman"));
        daftarMenu.add(new Menu("Kopi Susu", 18000, "Minuman"));
    }

    /**
     * menampilkan menu utama (pelanggan / admin)
     */
    public static void tampilkanMenuUtama() {
        System.out.println("\n--- Selamat Datang di Restoran ---");
        System.out.println("1. Menu Pemesanan (Pelanggan)");
        System.out.println("2. Menu Pengelolaan (Admin)");
        System.out.println("0. Keluar Aplikasi");
        System.out.print("Silakan pilih menu: ");
    }

    /**
     * menampilkan daftar menu yang dikelompokkan berdasarkan kategori
     * tampilkanNomor boolean untuk manajemen admin
     */
    public static void tampilkanDaftarMenu(boolean tampilkanNomor) {
        System.out.println("\n--- Daftar Menu Restoran ---");
        
        // struktur Pengulangan for-each
        System.out.println("\n--- Makanan ---");
        int nomor = 1;
        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu menu = daftarMenu.get(i);
            // struktur Keputusan if
            if (menu.getKategori().equals("Makanan")) {
                System.out.println(
                    (tampilkanNomor ? (i + 1) + ". " : "") + 
                    menu.getNama() + " - " + formatter.format(menu.getHarga())
                );
                nomor++;
            }
        }

        System.out.println("\n--- Minuman ---");
        nomor = 1; // reset nomor untuk penomoran per kategori, akan tetapi untuk admin
        
        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu menu = daftarMenu.get(i);
            if (menu.getKategori().equals("Minuman")) {
                System.out.println(
                    (tampilkanNomor ? (i + 1) + ". " : "") + 
                    menu.getNama() + " - " + formatter.format(menu.getHarga())
                );
                nomor++;
            }
        }
        System.out.println("------------------------------");
    }

    /**
     * logika untuk alur pemesanan pelanggan
     */
    public static void menuPelanggan() {
        ArrayList<Menu> pesanan = new ArrayList<>();
        ArrayList<Integer> jumlah = new ArrayList<>();
        String namaMenu;

        tampilkanDaftarMenu(false); // pelanggan memesan berdasarkan nama

        // struktur Pengulangan while
        while (true) {
            System.out.print("Masukkan nama menu (atau 'selesai' untuk selesai): ");
            namaMenu = scanner.nextLine();

            if (namaMenu.equalsIgnoreCase("selesai")) {
                break;
            }

            Menu menuDipesan = cariMenu(namaMenu);
            
            // struktur Keputusan if-else
            if (menuDipesan == null) {
                System.out.println("Menu tidak ditemukan. Silakan input nama menu yang valid.");
                continue; // kembali ke awal loop while
            }

            System.out.print("Masukkan jumlah: ");
            int jumlahItem;
            while (!scanner.hasNextInt()) {
                System.out.println("Input harus angka. Masukkan jumlah:");
                scanner.next(); // buang input salah
            }
            jumlahItem = scanner.nextInt();
            scanner.nextLine(); // membersihkan newline

            if (jumlahItem <= 0) {
                System.out.println("Jumlah harus lebih dari 0.");
                continue;
            }

            // cek apakah menu sudah ada di pesanan, jika ya, tambahkan jumlahnya
            boolean itemSudahAda = false;
            // struktur Pengulangan for
            for (int i = 0; i < pesanan.size(); i++) {
                if (pesanan.get(i).getNama().equalsIgnoreCase(menuDipesan.getNama())) {
                    jumlah.set(i, jumlah.get(i) + jumlahItem);
                    itemSudahAda = true;
                    break;
                }
            }

            if (!itemSudahAda) {
                pesanan.add(menuDipesan);
                jumlah.add(jumlahItem);
            }
            System.out.println(">> " + jumlahItem + " " + menuDipesan.getNama() + " ditambahkan ke pesanan.");
        }

        if (pesanan.isEmpty()) {
            System.out.println("Anda tidak memesan apapun.");
        } else {
            hitungTotalDanCetakStruk(pesanan, jumlah);
        }
    }

    /**
     * method untuk mencari menu berdasarkan nama
     */
    public static Menu cariMenu(String nama) {
        for (Menu menu : daftarMenu) {
            if (menu.getNama().equalsIgnoreCase(nama)) {
                return menu;
            }
        }
        return null;
    }

    /**
     * menghitung total biaya dan mencetak struk pesanan
     * pesanan Daftar menu yang dipesan
     * jumlah Daftar jumlah per menu
     */
    public static void hitungTotalDanCetakStruk(ArrayList<Menu> pesanan, ArrayList<Integer> jumlah) {
        int subtotal = 0;
        final double PAJAK_RATE = 0.10; // 10%
        final int BIAYA_PELAYANAN = 20000;
        double diskon = 0;
        int promoBogo = 0; // nilai promo beli 1 gratis 1

        System.out.println("\n--- Struk Pembayaran ---");
        for (int i = 0; i < pesanan.size(); i++) {
            Menu item = pesanan.get(i);
            int jml = jumlah.get(i);
            int totalPerItem = item.getHarga() * jml;
            subtotal += totalPerItem;

            System.out.println(item.getNama() + " x" + jml);
            System.out.println("  Harga: " + formatter.format(item.getHarga()));
            System.out.println("  Total: " + formatter.format(totalPerItem));
        }
        System.out.println("------------------------------");

        System.out.println("Subtotal: \t\t" + formatter.format(subtotal));

        // cek diskon 10%
        if (subtotal > 100000) {
            diskon = subtotal * 0.10;
            System.out.println("Diskon (10%): \t\t-" + formatter.format(diskon));
        }
        // cek promo BOGO (hanya jika diskon 10% tidak berlaku)
        else if (subtotal > 50000) {
            // cari minuman terendah di pesanan
            int hargaMinumanTermurah = Integer.MAX_VALUE;
            boolean adaMinuman = false;
            for (Menu item : pesanan) {
                if (item.getKategori().equals("Minuman") && item.getHarga() < hargaMinumanTermurah) {
                    hargaMinumanTermurah = item.getHarga();
                    adaMinuman = true;
                }
            }
            
            // struktur Keputusan nested if
            if (adaMinuman) {
                promoBogo = hargaMinumanTermurah;
                System.out.println("Promo BOGO: \t\t-" + formatter.format(promoBogo));
            }
        }
        
        // hitung total setelah diskon/promo
        double totalSetelahPromo = subtotal - diskon - promoBogo;
        
        // hitung pajak (10% dari total setelah promo)
        double biayaPajak = totalSetelahPromo * PAJAK_RATE;
        
        // hitung total akhir
        double totalBayar = totalSetelahPromo + biayaPajak + BIAYA_PELAYANAN;

        System.out.println("Pajak (10%): \t\t+" + formatter.format(biayaPajak));
        System.out.println("Biaya Pelayanan: \t+" + formatter.format(BIAYA_PELAYANAN));
        System.out.println("------------------------------");
        System.out.println("TOTAL BAYAR: \t\t" + formatter.format(totalBayar));
        System.out.println("------------------------------\n");
    }

    // admin
    /**
     * menampilkan menu untuk admin (tambah, ubah, hapus)
     */
    public static void menuAdmin() {
        int pilihan;
        do {
            System.out.println("\n--- Menu Pengelolaan Admin ---");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("0. Kembali ke Menu Utama");
            System.out.print("Pilihan: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Input tidak valid. Masukkan angka (0-3):");
                scanner.next(); // buang input salah
            }
            pilihan = scanner.nextInt();
            scanner.nextLine(); // membersihkan newline

            switch (pilihan) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    ubahMenu();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 0:
                    System.out.println("Kembali ke menu utama...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 0);
    }

    /**
     * logika untuk menambah menu baru
     */
    public static void tambahMenu() {
        System.out.print("Masukkan nama menu baru: ");
        String nama = scanner.nextLine();
        
        System.out.print("Masukkan harga baru: ");
        int harga;
        while (!scanner.hasNextInt()) {
            System.out.println("Harga harus angka. Masukkan harga:");
            scanner.next();
        }
        harga = scanner.nextInt();
        scanner.nextLine();
        
        String kategori;
        while(true) {
            System.out.print("Masukkan kategori (Makanan / Minuman): ");
            kategori = scanner.nextLine();
            if (kategori.equalsIgnoreCase("Makanan") || kategori.equalsIgnoreCase("Minuman")) {
                // kapitalisasi agar konsisten
                kategori = kategori.substring(0, 1).toUpperCase() + kategori.substring(1).toLowerCase();
                break;
            } else {
                System.out.println("Kategori tidak valid. Harap masukkan 'Makanan' atau 'Minuman'.");
            }
        }

        daftarMenu.add(new Menu(nama, harga, kategori));
        System.out.println("Menu '" + nama + "' berhasil ditambahkan.");
    }

    /**
     * logika untuk mengubah harga menu
     */
    public static void ubahMenu() {
        tampilkanDaftarMenu(true); // tampilkan dengan nomor
        int nomor;
        
        while (true) {
            System.out.print("Masukkan nomor menu yang akan diubah (0 untuk batal): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Input harus angka. Coba lagi.");
                scanner.next();
                continue;
            }
            nomor = scanner.nextInt();
            scanner.nextLine();

            if (nomor == 0) {
                System.out.println("Operasi dibatalkan.");
                return;
            }
            if (nomor > 0 && nomor <= daftarMenu.size()) {
                break; // nomor valid
            } else {
                System.out.println("Nomor menu tidak valid. Coba lagi (1-" + daftarMenu.size() + ").");
            }
        }
        
        Menu menu = daftarMenu.get(nomor - 1);
        System.out.println("Mengubah: " + menu.getNama() + " (Harga lama: " + formatter.format(menu.getHarga()) + ")");
        System.out.print("Masukkan harga baru: ");
        int hargaBaru;
        while (!scanner.hasNextInt()) {
            System.out.println("Harga harus angka. Masukkan harga baru:");
            scanner.next();
        }
        hargaBaru = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Anda yakin ingin mengubah harga? (Ya/Tidak): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            menu.setHarga(hargaBaru); // menggunakan method pada object
            System.out.println("Harga berhasil diubah.");
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    /**
     * logika untuk menghapus menu
     */
    public static void hapusMenu() {
        tampilkanDaftarMenu(true); // tampilkan dengan nomor
        int nomor;
        
        // while untuk validasi input
        while (true) {
            System.out.print("Masukkan nomor menu yang akan dihapus (0 untuk batal): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Input harus angka. Coba lagi.");
                scanner.next(); // buang input salah
                continue;
            }
            nomor = scanner.nextInt();
            scanner.nextLine();

            if (nomor == 0) {
                System.out.println("Operasi dibatalkan.");
                return;
            }
            // kondisi untuk validasi
            if (nomor > 0 && nomor <= daftarMenu.size()) {
                break; // nomor valid, keluar loop
            } else {
                System.out.println("Nomor menu tidak valid. Coba lagi (1-" + daftarMenu.size() + ").");
            }
        }

        Menu menu = daftarMenu.get(nomor - 1);
        System.out.print("Anda yakin ingin menghapus '" + menu.getNama() + "'? (Ya/Tidak): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            daftarMenu.remove(nomor - 1); // menghapus item dari ArrayList
            System.out.println("Menu berhasil dihapus.");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }
}