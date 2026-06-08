package com.hashvis.applicationmanager;

import com.hashvis.view.ui.startwindow.StartWindow;
import com.hashvis.view.ui.mainwindow.MainWindow;
import com.hashvis.controller.MainWindowController;
import com.hashvis.controller.StartWindowController;
import com.hashvis.model.collision.CollisionResolver;
import com.hashvis.model.helper.DataType;

/**
 * Top-level application controller that manages start and main windows, and routes between different views.
 */
public class ApplicationManager{
    private StartWindowController startCtrl;
    private MainWindowController mainCtrl;

    public void launch(){
        openStartWindow();
    }

    public void openStartWindow() {
        //Close the main window if it's open
        if(mainCtrl != null){
            mainCtrl.setViewVisible(false);
            mainCtrl = null;
        }
        
        //Open the start window
        StartWindow newStartWindow = new StartWindow();
        startCtrl = new StartWindowController(newStartWindow, this);
        startCtrl.setViewVisible(true);
    }

    public void openMainWindow() {
        CollisionResolver resolver;
        DataType dataType;

        //Get the collision resolver and data type
        //Close the start window if it's open
        try{
            resolver= startCtrl.getCollisionResolver();
            dataType = startCtrl.getDataType();
            if(resolver == null || dataType == null) 
                throw new IllegalArgumentException("Please select a collision resolver and data type before opening main window");
            startCtrl.setViewVisible(false);
            startCtrl = null;
        }catch(NullPointerException npe){
            throw new IllegalStateException("Start window must be open before opening main window");
        }

        //Open the main window
        MainWindow newMainWindow = new MainWindow();
        mainCtrl = new MainWindowController(resolver, dataType, newMainWindow, this);
        mainCtrl.setViewVisible(true);
    }
    
}
