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
        return "home";
    }

    @RequestMapping(value = "/home")
    public String getHomepage2(){
        return "home";
    }


    @RequestMapping("/hostName")
//    @ResponseBody
    public String getByHostName(ModelMap model){            // Originally planned on being a metric, but removed due to already having 3
        // Provides most common host names
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
    public String mainMetrics(ModelMap model){      // Provides all of the data for graphs
        List<ListingObject> listingList = listingRep.findByValid(true);         // List of all valid listings

        // Deliverable 1: 1: Bar chart of average price per room type
        Map<roomType, Double> distribution11 = new HashMap<>();
        Map<roomType, Integer> frequency = new HashMap<>();
        distribution11.put(roomType.ENTIRE, 0.0);           // Three entries in map, prices
        distribution11.put(roomType.PRIVATE, 0.0);
        distribution11.put(roomType.SHARED, 0.0);

        frequency.put(roomType.ENTIRE, 0);          // Three entries in map, frequency
        frequency.put(roomType.PRIVATE, 0);
        frequency.put(roomType.SHARED, 0);


        for (ListingObject l : listingList){            // Adds data into the maps
            distribution11.put(l.getR(),distribution11.get(l.getR())+l.getPrice());
            frequency.put(l.getR(),frequency.get(l.getR())+1);
        }

        // Averages the prices out by frequency, finds the "mean"
        distribution11.put(roomType.PRIVATE, distribution11.get(roomType.PRIVATE)/frequency.get(roomType.PRIVATE));
        distribution11.put(roomType.ENTIRE, distribution11.get(roomType.ENTIRE)/frequency.get(roomType.ENTIRE));
        distribution11.put(roomType.SHARED, distribution11.get(roomType.SHARED)/frequency.get(roomType.SHARED));


        // Sends data to template
        model.addAttribute("costRoom1", distribution11.get(roomType.PRIVATE));
        model.addAttribute("costRoom2", distribution11.get(roomType.ENTIRE));
        model.addAttribute("costRoom3", distribution11.get(roomType.SHARED));


        // Deliverable 1 : 2: Pie chart of percentage distribution of room types
        Map<roomType, Double> distribution12 = new HashMap<>();
        distribution12.put(roomType.ENTIRE, 0.0);       // Hash map of room types, frequency
        distribution12.put(roomType.PRIVATE, 0.0);
        distribution12.put(roomType.SHARED, 0.0);

        for (ListingObject l : listingList){            //  Goes through listings, adds into counter map entry
            distribution12.put(l.getR(),distribution12.get(l.getR())+1);
        }

        double sum = listingList.size();        // Averaging out over the total number of entries
        distribution12.put(roomType.PRIVATE, distribution12.get(roomType.PRIVATE)/sum);
        distribution12.put(roomType.ENTIRE, distribution12.get(roomType.ENTIRE)/sum);
        distribution12.put(roomType.SHARED, distribution12.get(roomType.SHARED)/sum);

        // Sends data to template
        model.addAttribute("percentRoom1", distribution12.get(roomType.PRIVATE)*100);
        model.addAttribute("percentRoom2", distribution12.get(roomType.ENTIRE)*100);
        model.addAttribute("percentRoom3", distribution12.get(roomType.SHARED)*100);

        // Deliverable 1 : 3: Most expensive neighborhoods, bar graph
        Map<neighborhood,Double> priceAggregate = new HashMap<>();      // Combined prices map in neighborhood
        Map<neighborhood, Integer> frequency13 = new HashMap<>();       // Frequency map for neighborhoods

        for (ListingObject l : listingList){        // Goes through all listings
            if (priceAggregate.containsKey(l.getN())){      // Adds data to pre-existing map entry if available
                priceAggregate.put(l.getN(),priceAggregate.get(l.getN())+l.getPrice());
                frequency13.put(l.getN(),frequency13.get(l.getN())+1);
            } else {                                        // Else creates entry with data
                priceAggregate.put(l.getN(),l.getPrice());
                frequency13.put(l.getN(),1);
            }
        }

        for (Map.Entry<neighborhood, Double> p : priceAggregate.entrySet()){            // For each neighborhood it sends data to template
            priceAggregate.put(p.getKey(),p.getValue()/frequency13.get(p.getKey()));
            model.addAttribute("n1"+p.getKey().toString(), p.getValue());
        }

        return "metrics";
    }

    @GetMapping("/price")           // Form page without output
    public String price(){
        return "price";
    }

    @PostMapping("/priceDisplay")           // Form page with output, for deliverable 2
    public String estimatedPrice(HttpServletRequest request, ModelMap model){
        Double lat = Double.valueOf(request.getParameter("lat"));       // Reads from form
        Double lon = Double.valueOf(request.getParameter("long"));
        double margin = .1;         // Radius in terms of miles to start searching
        margin /= 69.0;       // conversion from lat/long to miles

        List<ListingObject> listings = listingRep.findByValid(true);

        double sumPrice = 0, sumAvail60 = 0;
        int frequency = 0;
        while (frequency < 30) {            // Considers at least 30 listings before outputting final estimate to give a nice average
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
        // Sends to template the final output
        model.addAttribute("estimation",Math.round((sumPrice/frequency) * (sumAvail60/frequency)*100)/100);
        return "priceDisplay";
    }

    @GetMapping("/bookings")        // Bookings form for deliverable 3, no output
    public String getBookings(){
        return "bookings";
    }

    @PostMapping("/bookingsDisplay")        // Bookings form for deliverable 3 with output
    public String idealPrice(HttpServletRequest request, ModelMap model){
        Double lat = Double.valueOf(request.getParameter("lat"));       // Takes in parameters
        Double lon = Double.valueOf(request.getParameter("long"));

        double margin = .1;         // Radius in terms of miles
        margin /= 69.0;       // conversion from lat/long to miles

        List<ListingObject> listings = listingRep.findByValid(true);

        double sumPrice = 0;
        int frequency = 0;
        while (frequency < 30) {        // Considers at least 30 listings in final average to ensure a nice average
            sumPrice = 0;
            frequency = 0;
            for (ListingObject place : listings) {
                double latDiff = lat - place.getLatitude();
                double lonDiff = lon - place.getLongitude();
                double distance = Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);         // Calculates distance from listing and inputted coords

                if (distance < margin) {                     // If listing is within bounds established
                    sumPrice += place.getPrice();               // Adds the listing to the data
                    frequency++;
                }
            }
            //System.out.println(sumPrice + " " + frequency);
            margin *= 2;            // Doubles margin of error until finds at least 30 listings to make an accurate judgement off
            // Typically only one pass through is enough to satisfy 30 listing requirement, occasionally two pass throughs
        }
        model.addAttribute("estimation", (Math.round((sumPrice/frequency)*100)/100));       // Sends data to template
        return "bookingsDisplay";
    }

    @GetMapping("/other")           // Bonus deliverable: most popular neighborhood
    public String popularNeighborhoods(ModelMap model){
        List<ListingObject> listingList = listingRep.findByValid(true);         // Takes in all valid neighborhoods
        Map<neighborhood, Double> reviewAggregate = new HashMap<>();            // Sets up two maps
        Map<neighborhood, Integer> frequency = new HashMap<>();

        for (ListingObject l : listingList){        // For each valid listing, adds review to respective neighborhood map entry
            if (reviewAggregate.containsKey(l.getN())){
                reviewAggregate.put(l.getN(),reviewAggregate.get(l.getN())+l.getReviewScore());
                frequency.put(l.getN(),frequency.get(l.getN())+1);
            } else {
                reviewAggregate.put(l.getN(),(double)l.getReviewScore());
                frequency.put(l.getN(),1);
            }
        }

        for (Map.Entry<neighborhood, Double> p : reviewAggregate.entrySet()){
            reviewAggregate.put(p.getKey(),p.getValue()/frequency.get(p.getKey()));             // Averages the review scores
        }

        Comparator<neighborhood> valueComparator = new Comparator<neighborhood>(){          // Sort from highest to lowest
            public int compare(neighborhood n1, neighborhood n2){
                int compare = reviewAggregate.get(n2).compareTo(reviewAggregate.get(n1));
                return compare == 0 ? 1 : compare;
            }
        };

        TreeMap<neighborhood, Double> sortedByValues = new TreeMap<neighborhood, Double>(valueComparator);
        sortedByValues.putAll(reviewAggregate);

        model.addAttribute("neighborhood", sortedByValues.firstKey().getName());            // Returns the first entry, or the best neighborhood
        model.addAttribute("score", sortedByValues.firstEntry().getValue());

        return "other";
    }



}

