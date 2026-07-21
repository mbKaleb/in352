import java.util.HashMap;
import java.util.Map;

public class UserStore {

    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("alice", "Password1!");
        users.put("bob",   "Secure22@");
    }

    public static boolean validate(String username, String password) {
        return password.equals(users.get(username));
    }

    public static void addUser(String username, String password) {
        users.put(username, password);
    }
}
