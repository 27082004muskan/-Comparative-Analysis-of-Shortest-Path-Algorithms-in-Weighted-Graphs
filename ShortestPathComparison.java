import java.util.*;

public class ShortestPathComparison {

    static class Edge {
        int source, destination, weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {
        int vertices;
        List<Edge> edges = new ArrayList<>();

        public Graph(int vertices) {
            this.vertices = vertices;
        }

        public void addEdge(int source, int destination, int weight) {
            edges.add(new Edge(source, destination, weight));
        }

        public void dijkstra(int start) {
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
            int[] distances = new int[vertices];
            Arrays.fill(distances, Integer.MAX_VALUE);
            distances[start] = 0;

            pq.add(new int[]{start, 0});

            while (!pq.isEmpty()) {
                int[] current = pq.poll();
                int node = current[0];
                int distance = current[1];

                for (Edge edge : edges) {
                    if (edge.source == node) {
                        int newDist = distance + edge.weight;
                        if (newDist < distances[edge.destination]) {
                            distances[edge.destination] = newDist;
                            pq.add(new int[]{edge.destination, newDist});
                        }
                    }
                }
            }

            printResult("Dijkstra", start, distances);
        }

        public void bellmanFord(int start) {
            int[] distances = new int[vertices];
            Arrays.fill(distances, Integer.MAX_VALUE);
            distances[start] = 0;

            for (int i = 0; i < vertices - 1; i++) {
                for (Edge edge : edges) {
                    if (distances[edge.source] != Integer.MAX_VALUE &&
                            distances[edge.source] + edge.weight < distances[edge.destination]) {
                        distances[edge.destination] = distances[edge.source] + edge.weight;
                    }
                }
            }

            for (Edge edge : edges) {
                if (distances[edge.source] != Integer.MAX_VALUE &&
                        distances[edge.source] + edge.weight < distances[edge.destination]) {
                    System.out.println("Graph contains a negative weight cycle!");
                    return;
                }
            }

            printResult("Bellman-Ford", start, distances);
        }

        private void printResult(String algorithm, int start, int[] distances) {
            System.out.println("\n" + algorithm + " Algorithm starting from vertex " + start);
            for (int i = 0; i < vertices; i++) {
                System.out.println("Distance to vertex " + i + " is " + distances[i]);
            }
        }
    }

    public static void main(String[] args) {
        int vertices = 5;
        Graph graph = new Graph(vertices);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 1, 2);
        graph.addEdge(2, 3, 5);
        graph.addEdge(3, 4, 3);

        System.out.println("Running Dijkstra's Algorithm:");
        long startTime = System.nanoTime();
        graph.dijkstra(0);
        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) + " ns");

        System.out.println("\nRunning Bellman-Ford Algorithm:");
        startTime = System.nanoTime();
        graph.bellmanFord(0);
        endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) + " ns");
    }
}

