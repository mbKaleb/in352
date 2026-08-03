import java.util.ArrayList;
import java.util.List;

public class MiniJson {

    public static List<String> splitObjects(String json) {
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

    public static String getString(String json, String key) {
        int colon = findColon(json, key);
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

    public static double getDouble(String json, String key) {
        int colon = findColon(json, key);
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

    public static boolean has(String json, String key) {
        return json.indexOf("\"" + key + "\"") >= 0;
    }

    public static int count(String json) {
        return splitObjects(json).size();
    }

    private static int findColon(String json, String key) {
        int i = json.indexOf("\"" + key + "\"");
        if (i < 0) {
            return -1;
        }
        return json.indexOf(":", i);
    }
}