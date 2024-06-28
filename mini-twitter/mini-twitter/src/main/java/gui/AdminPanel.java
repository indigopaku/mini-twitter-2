import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class AdminPanel extends JFrame {
    private JButton validateIDsButton;
    private JButton findLastUpdatedUserButton;
    private Set<String> allIDs;
    private List<User> users;
    private List<Group> groups;

    public AdminPanel() {
        // Initialize components and layout here...
        validateIDsButton = new JButton("Validate IDs");
        validateIDsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateIDs();
            }
        });
        
        findLastUpdatedUserButton = new JButton("Find Last Updated User");
        findLastUpdatedUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findLastUpdatedUser();
            }
        });
        
        // Add buttons to the panel
        setLayout(new FlowLayout());
        add(validateIDsButton);
        add(findLastUpdatedUserButton);

        // Mock data for demonstration
        users = new ArrayList<>();
        users.add(new IndividualUser("user1"));
        users.add(new IndividualUser("user 2")); // Invalid ID

        groups = new ArrayList<>();
        groups.add(new Group("group1"));
        groups.add(new Group("group 2")); // Invalid ID

        // Mock update times
        ((IndividualUser) users.get(0)).postTweet("Hello, world!"); // Updates lastUpdateTime
    }

    private void validateIDs() {
        allIDs = new HashSet<>();
        boolean isValid = true;
        StringBuilder invalidMessages = new StringBuilder();

        for (User user : users) {
            if (!isValidID(user.getId())) {
                isValid = false;
                invalidMessages.append("Invalid User ID: ").append(user.getId()).append("\n");
            }
        }
        for (Group group : groups) {
            if (!isValidID(group.getId())) {
                isValid = false;
                invalidMessages.append("Invalid Group ID: ").append(group.getId()).append("\n");
            }
        }

        if (isValid) {
            JOptionPane.showMessageDialog(this, "All IDs are valid.");
        } else {
            JOptionPane.showMessageDialog(this, invalidMessages.toString());
        }
    }

    private boolean isValidID(String id) {
        if (id.contains(" ") || !allIDs.add(id)) {
            return false;
        }
        return true;
    }

    private void findLastUpdatedUser() {
        IndividualUser lastUpdatedUser = null;

        for (User user : users) {
            if (user instanceof IndividualUser) {
                IndividualUser individualUser = (IndividualUser) user;
                if (lastUpdatedUser == null || individualUser.getLastUpdateTime() > lastUpdatedUser.getLastUpdateTime()) {
                    lastUpdatedUser = individualUser;
                }
            }
        }

        if (lastUpdatedUser != null) {
            JOptionPane.showMessageDialog(this, "Last Updated User ID: " + lastUpdatedUser.getId());
        } else {
            JOptionPane.showMessageDialog(this, "No users found.");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new AdminPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }
}
