package com.hashvis.model.collision;

import java.util.List;

import com.hashvis.model.hashfunc.HashFunction;
import com.hashvis.model.table.Table;
import com.hashvis.model.helper.DataType;
import com.hashvis.model.helper.HashAction;

public interface CollisionResolver {

  // message: action message/result to be displayed to the user
  // currentLine: current line of the pseudocode, -1 is visualizing stop signal
  public record Result(String message, int currentLine) {
  }

  // Set up Table's view to be each bucket per row instead of warping
  boolean useSeparateChaining();

  // Return required hash function fields and return them for Views part to show
  // it to
  // user (auto-synced with the model)
  List<HashFunction> getHashFunctionFields(DataType dataType);

  // Set hash function fields
  void setHashFunctionFields(List<HashFunction> fields);

  // Prepare itself for collision resolution visualizing and return some
  // pseudocode
  List<String> getAlgorithmAndInitalize(HashAction action, String key, Table table);

  // Visualize one step of collision resolution
  Result nextStep();
}
