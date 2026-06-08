package com.hashvis.view.ui.mainwindow;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private String[] tableActions = {"Insert", "Search", "Delete"};
    private JTextField inputKeyField;
    private JComboBox<String> tableActionSelectBox;
    private JButton runButton;


    public ControlPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel= new JLabel("Table Operation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(titleLabel);
        add(Box.createVerticalStrut(10));

        inputKeyField = new JTextField();
        inputKeyField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 25));
        inputKeyField.setAlignmentX(LEFT_ALIGNMENT);

        tableActionSelectBox = new JComboBox<String>(tableActions);
        tableActionSelectBox.setPreferredSize(new Dimension(150, 25));
        tableActionSelectBox.setAlignmentX(LEFT_ALIGNMENT);

        runButton = new JButton("Run");
        runButton.setAlignmentX(LEFT_ALIGNMENT);
        runButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel inputKeyLabel = new JLabel("Input Key:");
        inputKeyLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(inputKeyLabel);
        add(Box.createVerticalStrut(5));
        add(inputKeyField);
        add(Box.createVerticalStrut(10));
        
        JLabel actionLabel = new JLabel("Select Action:");
        actionLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(actionLabel);
        add(Box.createVerticalStrut(5));
        add(tableActionSelectBox);
        add(Box.createVerticalStrut(10));
        
        add(runButton);
        add(Box.createVerticalStrut(10));
    }

    public String getTableAction(){
        return tableActionSelectBox.getSelectedItem().toString();
    }

    public String getInputKey(){
        return inputKeyField.getText();
    }

    public void addRunButtonListener(ActionListener listener){
        runButton.addActionListener(listener);
    }

    public void setEnableInteraction(boolean enable){
        inputKeyField.setEnabled(enable);
        tableActionSelectBox.setEnabled(enable);
        runButton.setEnabled(enable);
    }

}