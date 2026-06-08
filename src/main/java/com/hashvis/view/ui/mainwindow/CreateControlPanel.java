package com.hashvis.view.ui.mainwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import com.codepane.CodePane;
import com.hashvis.model.hashfunc.HashFunction;

import java.util.List;
import java.util.ArrayList;


public class CreateControlPanel extends JPanel {
    private JButton createButton;
    private JTextField tableSizeInputPanel;
    private JPanel hashFunctionInputArea;

    public CreateControlPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel= new JLabel("Create Hash Table");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(titleLabel);

        tableSizeInputPanel= new JTextField();
        tableSizeInputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        tableSizeInputPanel.setAlignmentX(LEFT_ALIGNMENT);
    
        createButton= new JButton("Create Table");
        createButton.setAlignmentX(LEFT_ALIGNMENT);
        createButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        hashFunctionInputArea= new JPanel();
        hashFunctionInputArea.setLayout(new BoxLayout(hashFunctionInputArea, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(10));

        JLabel tableSizeLabel= new JLabel("Table Size:");
        tableSizeLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(tableSizeLabel);
        add(Box.createVerticalStrut(5));
        add(tableSizeInputPanel);
        add(Box.createVerticalStrut(10));

        JLabel hashFunctionLabel= new JLabel("Hash Function:");
        hashFunctionLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(hashFunctionLabel);
        add(Box.createVerticalStrut(5));
        add(hashFunctionInputArea);
        add(Box.createVerticalStrut(10));

        add(createButton);
        add(Box.createVerticalStrut(10));

    }

    public void setUphashFunctionFields(List<HashFunction> hashFunctionFields) {
        hashFunctionInputArea.removeAll();
        int paneNumber= hashFunctionFields.size();
        for(int i=1; i<=paneNumber; i++){
            JPanel hashFuncPanel= new JPanel();
            hashFuncPanel.setLayout(new BoxLayout(hashFuncPanel, BoxLayout.X_AXIS));
            hashFuncPanel.add(new JLabel("h" + i + ":  "));

            hashFuncPanel.add(hashFunctionFields.get(i-1).getView());
            hashFuncPanel.add(Box.createHorizontalStrut(10));

            hashFuncPanel.setAlignmentX(LEFT_ALIGNMENT);
            hashFunctionInputArea.add(hashFuncPanel);
        }
    }

    public void addCreateButtonListener(ActionListener listener){
        createButton.addActionListener(listener);
    }

    public String getTableSize(){
        return tableSizeInputPanel.getText();
    }
    
    public void setEnableInteraction(boolean enable){
        tableSizeInputPanel.setEnabled(enable);
        hashFunctionInputArea.setEnabled(enable);
        createButton.setEnabled(enable);
    }
}
