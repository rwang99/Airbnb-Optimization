# Airbnb-Optimization

Web app and functionality made entirely by Richard Wang for the Capital One MindSumo Challenge (https://www.mindsumo.com/contests/airbnb-sf)
<h3> Personal Note </h3>
This project took two and a half weeks to complete, and was my first experience with frameworks, web apps, data storage, html, and css. Many hours were put into simply learning different languages and concepts just for this project and it was a very laboring yet rewarding experience. I hope you enjoy this as much as I did seeing the final product develop! 

<h3> About the program </h3>
This program takes in Airbnb public data and displays metrics, and has functions outputting a recommended dollar amounts given the prompt and a latitude/longitude. 

<b>Metrics:</b>
1. Bar graph of average price of a room per listing type
2. Pie chart of distribution of listing types throughout total listings
3. Bar graph comparing the average prices in each neighborhood

<b>Functions:</b>
1. Price Estimator
	- Takes in latitude and longitudinal coordinates, and produces an estimated dollar amount of the money you can make in a week at that location.
	- How it works:
		- Using coordinates and given coordinates of all listings, the program starts with a radius of 0.1 miles, and evaluates properties within that radius. If fewer than 30 properties were selected (to produce an accurate yet local average), then the radius doubles and the program restarts. Typically, this condition will be met within the first or second try if the location picked is in the city. It then computes the average price and cleaning fee of those properties, and multiples the result by the average number of days they are booked per week. The program then displays the final value.

2. Bookings Optimization
	- Takes in latitude and longitudinal coordinates, produces a dollar value of what we think a property should be listed at to maximize profits. 
	- How it works:
		- Using coordinates and given coordinates of all listings, the program starts with a radius of 0.1 miles, and evaluates properties within that radius. If fewer than 30 properties were selected (to produce an accurate yet local average), then the radius doubles and the program restarts. Typically, this condition will be met within the first or second try if the location picked is in the city. It then computes the average price of those properties, and displays the value. 

3. Bonus features
	- The most positively rated neighborhood
		- Located under the "/other" tab, averages the review score of properties within each neighborhood, and stores values into a TreeMap. Returns the first value in the sorted map.
	- Visualization animations
		- Uses Google Charts API and their animations option.

Pre-processing of data was done to remove unnecessary data and to filter out erroneous data. To conserve memory, many columns that were irrelevant were discarded. Listings with 0 reviews or extreme outliers were discounted from data processing to provide accurate results and estimations without erronous data or outliers. Furthermore, in the review score and number of reviews columns, blank spaces were replaced with "-1" to signify a listing to be ignored through the csv reader program. Using excel's cell formatting feature, dollar signs were removed from the price cells to speed up reader processing later on.

Data is first parsed through the CSVReader class, which utilizes Apache Commons CSVReader. This parses each listing into a listing object. Listings with values of -1 from pre-processing were assigned a boolean value "valid" to false, to remove erroneous listings or listings that may not have been "proven" to be effective.

Uses Spring framework and Java for data processing and back-end. Mongodb for storing and querying data.
Thymeleaf, HTMl, CSS, and Bootstrap for front-end.

Dependencies:
	- Spring boot starter thymeleaf
	- Spring boot
	- Spring boot starter data mongodb
	- mongodb java driver
	- Apache commons csv
	- spring data mongodb parent

Background image credit: by Christopher Harris on Unsplash (https://unsplash.com/photos/bJqeJxeyiJE)

