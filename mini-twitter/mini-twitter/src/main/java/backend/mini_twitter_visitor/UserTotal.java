package backend.mini_twitter_visitor;

import backend.mini_twitter_app.Group;
import backend.mini_twitter_app.IndividualUser;
import backend.mini_twitter_app.User;

/**
 * This class calculates the total number of IndividualUser instances
 * within a User structure. It counts the initial User if it is a IndividualUser.
 */

public class UserTotal implements Visitor {

    @Override
    public int visitUser(User user) {
        // Determine the type of the user and delegate to the specific method
        if (user instanceof IndividualUser) {
            return visitSingleUser(user);
        } else if (user instanceof Group) {
            return visitGroupUser(user);
        }
        return 0;
    }

    @Override
    public int visitSingleUser(User user) {
        // A IndividualUser is counted as one
        return 1;
    }

    @Override
    public int visitGroupUser(User user) {
        // Initialize count for users in a group
        int total = 0;
        // Recursively count users in the group
        for (User member : ((Group) user).getUsersList()) {
            total += visitUser(member);
        }
        return total;
    }
}