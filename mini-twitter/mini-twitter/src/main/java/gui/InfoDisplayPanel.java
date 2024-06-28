package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import backend.mini_twitter_app.User;
import backend.mini_twitter_visitor.GroupCount;
import backend.mini_twitter_visitor.TotalMessages;
import backend.mini_twitter_visitor.PositiveMessages;
import backend.mini_twitter_visitor.UserTotal;

/**
 * Panel for displaying statistics about users and groups.
 */
public class InfoDisplayPanel extends JPanel {

    private JPanel treeViewPanel;
    private JButton btnUserCount, btnGroupCount, btnMessageCount, btnPositiveMsgPercentage;

    public InfoDisplayPanel(JPanel treeViewPanel) {
        super();
        this.treeViewPanel = treeViewPanel;
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
        constraints.ipadx= 10;
        constraints.ipady = 10;

        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(btnUserCount, constraints);

        constraints.gridx = 1;
        this.add(btnGroupCount, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(btnMessageCount, constraints);

        constraints.gridx = 1;
        this.add(btnPositiveMsgPercentage, constraints);
    }

    private void setupUI() {
        btnUserCount = new JButton("User Count");
        btnGroupCount = new JButton("Group Count");
        btnMessageCount = new JButton("Message Count");
        btnPositiveMsgPercentage = new JButton("Positive Messages %");

        btnUserCount.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showUserCount();
            }
        }
        );
        btnGroupCount.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showGroupCount();
            }
        }
        );
        btnMessageCount.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showMessageCount();
            }
        }
        );
        btnPositiveMsgPercentage.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showPositiveMessagePercentage();
            }
        }
        );
    }

    private DefaultMutableTreeNode getSelectedUserNode() {
        JTree userTree = ((UserTree) treeViewPanel).getTree();
        DefaultMutableTreeNode selectedUserNode = (DefaultMutableTreeNode) userTree.getSelectionPath().getLastPathComponent();
        if (!((UserTree) treeViewPanel).getRoot().equals(selectedUserNode)) {
            selectedUserNode = (DefaultMutableTreeNode) selectedUserNode.getUserObject();
        }

        return selectedUserNode;
    }

    private void showUserCount() {
        DefaultMutableTreeNode node = getSelectedUserNode();
        UserTotal visitor = new UserTotal();
        ((User) node).acceptVisitor(visitor);
        int count = visitor.visitUser(((User) node));
        displayInfo("User Count", "Total Users: " + count);
    }

    private void showGroupCount() {
        DefaultMutableTreeNode node = getSelectedUserNode();
        GroupCount visitor = new GroupCount();
        ((User) node).acceptVisitor(visitor);
        int count = visitor.visitUser(((User) node));
        displayInfo("Group Count", "Total Groups: " + count);
    }

    private void showMessageCount() {
        DefaultMutableTreeNode node = getSelectedUserNode();
        TotalMessages visitor = new TotalMessages();
        ((User) node).acceptVisitor(visitor);
        int count = visitor.visitUser(((User) node));
        displayInfo("Message Count", "Total Messages: " + count);
    }

    private void showPositiveMessagePercentage() {
        DefaultMutableTreeNode node = getSelectedUserNode();
        PositiveMessages positiveVisitor = new PositiveMessages();
        ((User) node).acceptVisitor(positiveVisitor);
        int positiveCount = positiveVisitor.visitUser(((User) node));

        TotalMessages messageVisitor = new TotalMessages();
        ((User) node).acceptVisitor(messageVisitor);
        int totalCount = messageVisitor.visitUser(((User) node));

        double percentage = totalCount > 0 ? (double) positiveCount / totalCount * 100 : 0;
        displayInfo("Positive Messages %", "Positive Messages: " + String.format("%.2f%%", percentage));
    }

    private void displayInfo(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}