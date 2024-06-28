import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {
    private IndividualUser user;
    private JLabel creationTimeLabel;
    private JLabel lastUpdateTimeLabel;

    public UserPanel(IndividualUser user) {
        this.user = user;
        setLayout(new BorderLayout());

        creationTimeLabel = new JLabel("Creation Time: " + user.getCreationTime());
        lastUpdateTimeLabel = new JLabel("Last Update Time: " + user.getLastUpdateTime());

        add(creationTimeLabel, BorderLayout.NORTH);
        add(lastUpdateTimeLabel, BorderLayout.SOUTH);

        // Add other components...
    }

    public void updateLastUpdateTime() {
        lastUpdateTimeLabel.setText("Last Update Time: " + user.getLastUpdateTime());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("User View");
        IndividualUser user = new IndividualUser("user1");
        UserPanel userPanel = new UserPanel(user);

        frame.add(userPanel);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Simulate posting a tweet and updating the UI
        user.postTweet("Hello, Twitter!");
        userPanel.updateLastUpdateTime();
    }
}
