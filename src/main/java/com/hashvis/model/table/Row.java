package com.hashvis.model.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Row {

  public enum RowState {
    NORMAL,
    SELECTED,
    POSTSELECTED,
  }

  public interface RowListener {
    void stateChanged(RowState state);

    void itemAdded(Item item);

    void itemRemoved(Item item);
  }

  private final int index;
  private final List<Item> items = new ArrayList<>();

  // Highlight state for the visualizer.
  private RowState state = RowState.NORMAL;

  // Cursor: index of the item currently being inspected by the algorithm.
  // -1 means "no item selected yet"; items.size() means "walked off the end".
  private int currentItem = -1;

  private RowListener listener;

  public Row(int index) {
    if (index < 0)
      throw new IllegalArgumentException("Row index must be non-negative: " + index);
    this.index = index;
  }

  public void setListener(RowListener listener) {
    this.listener = listener;
  }

  public Item nextItem() {
    if (items.isEmpty())
      return null;
    if (currentItem == items.size())
      return null;
    if (currentItem != -1)
      items.get(currentItem).unchoose();
    if (++currentItem == items.size())
      return null;
    Item item = items.get(currentItem);
    item.choose();
    return item;
  }

  public void addItem(String name) {
    Item item = new Item(name);
    items.add(item);
    if (listener != null)
      listener.itemAdded(item);
    if (currentItem != -1 && currentItem < items.size() - 1)
      items.get(currentItem).unchoose();
    currentItem = items.size() - 1;
    item.choose();
  }

  public void removeItem(Item item) {
    int removedAt = items.indexOf(item);
    if (removedAt < 0)
      return;
    items.remove(removedAt);
    if (listener != null)
      listener.itemRemoved(item);

    // Keep the cursor pointing at the same logical position after removal.
    if (currentItem > removedAt) {
      currentItem--;
    } else if (currentItem == removedAt && currentItem >= items.size()) {
      currentItem = items.size();
    }
  }

  public List<Item> getItems() {
    return Collections.unmodifiableList(items);
  }

  public void choose() {
    state = RowState.SELECTED;
    if (currentItem != -1 && currentItem < items.size())
      items.get(currentItem).choose();
    if (listener != null)
      listener.stateChanged(state);
  }

  public void unchoose() {
    state = RowState.POSTSELECTED;
    if (currentItem != -1 && currentItem < items.size())
      items.get(currentItem).unchoose();
    if (listener != null)
      listener.stateChanged(state);
  }

  public void reset() {
    state = RowState.NORMAL;
    currentItem = -1;
    for (Item item : items)
      item.reset();
    if (listener != null)
      listener.stateChanged(state);
  }

  public int getIndex() {
    return index;
  }
}
