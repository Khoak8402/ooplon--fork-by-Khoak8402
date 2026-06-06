package com.hashvis.view.ui.mainwindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;

import com.hashvis.model.hashfunc.HashFunction;
import com.hashvis.view.table.TableView;

import java.util.List;
import com.codepane.CodePane;

/**
 * The main content panel for the hash table visualization workspace.
 *
 * The layout is divided into three functional areas:
 * <ol>
 *   <li><b>Control area</b> (top-left) — buttons for creating tables, entering
 *       keys, and running insert/search/delete operations, rendered via a
 *       sub-panel that the controller can swap out.</li>
 *   <li><b>Pseudocode area</b> (bottom-left) — a {@link HashVisualizerView}
 *       that shows the current algorithm's pseudocode and marks the active
 *       line during execution.</li>
 *   <li><b>Hash table area</b> (right) — a visual representation of the table
 *       rows rendered by a subclass of {@code TableView}.</li>
 * </ol>
 * A Vertical {@code JSplitPane} separates the left panel (controls +
 * pseudocode) from the right panel (table view). The controller calls
 * {@link #replaceControlPanel}, {@link #setHashTableView}, and
 * {@link #setPseudoCodeView} to swap contents dynamically.
 */
public class MainWindow extends JFrame {
  private JPanel controlPanelArea;
  private JPanel hashTableArea;
  private JButton backButton;
  private ControlPanel controlPanel;
  private CreateControlPanel createControlPanel;
  private HashVisualizerView pseudoCodeView;

  /**
   * Constructs the main window layout with a back button, a control panel area,
   * a pseudocode area, and a hash table area arranged in a Vertical split
   * pane.
   */
  public MainWindow() {
    setTitle("Hash Table Visualizer");
    setSize(1300, 650);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // --- Back button (top of the left column) ---
    // The back button returns the user to the type/resolver selection menu.
    // Its width is stretchable (MAX_VALUE) but its height is fixed at 25px
    // so that it occupies minimal Vertical space.
    backButton = new JButton("<- Back");
    backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
    backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    // --- Control panel area (middle of the left column) ---
    // This is the dynamic section of the left column. The controller swaps
    // between the "Create Table" panel (with size/hash function fields) and
    // the "Run" panel (with key input / action selector).
    controlPanelArea = new JPanel(new BorderLayout(10, 10));
    controlPanelArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

    // --- Left column: stacked Vertically using BoxLayout Y_AXIS ---
    // Three sections stacked top-to-bottom: back button, control panel,
    // pseudocode area. The pseudocode area gets extra Vertical space
    // (it is resizable), while the control panel is at its preferred height.
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    leftPanel.add(backButton);
    leftPanel.add(Box.createVerticalStrut(10));

    createControlPanel = new CreateControlPanel();
    createControlPanel.setAlignmentX(CENTER_ALIGNMENT);
    leftPanel.add(createControlPanel);
    leftPanel.add(Box.createVerticalStrut(20));

    controlPanel = new ControlPanel();
    controlPanel.setAlignmentX(CENTER_ALIGNMENT);
    leftPanel.add(controlPanel);
    
    JPanel managePanel = new JPanel(new BorderLayout());
    managePanel.add(leftPanel, BorderLayout.NORTH);

    // --- Hash table area (right side) ---
    // The visual representation of the table, rendered by a TableView
    // subclass (SeparateChainingTableView or OpenAddressingTableView).
    hashTableArea = new JPanel(new BorderLayout());
    hashTableArea.setBorder(new EmptyBorder(10, 10, 10, 10));

    // --- Pseudocode area (bottom of the left column) ---
    // Contains a HashVisualizerView that displays the algorithm's pseudocode
    // with a marker on the current line during step-by-step execution.
    JPanel pseudoCodeArea = new JPanel(new BorderLayout());
    pseudoCodeView = new HashVisualizerView();
    pseudoCodeArea.add(pseudoCodeView, BorderLayout.CENTER);

    // --- Vertical split pane ---
    JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, managePanel, hashTableArea);
    JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, pseudoCodeArea);
    splitPane1.setDividerLocation(300);
    splitPane2.setDividerLocation(750);
    add(splitPane2, BorderLayout.CENTER);

  }

  /**
   * Sets the component displayed in the hash table view area.
   *
   * @param view the component to show in the hash table area
   */
  public void setHashTableView(TableView view) {
    hashTableArea.removeAll();
    hashTableArea.add(view, BorderLayout.CENTER);
    hashTableArea.revalidate();
    hashTableArea.repaint();
  }

  public void addBackButtonListener(ActionListener listener) {
    backButton.addActionListener(listener);
  }

  public void addRunButtonListener(ActionListener listener) {
    controlPanel.addRunButtonListener(listener);
  }

  public void addCreateButtonListener(ActionListener listener) {
    createControlPanel.addCreateButtonListener(listener);
  }

  public String getTableSize() {
    return createControlPanel.getTableSize();
  }

  public List<CodePane> getHashFunctionFields() {
    return createControlPanel.getHashFunctionFields();
  }

  public void setUphashFunctionFields(List<HashFunction> hashFunctions) {
    createControlPanel.setUphashFunctionFields(hashFunctions);
  }

  public String getInputKey() {
    return controlPanel.getInputKey();
  }

  public String getTableAction() {
    return controlPanel.getTableAction();
  }

  public void resetHashTable() {
    hashTableArea.removeAll();
    hashTableArea.revalidate();
    hashTableArea.repaint();
  }

  public void setRunStatus(String status) {
    pseudoCodeView.setStatus(status); 
  } 

  public void setPseudoCode(List<String> lines) {
    pseudoCodeView.setPseudocode(lines);
  }

  public void setCurrentLine(int line) {
    pseudoCodeView.setCurrentLine(line);
  }

  public void resetPseudoCode() {
    pseudoCodeView.clear();
  }

  public void alertError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public void setEnableInteraction(boolean enable) {
    controlPanel.setEnableInteraction(enable);
    createControlPanel.setEnableInteraction(enable);
  }

}
