package com.richardwang.config;

import com.richardwang.model.ListingObject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Adapted from https://examples.javacodegeeks.com/core-java/apache/commons/csv-commons/writeread-csv-files-with-apache-commons-csv-example/
 *  Modified to fit specific csv file
 */

@Component
public class CSVreader {            // Reads data from csv file and puts into a list of listing objects

    // CSV header
    private static final String[] HEADER = {"id", "desc", "hostName", "neighborhood", "lat", "lon", "roomType",
            "price", "cleaning", "avail30", "avail60", "avail90", "avail365", "numReviews",
            "reviewScore", "rpm"};

    public static List<ListingObject> readCSV(){
        FileReader fileReader = null;
        CSVParser csvFileParser = null;

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(HEADER);

        List<ListingObject> listings = new ArrayList<>();
        try {       // Opens file
            listings = new ArrayList<>();
            fileReader = new FileReader("data/organizedListings.csv");
            csvFileParser = CSVParser.parse(fileReader, csvFileFormat);
            List csvRecords = csvFileParser.getRecords();

            //Read the CSV file records starting from the second record to skip the header
            for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord record = (CSVRecord)csvRecords.get(i);

                Long id = Long.parseLong(record.get("id"));
                ListingObject l = new ListingObject(id, record.get("desc"), record.get("hostName"),
                        record.get("neighborhood"), Double.parseDouble(record.get("lat")), Double.parseDouble(record.get("lon")),
                        record.get("roomType"), Double.parseDouble(record.get("price")), Double.parseDouble(record.get("cleaning")),
                        Integer.parseInt(record.get("avail30")),Integer.parseInt(record.get("avail60")),
                        Integer.parseInt(record.get("avail90")), Integer.parseInt(record.get("avail365")),
                        Integer.parseInt(record.get("numReviews")),Integer.parseInt(record.get("reviewScore")),
                        Double.parseDouble(record.get("rpm")));         // Parses the line
                listings.add(l);        // Adds as it goes

            }

            System.out.println("Done parsing");
        }

        catch (Exception e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileReader/csvFileParser !!!");
                e.printStackTrace();
            }

        }

        return listings;
    }

}
