package com.richardwang.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Listings")
public class Listing {

    @Id
    private int id;

    private String title, hostName;
    private neighborhood n;
    private roomType r;
    private double latitude, longitude;

    @Indexed(direction = IndexDirection.ASCENDING)
    private double price;

    private int numReviews, reviewScore;
    private double reviewPerMonth;

    protected Listing(){

    }

    public Listing(int id, String title, String hostName, String neighbor, String room,
                   double latitude, double longitude, double price, int numReviews, int reviewScore, double reviewPerMonth){

        this.id = id;
        this.title = title;
        this.hostName = hostName;
        this.n = neighborhood.valueOf(neighbor);
        this.r = roomType.valueOf(room);
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.numReviews = numReviews;
        this.reviewScore = reviewScore;
        this.reviewPerMonth = reviewPerMonth;
    }

    public Listing(int id, String title, String hostName, neighborhood n, roomType r,
                   double latitude, double longitude, double price, int numReviews, int reviewScore, double reviewPerMonth) {
        this.id = id;
        this.title = title;
        this.hostName = hostName;
        this.n = n;
        this.r = r;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.numReviews = numReviews;
        this.reviewScore = reviewScore;
        this.reviewPerMonth = reviewPerMonth;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getHostName() {
        return hostName;
    }

    public neighborhood getN() {
        return n;
    }

    public roomType getR() {
        return r;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getPrice() {
        return price;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public double getReviewPerMonth() {
        return reviewPerMonth;
    }


}
