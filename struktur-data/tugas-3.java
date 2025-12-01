// DFS (depth-first search)
import java.util.*;

public class DFSSearch {
    // list untuk menyimpan graf
    private Map<String, List<String>> adjVertices = new HashMap<>();

    // untuk menambahkan node dan hubungan
    public void addEdge(String label1, String label2) {
        adjVertices.computeIfAbsent(label1, k -> new ArrayList<>()).add(label2);
    }

    public void dfs(String root, String target) {
        Set<String> visited = new LinkedHashSet<>();
        Stack<String> stack = new Stack<>();

        stack.push(root);

        System.out.println("Mulai pencarian DFS untuk target: " + target);

        while (!stack.isEmpty()) {
            String vertex = stack.pop();

            if (!visited.contains(vertex)) {
                visited.add(vertex);
                System.out.print(vertex + " -> ");

                // jika target ditemukan
                if (vertex.equals(target)) {
                    System.out.println("\nBerhasil target " + target + " ditemukan!");
                    return;
                }

                // tambahkan tetangga ke stack
                // dibalik agar urutan kunjungan dari kiri ke kanan (opsional, tergantung implementasi)
                List<String> neighbors = adjVertices.get(vertex);
                if (neighbors != null) {
                    Collections.reverse(neighbors); 
                    for (String v : neighbors) {
                        stack.push(v);
                    }
                }
            }
        }
        System.out.println("\nTarget tidak ditemukan.");
    }

    public static void main(String[] args) {
        DFSSearch graph = new DFSSearch();
        
        // membangun graf sesuai skenario 8 node
        graph.addEdge("a1", "a2");
        graph.addEdge("a1", "a3");
        graph.addEdge("a2", "a4");
        graph.addEdge("a2", "a5"); // target disini
        graph.addEdge("a3", "a6");
        graph.addEdge("a3", "a7");
        graph.addEdge("a4", "a8");

        // jalankan DFS dan cari a5
        graph.dfs("a1", "a5");
    }
}

// BFS (breadth-first search)
import java.util.*;

public class BFSSearch {
    private Map<String, List<String>> adjVertices = new HashMap<>();

    public void addEdge(String label1, String label2) {
        adjVertices.computeIfAbsent(label1, k -> new ArrayList<>()).add(label2);
    }

    public void bfs(String root, String target) {
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(root);
        queue.add(root);

        System.out.println("Mulai pencarian BFS untuk target: " + target);

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            System.out.print(vertex + " -> ");

            if (vertex.equals(target)) {
                System.out.println("\nBerhasil target " + target + " ditemukan!");
                return;
            }

            List<String> neighbors = adjVertices.get(vertex);
            if (neighbors != null) {
                for (String v : neighbors) {
                    if (!visited.contains(v)) {
                        visited.add(v);
                        queue.add(v);
                    }
                }
            }
        }
        System.out.println("\nTarget tidak ditemukan.");
    }

    public static void main(String[] args) {
        BFSSearch graph = new BFSSearch();
        
        // graf yang sama
        graph.addEdge("a1", "a2");
        graph.addEdge("a1", "a3");
        graph.addEdge("a2", "a4");
        graph.addEdge("a2", "a5");
        graph.addEdge("a3", "a6");
        graph.addEdge("a3", "a7");
        graph.addEdge("a4", "a8");

        // jalankan BFS dan cari a5
        graph.bfs("a1", "a5");
    }
}