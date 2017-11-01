package com.richardwang.controller;

import com.richardwang.dao.ListingRepository;
import com.richardwang.model.Listing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
