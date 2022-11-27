package com.example.listary.interfaces;

import com.example.listary.model.Product;

public interface DatabaseInterface <ModelClass> {

     void sendDataToDatabase(ModelClass object, String userId);

     void updateDataToDatabase(ModelClass object, String userId, String documentId);

}
