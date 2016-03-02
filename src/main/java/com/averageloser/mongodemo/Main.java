/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.averageloser.mongodemo;

import com.averageloser.mongodemo.Model.Book;
import com.averageloser.mongodemo.Model.BookDBHelper;
import com.averageloser.mongodemo.Model.DBHelper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author tj
 */
public class Main {

    public static void main(String[] args) {
        List<Book> books = new ArrayList();
        for (int i = 0; i < 3; i++) {
            Book book = new Book();
            book.setTitle(i + " title");
            book.setDescription(i + " description");
            book.setAuthor(i + " author");
            book.setPublisher(i + " publisher");

            books.add(book);
        }

        DBHelper helper = BookDBHelper.getInstance();

        MongoClient client = helper.getMongoClient();

        //System.out.println("\n" + client.toString());
        MongoCollection<Document> booksCollection = helper.getMongoCollection(
                client.getDatabase("items"), "books");

        System.out.println(booksCollection.count());

        //Add documents to the books collection.
        helper.insertDocument(booksCollection, books.get(0));
        helper.insertDocument(booksCollection, books.get(1));
        helper.insertDocument(booksCollection, books.get(2));

        //Print size of the collection.
        System.out.println(booksCollection.count());

        //Grab the book from the database by its title.
        String query = "0 title";

        Book book = (Book) helper.getDocument(booksCollection, new Document("title", query));

        //Print first book details.
        System.out.println(book.toString());

        //Remove the third book from the collection and verify that it was deleted, printing collection size.
        helper.removeDocument(booksCollection, books.get(2));
        
        System.out.println(booksCollection.count());
    }
}
