/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.averageloser.mongodemo.Model;

import com.mongodb.Block;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.Set;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author tj
 */
public class BookDBHelper extends DBHelper<Book> {

    private static final DBHelper INSTANCE = new BookDBHelper();

    //prevent instantiation.
    private BookDBHelper() {
    }

    //I do not want multiple instances of this class.
    public static synchronized DBHelper getInstance() {
        return INSTANCE;
    }

    //Utility method to return document with book details.
    private Document getBookDocument(Book book) {
        Document document = new Document();
        document.append("title", book.getTitle())
                .append("description", book.getDescription())
                .append("author", book.getAuthor())
                .append("publisher", book.getPublisher());

        return document;
    }

    @Override
    public boolean insertDocument(MongoCollection<Document> collection, Book object) {
        Document document = getBookDocument(object);

        try {
            collection.insertOne(document);

            return true;
        } catch (MongoWriteException | MongoWriteConcernException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean removeDocument(MongoCollection<Document> collection, Book object) {
        Document doc = this.getBookDocument(object);
        
        collection.deleteOne(doc);
        
        return false;
    }

    @Override
    public Book getDocument(MongoCollection<Document> collection, Bson object) {
        Book book = new Book();
        
        Document doc = (Document) object;
        
        FindIterable<Document> iterable = collection.find(doc);
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document t) {
                book.setTitle(t.getString("title"));
                book.setDescription(t.getString("description"));
                book.setAuthor(t.getString("author"));
                book.setPublisher(t.getString("publisher"));
            }
            
        });
        
        return book;
    }

}
