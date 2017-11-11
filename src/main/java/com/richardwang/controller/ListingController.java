package com.richardwang.controller;

import com.richardwang.dao.ListingRepository;
import com.richardwang.model.ListingObject;
import com.richardwang.model.neighborhood;
import com.richardwang.model.roomType;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/listings")
public class ListingController {        // Controller accessing data from database
    @Autowired
    private ListingRepository listingRep;

    public ListingController(ListingRepository listingRep){
        this.listingRep = listingRep;
    }

    // Returns all listings
    @GetMapping("/all")
    public List<ListingObject> getAll(){
        List<ListingObject> listings = this.listingRep.findAll();
        return listings;
    }

    @PutMapping
    public void insert(@RequestBody ListingObject listing){
        listingRep.insert(listing);
    }

    @PostMapping
    public void update(@RequestBody ListingObject listing){
        listingRep.save(listing);
    }

    // Delete listing from db
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        listingRep.delete(id);
    }

    // Returns list of all listings with a certain id
    @GetMapping("/{id}")
    public ListingObject getbyID(@PathVariable("id") String id){
        ListingObject l = listingRep.findById(Integer.valueOf(id));
        return l;
    }

    // Returns list of all listings given max price
    @GetMapping("/price/{maxPrice}")
    public List<ListingObject> getByPrice(@PathVariable("maxPrice") double maxPrice){
        List<ListingObject> l = listingRep.findByPriceLessThan(maxPrice);
        return l;
    }

    // Returns list of all listings located in neighborhood specified
    @GetMapping("/neighborhood/{nHood}")
    public List<ListingObject> getByN(@PathVariable("nHood") String nHood){
        List<ListingObject> l = listingRep.findByN(neighborhood.valueOf(nHood));
        return l;
    }

    // Returns list of all listings with room type specified
    @GetMapping("/roomType/{roomT}")
    public List<ListingObject> getByR(@PathVariable("roomT") String roomT){
        List<ListingObject> l = listingRep.findByR(roomType.valueOf(roomT));
        return l;
    }

    // Returns list of all valid listings
    @GetMapping("/isValid/{valid}")
    public List<ListingObject> getByValid(@PathVariable("valid") String valid){
        List<ListingObject> l = listingRep.findByValid(Boolean.valueOf(valid));
        return l;
    }


}
