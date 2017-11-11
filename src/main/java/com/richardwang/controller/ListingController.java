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
public class ListingController {
    @Autowired
    private ListingRepository listingRep;

    public ListingController(ListingRepository listingRep){
        this.listingRep = listingRep;
    }

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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        listingRep.delete(id);
    }

    @GetMapping("/{id}")
    public ListingObject getbyID(@PathVariable("id") String id){
        ListingObject l = listingRep.findById(Integer.valueOf(id));
        return l;
    }

    @GetMapping("/price/{maxPrice}")
    public List<ListingObject> getByPrice(@PathVariable("maxPrice") double maxPrice){
        List<ListingObject> l = listingRep.findByPriceLessThan(maxPrice);
        return l;
    }

    @GetMapping("/neighborhood/{nHood}")
    public List<ListingObject> getByN(@PathVariable("nHood") String nHood){
        List<ListingObject> l = listingRep.findByN(neighborhood.valueOf(nHood));
        return l;
    }

    @GetMapping("/roomType/{roomT}")
    public List<ListingObject> getByR(@PathVariable("roomT") String roomT){
        List<ListingObject> l = listingRep.findByR(roomType.valueOf(roomT));
        return l;
    }

    @GetMapping("/isValid/{valid}")
    public List<ListingObject> getByValid(@PathVariable("valid") String valid){
        List<ListingObject> l = listingRep.findByValid(Boolean.valueOf(valid));
        return l;
    }

  /*  // Deliverable 1: 1
    @GetMapping("/hostName/{name}")
    public Map<String, Double> getByHostName(@PathVariable("name") String name){
        List<ListingObject> listingList = listingRep.findByValid(true);

        Map<String, Double> money = new TreeMap<>();
        Map<String, Integer> frequency = new HashMap<>();
        for (ListingObject l : listingList){
            if (money.containsKey(l.getHostName())){
                money.put(l.getHostName(),money.get(l.getHostName())+l.getPrice());
                frequency.put(l.getHostName(),frequency.get(l.getHostName())+1);
            } else {
                money.put(l.getHostName(),l.getPrice());
                frequency.put(l.getHostName(),1);
            }
        }

        for (Map.Entry<String, Double> m : money.entrySet()){
            money.put(m.getKey(),m.getValue()/frequency.get(m.getKey()));
        }

        return money;

    }

    // Deliverable 1: 2, pie chart
    @GetMapping("/costRoomType")
    public Map<roomType, Double> costRoomType(){
        List<ListingObject> listingList = listingRep.findByValid(true);

        Map<roomType, Double> distribution = new HashMap<>();
        Map<roomType, Integer> frequency = new HashMap<>();
        distribution.put(roomType.ENTIRE, 0.0);
        distribution.put(roomType.PRIVATE, 0.0);
        distribution.put(roomType.SHARED, 0.0);

        frequency.put(roomType.ENTIRE, 0);
        frequency.put(roomType.PRIVATE, 0);
        frequency.put(roomType.SHARED, 0);


        for (ListingObject l : listingList){
            distribution.put(l.getR(),distribution.get(l.getR())+l.getPrice());
            frequency.put(l.getR(),frequency.get(l.getR())+1);
        }

        distribution.put(roomType.PRIVATE, distribution.get(roomType.PRIVATE)/frequency.get(roomType.PRIVATE));
        distribution.put(roomType.ENTIRE, distribution.get(roomType.ENTIRE)/frequency.get(roomType.ENTIRE));
        distribution.put(roomType.SHARED, distribution.get(roomType.SHARED)/frequency.get(roomType.SHARED));

        return distribution;
    }

    @GetMapping("/roomDistribution")
    public Map<roomType, Double> roomDistribution(){
        List<ListingObject> listingList = listingRep.findByValid(true);

        Map<roomType, Double> distribution = new HashMap<>();
        distribution.put(roomType.ENTIRE, 0.0);
        distribution.put(roomType.PRIVATE, 0.0);
        distribution.put(roomType.SHARED, 0.0);

        for (ListingObject l : listingList){
            distribution.put(l.getR(),distribution.get(l.getR())+1);
        }

        double sum = listingList.size();
        distribution.put(roomType.PRIVATE, distribution.get(roomType.PRIVATE)/sum);
        distribution.put(roomType.ENTIRE, distribution.get(roomType.ENTIRE)/sum);
        distribution.put(roomType.SHARED, distribution.get(roomType.SHARED)/sum);

        return distribution;
    }

    // Deliverable 1: 3, bar graph
    @GetMapping("/expensiveNeighborhoods")
    public Map<neighborhood, Double> expensiveNeighborhoods(){
        List<ListingObject> listingList = listingRep.findByValid(true);
        Map<neighborhood,Double> priceAggregate = new HashMap<>();
        Map<neighborhood, Integer> frequency = new HashMap<>();

        for (ListingObject l : listingList){
            if (priceAggregate.containsKey(l.getN())){
                priceAggregate.put(l.getN(),priceAggregate.get(l.getN())+l.getPrice());
                frequency.put(l.getN(),frequency.get(l.getN())+1);
            } else {
                priceAggregate.put(l.getN(),l.getPrice());
                frequency.put(l.getN(),1);
            }
        }

        for (Map.Entry<neighborhood, Double> p : priceAggregate.entrySet()){
            priceAggregate.put(p.getKey(),p.getValue()/frequency.get(p.getKey()));
        }

        Comparator<neighborhood> valueComparator = new Comparator<neighborhood>(){
            public int compare(neighborhood n1, neighborhood n2){
                int compare = priceAggregate.get(n2).compareTo(priceAggregate.get(n1));
                return compare == 0 ? 1 : compare;
            }
        };

        Map<neighborhood, Double> sortedByValues = new TreeMap<neighborhood, Double>(valueComparator);
        sortedByValues.putAll(priceAggregate);
        return sortedByValues;
    }

    @GetMapping("/estimatedPrice/{lat}/{lon}")
    public double estimatedPrice(@PathVariable double lat, @PathVariable double lon){
        double margin = .1;         // Radius in terms of miles
        margin /= 69.0;       // conversion

        List<ListingObject> listings = listingRep.findByValid(true);

        double sumPrice = 0, sumAvail60 = 0;
        int frequency = 0;
        while (frequency < 30) {
            sumPrice = 0;
            sumAvail60 = 0;
            frequency = 0;
            for (ListingObject place : listings) {
                double latDiff = lat - place.getLatitude();
                double lonDiff = lon - place.getLongitude();
                double distance = Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);         // Calculates distance from listing and inputted coords

                if (distance < margin) {                     // If listing is within bounds
                    sumPrice += place.getPrice() + place.getCleaning();
                    sumAvail60 += place.getAvail60()*7/60.0;
                    frequency++;
                }
            }
            //System.out.println(sumPrice + " " + frequency);
            margin *= 2;            // Doubles margin of error until finds at least 30 listings to make an accurate judgement off
        }
        return (sumPrice/frequency) * (sumAvail60/frequency);
    }

    @GetMapping("/idealPrice/{lat}/{lon}")
    public double idealPrice(@PathVariable double lat, @PathVariable double lon){
        double margin = .1;         // Radius in terms of miles
        margin /= 69.0;       // conversion to lat/ong

        List<ListingObject> listings = listingRep.findByValid(true);

        double sumPrice = 0, sumAvail60 = 0;
        int frequency = 0;
        while (frequency < 30) {
            sumPrice = 0;
            sumAvail60 = 0;
            frequency = 0;
            for (ListingObject place : listings) {
                double latDiff = lat - place.getLatitude();
                double lonDiff = lon - place.getLongitude();
                double distance = Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);         // Calculates distance from listing and inputted coords

                if (distance < margin) {                     // If listing is within bounds
                    sumPrice += place.getPrice() + place.getCleaning();
                    sumAvail60 += place.getAvail60()/60.0;
                    frequency++;
                }
            }
            //System.out.println(sumPrice + " " + frequency);
            margin *= 2;            // Doubles margin of error until finds at least 30 listings to make an accurate judgement off
        }
        return (sumPrice/frequency) * (sumAvail60/frequency);
    }

    @GetMapping("/popularNeighborhoods")
    public Map<neighborhood, Double> popularNeighborhoods(){
        List<ListingObject> listingList = listingRep.findByValid(true);
        Map<neighborhood, Double> reviewAggregate = new HashMap<>();
        Map<neighborhood, Integer> frequency = new HashMap<>();

        for (ListingObject l : listingList){
            if (reviewAggregate.containsKey(l.getN())){
                reviewAggregate.put(l.getN(),reviewAggregate.get(l.getN())+l.getReviewScore());
                frequency.put(l.getN(),frequency.get(l.getN())+1);
            } else {
                reviewAggregate.put(l.getN(),(double)l.getReviewScore());
                frequency.put(l.getN(),1);
            }
        }

        for (Map.Entry<neighborhood, Double> p : reviewAggregate.entrySet()){
            reviewAggregate.put(p.getKey(),p.getValue()/frequency.get(p.getKey()));
        }

        Comparator<neighborhood> valueComparator = new Comparator<neighborhood>(){
            public int compare(neighborhood n1, neighborhood n2){
                int compare = reviewAggregate.get(n2).compareTo(reviewAggregate.get(n1));
                return compare == 0 ? 1 : compare;
            }
        };

        Map<neighborhood, Double> sortedByValues = new TreeMap<neighborhood, Double>(valueComparator);
        sortedByValues.putAll(reviewAggregate);
        return sortedByValues;
    }
*/

}
