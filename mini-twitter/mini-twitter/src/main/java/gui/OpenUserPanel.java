package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import backend.mini_twitter_app.Group;
import backend.mini_twitter_app.Observer;
import backend.mini_twitter_app.IndividualUser;
import backend.mini_twitter_app.User;

/**
 * Panel on main UI containing button to open UserViewPanel.
 *
 * - UserViewPanel is only opened if a IndividualUser is selected
 * in the UserTreePanel
 * - one UserViewPanel is opened per IndividualUser
 *
 * @author delin
 *
 */

public class OpenUserPanel extends JPanel {

    private JButton openUserViewButton;
    private JPanel spacerPanel, treePanel;
    private Map<String, Observer> userRegistry;
    private Map<String, JPanel> activeUserViews;

    // Create the panel
    public OpenUserPanel(JPanel treePanel, Map<String, Observer> userRegistry) {
        super();

        this.treePanel = treePanel;
        this.userRegistry = userRegistry;
        initComponents();
        layoutComponents();
    }

    /*
     * Private methods
     */

    private void initComponents() {
        openUserViewButton = new JButton("Open User View");
        initListener();

        activeUserViews = new HashMap<String, JPanel>();
        spacerPanel = new JPanel();
    }

    private void layoutComponents() {
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.ipadx = 10;
        constraints.ipady = 10;
        
        constraints.gridheight = 1;
        constraints.gridwidth = 2;

        constraints.gridx = 1;
        constraints.gridy = 2;
        this.add(openUserViewButton, constraints);
        constraints.gridy = 3;
        this.add(spacerPanel, constraints);
    }

    // return selected user
    private DefaultMutableTreeNode getSelectedUser() {
        JTree tree = ((UserTree) treePanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        if (!((UserTree) treePanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }

        return selectedNode;
    }

    private void initListener() {
        openUserViewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                handleViewUserClick();
            }
        });
    }

    private void handleViewUserClick() {
        {
            // get User selected in UserTreePanel
            DefaultMutableTreeNode selectedNode = getSelectedUser();
                if (!userRegistry.containsKey(((User) selectedNode).getIdentifier())) {
                    JOptionPane.showMessageDialog(null, "No Such User Exists", "Error!", JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == Group.class) {
                    JOptionPane.showMessageDialog(null, "Cannot open user view for a group!", "Error!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (activeUserViews.containsKey(((User) selectedNode).getIdentifier())) {
                    JOptionPane.showMessageDialog(null,
                            "User view already open for " + ((User) selectedNode).getIdentifier() + "!", "Error!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == IndividualUser.class) {
                    UserPanel userView = new UserPanel(userRegistry, activeUserViews, selectedNode);
                    activeUserViews.put(((User) selectedNode).getIdentifier(), userView);
                }
        }
    }

}
