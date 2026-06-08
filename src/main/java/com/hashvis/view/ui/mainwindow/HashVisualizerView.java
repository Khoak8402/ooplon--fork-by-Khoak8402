package com.hashvis.view.ui.mainwindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * A panel that displays pseudocode for the currently selected hash table
 * algorithm and highlights the active line during execution.
 */
public class HashVisualizerView extends JPanel {
  /** Index of the currently highlighted line, or -1 for no highlight. */
  private int currentLine = -1;
  /** Labels for each pseudocode line, stored in display order. */
  private List<JLabel> lineLabels = new ArrayList<>();
  /** Custom panel that paints the execution marker in the gutter. */
  private JPanel linePanel;
  /** Status label below the title. */
  private JLabel statusLabel;

  /**
   * Constructs the pseudocode view with a title, a status label, and a
   * scrollable panel for the pseudocode lines.
   */
  public HashVisualizerView() {
    super(new BorderLayout());
    setBorder(new EmptyBorder(5, 5, 5, 5));

    // --- Header: title + status ---
    JPanel titlePanel = new JPanel();
    titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

    JLabel title = new JLabel("Pseudocode");
    title.setFont(new Font("SansSerif", Font.BOLD, 14));
    titlePanel.add(title);

    statusLabel = new JLabel("Status: Waiting...");
    statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
    titlePanel.add(statusLabel);
    add(titlePanel, BorderLayout.NORTH);

    // --- Scrollable pseudocode body ---
    // LinePanel overrides paintComponent to draw the blue triangle marker
    // on the current line. Marker position is computed from the label's
    // bounds, so no re-layout is needed when the current line changes.
    linePanel = new JPanel();
    linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.Y_AXIS));
    add(new JScrollPane(linePanel), BorderLayout.CENTER);
  }

  /**
   * Replaces the displayed pseudocode with the given lines and resets the
   * current line highlight.
   *
   * @param lines the list of pseudocode lines to display
   */
  public void setPseudocode(List<String> lines) {
    currentLine = -1;
    lineLabels.clear();
    linePanel.removeAll();
    for (String line : lines) {
      JLabel label = new JLabel(line);
      label.setFont(new Font("Monospaced", Font.PLAIN, 14));
      lineLabels.add(label);
      linePanel.add(label);
    }
    linePanel.revalidate();
    linePanel.repaint();
  }

  /**
   * Sets the pseudocode line to highlight with a marker. The marker is drawn
   * on the next repaint.
   *
   * @param lineIndex the index of the line to highlight, or -1 to clear the
   *                  highlight
   */


  /**
   * Updates the status text displayed below the title.
   *
   * @param status the new status message to show
   */
  public void setStatus(String status) {
    // Wrap line trick with <html> tag
    statusLabel.setText("<html> Status: " + status + "</html>");
  }

  /**
   * Clears all pseudocode lines, resets the current line highlight, and
   * removes all components from the line panel.
   */
  public void clear() {
    currentLine = -1;
    lineLabels.clear();
    linePanel.removeAll();
    linePanel.revalidate();
    linePanel.repaint();
    //Reset the status
    statusLabel.setText("Status: Waiting...");
  }

}
