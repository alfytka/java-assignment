import java.util.Arrays;

public class MergeSortDescending {
    // fungsi utama untuk menjalankan merge sort.
    void sort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        // array sementara
        int[] L = new int[n1];
        int[] R = new int[n2];

        // salin data ke array sementara
        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
        }

        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] >= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        int[] data = { 38, 27, 43, 3, 9, 82, 10, 55};

        System.out.println("Data sebelum diurutkan:");
        printArray(data);
        MergeSortDescending sorter = new MergeSortDescending();
        sorter.sort(data, 0, data.length - 1);

        System.out.println("\n Data  setelah diurutkan (terbesar ke terkecil):");
        printArray(data);
    }
}
