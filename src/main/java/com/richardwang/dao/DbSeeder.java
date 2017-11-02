package com.richardwang.dao;

import com.richardwang.dao.ListingRepository;
import com.richardwang.model.Listing;
import com.richardwang.model.neighborhood;
import com.richardwang.model.roomType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner{

    private ListingRepository listingRep;

    public DbSeeder(ListingRepository listingRep){
        this.listingRep = listingRep;
    }

    @Override
    public void run(String... strings) throws Exception {
        Listing l1 = new Listing(9, "Beautiful house", "Richard", neighborhood.BAYVIEW, roomType.ENTIRE,
                -37, 37, 150, 5, 98, .5);
        Listing l2 = new Listing(10, "Stunning house", "Joe", neighborhood.CASTRO, roomType.ENTIRE,
                -37, 38, 200, 5, 95, .8);
        Listing l3 = new Listing(12, "Cabin in the woods", "Jim", neighborhood.BAYVIEW, roomType.ENTIRE,
                -37, 37, 150, 2, 94, .3);


        // drop all listings
        this.listingRep.deleteAll();

        List<Listing> listings = Arrays.asList(l1, l2, l3);
        this.listingRep.save(listings);

    }


}
