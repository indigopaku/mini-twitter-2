package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import backend.mini_twitter_app.Group;
import backend.mini_twitter_app.Observer;
import backend.mini_twitter_app.IndividualUser;

/**
 * Panel for adding users or groups to the application.
 * Ensures IDs are unique and places new entities correctly in the hierarchy.
 */
public class AddNewUserPanel extends JPanel {

    private JPanel selectionPanel;
    private Map<String, Observer> registeredUsers;

    private JButton createUserBtn;
    private JButton createGroupBtn;
    private JTextField userField;
    private JTextField groupField;

    public AddNewUserPanel(JPanel selectionPanel, Map<String, Observer> registeredUsers) {
        super();
        this.selectionPanel = selectionPanel;
        this.registeredUsers = registeredUsers;

        setupUI();
        layoutComponents();
    }

    private void layoutComponents() {
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridheight = 1;
        constraints.gridwidth = 1;

        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(userField, constraints);

        constraints.gridx = 1;
        this.add(createUserBtn, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(groupField, constraints);

        constraints.gridx = 1;
        this.add(createGroupBtn, constraints);
    }

    private void setupUI() {
        userField = new JTextField("Enter User ID");
        groupField = new JTextField("Enter Group ID");

        createUserBtn = new JButton("Create User");
        setupUserButtonListener();

        createGroupBtn = new JButton("Create Group");
        setupGroupButtonListener();
    }

    private void setupUserButtonListener() {
        createUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUserToTree(userField.getText());
            }
        });
    }

    private void setupGroupButtonListener() {
        createGroupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGroupToTree(groupField.getText());
            }
        });
        }
    

    private void addUserToTree(String userId) {
        if (registeredUsers.containsKey(userId)) {
            JOptionPane.showMessageDialog(this, "This user ID is taken. Try another.", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            Observer newUser = new IndividualUser(userId);
            registeredUsers.put(userId, newUser);
            ((UserTree) selectionPanel).addSingleUser((DefaultMutableTreeNode) newUser);
        }
    }

    private void addGroupToTree(String groupId){
        {
            if (registeredUsers.containsKey(groupId)) {
                JOptionPane.showMessageDialog(this, "This group ID is taken. Try another.", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                Observer newGroup = new Group(groupId);
                registeredUsers.put(groupId, newGroup);
                ((UserTree) selectionPanel).addGroupUser((DefaultMutableTreeNode) newGroup);                    }
                        }
    }
}