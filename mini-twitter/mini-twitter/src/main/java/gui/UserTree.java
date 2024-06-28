package gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import backend.mini_twitter_app.Group;
import backend.mini_twitter_app.IndividualUser;
import backend.mini_twitter_app.User;

/**
 * Tree view panel for IndividualUser and GroupUser.
 *
 *  - GroupUser icon will be differentiated from IndividualUser icon
 *      if it has one or more SingleUsers as its child
 *
 * @author delin
 *
 */

public class UserTree extends JPanel {

    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JTree userTree;
    private JScrollPane scrollPane;

    public UserTree(DefaultMutableTreeNode root) {
        super(new GridLayout(1,0));

        this.root = root;
        initializeComponents();
        add(scrollPane);
    }

    public JTree getTree() {
        return this.userTree;
    }

    public DefaultMutableTreeNode getRoot() {
        return this.root;
    }

  
    public void addGroupUser(DefaultMutableTreeNode child) {
        DefaultMutableTreeNode parent = null;
        TreePath parentPath = userTree.getSelectionPath();

        // set parent as selected User, set as root if no User is selected
        if (parentPath != null) {
            parent = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
           
        } else {
            parent = root;
        }

        // add to parent GroupUser if selected node is a IndividualUser
        if (parent.getUserObject().getClass() == IndividualUser.class) {
            parent = (DefaultMutableTreeNode) parent.getParent();
        }
        addUser(parent, child, true);
    }

    public void addSingleUser(DefaultMutableTreeNode child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = userTree.getSelectionPath();

        // set parent as selected User, set as root if no User is selected
        if (parentPath == null) {
            parentNode = root;
        } else {
            parentNode = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
        }

        // add to parent GroupUser if selected node is a IndividualUser
        if (parentNode.getUserObject().getClass() == IndividualUser.class) {
            parentNode = (DefaultMutableTreeNode) parentNode.getParent();
        }
        addUser(parentNode, child, true);
    }

    //Private methods

    private void initializeComponents() {
        treeModel = new DefaultTreeModel(root);
        userTree = new JTree(treeModel);
        format();
        scrollPane = new JScrollPane(userTree);
    }


    private void addUser(DefaultMutableTreeNode parent, DefaultMutableTreeNode child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = root;
        }

        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        if (shouldBeVisible) {
            userTree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }

        if (parent.getClass() != Group.class) {
            parent = (DefaultMutableTreeNode) parent.getUserObject();
        }
        ((Group) parent).addUserInGroup((User) child);
    }


    private void format() {
        userTree.setShowsRootHandles(true);
        userTree.setEditable(true);
        userTree.setSelectionRow(0);
        userTree.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);

    }

   

}