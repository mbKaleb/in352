import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Coordinate newYork = new Coordinate(40.7128, -74.0060);
    static final String[] FEATURE_NAMES = {"timeOfDay"};

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("=== IN352 Unit 9 - GPS + AI Assignment ===");
        System.out.println("1) Part 1: GPS Reliability Analysis");
        System.out.println("2) Part 2: AI Error Mitigation (train + test)");
        System.out.println("3) Interactive Coordinate Corrector (console)");
        System.out.print("Select a part to run (1-3): ");

        String choice = in.hasNextLine() ? in.nextLine().trim() : "";
        if (choice.equals("1")) {
            runPart1();
        } else if (choice.equals("2")) {
            runPart2();
        } else if (choice.equals("3")) {
            runInteractiveCorrector(in);
        } else {
            System.out.println("Unrecognized selection: '" + choice + "'. Exiting.");
        }
    }

    static void runPart1() {
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
    }

    static void runPart2() {
        List<DataRow> dataset = buildDataset();
        Models m = trainModels(dataset);

        System.out.println("Baseline: average correction (no features).");
        System.out.printf("  latitude correction:  %+.8f%n", m.avgLatCorr);
        System.out.printf("  longitude correction: %+.8f%n", m.avgLngCorr);

        System.out.println("\nModel trained: linear regression fit on " + String.join(", ", FEATURE_NAMES) + ".");
        printModel("latitude", m.latModel);
        printModel("longitude", m.lngModel);

        Coordinate newNoisy = new Coordinate(40.7126, -74.0063);
        double[] newFeatures = {10};
        Coordinate corrected = correct(newNoisy, newFeatures, m.latModel, m.lngModel);
        System.out.println("\nNew noisy input (timeOfDay=10):");
        System.out.println(newNoisy.display());
        System.out.println("Corrected output:");
        System.out.println(corrected.display());
    }

    static void runInteractiveCorrector(Scanner in) {
        Models m = trainModels(buildDataset());
        runConsoleForm(in, m.latModel, m.lngModel);
    }

    static void runConsoleForm(Scanner in, LinearRegression latModel, LinearRegression lngModel) {
        System.out.println("\n--- Coordinate Corrector ---");
        System.out.println("Enter a latitude, longitude, and time of day, or 'q' to quit.");

        while (true) {
            System.out.print("\nLatitude: ");
            if (!in.hasNextLine()) break;
            String latText = in.nextLine().trim();
            if (latText.equalsIgnoreCase("q")) break;

            System.out.print("Longitude: ");
            if (!in.hasNextLine()) break;
            String lngText = in.nextLine().trim();
            if (lngText.equalsIgnoreCase("q")) break;

            System.out.print("Time of day: ");
            if (!in.hasNextLine()) break;
            String timeText = in.nextLine().trim();
            if (timeText.equalsIgnoreCase("q")) break;

            try {
                Coordinate input = new Coordinate(Double.parseDouble(latText),
                                                  Double.parseDouble(lngText));
                double[] features = {
                        Double.parseDouble(timeText)
                };
                Coordinate fixed = correct(input, features, latModel, lngModel);
                System.out.println("Original  " + input.display());
                System.out.println("Corrected " + fixed.display());
                System.out.printf("Distance from New York: %.2f m%n",
                        haversineMeters(newYork, fixed));
            } catch (NumberFormatException e) {
                System.out.println("Please enter numbers only.");
            }
        }
        System.out.println("Goodbye.");
    }

    static List<DataRow> buildDataset() {
        List<DataRow> dataset = new ArrayList<>();
        dataset.add(new DataRow(newYork, new Coordinate(40.7126, -74.0063), 8));
        dataset.add(new DataRow(newYork, new Coordinate(40.7129, -74.0058), 12));
        dataset.add(new DataRow(newYork, new Coordinate(40.7124, -74.0065), 18));
        dataset.add(new DataRow(newYork, new Coordinate(40.7130, -74.0061), 6));
        dataset.add(new DataRow(newYork, new Coordinate(40.7127, -74.0062), 22));
        dataset.add(new DataRow(newYork, new Coordinate(40.7125, -74.0059), 14));
        dataset.add(new DataRow(newYork, new Coordinate(40.7131, -74.0060), 2));
        dataset.add(new DataRow(newYork, new Coordinate(40.7123, -74.0064), 16));
        dataset.add(new DataRow(newYork, new Coordinate(40.7128, -74.0057), 10));
        dataset.add(new DataRow(newYork, new Coordinate(40.7126, -74.0061), 20));
        return dataset;
    }

    static Models trainModels(List<DataRow> dataset) {
        int n = dataset.size();
        double[][] X = new double[n][FEATURE_NAMES.length];
        double[] latOffset = new double[n];
        double[] lngOffset = new double[n];
        for (int i = 0; i < n; i++) {
            DataRow r = dataset.get(i);
            X[i] = r.toFeatureArray();
            latOffset[i] = r.trueCoord.getLatitude() - r.noisyCoord.getLatitude();
            lngOffset[i] = r.trueCoord.getLongitude() - r.noisyCoord.getLongitude();
        }

        Models m = new Models();
        m.avgLatCorr = mean(latOffset);
        m.avgLngCorr = mean(lngOffset);
        m.latModel = LinearRegression.fit(X, latOffset);
        m.lngModel = LinearRegression.fit(X, lngOffset);
        return m;
    }

    static void printModel(String label, LinearRegression model) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%+.8f", model.coefficients[0]));
        for (int i = 0; i < FEATURE_NAMES.length; i++) {
            sb.append(String.format(" %+.8f*%s", model.coefficients[i + 1], FEATURE_NAMES[i]));
        }
        System.out.println("  " + label + " offset ~= " + sb);
    }

    static Coordinate correct(Coordinate c, double[] features, LinearRegression latModel, LinearRegression lngModel) {
        double latCorr = latModel.predict(features);
        double lngCorr = lngModel.predict(features);
        return new Coordinate(c.getLatitude() + latCorr, c.getLongitude() + lngCorr);
    }

    static double mean(double[] values) {
        double sum = 0;
        for (double v : values) sum += v;
        return sum / values.length;
    }

    static double haversineMeters(Coordinate a, Coordinate b) {
        final double R = 6371000;
        double lat1 = Math.toRadians(a.getLatitude());
        double lat2 = Math.toRadians(b.getLatitude());
        double dLat = lat2 - lat1;
        double dLon = Math.toRadians(b.getLongitude() - a.getLongitude());
        double hav = Math.sin(dLat/2)*Math.sin(dLat/2) + Math.cos(lat1)*Math.cos(lat2)*Math.sin(dLon/2)*Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(hav), Math.sqrt(1-hav));
        return R * c;
    }

    static class DataRow {
        Coordinate trueCoord, noisyCoord;
        int timeOfDay;

        DataRow(Coordinate trueCoord, Coordinate noisyCoord, int timeOfDay) {
            this.trueCoord = trueCoord;
            this.noisyCoord = noisyCoord;
            this.timeOfDay = timeOfDay;
        }

        double[] toFeatureArray() {
            return new double[] {timeOfDay};
        }
    }

    static class Models {
        double avgLatCorr, avgLngCorr;
        LinearRegression latModel, lngModel;
    }
}
