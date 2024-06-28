package gui;

import backend.mini_twitter_app.Group;
import backend.mini_twitter_app.Observer;

import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.concurrent.ConcurrentHashMap;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.awt.GridBagConstraints;

/**
 * Panel for main UI, applies Singleton design pattern.
 * Contains UserTreePanel, AddUserPanel, OpenUserViewPanel, ShowInfoPanel.
 *
 * @author delin
 *
 */
public class AdminPanel extends JFrame{

    private static AdminPanel singletonINSTANCE;

    private Map<String, Observer> usersMap;
    private DefaultMutableTreeNode baseNode;

    private JPanel treePanel, addUserPanel, viewUserPanel, infoDisplayPanel;

    private AdminPanel() {
        initializeUI();
    }

    public static AdminPanel getSingleton() {
        if (singletonINSTANCE == null) {
            synchronized (Driver.class) {
                if (singletonINSTANCE == null) {
                    singletonINSTANCE = new AdminPanel();
                }
            }
        }
        return singletonINSTANCE;
    }

    private void initializeUI() {
        setTitle("Mini Twitter Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(600, 400);
        setLocationRelativeTo(null); // Center on screen

        usersMap = new ConcurrentHashMap<>();
        baseNode = new Group("Root");

        setupPanels();
        configureAccessibility();
        setVisible(true);
    }

    private void setupPanels() {
        treePanel = new UserTree(baseNode);
        addUserPanel = new AddNewUserPanel(treePanel, usersMap);
        viewUserPanel = new OpenUserPanel(treePanel, usersMap);
        infoDisplayPanel = new InfoDisplayPanel(treePanel);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets= new Insets(0, 0, 0, 0);
        constraints.ipadx = 10;
        constraints.ipady = 10;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        addPanel(treePanel, 0, 0, 1, 6, constraints);
        addPanel(addUserPanel, 1, 0, 2, 2, constraints);
        addPanel(viewUserPanel, 1, 2, 2, 2, constraints);
        addPanel(infoDisplayPanel, 1, 4, 2, 2, constraints);
    }

    private void addPanel(JPanel panel, int gridx, int gridy, int gridwidth, int gridheight, GridBagConstraints constraints) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        add(panel, constraints);
    }

    private void configureAccessibility() {
        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        InputMap inputMap = (InputMap) UIManager.get("Button.focusInputMap");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "none");
    }
}