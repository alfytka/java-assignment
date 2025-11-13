import java.util.ArrayList;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

class Menu {
    String nama;
    int harga;
    String kategori; // makanan atau minuman

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

    // setter untuk ubah harga di menu admin
    public void setHarga(int harga) {
        this.harga = harga;
    }
}

public class Main {
    static ArrayList<Menu> daftarMenu = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public static void main(String[] args) {
        inisialisasiMenu();

        int pilihan;

        do {
            tampilkanMenuUtama();
            while (!scanner.hasNextInt()) {
                System.out.println("Input tidak valid. Masukkan angka (0-2):");
                scanner.next();
            }
            pilihan = scanner.nextInt();
            scanner.next();

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

    // mengisi daftarMenu dengan nilai default
    public static void inisialisasiMenu() {
        // makanan
        daftarMenu.add(new Menu("Nasi Goreng", 15000, "Makanan"));
        daftarMenu.add(new Menu("Mie Ayam", 14000, "Makanan"));
        daftarMenu.add(new Menu("Ayam Bakar", 20000, "Makanan"));
        daftarMenu.add(new Menu("Sate Ayam", 22000, "Makanan"));
        // minuman
        daftarMenu.add(new Menu("Es Teh", 6000, "Minuman"));
        daftarMenu.add(new Menu("Es Jeruk", 8000, "Minuman"));
        daftarMenu.add(new Menu("Kopi Hitam", 5000, "Minuman"));
        daftarMenu.add(new Menu("Jus Alpukat", 12000, "Minuman"));
    }

    public static void tampilkanMenuUtama() {
        System.out.println("\n--- Selamat Datang di Restoran ---");
        System.out.println("1. Menu Pemesanan (Pelanggan)");
        System.out.println("2. Menu Pengelolaan (Admin)");
        System.out.println("0. Keluar Aplikasi");
        System.out.print("Silakan pilih menu: ");
    }

    // menampilkan daftar menu berdasarkan kategori
    // param tampilkanMenu untuk manajemen admin
    public static void tampilkanDaftarMenu(boolean tampilkanMenu) {
        System.out.println("\n--- Daftar Menu Restoran ---");

        System.out.println("\n--- Makanan ---");
        int nomor = 1;
        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu menu = daftarMenu.get(i);
            if (menu.getKategori().equals("Makanan")) {
                System.out.println(
                    (tampilkanMenu ? (i + 1) + ". " : "") +
                    menu.getNama() + " - " + formatter.format(menu.getHarga())
                );
                nomor++;
            }
        }

        System.out.println("\n--- Minuman ---");
        nomor = 1; // reset nomor

        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu menu = daftarMenu.get(i);
            if (menu.getKategori().equals("Minuman")) {
                System.out.println(
                    (tampilkanMenu ? (i + 1) + ". " : "") +
                    menu.getNama() + " - " + formatter.format(menu.getHarga())
                );
                nomor++;
            }
        }
        System.out.println("------------------------------");
    }

    // proses pemesanan pelanggan
    public static void menuPelanggan() {
        ArrayList<Menu> pesanan = new ArrayList<>();
        ArrayList<Integer> jumlah = new ArrayList<>();
        String namaMenu;

        tampilkanDaftarMenu(false); // pelanggan memesan berdasarkan nama

        while (true) {
            System.out.print("Masukkan nama menu (atau 'selesai' untuk menyelesaikan pesanan): ");
            namaMenu = scanner.nextLine();

            if (namaMenu.equalsIgnoreCase("selesai")) {
                break;
            }

            Menu menuDipesan = cariMenu(namaMenu);

            if (menuDipesan == null) {
                System.out.println("Menu tidak ditemukan. Silakan input nama menu dengan valid.");
                continue;
            }

            System.out.print("Masukkan jumlah: ");
            int jumlahItem;
            while (!scanner.hasNextInt()) {
                System.out.println("Input harus angka. Masukkan jumlah:");
                scanner.next();
            }
            jumlahItem = scanner.nextInt();
            scanner.nextLine();

            if (jumlahItem <= 0) {
                System.out.println("Jumlah harus lebih dari 0.");
                continue;
            }

            boolean itemSudahAda = false;
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

    // helper method untuk mencari menu berdasarkan nama
    public static Menu cariMenu(String nama) {
        for (Menu menu: daftarMenu) {
            if (menu.getNama().equalsIgnoreCase(nama)) {
                return menu;
            }
        }
        return null;
    }

    /**
     * menghitung total biaya dan mencetak struk pesanan
     * @param pesanan daftar menu yang dipesan
     * @param jumlah daftar jumlah per menu
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
            System.out.println(" Harga: " + formatter.format(item.getHarga()));
            System.out.println(" Total: " + formatter.format(totalPerItem));
        }
        System.out.println("------------------------------");

        System.out.println("Subtotal: \t\t" + formatter.format(subtotal));

        // cek diskon 10%
        if (subtotal > 100000) {
            diskon = subtotal * 0.10;
            System.out.println("Diskon (10%): \t\t-" + formatter.format(diskon));
        }
        // cek promo bogo
        else if (subtotal > 50000) {
            int hargaMinumanTermurah = Integer.MAX_VALUE;
            boolean adaMinuman = false;
            for (Menu item: pesanan) {
                if (item.getKategori().equals("Minuman") && item.getHarga() < hargaMinumanTermurah) {
                    hargaMinumanTermurah = item.getHarga();
                    adaMinuman = true;
                }
            }

            if (adaMinuman) {
                promoBogo = hargaMinumanTermurah;
                System.out.println("Promo BOGO: \t\t-" + formatter.format(promoBogo));
            }
        }

        double totalSetelahPromo = subtotal - diskon - promoBogo;
        double biayaPajak = totalSetelahPromo * PAJAK_RATE;
        double totalBayar = totalSetelahPromo + biayaPajak + BIAYA_PELAYANAN;

        System.out.println("Pajak (10%): \t\t+" + formatter.format(biayaPajak));
        System.out.println("Biaya Pelayanan: \t+" + formatter.format(BIAYA_PELAYANAN));
        System.out.println("------------------------------");
        System.out.println("TOTAL BAYAR: \t\t" + formatter.format(totalBayar));
        System.out.println("------------------------------\n");
    }

    // menampilkan menu untuk admin (tambah, ubah, hapus)
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
                scanner.next();
            }
            pilihan = scanner.nextInt();
            scanner.nextLine();

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
        while (true) {
            System.out.print("Masukkan kategori (Makanan atau Minuman): ");
            kategori = scanner.nextLine();
            if (kategori.equalsIgnoreCase("Makanan") || kategori.equalsIgnoreCase("Minuman")) {
                kategori = kategori.substring(0, 1).toUpperCase() + kategori.substring(1).toLowerCase();
                break;
            } else {
                System.out.println("Kategori tidak valid. Harap masukkan Makanan atau Minuman.");
            }
        }

        daftarMenu.add(new Menu(nama, harga, kategori));
        System.out.println("Menu '" + nama + "' berhasil ditambahkan.");
    }

    public static void ubahMenu() {
        tampilkanDaftarMenu(true);
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
                System.out.println("Nomor menu tidak valid, coba lagi (1-" + daftarMenu.size() + ").");
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
            menu.setHarga(hargaBaru);
            System.out.println("Harga berhasil diubah.");
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    public static void hapusMenu() {
        tampilkanDaftarMenu(true);
        int nomor;

        while (true) {
            System.out.print("Masukkan nomor menu yang akan dihapus (0 untuk batal): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Input harus angka. Coba lagi.");
                scanner.next();
                continue;
            }
            nomor = scanner.nextInt();
            scanner.nextLine();

            if (nomor == 0) {
                System.out.println("Operasi dibatalkan.");
                return; // Keluar dari method
            }
            
            // (Indikator 3: if-else untuk validasi)
            if (nomor > 0 && nomor <= daftarMenu.size()) {
                // INI ADALAH BARIS KRUSIAL
                break; // Keluar dari loop while(true) untuk melanjutkan ke kode di bawah
            } else {
                System.out.println("Nomor menu tidak valid. Coba lagi (1-" + daftarMenu.size() + ").");
            }
        }

        Menu menu = daftarMenu.get(nomor - 1);
        System.out.print("Anda yakin ingin menghapus '" + menu.getNama() + "'? (Ya/Tidak): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            daftarMenu.remove(nomor - 1); // menghapus item dari array list
            System.out.println("Menu berhasil dihapus.");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }
}
