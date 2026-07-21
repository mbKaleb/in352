import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class SignUpForm extends JFrame {

    // --- Input fields ---
    private final JTextField usernameField        = new JTextField(18);
    private final JPasswordField passwordField    = new JPasswordField(18);
    private final JPasswordField confirmField     = new JPasswordField(18);
    private final JTextField nameField            = new JTextField(18);
    private final JTextField ageField             = new JTextField(18);
    private final JTextField emailField           = new JTextField(18);
    private final JTextField phoneField           = new JTextField(18);
    private final JTextField dobField             = new JTextField(18);
    private final JTextField addressField         = new JTextField(18);
    private final JTextField zipField             = new JTextField(18);

    private final JComboBox<String> stateBox = new JComboBox<>(new String[]{
        "-- Select State --","AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA",
        "HI","ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS",
        "MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA",
        "RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"
    });
    private final JComboBox<String> colorBox = new JComboBox<>(new String[]{
        "-- Select Color --","Red","Blue","Green","Yellow","Orange","Purple","Pink","Black","White","Other"
    });
    private final JComboBox<String> genderBox = new JComboBox<>(new String[]{
        "-- Select Gender --","Male","Female","Non-binary","Prefer not to say"
    });
    private final JComboBox<String> programBox = new JComboBox<>(new String[]{
        "-- Select Program --","Computer Science","Information Technology","Cybersecurity","Data Science","Software Engineering"
    });
    private final JComboBox<String> contactBox = new JComboBox<>(new String[]{
        "-- Select --","Email","Phone","Mail","Text"
    });

    private final JCheckBox[] languageBoxes = {
        new JCheckBox("English"), new JCheckBox("Spanish"), new JCheckBox("French"),
        new JCheckBox("German"),  new JCheckBox("Mandarin"), new JCheckBox("Other")
    };
    private final JCheckBox[] skillBoxes = {
        new JCheckBox("Java"),  new JCheckBox("Python"), new JCheckBox("JavaScript"),
        new JCheckBox("SQL"),   new JCheckBox("AWS"),    new JCheckBox("CompTIA A+")
    };
    private final JCheckBox[] deviceBoxes = {
        new JCheckBox("Desktop"), new JCheckBox("Laptop"), new JCheckBox("Tablet"),
        new JCheckBox("Smartphone"), new JCheckBox("Smartwatch")
    };
    private final JCheckBox termsBox = new JCheckBox("I agree to the Terms and Conditions");

    // --- Error labels ---
    private final JLabel usernameError = new JLabel(" ");
    private final JLabel passwordError = new JLabel(" ");
    private final JLabel confirmError  = new JLabel(" ");
    private final JLabel nameError     = new JLabel(" ");
    private final JLabel ageError      = new JLabel(" ");
    private final JLabel emailError    = new JLabel(" ");
    private final JLabel phoneError    = new JLabel(" ");
    private final JLabel dobError      = new JLabel(" ");
    private final JLabel addressError  = new JLabel(" ");
    private final JLabel zipError      = new JLabel(" ");
    private final JLabel stateError    = new JLabel(" ");
    private final JLabel colorError    = new JLabel(" ");
    private final JLabel genderError   = new JLabel(" ");
    private final JLabel programError  = new JLabel(" ");
    private final JLabel contactError  = new JLabel(" ");
    private final JLabel languageError = new JLabel(" ");
    private final JLabel skillError    = new JLabel(" ");
    private final JLabel deviceError   = new JLabel(" ");
    private final JLabel termsError    = new JLabel(" ");

    private final Runnable onSuccess;

    public SignUpForm(Runnable onSuccess) {
        super("Sign Up");
        this.onSuccess = onSuccess;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 0, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int[] row = {0};

        addField(form, gbc, row, "Username:",                   usernameField, usernameError);
        addField(form, gbc, row, "Password:",                   passwordField, passwordError);
        addField(form, gbc, row, "Confirm Password:",           confirmField,  confirmError);
        addField(form, gbc, row, "Full Name:",                  nameField,     nameError);
        addField(form, gbc, row, "Age:",                        ageField,      ageError);
        addField(form, gbc, row, "Email:",                      emailField,    emailError);
        addField(form, gbc, row, "Phone (10 digits):",          phoneField,    phoneError);
        addField(form, gbc, row, "Date of Birth (YYYY-MM-DD):", dobField,      dobError);
        addField(form, gbc, row, "Address:",                    addressField,  addressError);
        addField(form, gbc, row, "ZIP Code:",                   zipField,      zipError);
        addCombo(form, gbc, row, "State:",                      stateBox,      stateError);
        addCombo(form, gbc, row, "Favorite Color:",             colorBox,      colorError);
        addCombo(form, gbc, row, "Gender:",                     genderBox,     genderError);
        addCombo(form, gbc, row, "Program Type:",               programBox,    programError);
        addCombo(form, gbc, row, "Preferred Contact Method:",   contactBox,    contactError);
        addCheckGroup(form, gbc, row, "Languages:",             languageBoxes, languageError);
        addCheckGroup(form, gbc, row, "Skills & Certifications:", skillBoxes,  skillError);
        addCheckGroup(form, gbc, row, "Devices:",               deviceBoxes,   deviceError);

        // Terms — spans both columns
        gbc.gridx = 0; gbc.gridy = row[0]; gbc.gridwidth = 2;
        form.add(termsBox, gbc); row[0]++;
        termsError.setForeground(Color.RED);
        gbc.gridy = row[0]++;
        form.add(termsError, gbc);
        gbc.gridwidth = 1;

        // Submit button — spans both columns
        JButton submit = new JButton("Sign Up");
        submit.addActionListener(e -> onSubmit());
        gbc.gridx = 0; gbc.gridy = row[0]++; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 5, 5, 5);
        form.add(submit, gbc);

        JScrollPane scroll = new JScrollPane(form);
        scroll.setPreferredSize(new Dimension(1000, 620));
        scroll.setMinimumSize(new Dimension(1000, 400));
        add(scroll);
        pack();
        setLocationRelativeTo(null);
    }

    private void addField(JPanel p, GridBagConstraints gbc, int[] row,
                          String label, JComponent field, JLabel error) {
        gbc.gridx = 0; gbc.gridy = row[0]; gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST;
        p.add(field, gbc); row[0]++;
        gbc.gridx = 1; gbc.gridy = row[0]; gbc.fill = GridBagConstraints.NONE;
        error.setForeground(Color.RED);
        p.add(error, gbc); row[0]++;
    }

    private void addCombo(JPanel p, GridBagConstraints gbc, int[] row,
                          String label, JComboBox<String> box, JLabel error) {
        gbc.gridx = 0; gbc.gridy = row[0]; gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        p.add(box, gbc); row[0]++;
        gbc.gridx = 1; gbc.gridy = row[0];
        error.setForeground(Color.RED);
        p.add(error, gbc); row[0]++;
    }

    private void addCheckGroup(JPanel p, GridBagConstraints gbc, int[] row,
                               String label, JCheckBox[] boxes, JLabel error) {
        JPanel group = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        group.setBorder(BorderFactory.createTitledBorder(label));
        for (JCheckBox cb : boxes) group.add(cb);
        gbc.gridx = 0; gbc.gridy = row[0]; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST;
        p.add(group, gbc); row[0]++;
        gbc.gridy = row[0]; gbc.fill = GridBagConstraints.NONE;
        error.setForeground(Color.RED);
        p.add(error, gbc); row[0]++;
        gbc.gridwidth = 1;
    }

    private boolean anyChecked(JCheckBox[] boxes) {
        for (JCheckBox cb : boxes) if (cb.isSelected()) return true;
        return false;
    }

    private void onSubmit() {
        for (JLabel e : new JLabel[]{usernameError, passwordError, confirmError, nameError,
                ageError, emailError, phoneError, dobError, addressError, zipError,
                stateError, colorError, genderError, programError, contactError,
                languageError, skillError, deviceError, termsError}) {
            e.setText(" ");
        }

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirm  = new String(confirmField.getPassword());
        String name     = nameField.getText().trim();
        String ageText  = ageField.getText().trim();
        String email    = emailField.getText().trim();
        String phone    = phoneField.getText().trim();
        String dob      = dobField.getText().trim();
        String address  = addressField.getText().trim();
        String zip      = zipField.getText().trim();

        boolean valid = true;

        // Username
        if (username.isEmpty()) {
            usernameError.setText("Username is required."); valid = false;
        } else if (username.length() < 4) {
            usernameError.setText("Username must be at least 4 characters."); valid = false;
        }

        // Password
        if (password.isEmpty()) {
            passwordError.setText("Password is required."); valid = false;
        } else if (password.length() < 8) {
            passwordError.setText("Password must be at least 8 characters."); valid = false;
        } else if (!password.matches(".*\\d.*")) {
            passwordError.setText("Password must contain a number."); valid = false;
        }

        // Confirm password
        if (!confirm.equals(password)) {
            confirmError.setText("Passwords do not match."); valid = false;
        }

        // Name
        if (name.isEmpty()) {
            nameError.setText("Name is required."); valid = false;
        } else if (!name.matches("[a-zA-Z ]+")) {
            nameError.setText("Name must not contain numbers or special characters."); valid = false;
        }

        // Age
        if (ageText.isEmpty()) {
            ageError.setText("Age is required."); valid = false;
        } else {
            try {
                int age = Integer.parseInt(ageText);
                if (age < 16)       { ageError.setText("Must be at least 16."); valid = false; }
                else if (age > 100) { ageError.setText("Age must be 100 or below."); valid = false; }
            } catch (NumberFormatException ex) {
                ageError.setText("Age must be a number."); valid = false;
            }
        }

        // Email
        if (email.isEmpty()) {
            emailError.setText("Email is required."); valid = false;
        } else if (email.contains(" ") || !email.contains("@") || !email.contains(".")) {
            emailError.setText("Enter a valid email (must include @ and .)."); valid = false;
        }

        // Phone
        if (phone.isEmpty()) {
            phoneError.setText("Phone number is required."); valid = false;
        } else if (!phone.matches("\\d{10}")) {
            phoneError.setText("Phone must be exactly 10 digits."); valid = false;
        }

        // Date of birth
        if (dob.isEmpty()) {
            dobError.setText("Date of birth is required."); valid = false;
        } else {
            try {
                LocalDate birthDate = LocalDate.parse(dob);
                LocalDate today = LocalDate.now();
                if (birthDate.isAfter(today)) {
                    dobError.setText("Date of birth cannot be in the future."); valid = false;
                } else {
                    int dobAge = Period.between(birthDate, today).getYears();
                    if (dobAge < 16)       { dobError.setText("Must be at least 16 years old."); valid = false; }
                    else if (dobAge > 100) { dobError.setText("Age from DOB must be 100 or below."); valid = false; }
                }
            } catch (DateTimeParseException ex) {
                dobError.setText("Use format YYYY-MM-DD."); valid = false;
            }
        }

        // Address
        if (address.isEmpty()) { addressError.setText("Address is required."); valid = false; }

        // ZIP
        if (zip.isEmpty()) {
            zipError.setText("ZIP code is required."); valid = false;
        } else if (!zip.matches("\\d{5}")) {
            zipError.setText("ZIP must be exactly 5 digits."); valid = false;
        }

        // Dropdowns
        if (stateBox.getSelectedIndex()   == 0) { stateError.setText("Please select a state."); valid = false; }
        if (colorBox.getSelectedIndex()   == 0) { colorError.setText("Please select a favorite color."); valid = false; }
        if (genderBox.getSelectedIndex()  == 0) { genderError.setText("Please select a gender."); valid = false; }
        if (programBox.getSelectedIndex() == 0) { programError.setText("Please select a program type."); valid = false; }
        if (contactBox.getSelectedIndex() == 0) { contactError.setText("Please select a contact method."); valid = false; }

        // Checkboxes
        if (!anyChecked(languageBoxes)) { languageError.setText("Select at least one language."); valid = false; }
        if (!anyChecked(skillBoxes))    { skillError.setText("Select at least one skill or certification."); valid = false; }
        if (!anyChecked(deviceBoxes))   { deviceError.setText("Select at least one device."); valid = false; }
        if (!termsBox.isSelected())     { termsError.setText("You must agree to the Terms and Conditions."); valid = false; }

        if (valid) {
            UserStore.addUser(username, password);
            JOptionPane.showMessageDialog(this, "Account created for " + name + "!", "Signed Up", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            if (onSuccess != null) onSuccess.run();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignUpForm(null).setVisible(true));
    }
}
