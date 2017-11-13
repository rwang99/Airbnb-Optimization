package com.richardwang.controller;

import com.richardwang.dao.ListingRepository;
import com.richardwang.model.ListingObject;
import com.richardwang.model.neighborhood;
import com.richardwang.model.roomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
public class OptimizeController {           // Main controller for algorithms

    @Autowired
    private ListingRepository listingRep;


    @RequestMapping(value = "/")
    public String getHomepage(){
        return "/home";
    }

    @RequestMapping(value = "/home")
    public String getHomepage2(){
        return "/home";
    }


    @RequestMapping("/hostName")
//    @ResponseBody
    public String getByHostName(ModelMap model){
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
        return "metrics";

    }

    @RequestMapping("/metrics")
//    @ResponseBody
    public String mainMetrics(ModelMap model){      // Data for graphs
        List<ListingObject> listingList = listingRep.findByValid(true);         // List of all valid listings

        // Deliverable 1: 1: Bar chart of average price per room type
        Map<roomType, Double> distribution11 = new HashMap<>();
        Map<roomType, Integer> frequency = new HashMap<>();
        distribution11.put(roomType.ENTIRE, 0.0);
        distribution11.put(roomType.PRIVATE, 0.0);
        distribution11.put(roomType.SHARED, 0.0);

        frequency.put(roomType.ENTIRE, 0);
        frequency.put(roomType.PRIVATE, 0);
        frequency.put(roomType.SHARED, 0);


        for (ListingObject l : listingList){
            distribution11.put(l.getR(),distribution11.get(l.getR())+l.getPrice());
            frequency.put(l.getR(),frequency.get(l.getR())+1);
        }

        distribution11.put(roomType.PRIVATE, distribution11.get(roomType.PRIVATE)/frequency.get(roomType.PRIVATE));
        distribution11.put(roomType.ENTIRE, distribution11.get(roomType.ENTIRE)/frequency.get(roomType.ENTIRE));
        distribution11.put(roomType.SHARED, distribution11.get(roomType.SHARED)/frequency.get(roomType.SHARED));

        model.addAttribute("costRoom1", distribution11.get(roomType.PRIVATE));
        model.addAttribute("costRoom2", distribution11.get(roomType.ENTIRE));
        model.addAttribute("costRoom3", distribution11.get(roomType.SHARED));


        // Deliverable 1 : 2: Pie chart of percentage distribution of room types
        Map<roomType, Double> distribution12 = new HashMap<>();
        distribution12.put(roomType.ENTIRE, 0.0);
        distribution12.put(roomType.PRIVATE, 0.0);
        distribution12.put(roomType.SHARED, 0.0);

        for (ListingObject l : listingList){
            distribution12.put(l.getR(),distribution12.get(l.getR())+1);
        }

        double sum = listingList.size();
        distribution12.put(roomType.PRIVATE, distribution12.get(roomType.PRIVATE)/sum);
        distribution12.put(roomType.ENTIRE, distribution12.get(roomType.ENTIRE)/sum);
        distribution12.put(roomType.SHARED, distribution12.get(roomType.SHARED)/sum);

        model.addAttribute("percentRoom1", distribution12.get(roomType.PRIVATE)*100);
        model.addAttribute("percentRoom2", distribution12.get(roomType.ENTIRE)*100);
        model.addAttribute("percentRoom3", distribution12.get(roomType.SHARED)*100);

        // Deliverable 1 : 3: Most expensive neighborhoods

        Map<neighborhood,Double> priceAggregate = new HashMap<>();
        Map<neighborhood, Integer> frequency13 = new HashMap<>();

        for (ListingObject l : listingList){
            if (priceAggregate.containsKey(l.getN())){
                priceAggregate.put(l.getN(),priceAggregate.get(l.getN())+l.getPrice());
                frequency13.put(l.getN(),frequency13.get(l.getN())+1);
            } else {
                priceAggregate.put(l.getN(),l.getPrice());
                frequency13.put(l.getN(),1);
            }
        }

        for (Map.Entry<neighborhood, Double> p : priceAggregate.entrySet()){
            priceAggregate.put(p.getKey(),p.getValue()/frequency13.get(p.getKey()));
            model.addAttribute("n1"+p.getKey().toString(), p.getValue());
        }

        return "/metrics";
    }

    /*@GetMapping("/roomDistribution")
    @ResponseBody
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
    }*/

/*    // Deliverable 1: 3, bar graph
    @GetMapping("/expensiveNeighborhoods")
    @ResponseBody
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
    }*/

    @GetMapping("/price")
    public String price(){
        return "/price";
    }

    @PostMapping("/priceDisplay")
    public String estimatedPrice(HttpServletRequest request, ModelMap model){
        Double lat = Double.valueOf(request.getParameter("lat"));
        Double lon = Double.valueOf(request.getParameter("long"));
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
        model.addAttribute("estimation",Math.round((sumPrice/frequency) * (sumAvail60/frequency)*100)/100);
        return "/priceDisplay";
    }

    @GetMapping("/bookings")
    public String getBookings(){
        return "/bookings";
    }

    @PostMapping("/bookingsDisplay")
    public String idealPrice(HttpServletRequest request, ModelMap model){
        Double lat = Double.valueOf(request.getParameter("lat"));
        Double lon = Double.valueOf(request.getParameter("long"));

        double margin = .1;         // Radius in terms of miles
        margin /= 69.0;       // conversion to lat/ong

        List<ListingObject> listings = listingRep.findByValid(true);

        double sumPrice = 0;
        int frequency = 0;
        while (frequency < 30) {
            sumPrice = 0;
            frequency = 0;
            for (ListingObject place : listings) {
                double latDiff = lat - place.getLatitude();
                double lonDiff = lon - place.getLongitude();
                double distance = Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);         // Calculates distance from listing and inputted coords

                if (distance < margin) {                     // If listing is within bounds
                    sumPrice += place.getPrice();
                    frequency++;
                }
            }
            //System.out.println(sumPrice + " " + frequency);
            margin *= 2;            // Doubles margin of error until finds at least 30 listings to make an accurate judgement off
        }
        model.addAttribute("estimation", (Math.round((sumPrice/frequency)*100)/100));
        return "/bookingsDisplay";
    }

    @GetMapping("/other")
    public String popularNeighborhoods(ModelMap model){
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

        TreeMap<neighborhood, Double> sortedByValues = new TreeMap<neighborhood, Double>(valueComparator);
        sortedByValues.putAll(reviewAggregate);

        model.addAttribute("neighborhood", sortedByValues.firstKey().getName());
        model.addAttribute("score", sortedByValues.firstEntry().getValue());

        return "/other";
    }



}

