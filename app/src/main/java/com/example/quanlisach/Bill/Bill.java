package com.example.quanlisach.Bill;

public class Bill {
    private String Buyer;
    private long ID_Book;
    private long ID_bill;
    private String Seller;
    private long Total_price;
    private String Name_book;
    private String Image;

    public Bill() {
    }

    public Bill(String buyer, long ID_Book, long ID_bill, String seller, long total_price, String Name_book, String image) {
        Buyer = buyer;
        this.ID_Book = ID_Book;
        this.ID_bill = ID_bill;
        Seller = seller;
        Total_price = total_price;
        Name_book = Name_book;
        Image = image;
    }

    public String getBuyer() {
        return Buyer;
    }

    public void setBuyer(String buyer) {
        Buyer = buyer;
    }

    public long getID_Book() {
        return ID_Book;
    }

    public void setID_Book(long ID_Book) {
        this.ID_Book = ID_Book;
    }

    public long getID_bill() {
        return ID_bill;
    }

    public void setID_bill(long ID_bill) {
        this.ID_bill = ID_bill;
    }

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String seller) {
        Seller = seller;
    }

    public long getTotal_price() {
        return Total_price;
    }

    public void setTotal_price(long total_price) {
        Total_price = total_price;
    }

    public String getName_Book() {
        return Name_book;
    }

    public void setName_Book(String Name_book) {
        Name_book = Name_book;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
