import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class RouteOptimizerService {

    private final Map<String, String> names = new LinkedHashMap<String, String>();
    private final Map<String, double[]> coords = new LinkedHashMap<String, double[]>();
    private final Map<String, List<String>> adj = new LinkedHashMap<String, List<String>>();

    private final GpsDistanceService gps = new GpsDistanceService();

    private double greedyKm = 0.0;
    private double optimalKm = 0.0;

    public RouteOptimizerService() {
        addNode("A", "New York", 40.7128, -74.0060);
        addNode("B", "Philadelphia", 39.9526, -75.1652);
        addNode("C", "Washington DC", 38.9072, -79.9959);
        addNode("D", "Pittsburgh", 40.4406, -79.9959);
        addNode("E", "Boston", 42.3601, -71.0589);

        addEdge("A", "B");
        addEdge("B", "C");
        addEdge("A", "E");
        addEdge("B", "D");
        addEdge("C", "D");
        addEdge("D", "E");
    }

    private void addNode(String id, String name, double lat, double lon) {
        names.put(id, name);
        coords.put(id, new double[] { lat, lon });
        adj.put(id, new ArrayList<String>());
    }

    private void addEdge(String from, String to) {
        adj.get(from).add(to);
    }

    public double weight(String u, String v) {
        double[] a = coords.get(u);
        double[] b = coords.get(v);
        return gps.getDistance(a[0], a[1], b[0], b[1]);
    }

    public void printGraph() {
        System.out.println("NODES");
        for (String id : coords.keySet()) {
            double[] c = coords.get(id);
            System.out.printf("  %s  %-14s  %9.4f, %9.4f%n", id, names.get(id), c[0], c[1]);
        }

        System.out.println();
        System.out.println("EDGES (weights from Haversine)");
        for (String u : adj.keySet()) {
            for (String v : adj.get(u)) {
                System.out.printf("  %s -> %s   %8.2f km%n", u, v, weight(u, v));
            }
        }

        System.out.println();
        System.out.println("ADJACENCY MATRIX (km)");
        System.out.print("      ");
        for (String v : coords.keySet()) {
            System.out.printf("%10s", v);
        }
        System.out.println();
        for (String u : coords.keySet()) {
            System.out.printf("  %-4s", u);
            for (String v : coords.keySet()) {
                if (adj.get(u).contains(v)) {
                    System.out.printf("%10.2f", weight(u, v));
                } else {
                    System.out.printf("%10s", "-");
                }
            }
            System.out.println();
        }
    }

    public List<String> greedyRoute(String start, String goal) {
        List<String> path = new ArrayList<String>();
        Set<String> visited = new HashSet<String>();
        double total = 0.0;
        String current = start;

        path.add(current);
        visited.add(current);

        while (!current.equals(goal)) {
            String best = null;
            double bestDist = Double.MAX_VALUE;

            System.out.print("  [AI] at " + current + " candidates:");
            for (String n : adj.get(current)) {
                double d = weight(current, n);
                System.out.printf(" %s(%.2f)", n, d);
                if (visited.contains(n)) {
                    System.out.print("[visited]");
                }
                if (!visited.contains(n) && d < bestDist) {
                    bestDist = d;
                    best = n;
                }
            }
            if (adj.get(current).isEmpty()) {
                System.out.print(" none");
            }
            System.out.println();

            if (best == null) {
                System.out.println("  [AI] dead end at " + current + " - backtracking");
                path.remove(path.size() - 1);
                if (path.isEmpty()) {
                    greedyKm = 0.0;
                    return path;
                }
                String prev = path.get(path.size() - 1);
                total = total - weight(prev, current);
                current = prev;
                continue;
            }

            System.out.printf("  [AI] choice: %s -> %s (%.2f km)%n", current, best, bestDist);
            total = total + bestDist;
            current = best;
            path.add(current);
            visited.add(current);
        }

        greedyKm = total;
        return path;
    }

    public List<String> dijkstra(String start, String goal) {
        final Map<String, Double> dist = new HashMap<String, Double>();
        Map<String, String> prev = new HashMap<String, String>();

        for (String v : coords.keySet()) {
            dist.put(v, Double.POSITIVE_INFINITY);
        }
        dist.put(start, 0.0);

        PriorityQueue<String> pq = new PriorityQueue<String>(5, new Comparator<String>() {
            public int compare(String a, String b) {
                return Double.compare(dist.get(a), dist.get(b));
            }
        });
        pq.add(start);

        Set<String> done = new HashSet<String>();

        while (!pq.isEmpty()) {
            String u = pq.poll();
            if (!done.add(u)) {
                continue;
            }
            for (String v : adj.get(u)) {
                double nd = dist.get(u) + weight(u, v);
                if (nd < dist.get(v)) {
                    dist.put(v, nd);
                    prev.put(v, u);
                    pq.add(v);
                }
            }
        }

        LinkedList<String> path = new LinkedList<String>();
        if (dist.get(goal) == Double.POSITIVE_INFINITY) {
            optimalKm = 0.0;
            return path;
        }
        String at = goal;
        while (at != null) {
            path.addFirst(at);
            at = prev.get(at);
        }
        optimalKm = dist.get(goal);
        return path;
    }

    public void handleOptimizeRoute(String requestJson) {
        System.out.println("INPUT JSON");
        System.out.println(requestJson);
        System.out.println();

        String requestId = getValue(requestJson, "requestId");
        String origin = getValue(requestJson, "origin");
        String destination = getValue(requestJson, "destination");

        System.out.println("BACKEND LOG");
        System.out.println("  INFO  POST /optimizeRoute received (" + requestId + ")");

        if (origin == null || destination == null
                || !coords.containsKey(origin) || !coords.containsKey(destination)) {
            System.out.println("  INFO  validation failed - unknown or missing node id");
            System.out.println();
            System.out.println("OUTPUT JSON");
            System.out.println("{\"status\":400,\"requestId\":\"" + requestId
                    + "\",\"error\":\"invalid origin or destination\"}");
            return;
        }

        System.out.println("  INFO  validated: origin=" + origin + " destination=" + destination);
        System.out.println("  INFO  graph loaded: 5 nodes, 6 edges");

        long t0 = System.nanoTime();
        List<String> greedy = greedyRoute(origin, destination);
        double ms = (System.nanoTime() - t0) / 1000000.0;

        List<String> optimal = dijkstra(origin, destination);

        double deviation = 0.0;
        if (optimalKm > 0) {
            deviation = ((greedyKm - optimalKm) / optimalKm) * 100.0;
        }

        System.out.printf("  INFO  greedy route: %s (%.2f km)%n", join(greedy), greedyKm);
        System.out.printf("  INFO  dijkstra optimum: %s (%.2f km)%n", join(optimal), optimalKm);
        System.out.printf("  WARN  deviation from optimum: %.2f%%%n", deviation);
        System.out.println("  INFO  response 200 returned");

        System.out.println();
        System.out.println("OUTPUT JSON");
        System.out.println(buildResponse(requestId, greedy, deviation, ms));
    }

    private String buildResponse(String requestId, List<String> path, double deviation, double ms) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":200,");
        sb.append("\"requestId\":\"").append(requestId).append("\",");
        sb.append("\"endpoint\":\"POST /optimizeRoute\",");
        sb.append("\"algorithm\":\"greedy\",");
        sb.append("\"route\":[");
        for (int i = 0; i < path.size(); i++) {
            String id = path.get(i);
            double[] c = coords.get(id);
            sb.append("{\"id\":\"").append(id).append("\",");
            sb.append("\"city\":\"").append(names.get(id)).append("\",");
            sb.append("\"lat\":").append(c[0]).append(",");
            sb.append("\"lon\":").append(c[1]).append("}");
            if (i < path.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("],");
        sb.append("\"hops\":").append(path.size() - 1).append(",");
        sb.append("\"totalDistanceKm\":").append(round(greedyKm)).append(",");
        sb.append("\"optimalDistanceKm\":").append(round(optimalKm)).append(",");
        sb.append("\"deviationPercent\":").append(round(deviation)).append(",");
        sb.append("\"computeTimeMs\":").append(round(ms));
        sb.append("}");
        return sb.toString();
    }

    private static String getValue(String json, String key) {
        int i = json.indexOf("\"" + key + "\"");
        if (i < 0) {
            return null;
        }
        int colon = json.indexOf(":", i);
        int start = json.indexOf("\"", colon) + 1;
        int end = json.indexOf("\"", start);
        if (start <= 0 || end < 0) {
            return null;
        }
        return json.substring(start, end);
    }

    private static String join(List<String> path) {
        if (path.isEmpty()) {
            return "no route";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
            if (i < path.size() - 1) {
                sb.append(" -> ");
            }
        }
        return sb.toString();
    }

    private static double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    public static void main(String[] args) {

        RouteOptimizerService service = new RouteOptimizerService();

        System.out.println("SIMULATED REST ENDPOINT: POST /optimizeRoute");
        System.out.println();

        service.printGraph();

        System.out.println();
        System.out.println("REQUEST 1: A to E");
        service.handleOptimizeRoute(
            "{\"requestId\":\"req-001\",\"origin\":\"A\",\"destination\":\"E\",\"unit\":\"km\"}");

        System.out.println();
        System.out.println("REQUEST 2: A to D");
        service.handleOptimizeRoute(
            "{\"requestId\":\"req-002\",\"origin\":\"A\",\"destination\":\"D\",\"unit\":\"km\"}");

        System.out.println();
        System.out.println("REQUEST 3: A to C");
        service.handleOptimizeRoute(
            "{\"requestId\":\"req-003\",\"origin\":\"A\",\"destination\":\"C\",\"unit\":\"km\"}");
    }
}