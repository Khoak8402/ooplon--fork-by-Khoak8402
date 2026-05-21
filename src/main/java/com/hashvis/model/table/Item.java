package com.hashvis.model.table;

import java.util.Objects;

public class Item {

  public enum ItemState {
    // NORMAL is the default state. It can transition to SELECTED, POSTSELECTED, or GHOSTED.
    NORMAL,
    // SELECTED is a transient state: it can transition to POSTSELECTED, or GHOSTED.
    SELECTED,
    // POSTSELECTED is a transient state: it can transition to SELECTED, or GHOSTED.
    POSTSELECTED,
    // GHOSTED is a permanent state: it cannot transition to any other state.
    GHOSTED
  }

  public interface ItemListener {
    void stateChanged(ItemState state);
  }

  private final String name;
  private ItemState state = ItemState.NORMAL;
  private ItemListener listener;

  public Item(String name) {
    Objects.requireNonNull(name, "Item name must not be null");
    if (name.isEmpty())
      throw new IllegalArgumentException("Item name must not be empty");
    this.name = name;
  }

  public void setListener(ItemListener listener) {
    this.listener = listener;
  }

  private void notifyListener() {
    if (listener != null)
      listener.stateChanged(state);
  }

  public void ghost() {
    state = ItemState.GHOSTED;
    notifyListener();
  }

  public void choose() {
    if (state == ItemState.GHOSTED)
      return;
    state = ItemState.SELECTED;
    notifyListener();
  }

  public void unchoose() {
    if (state == ItemState.GHOSTED || state == ItemState.NORMAL)
      return;
    state = ItemState.POSTSELECTED;
    notifyListener();
  }

  public void reset() {
    if (state == ItemState.GHOSTED)
      return;
    state = ItemState.NORMAL;
    notifyListener();
  }

  public boolean isGhosted() {
    return state == ItemState.GHOSTED;
  }

  public String getName() {
    return name;
  }
}
