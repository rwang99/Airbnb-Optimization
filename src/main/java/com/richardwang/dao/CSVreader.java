package com.richardwang.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVreader {
    /*public static void main(String[] args) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("data/organizedListings.csv"));
            String line;

            System.out.println(bufferedReader.readLine());
            while ((line = bufferedReader.readLine()) != null) { *//* null marks the end of file *//*
				*//*  We need println as readLine removes the newline *//*
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }*/
}
