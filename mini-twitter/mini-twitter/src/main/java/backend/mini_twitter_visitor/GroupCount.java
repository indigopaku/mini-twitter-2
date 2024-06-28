package backend.mini_twitter_visitor;

import java.util.HashSet;
import java.util.Set;

import backend.mini_twitter_app.Group;
import backend.mini_twitter_app.User;

public class GroupCount implements Visitor {

    @Override
    public int visitUser(User user) {
        if (user instanceof Group) {
            return visitGroupUser(user);
        }
        return 0;
    }

    @Override
    public int visitSingleUser(User user) {
        return 0;
    }

    @Override
    public int visitGroupUser(User user) {
        Set<String> uniqueGroups = new HashSet<>();
        visitGroupUserHelper(user, uniqueGroups);
        return uniqueGroups.size();
    }

    private void visitGroupUserHelper(User user, Set<String> uniqueGroups) {
        for (User member : ((Group) user).getUsersList()) {
            if (member instanceof Group) {
                uniqueGroups.add(member.getIdentifier()); 
                visitGroupUserHelper(member, uniqueGroups); 
            }
        }
    }
}