package com.hashvis.view.ui.component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.FlowLayout;

public class TextInputComponent extends JPanel {
    private JTextField inputField;

    public TextInputComponent(String description, Dimension size) {
        super(new FlowLayout(FlowLayout.LEFT));
        JLabel inputLabel = new JLabel(description);
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(150, 25));
        add(inputLabel);
        add(inputField);
        setPreferredSize(size);
    }

    public String getInput() {
        return inputField.getText();
    }
}
