package backend.mini_twitter_app;

import java.util.ArrayList;
import java.util.List;

public class IndividualUser extends User {
    private long creationTime;
    private long lastUpdateTime;

    public IndividualUser(String id) {
        super(id);
        this.creationTime = System.currentTimeMillis();
        this.lastUpdateTime = creationTime; // Initial last update time is the creation time
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public void postTweet(String message) {
        super.postTweet(message);
        this.setLastUpdateTime(System.currentTimeMillis());
        // Notify followers
        for (User follower : followers) {
            if (follower instanceof IndividualUser) {
                ((IndividualUser) follower).setLastUpdateTime(this.lastUpdateTime);
            }
        }
    }
}
