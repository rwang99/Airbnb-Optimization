# Airbnb-Optimization

<h2> See it live: https://airbnb-rwang.herokuapp.com/home.html </h2>

Web app, UI, and functionality made by Richard Wang for the Capital One MindSumo Challenge (https://www.mindsumo.com/contests/airbnb-sf)

<h3> Personal Note </h3>
This project took three weeks to complete, and was my first experience with frameworks, web apps, data storage, html, and css whatsoever. Many long hours were put into just learning the different frameworks, languages, and concepts required for this project and it was a very laboring yet rewarding experience. I hope you enjoy this as much as I did seeing the final product develop! 

<h3> About the program </h3>
This program takes in Airbnb public data and displays metrics, and has functions outputting a recommended dollar amounts given the prompt and a latitude/longitude. 

<b>Home:</b>
![Image](https://i.imgur.com/E0xwYR8.jpg)

<b>Metrics:</b>
![Metrics Image](https://i.imgur.com/emsL29O.jpg)

1. Bar graph of average price of a room per listing type
2. Pie chart of distribution of listing types throughout total listings
3. Bar graph comparing the average prices in each neighborhood
	- Some labels may be hidden due to sizing. Hover over each bar to show data!

<b>Functions:</b>
1. Price Estimator
![Price Estimator](https://i.imgur.com/tOs3HT9.jpg)
	- Takes in latitude and longitudinal coordinates, and produces an estimated dollar amount of the money you can make in a week at that location.
	- How it works:
		- Using coordinates and given coordinates of all listings, the program starts with a radius of 0.1 miles, and evaluates properties within that radius. If fewer than 30 properties were selected (to produce an accurate yet local average), then the radius doubles and the program restarts. Typically, this condition will be met within the first or second try if the location picked is in the city. It then computes the average price and cleaning fee of those properties, and multiples the result by the average number of days they are booked per week. The program then displays the final value.

2. Bookings Optimization
![Bookings](https://i.imgur.com/EP6riQK.jpg)
	- Takes in latitude and longitudinal coordinates, produces a dollar value of what we think a property should be listed at to maximize profits. 
	- How it works:
		- Using coordinates and given coordinates of all listings, the program starts with a radius of 0.1 miles, and evaluates properties within that radius. If fewer than 30 properties were selected (to produce an accurate yet local average), then the radius doubles and the program restarts. Typically, this condition will be met within the first or second try if the location picked is in the city. It then computes the average price of those properties, and displays the value. 

3. Bonus features
![Bonus](https://i.imgur.com/fbDSs6v.jpg)
	- The most positively rated neighborhood
		- Located under the "/other" tab, averages the review score of properties within each neighborhood, and stores values into a TreeMap. Returns the first value in the sorted map.
	- Visualization animations
		- Uses Google Charts API and their animations option.

<b>Other:</b>
Pre-processing of data was done to remove unnecessary data and to filter out erroneous data. To conserve memory, many columns that were irrelevant were discarded. Listings with 0 reviews or extreme outliers were discounted from data processing to provide accurate results and estimations without erronous data or outliers. Furthermore, in the review score and number of reviews columns, blank spaces were replaced with "-1" to signify a listing to be ignored through the csv reader program. Using excel's cell formatting feature, dollar signs were removed from the price cells to speed up reader processing later on.

Data is first parsed through the CSVReader class, which utilizes Apache Commons CSVReader. This parses each listing into a listing object. Listings with values of -1 from pre-processing were assigned a boolean value "valid" to false, to remove erroneous listings or listings that may not have been "proven" to be effective. Even with the data removed, there is still plenty of data to produce accurate averages and results. After the CSVReader parses the .csv file, it is passed to DBSeeder.java that sends the listing objects to the MongoDB database.

Back-end uses Spring framework and Java. Mongodb was used for storing and querying data. Thymeleaf was used for templating, while HTMl, CSS, and Bootstrap aided in front-end design and functionality. Google Charts API was used to display metrics. No external templates were used for the UI. Heroku with addon MongoLabs was used for deploying to https://airbnb-rwang.herokuapp.com/home.html.

Dependencies required:
	- Spring boot starter thymeleaf
	- Spring boot
	- Spring boot starter data mongodb
	- Mongodb java driver
	- Apache commons csv
	- Spring data mongodb parent

Background image credit: by Christopher Harris on Unsplash (https://unsplash.com/photos/bJqeJxeyiJE)

