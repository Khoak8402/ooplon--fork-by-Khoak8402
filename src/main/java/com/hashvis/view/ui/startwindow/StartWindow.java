package com.hashvis.view.ui.startwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartWindow extends JFrame{
    private String[] dataTypes = {"Integer", "String"};
    private String[] collisionResolvers = {"Linear Probing", "Quadratic Probing", "Double Hashing", "Separate Chaining"};
    private JComboBox<String> dataTypeSelectionField;
    private JComboBox<String> collisionResolverSelectionField;
    private JButton createTableButton;
    private JButton helpButton;
    private JButton quitButton;

    
    public StartWindow() {
        setTitle("Hash Table Visualizer Application");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Hash Table Visualizer");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(CENTER_ALIGNMENT);

        mainPanel.add(title);
        mainPanel.add(Box.createHorizontalStrut(20));

        JLabel visualImage = createVisualImage();
        mainPanel.add(visualImage);
        mainPanel.add(Box.createHorizontalStrut(20));

        JPanel dataTypeSelectionPanel = new JPanel();
        dataTypeSelectionPanel.setLayout(new BoxLayout(dataTypeSelectionPanel, BoxLayout.X_AXIS));
        dataTypeSelectionField = new JComboBox<>(dataTypes);
        dataTypeSelectionPanel.add(new JLabel("Data Type:  "));
        dataTypeSelectionPanel.add(dataTypeSelectionField);
        dataTypeSelectionPanel.setPreferredSize(new Dimension(150, 10));
        dataTypeSelectionPanel.add(Box.createHorizontalGlue());
        mainPanel.add(dataTypeSelectionPanel);
        mainPanel.add(Box.createHorizontalStrut(20));

        JPanel collisionResolverSelectionPanel = new JPanel();
        collisionResolverSelectionPanel.setLayout(new BoxLayout(collisionResolverSelectionPanel, BoxLayout.X_AXIS));
        collisionResolverSelectionField = new JComboBox<>(collisionResolvers);
        collisionResolverSelectionPanel.add(new JLabel("Collision Resolver:  "));
        collisionResolverSelectionPanel.add(collisionResolverSelectionField);
        collisionResolverSelectionPanel.add(Box.createHorizontalGlue());
        collisionResolverSelectionPanel.setPreferredSize(new Dimension(150, 10));
    
        mainPanel.add(collisionResolverSelectionPanel);
        mainPanel.add(Box.createHorizontalStrut(20));

        createTableButton = new JButton("Create Table");
        createTableButton.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(createTableButton);

        mainPanel.add(Box.createHorizontalStrut(30));
        mainPanel.add(new JSeparator());
        mainPanel.add(Box.createHorizontalStrut(20));

        helpButton = new JButton("Help");
        helpButton.setAlignmentX(CENTER_ALIGNMENT);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                menuButtonAction();
            }
        });
        mainPanel.add(helpButton);
        mainPanel.add(Box.createHorizontalStrut(20));

        quitButton = new JButton("Quit");
        quitButton.setAlignmentX(CENTER_ALIGNMENT);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                confirmQuitButtonAction();
            }
        });
        mainPanel.add(quitButton);

        add(mainPanel);
    }

    public JLabel createVisualImage(){
        ImageIcon imageIcon = new ImageIcon("./resource/hash_table_vis.png");
        JLabel img= new JLabel(imageIcon);
        img.setAlignmentX(CENTER_ALIGNMENT);
        return img;
    }

    private void confirmQuitButtonAction(){
        int result = JOptionPane.showConfirmDialog(this, "Want to quit?", "Confirm Quit", JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION){
                System.exit(0);
            }
    }

    private void menuButtonAction(){
        HelpMenu helpMenu = new HelpMenu(this);
        helpMenu.setVisible(true);
    }
    
    public void addCreateTableButtonListener(ActionListener listener) {
        createTableButton.addActionListener(listener);
    }
    
    public String getDataType(){
        return (String)dataTypeSelectionField.getSelectedItem();
    }

    public String getCollisionResolver(){
        return (String)collisionResolverSelectionField.getSelectedItem();
    }

}
