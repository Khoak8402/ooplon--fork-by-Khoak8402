package com.hashvis.model.helper;

  public enum HashAction {
    INSERT, SEARCH, DELETE;

    public static HashAction parse(String action){
        for(HashAction ha : HashAction.values()){
            if(ha.name().equalsIgnoreCase(action)){
                return ha;
            }
        }
        return null;
    }
  }
