import java.util.Scanner;

public class Test {

    private static final String FEED =
        "["
        + "{\"deviceId\":\"dev-7742\",\"timestamp\":\"2025-01-15T14:00:00Z\",\"lat\":40.75800,\"lon\":-73.98550},"
        + "{\"deviceId\":\"dev-7742\",\"timestamp\":\"2025-01-15T14:02:00Z\",\"lat\":40.75944,\"lon\":-73.98499},"
        + "{\"deviceId\":\"dev-7742\",\"timestamp\":\"2025-01-15T14:04:00Z\",\"lat\":40.76089,\"lon\":-73.98447},"
        + "{\"deviceId\":\"dev-7742\",\"timestamp\":\"2025-01-15T14:06:00Z\",\"lat\":40.76233,\"lon\":-73.98396},"
        + "{\"deviceId\":\"dev-7742\",\"timestamp\":\"2025-01-15T14:07:00Z\",\"lat\":95.00000,\"lon\":-73.98370},"
        + "{\"deviceId\":\"dev-7742\",\"timestamp\":\"2025-01-15T14:08:00Z\",\"lat\":40.76377,\"lon\":-73.98344},"
        + "{\"deviceId\":\"dev-7742\",\"timestamp\":\"2025-01-15T14:10:00Z\",\"lat\":40.76521,\"lon\":-73.98293},"
        + "{\"deviceId\":\"dev-7742\",\"timestamp\":\"2025-01-15T14:14:00Z\",\"lat\":40.76666,\"lon\":-73.98241},"
        + "{\"deviceId\":\"dev-7742\",\"timestamp\":\"2025-01-15T14:16:00Z\",\"lat\":40.76810,\"lon\":-73.98190}"
        + "]";

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println();
            System.out.println("IN352 UNIT 8 - SERVICE TEST CLIENT");
            System.out.println("  1  GET  /api/v1/distance");
            System.out.println("  2  POST /optimizeRoute");
            System.out.println("  3  POST /api/v1/track");
            System.out.println("  0  exit");
            System.out.print("select> ");

            if (!in.hasNextLine()) {
                break;
            }

            String choice = in.nextLine().trim();

            if (choice.equals("1")) {
                testDistance();
            } else if (choice.equals("2")) {
                testOptimizeRoute();
            } else if (choice.equals("3")) {
                testTracking();
            } else if (choice.equals("0") || choice.equalsIgnoreCase("exit")) {
                System.out.println("client stopped.");
                break;
            } else {
                System.out.println("unknown option");
            }
        }

        in.close();
    }

    private static void testDistance() {

        GpsDistanceService gps = new GpsDistanceService();

        System.out.println();
        System.out.println("SIMULATED REST ENDPOINT: GET /api/v1/distance");
        System.out.println();

        System.out.println("Request: lat1=40.7128 lon1=-74.0060 lat2=34.0522 lon2=-118.2437");
        System.out.println("         New York -> Los Angeles");
        System.out.printf("Response: 200 OK  %.2f km%n", gps.getDistance(40.7128, -74.0060, 34.0522, -118.2437));
        System.out.println();

        System.out.println("Request: lat1=51.5074 lon1=-0.1278 lat2=48.8566 lon2=2.3522");
        System.out.println("         London -> Paris");
        System.out.printf("Response: 200 OK  %.2f km%n", gps.getDistance(51.5074, -0.1278, 48.8566, 2.3522));
        System.out.println();

        System.out.println("Request: lat1=99.0000 lon1=-74.0060 lat2=34.0522 lon2=-118.2437");
        System.out.println("         invalid latitude");
        try {
            gps.getDistance(99.0, -74.0060, 34.0522, -118.2437);
            System.out.println("Response: 200 OK");
        } catch (IllegalArgumentException e) {
            System.out.println("Response: 400 Bad Request - " + e.getMessage());
        }
    }

    private static void testOptimizeRoute() {

        RouteOptimizerService router = new RouteOptimizerService();

        System.out.println();
        System.out.println("SIMULATED REST ENDPOINT: POST /optimizeRoute");
        System.out.println();

        router.printGraph();

        System.out.println();
        System.out.println("Paste a JSON request body, or type back.");
        System.out.println("Example: {\"requestId\":\"req-001\",\"origin\":\"A\",\"destination\":\"E\"}");

        while (true) {
            System.out.println();
            System.out.print("request> ");

            if (!in.hasNextLine()) {
                return;
            }

            String line = in.nextLine().trim();

            if (line.equalsIgnoreCase("back") || line.equalsIgnoreCase("exit")) {
                return;
            }
            if (line.isEmpty()) {
                continue;
            }

            router.handleOptimizeRoute(line);
        }
    }

    private static void testTracking() {

        RealTimeTrackingService tracker = new RealTimeTrackingService();

        System.out.println();
        tracker.runFeed(FEED);
    }
}