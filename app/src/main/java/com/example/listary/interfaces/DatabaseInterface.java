package com.example.listary.interfaces;


public interface DatabaseInterface <ModelClass> {

     void sendDataToDatabase(ModelClass object);

     void updateDataToDatabase(ModelClass object, String documentId);

     void getDataFromDatabase(Callback callback);

}
