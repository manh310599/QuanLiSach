
package com.example.quanlisach.Book;

import java.util.HashMap;
import java.util.Map;

public class Book {



    private String Author;
    private String Name_book;
    private String Image;
    private long ID_Book;
    private String Language;
    private long Likes;
    private long Price;
    private String Seller;
    private long remaining_stock;


    public Book() {

    }



    public Book(String Author, String name_book, String image, long ID_Book, String language, long likes, long price, String seller, long remaining_stock) {
        this.Author = Author;
        this.Name_book = name_book;
        this.Image = image;
        this.ID_Book = ID_Book;
        this.Language = language;
        this.Likes = likes;
        this.Price = price;
        this.Seller = seller;
        this.remaining_stock = remaining_stock;

    }



    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getName_book() {
        return Name_book;
    }

    public void setName_book(String name_book) {
        Name_book = name_book;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public long getID_Book() {
        return ID_Book;
    }

    public void setID_Book(long ID_Book) {
        this.ID_Book = ID_Book;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public long getLikes() {
        return Likes;
    }

    public void setLikes(long likes) {
        Likes = likes;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String seller) {
        Seller = seller;
    }

    public long getRemaining_stock() {
        return remaining_stock;
    }

    public void setRemaining_stock(long remaining_stock) {
        this.remaining_stock = remaining_stock;
    }



    public Map<String,Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("name_book",Name_book);
        result.put("price",Price);
        result.put("remaining_stock",remaining_stock);
        return result;
    }
}