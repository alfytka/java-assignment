import java.util.Scanner;

class Menu {
	String nama;
	double harga;
	String kategori; // makanan atau minuman

	public Menu(String nama, double harga, String kategori) {
		this.nama = nama;
		this.harga = harga;
		this.kategori = kategori;
	}
}

class Main {
    // data menu dengan tipe data array
    static Menu[] makanan = {
        new Menu("Nasi Goreng", 15000, "Makanan"),
        new Menu("Mie Ayam", 14000, "Makanan"),
        new Menu("Ayam Bakar", 20000, "Makanan"),
        new Menu("Sate Ayam", 22000, "Makanan"),
    }

    static Menu[] minuman = {
        new Menu("Es Teh", 6000, "Minuman"),
        new Menu("Es Jeruk", 8000, "Minuman"),
        new Menu("Kopi Hitam", 5000, "Minuman"),
        new Menu("Jus Alpukat", 12000, "Minuman"),
    }

    // method untuk menampilkan menu
    static void tampilkanMenu() {
        System.out.println("===== MENU RESTORAN =====");
        System.out.println(">> Makanan:");
        System.out.println("1. " + makanan[0].nama + " - Rp" + makanan[0].harga);
        System.out.println("2. " + makanan[1].nama + " - Rp" + makanan[1].harga);
        System.out.println("3. " + makanan[2].nama + " - Rp" + makanan[2].harga);
        System.out.println("4. " + makanan[3].nama + " - Rp" + makanan[3].harga);
        System.out.println();
        System.out.println(">> Minuman:");
        System.out.println("5. " + minuman[0].nama + " - Rp" + minuman[0].harga);
        System.out.println("6. " + minuman[1].nama + " - Rp" + minuman[1].harga);
        System.out.println("7. " + minuman[2].nama + " - Rp" + minuman[2].harga);
        System.out.println("8. " + minuman[3].nama + " - Rp" + minuman[3].harga);
        System.out.println("==========================");
    }

    // method utama
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // panggil method tampilkanMenu
        tampilkanMenu();
        System.out.println("Masukkan maksimal 4 menu yang ingin dipesan.");

        // input pesanan (tanpa pengulangan)
        System.out.println("Pesanan 1: ");
        String menu1 = input.nextLine();
        System.out.println("Jumlah: ");
        int jml1 = input.nextInt();
        input.nextLine();

        System.out.println("Pesanan 2: ");
        String menu2 = input.nextLine();
        System.out.println("Jumlah: ");
        int jml2 = input.nextInt();
        input.nextLine();

        System.out.println("Pesanan 3: ");
        String menu3 = input.nextLine();
        System.out.println("Jumlah: ");
        int jml3 = input.nextInt();
        input.nextLine();

        System.out.println("Pesanan 4: ");
        String menu4 = input.nextLine();
        System.out.println("Jumlah: ");
        int jml4 = input.nextInt();

        // menghitung total harga setiap pesanan
        double total = hitungTotal(menu1, jml1) + hitungTotal(menu2, jml2) + hitungTotal(menu3, jml3) + hitungTotal(menu4, jml4);
        double pajak = total * 0.10;
        double service = 20000;
        double totalBayar = total + pajak + service;
        double diskon = 0;
        boolean freeDrink = false;

        // pengkondisian struktur keputusan untuk diskon dan promo
        if (total > 100000) {
            diskon = totalBayar * 0.10;
            totalBayar -= diskon;
        } else if (total > 50000) {
            freeDrink = true;
        }

        // cetak struk
        System.out.println("\n===== STRUK PEMESANAN =====");
        cetakItem(menu1, jml1);
        cetakItem(menu2, jml2);
        cetakItem(menu3, jml3);
        cetakItem(menu4, jml4);

        System.out.println("-----------------------------");
        System.out.println("Subtotal: Rp" + total);
        System.out.println("Pajak (10%): Rp" + pajak);
        System.out.println("Biaya Pelayanan: Rp" + service);

        if (diskon > 0) {
            System.out.println("Diskon 10%: -Rp" + diskon);
        } else if (freeDrink) {
            System.out.println("Promo: Beli satu gratis satu minuman");
        }

        System.out.println("=============================");
        System.out.println("Total Bayar: Rp" + totalBayar);
        System.out.println("=============================");
    }

    // method untuk menghitung total per menu berdasarkan nama menu
    static double hitungTotal(String namaMenu, int jumlah) {
        double harga = 0;

        switch (namaMenu.toLowerCase()) {
            case "nasi goreng":
                harga = makanan[0].harga;
                break;
            case "mie ayam":
                harga = makanan[1].harga;
                break;
            case "ayam bakar":
                harga = makanan[2].harga;
                break;
            case "sate ayam":
                harga = makanan[3].harga;
                break;
            case "es teh":
                harga = minuman[0].harga;
                break;
            case "es jeruk":
                harga = minuman[1].harga;
                break;
            case "kopi hitam":
                harga = minuman[2].harga;
                break;
            case "jus alpukat":
                harga = minuman[3].harga;
                break;
            default:
                harga = 0;
                break;
        }

        return harga * jumlah;
    }

    // method untuk menampilkan item pesanan di struk
    static void cetakItem(String namaMenu, int jumlah) {
        double total = hitungTotal(namaMenu, jumlah);
        
        if (total > 0) {
            System.out.println(namaMenu + " x" + jumlah + " = Rp" + total);
        }
    }
}