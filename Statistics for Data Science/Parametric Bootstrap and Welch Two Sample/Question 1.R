#Reading the csv file for computation
> fulldata=read.csv('bodytemp-heartrate.csv', header = TRUE, sep = ",")
> maledata=which(fulldata$gender==1) #Checking which positions are male data
> maledata
 [1]  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
[27] 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52
[53] 53 54 55 56 57 58 59 60 61 62 63 64 65
> maledata=fulldata[1:65, ] #Storing maledata with their bodytemp & heartrate
> femaledata=fulldata[66:130, ] #Similarly storing femaledata
> maletemp=maledata[,1]   #Storing male body temperature data
> femaletemp=femaledata[,1]  #Storing female body temperature data


> hist(femaletemp, main="Histogram of Female Body Temperature", breaks = 20, xlim = c(96,101), ylim = c(0,13)) #Plotting histogram for Female bodytemp
> hist(maletemp, main="Histogram of Male Body Temperature", breaks = 20, xlim = c(96,101), ylim = c(0,13)) #Plotting histogram for Male bodytemp
> qqnorm(maletemp) #Plotting QQ Plot for Male body temperatures
> qqline(maletemp)
> qqnorm(femaletemp) #Plotting QQ Plot for Female body temperatures
> qqline(femaletemp)
> boxplot(maletemp,femaletemp, main="Side by Side Boxplots", names=c('Male','Female')) #Plotting side by side boxplot to understand means
#Computing IQR of both female & male bodytemp to check variance are equal or #not 
> IQR(maletemp) 
[1] 1
> IQR(femaletemp)
[1] 0.8

#Confirming what was obtained from boxplots that there is difference in means
> t.test(maletemp,femaletemp, alternative = "two.sided", conf.level = 0.95, var.equal = FALSE) 

	Welch Two Sample t-test

data:  maletemp and femaletemp
t = -2.2854, df = 127.51, p-value = 0.02394
alternative hypothesis: true difference in means is not equal to 0
95 percent confidence interval:
 -0.53964856 -0.03881298
sample estimates:
mean of x mean of y 
 98.10462  98.39385 

#Part b
> maleheart=maledata[,3] #Storing male heartrate data
> femaleheart=femaledata[,3] #Storing female heartrate data

> hist(femaleheart, main="Histogram of Female Heartrate", breaks = 20, xlim = c(55,92), ylim = c(0,8)) #Plotting histogram for Female heartrates
> hist(maleheart, main="Histogram of Male Heartrate", breaks = 20, xlim = c(55,92), ylim = c(0,8)) #Plotting histogram for Male heartrates
> qqnorm(maleheart) #Plotting QQ Plot for Male heartrates
> qqline(maleheart)
> qqnorm(femaleheart) #Plotting QQ Plot for Female heartrates
> qqline(femaleheart)
> boxplot(maleheart,femaleheart, main="Side by Side Boxplots", names=c('Male','Female')) #Plotting side by side boxplots to understand the means
#Computing IQR of both female and male heartrates to check variance are equal #or not
> IQR(maleheart)
[1] 8
> IQR(femaleheart)
[1] 12
#Confirming what was obtained from boxplots that there is difference in means
> t.test(maleheart,femaleheart, alternative = "two.sided", conf.level = 0.95, var.equal = FALSE)

	Welch Two Sample t-test

data:  maleheart and femaleheart
t = -0.63191, df = 116.7, p-value = 0.5287
alternative hypothesis: true difference in means is not equal to 0
95 percent confidence interval:
 -3.243732  1.674501
sample estimates:
mean of x mean of y 
 73.36923  74.15385 


#Part C

> fulltemp=fulldata[,1] #Storing all the body temperatures
> fullheart=fulldata[,3] #Storing all the heartrates
> cor(fulltemp,fullheart) #Computing correlation b/w the two
[1] 0.2536564

#Plotting Scatterplot to understand the linear relationship b/w them
> plot(fulltemp,fullheart,main = "Scatterplot of Heartrate vs Temperature")
> abline(lm(fullheart~fulltemp))
> cor(maletemp,maleheart) # Correlation b/w male heartrates and bodytemp
[1] 0.1955894

#Plotting scatterplot to understand whether gender affects the relationship
> plot(maletemp,maleheart,main = "Scatterplot of Heartrate vs Temperature for Males")
> abline(lm(maleheart~maletemp))
> cor(femaletemp,femaleheart) #Correlation b/w female heartrates and bodytemp
[1] 0.2869312

#Plotting scatterplot to understand whether gender affects the relationship
> plot(femaletemp,femaleheart,main = "Scatterplot of Heartrate vs Temperature for Females")
> abline(lm(femaleheart~femaletemp))
