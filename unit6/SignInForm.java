import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SignInForm extends JFrame {

    private final JTextField usernameField    = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JLabel usernameError        = new JLabel(" ");
    private final JLabel passwordError        = new JLabel(" ");
    private final JLabel messageLabel         = new JLabel(" ");

    public SignInForm() {
        super("Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2, 8, 8));

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel());
        add(usernameError);

        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel());
        add(passwordError);

        JButton submit = new JButton("Sign In");
        submit.addActionListener(e -> onSubmit());
        add(new JLabel());
        add(submit);

        JButton signUpBtn = new JButton("Create an account");
        signUpBtn.addActionListener(e -> {
            dispose();
            new SignUpForm(() -> SwingUtilities.invokeLater(() -> new SignInForm().setVisible(true))).setVisible(true);
        });
        add(new JLabel());
        add(signUpBtn);

        add(new JLabel());
        add(messageLabel);

        pack();
        setLocationRelativeTo(null);
    }

    private void onSubmit() {
        usernameError.setText(" ");
        passwordError.setText(" ");
        messageLabel.setText(" ");

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        boolean valid = true;
        if (username.isEmpty()) { usernameError.setText("Username is required."); valid = false; }
        if (password.isEmpty()) { passwordError.setText("Password is required."); valid = false; }

        if (valid) {
            try {
                String body = "username=" + URLEncoder.encode(username, StandardCharsets.UTF_8)
                        + "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8);

                @SuppressWarnings("deprecation")
                HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/login").openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                try (OutputStream out = conn.getOutputStream()) {
                    out.write(body.getBytes(StandardCharsets.UTF_8));
                }

                String response = new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                if (response.contains("\"success\":true")) {
                    JOptionPane.showMessageDialog(this, "Welcome back, " + username + "!", "Signed In", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    messageLabel.setText("Invalid username or password.");
                }
            } catch (Exception ex) {
                messageLabel.setText("Could not connect to server.");
            }
        }
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignInForm().setVisible(true));
    }
}
