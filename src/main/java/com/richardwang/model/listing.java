package com.richardwang.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Listings")
public class Listing {

    @Id
    private long id;

    private String title, hostName;
    private neighborhood n;
    private roomType r;
    private double latitude, longitude;

    @Indexed(direction = IndexDirection.ASCENDING)
    private double price;
    private double cleaning;



    private int avail30, avail60, avail90, avail365;

    private int numReviews, reviewScore;
    private double reviewPerMonth;

    private boolean valid;

    protected Listing(){

    }

    public Listing(long id, String title, String hostName, String neighbor,
                   double latitude, double longitude, String room, double price, double cleaning,
                   int avail30, int avail60, int avail90, int avail365,
                   int numReviews, int reviewScore,
                   double reviewPerMonth){

        this.id = id;
        this.title = title;
        this.hostName = hostName;
        this.n = neighborhood.get(neighbor);
        this.r = roomType.get(room);
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.cleaning = cleaning;
        this.avail30 = avail30;
        this.avail60 = avail60;
        this.avail90 = avail90;
        this.avail365 = avail365;
        this.numReviews = numReviews;
        this.reviewScore = reviewScore;
        this.reviewPerMonth = reviewPerMonth;
        if (reviewScore == -1 || reviewPerMonth == -1){
            this.valid = false;
        } else {
            this.valid = true;
        }
    }

    /*public Listing(long id, String title, String hostName, neighborhood n, roomType r,
                   double latitude, double longitude, double price, double cleaning, int numReviews,
                   int reviewScore, double reviewPerMonth) {
        this.id = id;
        this.title = title;
        this.hostName = hostName;
        this.n = n;
        this.r = r;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.cleaning = cleaning;
        this.numReviews = numReviews;
        this.reviewScore = reviewScore;
        this.reviewPerMonth = reviewPerMonth;
    }*/

    public long getId() {
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

    public double getCleaning() { return cleaning; }

    public int getNumReviews() {
        return numReviews;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public double getReviewPerMonth() {
        return reviewPerMonth;
    }

    public int getAvail30() {
        return avail30;
    }

    public int getAvail60() {
        return avail60;
    }

    public int getAvail90() {
        return avail90;
    }

    public int getAvail365() {
        return avail365;
    }

    public boolean isValid() {
        return valid;
    }


}
