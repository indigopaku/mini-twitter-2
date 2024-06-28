package backend.mini_twitter_visitor;

import backend.mini_twitter_app.Group;
import backend.mini_twitter_app.IndividualUser;
import backend.mini_twitter_app.User;

public class TotalMessages implements Visitor {

    @Override
    public int visitUser(User user) {
        if (user instanceof IndividualUser) {
            return visitSingleUser(user);
        } else if (user instanceof Group) {
            return visitGroupUser(user);
        }
        return 0;
    }

    @Override
    public int visitSingleUser(User user) {
        return ((IndividualUser) user).getMessageCount();
    }

    @Override
    public int visitGroupUser(User user) {
        int total = 0;
        for (User member : ((Group) user).getUsersList()) {
            total += visitUser(member);
        }
        return total;
    }
}