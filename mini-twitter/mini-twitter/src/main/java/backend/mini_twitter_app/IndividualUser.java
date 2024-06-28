package backend.mini_twitter_app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.mini_twitter_visitor.Visitor;


public class IndividualUser extends User implements Subject {

    private static final List<String> POSITIVE_WORDS = Arrays.asList("good", "great", "excellent", "awesome");

    private Map<String, Observer> followers;
    private Map<String, Subject> following;
    private List<String> newsFeed;
    
    private String latestMessage;
    private int positiveMessageCount;

    public IndividualUser(String id) {
        super(id);
        followers = new HashMap<String, Observer>();
        followers.put(this.getIdentifier(), (Observer) this);
        following = new HashMap<String, Subject>();
        newsFeed = new ArrayList<String>();
    }

    // Returns the IndividualUser followers of this User.

    public Map<String, Observer> getFollowers() {
        return followers;
    }

    // Returns the SingleUsers this User is following.

    public Map<String, Subject> getFollowing() {
        return following;
    }

    // Return news feed of user
    public List<String> getNewsFeed() {
        return newsFeed;
    }

    // Sends specified message to the news feeds of the followers of this User,
    // checks if message is positive,
    public void sendMessage(String message) {
        this.latestMessage = message;
        this.incrementMessageCount();

        if (isPositiveMessage(message)) {
            ++positiveMessageCount;
        }
        notifyObservers();
    }

    // Returns the most recent message sent by this User.
    public String getLatestMessage() {
        return this.latestMessage;
    }

    // Returns the number of positive messages sent by this User.
    public int getPositiveMessageCount() {
        return positiveMessageCount;
    }

    @Override
    public boolean containsUser(String id) {
        return this.getIdentifier().equals(id);
    }

    @Override
    public int calculateGroupCount() {
        return 0;
    }

    @Override
    public int calculateUserCount() {
        return 1;
    }

    @Override
    public void update(Subject subject) {
        newsFeed.add(0, (((IndividualUser) subject).getIdentifier() + ": " + ((IndividualUser) subject).getLatestMessage()));
    }

    // Attaches the specified observer User as a follower of this subject User.
    @Override
    public void attach(Observer observer) {
        addFollower(observer);
    }

    // Update the followers of this User with the most recent message.
    @Override
    public void notifyObservers() {
        for (Observer obs : followers.values()) {
            obs.update(this);
        }
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitSingleUser(this);
    }

    private void addFollower(Observer user) {
        this.getFollowers().put(((User) user).getIdentifier(), user);
        ((IndividualUser) user).addUserToFollow(this);
    }

    private void addUserToFollow(Subject toFollow) {
        if (toFollow.getClass() == IndividualUser.class) {
            getFollowing().put(((User) toFollow).getIdentifier(), toFollow);
        }
    }

    private boolean isPositiveMessage(String message) {
        boolean positive = false;
        message = message.toLowerCase();
        for (String word : POSITIVE_WORDS) {
            if (message.contains(word)) {
                positive = true;
            }
        }
        return positive;
    }

}

