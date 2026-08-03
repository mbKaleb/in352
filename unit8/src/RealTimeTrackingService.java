import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RealTimeTrackingService {
    
    private String FEED;

    public RealTimeTrackingService() {
    }

    public RealTimeTrackingService(String feed) {
        this.FEED = feed;
    }

    private static final double DEST_LAT = 40.76810;
    private static final double DEST_LON = -73.98190;
    private static final double GEOFENCE_KM = 0.10;

    private final GpsDistanceService gps = new GpsDistanceService();

    private double prevLat = 0.0;
    private double prevLon = 0.0;
    private Instant prevTime = null;
    private boolean hasPrev = false;

    private double predLat = 0.0;
    private double predLon = 0.0;
    private boolean hasPred = false;

    private double totalKm = 0.0;
    private int received = 0;
    private int accepted = 0;
    private int rejected = 0;

    private Instant firstTime = null;
    private Instant lastTime = null;

    private final List<Double> speeds = new ArrayList<Double>();

    public void runFeed(String feed) {
        this.FEED = feed;
        runFeed();
    }

    public void runFeed() {
        List<String> messages = splitObjects(this.FEED);

        System.out.println("SIMULATED STREAMING ENDPOINT: POST /api/v1/track");
        System.out.println("Route: Times Square -> Central Park (Columbus Circle)");
        System.out.println();

        for (int i = 0; i < messages.size(); i++) {

            String msg = messages.get(i);
            received++;

            String device = getString(msg, "deviceId");
            String stamp = getString(msg, "timestamp");
            double lat = getDouble(msg, "lat");
            double lon = getDouble(msg, "lon");

            String error = validate(device, stamp, lat, lon);

            if (error != null) {
                rejected++;
                System.out.printf("[--:--:--] REJECT  %9.5f, %9.5f   400 Bad Request - %s%n",
                        lat, lon, error);
                sleep();
                continue;
            }

            Instant time = Instant.parse(stamp);
            String clock = stamp.substring(11, 19);

            double leg = 0.0;
            double speed = 0.0;

            if (hasPrev) {
                leg = gps.getDistance(prevLat, prevLon, lat, lon);
                totalKm = totalKm + leg;
                long seconds = Duration.between(prevTime, time).getSeconds();
                if (seconds > 0) {
                    speed = leg / (seconds / 3600.0);
                }
            }

            accepted++;
            if (firstTime == null) {
                firstTime = time;
            }
            lastTime = time;

            if (hasPrev) {
                System.out.printf("[%s] ACCEPT  %9.5f, %9.5f   leg %6.3f km   total %6.3f km   speed %5.2f km/h%n",
                        clock, lat, lon, leg, totalKm, speed);
            } else {
                System.out.printf("[%s] ACCEPT  %9.5f, %9.5f   leg    ---     total %6.3f km   speed   --- %n",
                        clock, lat, lon, totalKm);
            }

            if (hasPred) {
                double errKm = gps.getDistance(predLat, predLon, lat, lon);
                System.out.printf("           [AI] prediction error %.1f m%n", errKm * 1000.0);
            }

            if (speed > 0) {
                double avg = rollingAverage();
                if (avg > 0 && speed < avg * 0.6) {
                    System.out.printf("           [AI] speed anomaly - %.2f km/h vs %.2f km/h average, possible congestion%n",
                            speed, avg);
                }
                speeds.add(new Double(speed));
            }

            if (hasPrev) {
                predLat = lat + (lat - prevLat);
                predLon = lon + (lon - prevLon);
                hasPred = true;
                System.out.printf("           [AI] predicted next position %9.5f, %9.5f%n", predLat, predLon);
            }

            double remaining = gps.getDistance(lat, lon, DEST_LAT, DEST_LON);
            if (remaining < GEOFENCE_KM) {
                System.out.println("           [AI] geofence entered - destination reached");
                hasPred = false;
            }

            prevLat = lat;
            prevLon = lon;
            prevTime = time;
            hasPrev = true;

            sleep();
        }

        printSummary();
    }

    private String validate(String device, String stamp, double lat, double lon) {
        if (device == null) {
            return "deviceId missing";
        }
        if (stamp == null) {
            return "timestamp missing";
        }
        if (Double.isNaN(lat) || Double.isNaN(lon)) {
            return "lat/lon missing or non-numeric";
        }
        if (lat < -90.0 || lat > 90.0) {
            return "latitude out of range (-90..90): " + lat;
        }
        if (lon < -180.0 || lon > 180.0) {
            return "longitude out of range (-180..180): " + lon;
        }
        return null;
    }

    private double rollingAverage() {
        if (speeds.isEmpty()) {
            return 0.0;
        }
        int count = 0;
        double sum = 0.0;
        for (int i = speeds.size() - 1; i >= 0 && count < 3; i--) {
            sum = sum + speeds.get(i).doubleValue();
            count++;
        }
        return sum / count;
    }

    private void printSummary() {
        System.out.println();
        System.out.println("SESSION SUMMARY");
        System.out.println("  Messages received : " + received);
        System.out.println("  Accepted          : " + accepted);
        System.out.println("  Rejected          : " + rejected);
        System.out.printf("  Total distance    : %.3f km%n", totalKm);

        long seconds = 0;
        if (firstTime != null && lastTime != null) {
            seconds = Duration.between(firstTime, lastTime).getSeconds();
        }
        System.out.printf("  Session duration  : %d min %02d s%n", seconds / 60, seconds % 60);

        if (seconds > 0) {
            System.out.printf("  Average speed     : %.2f km/h%n", totalKm / (seconds / 3600.0));
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static List<String> splitObjects(String json) {
        List<String> out = new ArrayList<String>();
        int depth = 0;
        int start = -1;
        for (int i = 0; i < json.length(); i++) {
            char ch = json.charAt(i);
            if (ch == '{') {
                if (depth == 0) {
                    start = i;
                }
                depth++;
            } else if (ch == '}') {
                depth--;
                if (depth == 0 && start >= 0) {
                    out.add(json.substring(start, i + 1));
                    start = -1;
                }
            }
        }
        return out;
    }

    private static String getString(String json, String key) {
        int i = json.indexOf("\"" + key + "\"");
        if (i < 0) {
            return null;
        }
        int colon = json.indexOf(":", i);
        if (colon < 0) {
            return null;
        }
        int s = json.indexOf("\"", colon);
        if (s < 0) {
            return null;
        }
        int e = json.indexOf("\"", s + 1);
        if (e < 0) {
            return null;
        }
        return json.substring(s + 1, e);
    }

    private static double getDouble(String json, String key) {
        int i = json.indexOf("\"" + key + "\"");
        if (i < 0) {
            return Double.NaN;
        }
        int colon = json.indexOf(":", i);
        if (colon < 0) {
            return Double.NaN;
        }
        int end = colon + 1;
        while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}') {
            end++;
        }
        try {
            return Double.parseDouble(json.substring(colon + 1, end).trim());
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public static void main(String[] args) {
        String testFeed = "[" +
            "{\"deviceId\":\"truck001\", \"timestamp\":\"2024-01-15T10:30:00Z\", \"lat\":40.75810, \"lon\":-73.98190}," +
            "{\"deviceId\":\"truck001\", \"timestamp\":\"2024-01-15T10:31:00Z\", \"lat\":40.76000, \"lon\":-73.97900}," +
            "{\"deviceId\":\"truck001\", \"timestamp\":\"2024-01-15T10:32:00Z\", \"lat\":40.76500, \"lon\":-73.97500}," +
            "{\"deviceId\":\"truck001\", \"timestamp\":\"2024-01-15T10:33:00Z\", \"lat\":40.77000, \"lon\":-73.97200}" +
            "]";
        RealTimeTrackingService service = new RealTimeTrackingService(testFeed);
        service.runFeed();
    }
}