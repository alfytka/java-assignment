import java.util.Arrays;

public class CountingSortDescending {
    void sort(int[] arr) {
        int n = arr.length;

        // menemukan elemen maksimum dalam array
        int max = arr[0];
        for (int i = 0; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // menyimpan frekuensi jumlah dari setiap elemen
        int[] count = new int[max + 1];

        // simpan hasil hitungan setiap elemen
        for (int i = 0; i < n; i++) {
            count[arr[i]]++;
        }

        int arrIndex = 0; // index untuk array 'arr'

        // iterasi dari max turun ke 0
        for (int i = max; i >= 0; i--) {
            while (count[i] > 0) {
                arr[arrIndex] = i;  // masukkan nilai i ke array
                arrIndex++;         // pindahkan indeks array
                count[i]--;         // kurangi frekuensinya
            }
        }
    }

    static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        int[] data = { 5, 1, 8, 3, 5, 2, 8, 1 };

        System.out.println("Data sebelum diurutkan:");
        printArray(data);

        CountingSortDescending sorter = new CountingSortDescending();
        sorter.sort(data);

        System.out.println("\nData setelah diurutkan (terbesar ke terkecil):");
        printArray(data);
    }
}
