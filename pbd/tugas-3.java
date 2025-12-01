import java.io.*;
import java.util.*;

// 1. abstract class
abstract class MenuItem {
    // encapsulation atribut private
    private String nama;
    private double harga;
    private String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // getter dan setter
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public String getKategori() { return kategori; }

    // metode abstrak polymorphism
    public abstract void tampilMenu();
}

// 2. subclass makanan
class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() { return jenisMakanan; }

    @Override
    public void tampilMenu() {
        System.out.println("Makanan: " + getNama() + " | Harga: Rp" + getHarga() + " | Jenis: " + jenisMakanan);
    }
}

// 2. subclass minuman
class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() { return jenisMinuman; }

    @Override
    public void tampilMenu() {
        System.out.println("Minuman: " + getNama() + " | Harga: Rp" + getHarga() + " | Jenis: " + jenisMinuman);
    }
}

// 2. subclass diskon
class Diskon extends MenuItem {
    private double besarDiskon;

    public Diskon(String nama, double besarDiskon) {
        // harga diset 0 karena ini adalah item pengurang harga
        super(nama, 0, "Diskon");
        this.besarDiskon = besarDiskon;
    }

    public double getBesarDiskon() { return besarDiskon; }

    @Override
    public void tampilMenu() {
        System.out.println("Promo: " + getNama() + " | Potongan: Rp" + besarDiskon);
    }
}

class Menu {
    private ArrayList<MenuItem> daftarMenu;

    public Menu() {
        daftarMenu = new ArrayList<>();
    }

    public void tambahItem(MenuItem item) {
        daftarMenu.add(item);
    }

    public ArrayList<MenuItem> getDaftarMenu() {
        return daftarMenu;
    }

    public void tampilkanSemuaMenu() {
        System.out.println("\n--- DAFTAR MENU RESTORAN ---");
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu belum tersedia.");
        } else {
            for (int i = 0; i < daftarMenu.size(); i++) {
                System.out.print((i + 1) + ". ");
                daftarMenu.get(i).tampilMenu();
            }
        }
    }

    // file I/O menyimpan menu ke file teks
    public void simpanMenuKeFile(String namaFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            for (MenuItem item : daftarMenu) {
                // formatnya yaitu Tipe;Nama;Harga;AtributKhusus
                if (item instanceof Makanan) {
                    writer.write("Makanan;" + item.getNama() + ";" + item.getHarga() + ";" + ((Makanan) item).getJenisMakanan());
                } else if (item instanceof Minuman) {
                    writer.write("Minuman;" + item.getNama() + ";" + item.getHarga() + ";" + ((Minuman) item).getJenisMinuman());
                } else if (item instanceof Diskon) {
                    writer.write("Diskon;" + item.getNama() + ";" + ((Diskon) item).getBesarDiskon());
                }
                writer.newLine();
            }
            System.out.println("Menu berhasil disimpan ke file.");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan menu: " + e.getMessage());
        }
    }

    // file I/O memuat menu dari file teks
    public void muatMenuDariFile(String namaFile) {
        File file = new File(namaFile);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            daftarMenu.clear(); // bersihkan menu lama
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals("Makanan")) {
                    daftarMenu.add(new Makanan(parts[1], Double.parseDouble(parts[2]), parts[3]));
                } else if (parts[0].equals("Minuman")) {
                    daftarMenu.add(new Minuman(parts[1], Double.parseDouble(parts[2]), parts[3]));
                } else if (parts[0].equals("Diskon")) {
                    daftarMenu.add(new Diskon(parts[1], Double.parseDouble(parts[2])));
                }
            }
            System.out.println("Menu berhasil dimuat dari file.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Terjadi kesalahan saat memuat menu: " + e.getMessage());
        }
    }
}

class Pesanan {
    private ArrayList<MenuItem> daftarPesanan;

    public Pesanan() {
        daftarPesanan = new ArrayList<>();
    }

    public void tambahPesanan(MenuItem item) {
        daftarPesanan.add(item);
        System.out.println(item.getNama() + " ditambahkan ke pesanan.");
    }

    public double hitungTotal() {
        double total = 0;
        for (MenuItem item : daftarPesanan) {
            if (item instanceof Diskon) {
                total -= ((Diskon) item).getBesarDiskon(); // kurangi total jika item adalah diskon
            } else {
                total += item.getHarga();
            }
        }
        return (total < 0) ? 0 : total; // mencegah total negatif
    }

    public void cetakStruk() {
        System.out.println("\n--- STRUK PESANAN ---");
        for (MenuItem item : daftarPesanan) {
            if (item instanceof Diskon) {
                System.out.printf("%-20s : -Rp%.2f\n", item.getNama(), ((Diskon)item).getBesarDiskon());
            } else {
                System.out.printf("%-20s :  Rp%.2f\n", item.getNama(), item.getHarga());
            }
        }
        System.out.println("------------------------------");
        System.out.printf("TOTAL BAYAR          :  Rp%.2f\n", hitungTotal());
    }

    // file I/O simpan struk ke file
    public void simpanStrukKeFile(String namaFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile, true))) { // true = append mode
            writer.write("\n--- STRUK BARU ---\n");
            for (MenuItem item : daftarPesanan) {
                if (item instanceof Diskon) {
                    writer.write("DISKON " + item.getNama() + " : -Rp" + ((Diskon)item).getBesarDiskon() + "\n");
                } else {
                    writer.write(item.getNama() + " : Rp" + item.getHarga() + "\n");
                }
            }
            writer.write("TOTAL: Rp" + hitungTotal() + "\n");
            writer.write("------------------\n");
            System.out.println("Struk berhasil disimpan ke " + namaFile);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan struk: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menuRestoran = new Menu();
        Pesanan pesananSaatIni = new Pesanan();
        
        // memuat menu dari file saat program mulai
        menuRestoran.muatMenuDariFile("menu.txt");

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n=== SISTEM MANAJEMEN RESTORAN ===");
            System.out.println("1. Tambah Item Menu Baru");
            System.out.println("2. Tampilkan Menu");
            System.out.println("3. Buat Pesanan");
            System.out.println("4. Hitung & Tampilkan Struk");
            System.out.println("5. Simpan Menu & Keluar");
            System.out.print("Pilih menu: ");

            try {
                int pilihan = Integer.parseInt(scanner.nextLine());

                switch (pilihan) {
                    case 1: // tambah Item
                        System.out.println("Pilih tipe: 1. Makanan, 2. Minuman, 3. Diskon");
                        int tipe = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nama: ");
                        String nama = scanner.nextLine();
                        
                        if (tipe == 1) {
                            System.out.print("Harga: ");
                            double harga = Double.parseDouble(scanner.nextLine());
                            System.out.print("Jenis (e.g., Gorengan/Kuah): ");
                            String jenis = scanner.nextLine();
                            menuRestoran.tambahItem(new Makanan(nama, harga, jenis));
                        } else if (tipe == 2) {
                            System.out.print("Harga: ");
                            double harga = Double.parseDouble(scanner.nextLine());
                            System.out.print("Jenis (e.g., Dingin/Panas): ");
                            String jenis = scanner.nextLine();
                            menuRestoran.tambahItem(new Minuman(nama, harga, jenis));
                        } else if (tipe == 3) {
                            System.out.print("Besar Potongan Harga: ");
                            double disc = Double.parseDouble(scanner.nextLine());
                            menuRestoran.tambahItem(new Diskon(nama, disc));
                        }
                        break;

                    case 2: // tampilkan Menu
                        menuRestoran.tampilkanSemuaMenu();
                        break;

                    case 3: // pesan
                        menuRestoran.tampilkanSemuaMenu();
                        System.out.print("Masukkan nomor menu yang ingin dipesan: ");
                        int index = Integer.parseInt(scanner.nextLine()) - 1;
                        
                        // exception handling untuk index array
                        try {
                            MenuItem itemDipesan = menuRestoran.getDaftarMenu().get(index);
                            pesananSaatIni.tambahPesanan(itemDipesan);
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Error: Nomor menu tidak valid!");
                        }
                        break;

                    case 4: // struk
                        pesananSaatIni.cetakStruk();
                        pesananSaatIni.simpanStrukKeFile("struk.txt");
                        break;

                    case 5: // keluar
                        menuRestoran.simpanMenuKeFile("menu.txt");
                        isRunning = false;
                        System.out.println("Terima kasih. Data menu telah disimpan.");
                        break;

                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Masukkan angka yang valid!");
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }
        scanner.close();
    }
}