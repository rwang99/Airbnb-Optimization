package com.richardwang.dao;

// "C:\Program Files\MongoDB\Server\3.4\bin\mongod.exe" to run mongo

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

        // UNCOMMENT BELOW TWO LINES TO REFRESH DATA

        //listingRep.deleteAll();
        //listingRep.save(CSVreader.readCSV());



    }


}
