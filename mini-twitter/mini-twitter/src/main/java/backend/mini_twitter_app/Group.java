package backend.mini_twitter_app;

public class Group {
    private String id;
    private long creationTime;

    public Group(String id) {
        this.id = id;
        this.creationTime = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public long getCreationTime() {
        return creationTime;
    }
}
