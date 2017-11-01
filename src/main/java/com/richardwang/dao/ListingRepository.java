package com.richardwang.dao;

import com.richardwang.model.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends MongoRepository<Listing,Integer>{

}
