package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import backend.mini_twitter_app.Group;
import backend.mini_twitter_app.Observer;
import backend.mini_twitter_app.IndividualUser;
import backend.mini_twitter_app.Subject;
import backend.mini_twitter_app.User;


public class UserPanel extends JPanel {

    private static JFrame mainFrame;
    private JTextField followUserTextField;
    private JTextArea tweetTextArea, followingTextArea, newsFeedTextArea;
    private JScrollPane tweetScrollPane, followingScrollPane, newsFeedScrollPane;
    private JButton followButton, tweetButton;

    private Subject currentUser;
    private Map<String, Observer> userMap;
    private Map<String, JPanel> panelMap;

   
    public UserPanel(Map<String, Observer> userMap, Map<String, JPanel> allPanels, DefaultMutableTreeNode currentUser) {
        super();

        this.currentUser = (Subject) currentUser;
        this.userMap = userMap;
        this.panelMap = allPanels;
        initializeComponents();
        addComponents();
    }

   

    private void addComponents() {
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridheight = 1;
        constraints.gridwidth = 1;

        constraints.gridx = 0;
        constraints.gridy = 0;
        mainFrame.add(followUserTextField, constraints);
        constraints.gridx = 1;
        mainFrame.add(followButton, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        mainFrame.add(followingScrollPane, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        mainFrame.add(tweetScrollPane, constraints);
        constraints.gridx = 1;
        mainFrame.add(tweetButton, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        mainFrame.add(newsFeedScrollPane, constraints);
    
    }

    private void initializeComponents() {
        mainFrame = new JFrame("User View");
        format();

       

       followingArea();
       tweetArea();

       
       newsFeedTextArea = new JTextArea("News Feed: ");
       formatTextArea(newsFeedTextArea);
       newsFeedScrollPane = new JScrollPane(newsFeedTextArea);
       newsFeedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        // current following and news feed lists reflect most recent state of UserViewPanel
        updateCurrentFollowingTextArea();

        // news feed is updated even while UserViewPanel is closed
        updateNewsFeedTextArea();
    }


    private void followingArea(){
        followUserTextField = new JTextField("User ID");
        followButton = new JButton("Follow User");
        initFollowListener();

        followingTextArea = new JTextArea("Following: ");
        formatTextArea(followingTextArea);
        followingScrollPane = new JScrollPane(followingTextArea);
        followingScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    private void tweetArea(){
        tweetTextArea = new JTextArea("Create a new Tweet: ");
        tweetScrollPane = new JScrollPane(tweetTextArea);
        tweetScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        tweetButton = new JButton("Post Tweet");
        initTweetPostListener();
        
    }

    private void formatTextArea(JTextArea textArea) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(8);
        textArea.setEditable(false);
    }

    private void format() {
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setSize(800, 400);
        mainFrame.setVisible(true);
        mainFrame.setTitle(((User) currentUser).getIdentifier());

        // allows UserViewPanel to be reopened after it has been closed
        mainFrame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                panelMap.remove(((User) currentUser).getIdentifier());
            }
        });
    }

    
    private void updateNewsFeedTextArea() {
        String list = "News Feed: \n";

        for (String news : ((IndividualUser) currentUser).getNewsFeed()) {
            list += " - " + news + "\n";
        }

        // show most recent message at top of news feed
        newsFeedTextArea.setText(list);
        newsFeedTextArea.setCaretPosition(0);
    }

    
    private void updateCurrentFollowingTextArea() {
        String list = "Current Following: \n";
        for (String following : ((IndividualUser) currentUser).getFollowing().keySet()) {
            list += " - " + following + "\n";
        }
        followingTextArea.setText(list);
        followingTextArea.setCaretPosition(0);
    }

    // Action listeners
    private void initTweetPostListener() {
        tweetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                handlePostTweetAction();
            }
        });
    }

   
    private void initFollowListener() {
        followButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              handleFollowAction();
            }
        });
    }

    // Action handlers 

    private void handlePostTweetAction(){
        ((IndividualUser) currentUser).sendMessage(tweetTextArea.getText());

        for (JPanel panel : panelMap.values()) {
            ((UserPanel) panel).updateNewsFeedTextArea();
        }
    }

    private void handleFollowAction(){
        User toFollow = (User) userMap.get(followUserTextField.getText());

        if (!userMap.containsKey(followUserTextField.getText())) {
            JOptionPane.showMessageDialog(this, "User does not exist!", "Error!", JOptionPane.ERROR_MESSAGE);

        } else if (toFollow.getClass() == Group.class) {
            JOptionPane.showMessageDialog(this, "Cannot follow a GROUP.", "Error!", JOptionPane.ERROR_MESSAGE);

        } else if (userMap.containsKey(followUserTextField.getText())) {
            ((Subject) toFollow).attach((Observer) currentUser);
        }

        // show current following as list
        updateCurrentFollowingTextArea();
    }
}
