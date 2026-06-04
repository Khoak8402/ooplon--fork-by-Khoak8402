package com.hashvis.view.ui.startwindow;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class HelpMenu extends JDialog {
    private JScrollPane projectPurposeScrollPane;
    private JScrollPane hashTableScrollPane;
    private JScrollPane collisionResolutionScrollPane;
    private JScrollPane userInstructionsScrollPane;
    private JButton closeButton;


   public HelpMenu(JFrame parentFrame){
    super(parentFrame, "Help Menu", true);
    setSize(500, 300);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

    JTabbedPane tabbedPane = new JTabbedPane();

    projectPurposeScrollPane = createContentPanel("");

    hashTableScrollPane = createContentPanel("");

    collisionResolutionScrollPane = createContentPanel("");

    userInstructionsScrollPane = createContentPanel("");

    closeButton = new JButton("Close");
    closeButton.setAlignmentX(CENTER_ALIGNMENT);
    closeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e){
        closeOperation();
      }
    });

    tabbedPane.addTab("Project Purpose", projectPurposeScrollPane);
    tabbedPane.addTab("Hash Table", hashTableScrollPane);
    tabbedPane.addTab("Collision Resolution", collisionResolutionScrollPane);
    tabbedPane.addTab("User Instructions", userInstructionsScrollPane);

    add(tabbedPane, BorderLayout.CENTER);

    add(closeButton, BorderLayout.SOUTH);

    setLocationRelativeTo(parentFrame);

   }

   private JScrollPane createContentPanel(String content){
      JTextArea textArea= new JTextArea(content);
      textArea.setEditable(false);
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);
      
      return new JScrollPane(textArea);
   }

    private void closeOperation(){
      this.dispose();
    }
    
}
