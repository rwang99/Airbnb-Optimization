package com.richardwang.dao;

import com.richardwang.model.ListingObject;
import com.richardwang.model.neighborhood;
import com.richardwang.model.roomType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface ListingRepository extends MongoRepository<ListingObject,Integer>{

    ListingObject findById(Integer Id);
    List<ListingObject> findByPriceLessThan(double priceMax);

    @Query(value = "{n:?0}")
    List<ListingObject> findByN(neighborhood n);

    @Query(value = "{r:?0}")
    List<ListingObject> findByR(roomType r);

    @Query(value = "{hostName:?0}")
    List<ListingObject> findByName(String hostName);

    @Query(value = "{valid:?0}")
    List<ListingObject> findByValid(Boolean valid);

//    @Query(value = "{'latitude' : { $gt: ?0+.02, $lt: ?0-.02}, 'longitude' : { $gt: ?1+.02, $lt : ?1-.02}}" )
//    List<ListingObject> find

}
