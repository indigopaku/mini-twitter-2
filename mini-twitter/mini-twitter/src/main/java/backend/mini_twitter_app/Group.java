 package backend.mini_twitter_app;

// import java.util.HashMap;
// import java.util.Map;
// import edu.cpp.cs585.mini_twitter_visitor.Visitor;


// public class GroupedUser extends User {

//     private Map<String, User> groupUsers;

//     public GroupedUser(String identifier) {
//         super(identifier);
//         this.groupUsers = new HashMap<>();
//     }

//     public Map<String, User> getGroupUsers() {
//         return this.groupUsers;
//     }

//     public User addUserInGroup(User userNode) {
//         if (!this.containsUser(userNode.getIdentifier())) {
//             this.groupUsers.put(userNode.getIdentifier(), userNode);
//         }
//         return this;
//     }

//     @Override
//     public boolean containsUser(String identifier) {
//         for (User user : this.groupUsers.values()) {
//             if (user.containsUser(identifier)) {
//                 return true;
//             }
//         }
//         return false;
//     }

//     @Override
//     public void update(Subject subject) {
//         for (User user : groupUsers.values()) {
//             ((Observer) user).update(subject);
//         }
//     }

//     @Override
//     public int calcSingleUsers() {
//         int sum = 0;
//         for (User user : this.groupUsers.values()) {
//             sum += user.calcSingleUsers();
//         }
//         return sum;
//     }

//     @Override
//     public int calculateGroupCount() {
//         // This group itself counts as one, hence starting from 1
//         int count = 1;
//         for (User userNode : this.groupUsers.values()) {
//             count += userNode.calculateGroupCount();
//         }
//         return count;
//     }

//     @Override
//     public void acceptVisitor(Visitor visitor) {
//         visitor.visitGroupUser(this);
//         for (User userNode : this.groupUsers.values()) {
//             userNode.acceptVisitor(visitor);
//         }
//     }

// }


import java.util.ArrayList;
import java.util.List;

import backend.mini_twitter_visitor.Visitor;

public class Group extends User  {

    private List<User> usersList;

    public Group(String id) {
        super(id);
        usersList = new ArrayList<>();
    }

    public List<User> getUsersList() {
        return new ArrayList<>(usersList); // Return a copy to preserve encapsulation
    }

    public User addUserInGroup(User user) {
        boolean exists = false;
        for (User u : usersList) {
            if (u.getIdentifier().equals(user.getIdentifier())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            usersList.add(user);
        }
        return this;
    }

    @Override
    public boolean containsUser(String id) {
        for (User user : usersList) {
            if (user.containsUser(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int calculateUserCount() {
        int count = 0;
        for (User user : usersList) {
            count += user.calculateUserCount();
        }
        return count;
    }

    @Override
    public int calculateGroupCount() {
        int count = 1; // Includes this group
        for (User user : usersList) {
            if (user instanceof Group) {
                count += user.calculateGroupCount();
            }
            else 
            {
                count += 1;
            }
        }
        return count;
    }

    @Override
    public int getMessageCount() {
        int msgCount = 0;
        for (User user : usersList) {
            msgCount += user.getMessageCount();
        }
        return msgCount;
    }

    @Override
    public void update(Subject subject) {
        for (User user : usersList) {
            if (user instanceof Observer) {
                ((Observer) user).update(subject);
            }
        }
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitGroupUser(this);
        for (User user : usersList) {
            user.acceptVisitor(visitor);
        }
    }
}