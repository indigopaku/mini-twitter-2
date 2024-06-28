package backend.mini_twitter_visitor;

import backend.mini_twitter_app.Group;
import backend.mini_twitter_app.IndividualUser;
import backend.mini_twitter_app.User;

/**
 * This class is responsible for calculating the total number of positive messages
 * across all users in a User structure. It differentiates between IndividualUser and GroupUser
 * instances to aggregate their positive message counts.
 */
public class PositiveMessages implements Visitor {

    @Override
    public int visitUser(User user) {
        // Use instanceOf for type checking and delegate accordingly
        if (user instanceof IndividualUser) {
            return visitSingleUser(user);
        } else if (user instanceof Group) {
            return visitGroupUser(user);
        }
        return 0;
    }

    @Override
    public int visitSingleUser(User user) {
        return ((IndividualUser) user).getPositiveMessageCount();
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