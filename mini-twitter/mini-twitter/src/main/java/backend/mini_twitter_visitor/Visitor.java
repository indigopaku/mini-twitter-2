package backend.mini_twitter_visitor;

import backend.mini_twitter_app.User;


public interface Visitor {

    public int visitUser(User user);
    public int visitSingleUser(User user);
    public int visitGroupUser(User user);

}
