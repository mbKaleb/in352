import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // True New York coordinates
    static Coordinate newYork = new Coordinate(40.7128, -74.0060);

    public static void main(String[] args) {
        // Part 1: Simulate 10 noisy GPS readings and compute average error (meters)
        List<Coordinate> noisy = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            noisy.add(newYork.pingLocation());
        }

        System.out.println("True location (New York):");
        System.out.println(newYork.display());

        double sumError = 0;
        System.out.println("\nNoisy readings:");
        for (Coordinate c : noisy) {
            double err = haversineMeters(newYork, c);
            sumError += err;
            System.out.printf("  (%.6f, %.6f)  -> error: %.2f m%n",
                    c.getLatitude(), c.getLongitude(), err);
        }
        System.out.printf("Average error: %.2f m%n", sumError / noisy.size());

        // Part 2: Prepare fixed dataset and compute average corrections (simple regression)
        // Dataset entries: {noisyLat, noisyLng, timeOfDay}
        List<DataRow> dataset = new ArrayList<>();
        dataset.add(new DataRow(40.7126, -74.0063, 8));
        dataset.add(new DataRow(40.7129, -74.0058, 12));
        dataset.add(new DataRow(40.7124, -74.0065, 18));
        dataset.add(new DataRow(40.7130, -74.0061, 6));
        dataset.add(new DataRow(40.7127, -74.0062, 22));

        // Compute average correction: true - noisy
        double sumLatCorr = 0, sumLngCorr = 0;
        for (DataRow r : dataset) {
            sumLatCorr += (newYork.getLatitude() - r.noisyLat);
            sumLngCorr += (newYork.getLongitude() - r.noisyLng);
        }
        double avgLatCorr = sumLatCorr / dataset.size();
        double avgLngCorr = sumLngCorr / dataset.size();
        System.out.println("\nModel trained: average corrections computed.");
        System.out.printf("  latitude correction:  %+.6f%n", avgLatCorr);
        System.out.printf("  longitude correction: %+.6f%n", avgLngCorr);

        // Apply to new noisy input
        Coordinate newNoisy = new Coordinate(40.7126, -74.0063);
        Coordinate corrected = correct(newNoisy, avgLatCorr, avgLngCorr);
        System.out.println("\nNew noisy input:");
        System.out.println(newNoisy.display());
        System.out.println("Corrected output:");
        System.out.println(corrected.display());

        // Part 3: Console form -- let the user enter coordinates and correct them
        runConsoleForm(avgLatCorr, avgLngCorr);
    }

    // Reads latitude/longitude pairs from the console and prints the corrected values.
    static void runConsoleForm(double avgLatCorr, double avgLngCorr) {
        Scanner in = new Scanner(System.in);
        System.out.println("\n--- Coordinate Corrector ---");
        System.out.println("Enter a latitude and longitude, or 'q' to quit.");

        while (true) {
            System.out.print("\nLatitude: ");
            if (!in.hasNextLine()) break;
            String latText = in.nextLine().trim();
            if (latText.equalsIgnoreCase("q")) break;

            System.out.print("Longitude: ");
            if (!in.hasNextLine()) break;
            String lngText = in.nextLine().trim();
            if (lngText.equalsIgnoreCase("q")) break;

            try {
                Coordinate input = new Coordinate(Double.parseDouble(latText),
                                                  Double.parseDouble(lngText));
                Coordinate fixed = correct(input, avgLatCorr, avgLngCorr);
                System.out.println("Corrected " + fixed.display());
                System.out.printf("Distance from New York: %.2f m%n",
                        haversineMeters(newYork, fixed));
            } catch (NumberFormatException e) {
                System.out.println("Please enter numbers only, e.g. 40.7126 and -74.0063.");
            }
        }
        System.out.println("Goodbye.");
    }

    static Coordinate correct(Coordinate c, double latCorr, double lngCorr) {
        return new Coordinate(c.getLatitude() + latCorr, c.getLongitude() + lngCorr);
    }

    static double haversineMeters(Coordinate a, Coordinate b) {
        final double R = 6371000; // meters
        double lat1 = Math.toRadians(a.getLatitude());
        double lat2 = Math.toRadians(b.getLatitude());
        double dLat = lat2 - lat1;
        double dLon = Math.toRadians(b.getLongitude() - a.getLongitude());
        double hav = Math.sin(dLat/2)*Math.sin(dLat/2) + Math.cos(lat1)*Math.cos(lat2)*Math.sin(dLon/2)*Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(hav), Math.sqrt(1-hav));
        return R * c;
    }

    static class DataRow {
        double noisyLat, noisyLng; int timeOfDay;
        DataRow(double nLat, double nLng, int t) { noisyLat = nLat; noisyLng = nLng; timeOfDay = t; }
    }
}
