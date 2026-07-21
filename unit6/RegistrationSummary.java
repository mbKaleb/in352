import javax.swing.*;
import java.awt.*;

public class RegistrationSummary extends JFrame {

    public RegistrationSummary(
            String username, String name, String age, String email,
            String phone, String dob, String address, String zip,
            String state, String color, String gender, String program,
            String contact, String languages, String skills, String devices) {

        super("Registration Summary");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;
        int[] row = {0};

        // Title
        JLabel title = new JLabel("Registration Confirmed");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        gbc.gridx = 0; gbc.gridy = row[0]++; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 8, 12, 8);
        panel.add(title, gbc);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 8, 5, 8);

        addSectionHeader(panel, gbc, row, "Account");
        addRow(panel, gbc, row, "Username:",                username);

        addSectionHeader(panel, gbc, row, "Personal Information");
        addRow(panel, gbc, row, "Full Name:",               name);
        addRow(panel, gbc, row, "Age:",                     age);
        addRow(panel, gbc, row, "Date of Birth:",           dob);
        addRow(panel, gbc, row, "Gender:",                  gender);

        // Contact section
        addSectionHeader(panel, gbc, row, "Contact");
        addRow(panel, gbc, row, "Email:",                   email);
        addRow(panel, gbc, row, "Phone:",                   phone);
        addRow(panel, gbc, row, "Preferred Contact:",       contact);

        // Address section
        addSectionHeader(panel, gbc, row, "Address");
        addRow(panel, gbc, row, "Street Address:",          address);
        addRow(panel, gbc, row, "ZIP Code:",                zip);
        addRow(panel, gbc, row, "State:",                   state);

        // Program section
        addSectionHeader(panel, gbc, row, "Program");
        addRow(panel, gbc, row, "Program Type:",            program);
        addRow(panel, gbc, row, "Skills & Certifications:", skills);

        // Preferences section
        addSectionHeader(panel, gbc, row, "Preferences");
        addRow(panel, gbc, row, "Favorite Color:",          color);
        addRow(panel, gbc, row, "Languages:",               languages);
        addRow(panel, gbc, row, "Devices:",                 devices);

        // Close button
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        gbc.gridx = 0; gbc.gridy = row[0]++; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 8, 5, 8);
        panel.add(closeBtn, gbc);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setPreferredSize(new Dimension(520, 580));
        add(scroll);
        pack();
        setLocationRelativeTo(null);
    }

    private void addSectionHeader(JPanel p, GridBagConstraints gbc, int[] row, String title) {
        JSeparator sep = new JSeparator();
        gbc.gridx = 0; gbc.gridy = row[0]++; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 8, 0, 8);
        p.add(sep, gbc);

        JLabel label = new JLabel(title);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        label.setForeground(new Color(30, 100, 200));
        gbc.gridy = row[0]++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(2, 8, 4, 8);
        p.add(label, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 8, 5, 8);
    }

    private void addRow(JPanel p, GridBagConstraints gbc, int[] row, String labelText, String value) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
        gbc.gridx = 0; gbc.gridy = row[0]; gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        p.add(lbl, gbc);

        JLabel val = new JLabel(value);
        val.setFont(val.getFont().deriveFont(Font.PLAIN));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        p.add(val, gbc);

        row[0]++;
    }
}
