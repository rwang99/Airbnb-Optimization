package com.richardwang.dao;

import com.richardwang.model.Listing;
import com.richardwang.model.neighborhood;
import com.richardwang.model.roomType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends MongoRepository<Listing,Integer>{

    Listing findById(Integer Id);
    List<Listing> findByPriceLessThan(double priceMax);

    @Query(value = "{n:?0}")
    List<Listing> findByN(neighborhood n);

    @Query(value = "{r:?0}")
    List<Listing> findByR(roomType r);

    @Query(value = "{hostName:?0}")
    List<Listing> findByName(String hostName);

}
