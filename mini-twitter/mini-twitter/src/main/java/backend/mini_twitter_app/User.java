package backend.mini_twitter_app;

import javax.swing.tree.DefaultMutableTreeNode;
import backend.mini_twitter_visitor.Visitor;


public abstract class User extends DefaultMutableTreeNode implements Observer {

    private String identifier;
    private int messageCount;

    public User(String identifier) {
        super(identifier);
        this.identifier = identifier;
        this.messageCount = 0;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void incrementMessageCount() {
        this.messageCount++;
    }

    // Determines if a specific user exists within the structure
    public abstract boolean containsUser(String identifier);
    // Counts the number of individual user nodes
    public abstract int calculateUserCount();
    // Counts the number of group nodes
    public abstract int calculateGroupCount();
    // Accepts a visitor for various operations
    public abstract void acceptVisitor(Visitor visitor);
}