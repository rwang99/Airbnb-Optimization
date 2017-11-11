package com.richardwang.config;

// "C:\Program Files\MongoDB\Server\3.4\bin\mongod.exe" to run mongo

import com.richardwang.dao.ListingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class DbSeeder implements CommandLineRunner{

    private ListingRepository listingRep;

    public DbSeeder(ListingRepository listingRep){
        this.listingRep = listingRep;
    }

    @Override
    public void run(String... strings) throws Exception {

        // UNCOMMENT BELOW TWO LINES TO REFRESH/RESEED DATA


        listingRep.deleteAll();
        listingRep.save(CSVreader.readCSV());




    }


}
