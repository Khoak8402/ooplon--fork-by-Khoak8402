package com.hashvis.controller;

import java.util.List;
import javax.swing.Timer;

import com.hashvis.view.table.OpenAddressingTableView;
import com.hashvis.view.table.SeparateChainingTableView;
import com.hashvis.view.ui.mainwindow.MainWindow;
import com.hashvis.applicationmanager.ApplicationManager;
import com.hashvis.model.collision.CollisionResolver;
import com.hashvis.model.collision.CollisionResolver.Result;
import com.hashvis.model.hashfunc.HashFunction;
import com.hashvis.model.helper.DataType;
import com.hashvis.model.helper.HashAction;
import com.hashvis.model.table.Table;

/**
 * Coordinates the main hash table visualization window and its sub-panels.
 *
 * Manages the top-level view of the application, including the control panel,
 * the visualizer panel and the pseudocode panel.
 */
public class MainWindowController {
  //View
  private MainWindow view;
  
  //Model
  private Table table; // The hash table
  private CollisionResolver resolver; // The collision resolution strategy
  private DataType dataType; // The type of key data
  List<HashFunction> hashFunctions; // The hash functions
  
  /**
   * Constructs a new main window controller with the specified resolver and
   * key type, and sets up the initial table and sub-controllers.
   *
   * @param resolver    the collision resolution strategy to use
   * @param dataType    the type of key data
   * @param view         the main window view
   * @param appRouting   the application window control interface
   */
  public MainWindowController(CollisionResolver resolver, DataType dataType, MainWindow view, ApplicationManager appRouting) { 
    //Init the controller
    this.view = view;
    this.resolver = resolver;
    this.dataType = dataType;
    this.hashFunctions = resolver.getHashFunctionFields(dataType);

    //Initializing button functionalities
    //Back button -> back to start window
    view.addBackButtonListener(e -> appRouting.openStartWindow());
    //Create button -> create new hash table
    view.addCreateButtonListener(e -> onCreateTableEvent());
    //Run button -> run visualization
    view.addRunButtonListener(e -> onRunEvent());

    //Initialize the hash function code pane and add it to the control panel
    view.setUphashFunctionFields(hashFunctions);

  }

  /**
   * Gets the user input key
   * @return the user input key
   * @throws IllegalArgumentException if the input key is invalid
   * @throws NullPointerException if the input key field is empty
   */
  private String getInputKey(){
     try{
      //Get the input key
      String inputKey = view.getInputKey();
      //Validate it based on datatype
      if(dataType.validate(inputKey))
        return inputKey; //Valid
      else
        //Invalid
        throw new IllegalArgumentException("Invalid input key"); 
      }catch(NullPointerException e){ //If the input field is empty
       throw new IllegalArgumentException("Empty key input field");
     }
  }
  
  /**
   * Gets the table size
   * @return the table size
   * @throws IllegalArgumentException if the table size is invalid
   * @throws NullPointerException if the table size field is empty
   */
  private int getTableSize(){
    try{
      //Get the table size
      return Integer.parseInt(view.getTableSize());
    }catch(NumberFormatException e){ //If the table size is invalid
      throw new IllegalArgumentException("Invalid table size");
    }catch(NullPointerException e){ //If the table size field is empty
      throw new IllegalArgumentException("Empty table size input field");
    }
  }

  /**
   * Gets the table action
   * @return the table action
   */
  private HashAction getAction(){
    //Get the table action
    HashAction ha= HashAction.parse(view.getTableAction());
    //If the action is invalid, it can not be parsed and will be null
    if(ha == null)
      throw new IllegalArgumentException("Invalid table action");
    return ha;
  }

  /**
   * Sets the hash table to a specified table
   * @param table the table
   */
  private void setTable(Table table){
    //Set the table
    this.table = table;
    //Determine the table type and tell view to create corresponding table view
    if(resolver.useSeparateChaining())
      view.setHashTableView(new SeparateChainingTableView(table));
    else
      view.setHashTableView(new OpenAddressingTableView(table));
  }

  /**
   * Specify the create table event triggered by create table button
   */
  private void onCreateTableEvent(){
    try{
     //Reset the table and pseudo code view
     view.resetHashTable();
     view.resetPseudoCode();
     
     //Take the user data and initialize new hash table
     int tableSize = getTableSize();
     resolver.setHashFunctionFields(hashFunctions);

     //Validate the hash functions
     for(HashFunction hf : hashFunctions)
       if(!hf.isValidHashFunction())
        view.alertError("Invalid hash function");

     //Reset the hash function code pane to default, prevent user from changing the data
     hashFunctions = resolver.getHashFunctionFields(dataType);
     view.setUphashFunctionFields(hashFunctions);
     
     //Create the hash table and set it 
     Table table = new Table(tableSize);
     setTable(table);

    }catch(Exception e){
      //If any error happened, alert the user
      view.alertError(e.getMessage());
    }
  }

  /**
   * Specify the run event triggered by run button
   */
  private void onRunEvent(){
    //Freeze the input field and button during visualization process
    view.setEnableInteraction(false);

    try{
      //If the table is not created, alert error
      if(table == null)
        throw new IllegalStateException("Table must be created first");

      //Get the input key and action
      String inputKey = getInputKey();
      HashAction action = getAction();
      
      //Get the pseudo code and tell view to display them
      List<String> pseudoCode = resolver.getAlgorithmAndInitalize(action, inputKey, table);
      view.setPseudoCode(pseudoCode);

      //Reset table effect to default
      table.reset();

      //Play visualize animation
      //The visualization will take 1 step/second
      Timer timer = new Timer(1000, null);
      timer.addActionListener(e -> {
      Result r;
      try {
        //Get the current step
        r = resolver.nextStep();
      } catch (RuntimeException e1) { //if there are none then done, end the visualization process
        r = new Result(e1.getMessage(), -1); //Alert status and terminate the visualization
      }
      view.setRunStatus(r.message());

      //If there are no lines left, stop the visualization
      if(r.currentLine() == -1){
        timer.stop();
        view.setEnableInteraction(true);//Reenable the input field and buttons
      }
    });
     
    timer.start();//Start the visualization
    }catch(Exception e){
      //Any exception happen in the process, terminate it and alert error, along with re-enable everything
      view.alertError(e.getMessage());
      view.setEnableInteraction(true);
    }
  }

  /**
   * Set the view visibility
   * @param visible either true or false
   */
  public void setViewVisible(boolean visible){
    view.setVisible(visible);
  }

}
