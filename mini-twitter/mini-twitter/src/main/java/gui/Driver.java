package gui;

import java.awt.EventQueue;



public class Driver  {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminPanel adminFrame = AdminPanel.getSingleton();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
