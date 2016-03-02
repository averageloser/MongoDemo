/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.averageloser.mongodemo.Model;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * 
 * @author tj
 * @param <T> - Type of objects stored in the db. 
 */
public abstract class DBHelper<T> {
    private static MongoClient client;
    
     /* Returns a single mongo client on localhost/default port.
    I cannot stop clients from instantiating MongoClient directly, but only
    one instance of MongoClient should typically exist, so use this.
     */
    public MongoClient getMongoClient() {
        if (client == null) {
            return client = new MongoClient();
        }

        return client;
    }

    /*Returns a collection for this database, if it exists, or null if not.
    Make sure to check if this value is null.
     */
    public MongoCollection<Document> getMongoCollection(MongoDatabase database, String name) {
        return database.getCollection(name);
    }

    /*Inserts a document into the specified collection and returns a result for 
    the operation.
     */
    public abstract boolean insertDocument(MongoCollection<Document> collection, T object);
    
    
    //Returns a document matching the data from this collection.
    public abstract T getDocument(MongoCollection<Document> collection, Bson object);
    
    //Removes a document matching this data from this collection.
    public abstract boolean removeDocument(MongoCollection<Document> collection, T object);
}
