package com.example.listary.interfaces;


public interface DatabaseInterface <ModelClass> {

     void sendDataToDatabase(ModelClass object, String userId);

     void updateDataToDatabase(ModelClass object, String userId, String documentId);

}
