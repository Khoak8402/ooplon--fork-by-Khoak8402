package com.hashvis.view.ui.component;

import javax.swing.*;
import java.awt.*;


public class SelectionBoxComponent extends JPanel {
    private JLabel descriptionLabel;
    private JComboBox<String> selectionList;

    public SelectionBoxComponent(String description, Dimension size){
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //setAlignmentX(orient);

        descriptionLabel = new JLabel(description);
        add(descriptionLabel);

        selectionList = new JComboBox<>();
        add(selectionList);

        add(Box.createHorizontalGlue());

        setPreferredSize(size);
    }

    public SelectionBoxComponent(String description, String[] options, Dimension size){
        this(description, size);
        setupSelectionList(options);
    }

    public void setupSelectionList(String[] options){
        selectionList.removeAllItems();
        for (String option : options) {
            selectionList.addItem(option);
        }
    }

    public String getSelectedOption(){
        return (String)selectionList.getSelectedItem();
    }
    
}
