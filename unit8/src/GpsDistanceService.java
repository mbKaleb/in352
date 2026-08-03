public class GpsDistanceService {

    private static final double EARTH_RADIUS_KM = 6371.0088;

    public double getDistance(double lat1, double lon1, double lat2, double lon2) {

        if (lat1 < -90 || lat1 > 90 || lat2 < -90 || lat2 > 90) {
            throw new IllegalArgumentException("Invalid latitude");
        }
        if (lon1 < -180 || lon1 > 180 || lon2 < -180 || lon2 > 180) {
            throw new IllegalArgumentException("Invalid longitude");
        }

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double rLat1 = Math.toRadians(lat1);
        double rLat2 = Math.toRadians(lat2);

        double sinLat = Math.sin(dLat / 2.0);
        double sinLon = Math.sin(dLon / 2.0);

        double a = (sinLat * sinLat) + Math.cos(rLat1) * Math.cos(rLat2) * (sinLon * sinLon);
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));

        return EARTH_RADIUS_KM * c;
    }

    public static void main(String[] args) {

        GpsDistanceService service = new GpsDistanceService();

        System.out.println("SIMULATED REST ENDPOINT: GET /api/v1/distance");
        System.out.println();

        double d1 = service.getDistance(40.7128, -74.0060, 34.0522, -118.2437);
        System.out.println("Request: New York -> Los Angeles");
        System.out.printf("Response: %.2f km%n", d1);
        System.out.println();

        double d2 = service.getDistance(51.5074, -0.1278, 48.8566, 2.3522);
        System.out.println("Request: London -> Paris");
        System.out.printf("Response: %.2f km%n", d2);
        System.out.println();

        try {
            service.getDistance(99.0, -74.0060, 34.0522, -118.2437);
        } catch (IllegalArgumentException e) {
            System.out.println("Request: latitude 99.0");
            System.out.println("Response: 400 Bad Request - " + e.getMessage());
        }
    }
}