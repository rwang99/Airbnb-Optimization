package com.richardwang.controller;

import com.richardwang.dao.ListingRepository;
import com.richardwang.model.Listing;
import com.richardwang.model.neighborhood;
import com.richardwang.model.roomType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listings")
public class ListingController {
    private ListingRepository listingRep;

    public ListingController(ListingRepository listingRep){
        this.listingRep = listingRep;
    }

    @GetMapping("/all")
    public List<Listing> getAll(){
        List<Listing> listings = this.listingRep.findAll();

        return listings;
    }

    @PutMapping
    public void insert(@RequestBody Listing listing){
        listingRep.insert(listing);
    }

    @PostMapping
    public void update(@RequestBody Listing listing){
        listingRep.save(listing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        listingRep.delete(id);
    }

    @GetMapping("/{id}")
    public Listing getbyID(@PathVariable("id") String id){
        Listing l = listingRep.findById(Integer.valueOf(id));
        return l;
    }

    @GetMapping("/price/{maxPrice}")
    public List<Listing> getByPrice(@PathVariable("maxPrice") double maxPrice){
        List<Listing> l = listingRep.findByPriceLessThan(maxPrice);
        return l;
    }

    @GetMapping("/neighborhood/{nHood}")
    public List<Listing> getByN(@PathVariable("nHood") String nHood){
        List<Listing> l = listingRep.findByN(neighborhood.valueOf(nHood));
        return l;
    }

    @GetMapping("/roomType/{roomT}")
    public List<Listing> getByR(@PathVariable("roomT") String roomT){
        List<Listing> l = listingRep.findByR(roomType.valueOf(roomT));
        return l;
    }
}
