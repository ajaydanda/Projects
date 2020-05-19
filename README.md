# Projects
### 1. Hotel Management System  
The project is an effort to provide a one-step solution to the customer with a system that can provide them with information regarding availability of rooms in multiple hotels at the same place. The system provides a way to make room bookings for the customers. The availability of the hotel rooms can only be updated by an authorized hotel personnel, and hence it becomes easy for the Customer to select their room from the available rooms. We have implemented Agile Methodology while designing the system. The system is implemented with an Iterative Approach where each iteration of the project helps to accommodate any new features or requirements that are analyzed in the later stages of the SDLC. The overall design of the system was improved by implementing Gang of Four(GoF) patterns like Adapter, Strategy, Controller, Abstract Factory and Singleton. These patterns were applied so that the system has high cohesion modules, low coupling and should be easy to maintain and understand. 

### 2. Intruder Detection Using Face Identification  
This system includes a camera, which captures live images and detects any intrusion in the room in which it is installed. This camera is connected to a system, which already stores the facial features of the Admin and its family members or office employees. The face of the intruder is extracted from the captured images and video. If this face matches the existing faces in the database then no action takes place; however, if the face does not match, an alarm rings and an SMS is sent to the Admin indicating intrusion. 
The dataset.zip file has the stored dataset which was used to train the system. 

### 3. Emergency SOS  Mobile Application
This is an android based application that is capable of locating the mobile user on Maps and send their exact location to listed emergency contacts via an SMS.
The entire project was build using Android Studio.
The App implements Google Maps API for retrieving location information of the user.

### 4. Foodify Website  
The website dynamically asks the user to register. The database of the website holds all the client details. After registering, it enables the user to explore the food hubs in detail.
The website also holds reviews for each and every food hub which have been submitted by our users. These reviews are visible to all users registered with us.

### 5. Sentiment Analysis of Twitter Streaming Data using Apache Kafka
The key idea of the project was to implement a framework that performs sentiment analysis of particular hashtags from twitter data in real-time. The five major components of the framework are the Scrapper, Kafka, Sentiment Analyzer, ElasticSearch and Kibana. The Scrapper collected the real-time streaming data from Twitter with particular hashtags and send them to Kafka. There was a topic already created in Kafka for data transport. A third party Sentiment Analyzer, vaderSentiment from NLTK was used to determine whether tweet is positive, negative or neutral. ElasticSearch was used to store the tweets and their sentiment classification. Kibana was the visualization tool used to explore and visualize the data stored in ElasticSearch in a real-time manner.

### 6. Social Network Analysis using Hadoop MapReduce and Apache Spark  
#### Hadoop MapReduce  
Implemented a Mutual/Common List of friends for any given pair of friends given as input to the MapReduce Java program. The program will find a list of common friends between them. Implemented 2 Map and 2 Reduce tasks to perform In-Memory Join between the two datasets to retrieve names and birthdates of mutual/common friends for any given pair of friends as input. Implemented 2 Map and 2 Reduce tasks to perform Reduce-side Join and Job chaining to calculate maximum age of direct friends of any given user and perform reduce-side join to retrieve their address and maximum age.  
#### Apache Spark  
Implemented a Mutual/Common List of friends for all pairs of friends in Scala using Apache Spark. Implemented Top10 list of friend pairs having the maximum number of mutual friends between them and performed join of two data sets for better representation of data in Scala using Apache Spark.  

### 7. YELP Statistics Derivation using Apache Spark  
Derived statistics from Yelp Dataset by creating dataframes of required attributes from each dataset and performing join operations between them. Performed select(), groupBy(), agg(), sort(), filter() operations on them to retrieve required statistics.    

### 8. Statistics for Data Science using R
#### Probability Estimation using Monte Carlo Simulation  
Estimated the probability of lifetime of a satellite using Monte Carlo Simulation with 10,000 replications on different values of N=1,000, 10,000, 100,000 using R programming. Estimated value of π based on 10,000 replications using the Monte Carlo Approach using R programming.  
#### Data Visualization and Interpretation  
Visualized data from motorcycle.csv and roadrace.csv in the form of histograms, bar graphs and side-by-side boxplots in R programing. Interpreted this visualized data to draw conclusions from the data.  
#### Parameter Estimation using MOM and MLE  
Estimated parameter θ of a Uniform population on a random sample from the population using the Maximum Likelihood Estimator (MLE) and Methods of Moments (MOM) with approximated 95% confidence interval (CI) for θ and compared results obtained from both estimators in R programming. Computed standard error for both estimators to draw conclusions.   
#### Bootstrap and Normal Approximation  
Estimated parameter ρ using Non-parametric bootstrap and computed 95% CI of ρ using Percentile Bootstrap from data gpa.csv and vapor.csv. Normally approximated parameter θ using Satterthwaite’s approximation assuming unequal variances from data voltage.csv in R programming  
#### Parametric Bootstrap and Welch Two Sample  
Calculated coverage probabilities of Z for 95% CI and Parametric Bootstrap to find value of (n, λ) to compute how large n should be for the mean of an exponential population to be accurate in R programming. Performed Welch Two Sample Test to conclude relationship between body temperature and heartrate and their dependence on gender from data bodytemp-heartrate.csv.  
#### Multiple Regression  
Designed a multiple regression model from the data prostate_cancer.csv consisting of both qualitative and quantitative features in data. Performed dimensionality reduction and feature scaling on data by visualizing each feature and extracting important features from the data. Built a model to predict PSA level of patient given its cancervol, gleason and vesinv features using R programming.  
