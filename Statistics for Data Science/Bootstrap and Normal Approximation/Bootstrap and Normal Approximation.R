> ques1=read.csv("gpa.csv", header=TRUE, sep = ",") #Reading csv file
> gpaofstudents=as.numeric(as.character((ques1[,1])))
> actofstudents=as.numeric(as.character((ques1[,2])))

#Plotting Scatter plot of GPA vs ACT of students, and trying to fit a line.  
> plot(x=actofstudents,y=gpaofstudents,main="Scatter plot of GPA vs ACT")
> abline(lm(gpaofstudents~actofstudents), col="yellow")
> cor(actofstudents,gpaofstudents) #Generating point estimate from the sample
[1] 0.2694818

> library(boot) #Importing the boot library 
> nonparacor=function(a,index){answer=cor(a[index,1],a[index,2]); return(answer)} # Function to calculate correlation from the sampled distribution

#Generating bootstrap distribution 
> finalanswer=boot(ques1, nonparacor, 10000, sim = "ordinary", stype = "i")
> finalanswer

ORDINARY NONPARAMETRIC BOOTSTRAP


Call:
boot(data = ques1, statistic = nonparacor, R = 10000, sim = "ordinary", 
    stype = "i")


Bootstrap Statistics :
     original      bias    std. error
t1* 0.2694818 0.003487544    0.105556

> mean(finalanswer$t)
[1] 0.2729693

#Percentile confidence interval of bootstrap distribution
boot.ci(boot.out = finalanswer, conf = 0.95, type = "perc")

BOOTSTRAP CONFIDENCE INTERVAL CALCULATIONS
Based on 10000 bootstrap replicates

CALL : 
boot.ci(boot.out = finalanswer, conf = 0.95, type = "perc")

Intervals : 
Level     Percentile     
95%   ( 0.0642,  0.4778 )  
Calculations and Intervals on Original Scale


#Question 2 part a
> fulldata=read.csv('voltage.csv', header = TRUE, sep = ",")#Reading csv file
> reomtedata=(fulldata$location==0)
> remotedata=which(fulldata$location==0) #Check locations of remote site
> remotedata
 [1]  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
[27] 27 28 29 30
> remote=fulldata[1:30, ]#Observed data shows that 1st 30 are remote locations
> local=fulldata[31:60, ] #Next 30 entries will be local locations
> remotevol=remote[,2] #Storing values of voltages of remote locations
> localvol=local[,2] #Storing values of voltages of local locations

> hist(remotevol, main="Histogram of Remote Location Voltages", breaks = 20, xlim = c(8,11), ylim = c(0,6))#Plotting histogram of Remote location voltages
> hist(localvol, main="Histogram of Local Location Voltages", breaks = 20, xlim = c(8,11), ylim = c(0,6)) # Plotting histogram of Local location voltages
> boxplot(localvol,remotevol, main="Side-by-Side Boxplot", names=c("Local Voltages","Remote Voltages"))
#Plotting side-by-side boxplots of the two volatages
> qqnorm(localvol) #Plotting QQ Plot for Local voltages
> qqline(localvol)
> qqnorm(remotevol) #Plotting QQ Plot for Remote Voltages
> qqline(remotevol)
#Summary Statistics to compute difference in Means
> summary(localvol) 
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
  8.510   9.152   9.455   9.422   9.738  10.120 
> summary(remotevol)
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
  8.050   9.800   9.975   9.804  10.050  10.550 
> sd(localvol)
[1] 0.4788757
> sd(remotevol)
[1] 0.5409155

#Question 2 part b
#Using Satterthwaite’s approximation because nx=ny=30 sample and unequal 
variances
>nx=ny=30
v=((sd(remotevol)^2/nx)+((sd(localvol)^2)/ny))^2/((sd(remotevol)^4)/((nx^2)*(nx-1))+(sd(localvol)^4/((ny^2)*(ny-1))))
> v
[1] 57.16003

#Computing 95% confidence interval 
ci=mean(remotevol)-mean(localvol)+c(-1,1)*qt(1-(1-0.95)/2,v)*sqrt((sd(remotevol)^2/nx)+sd(localvol)^2/ny)
> ci
[1] 0.1172284 0.6454382

#Question 3
> fulldata=read.csv("Vapor.csv", header=TRUE, sep=",") #Reading csv file
> tempdata=fulldata[,1]
> theorydata=fulldata[,2] #Storing theoretical observations
> expdata=fulldata[,3] #Storing experimental observations 

#Plotting QQ Plot for Theoretical observations
> qqnorm(theorydata, main="Q-Q Plot of Theoretical Data")
> qqline(theorydata)
# Plotting QQ Plot for Experimental Observations
> qqnorm(expdata, main="Q-Q Plot of Experimental Data")
> qqline(expdata)
> boxplot(theorydata,expdata, main="Side-By-Side Boxplots", names=c("Theoretical", "Experimental")) # Plotting Side by side Boxplots for comparison
> diff=expdata-theorydata #Computing Difference distribution
> diff
 [1] -0.006 -0.007  0.015 -0.014  0.022 -0.008  0.000 -0.002  0.026 -0.029 -0.008
[12]  0.000  0.010 -0.010  0.010 -0.010
> mean(diff)
[1] -0.0006875

#Plotting QQ Plot for Difference Distribution
> qqnorm(diff, main = "Q-Q Plot of Difference") 
> qqline(diff)
> differencemean=function(a,index){answer=mean(a[index); return(answer)} # Function to calculate mean from the resampled diff distribution

# Computing bootstrap distribution 
> bootdistribution=boot(diff,differencemean,1000, sim = "ordinary", stype = "i")
> bootdistribution

ORDINARY NONPARAMETRIC BOOTSTRAP

Call:
boot(data = diff, statistic = differencemean, R = 1000, sim = "ordinary", 
    stype = "i")


Bootstrap Statistics :
      original      bias    std. error
t1* -0.0006875 8.59375e-05 0.003437996

#Computing Confidence Interval
> boot.ci(boot.out = bootdistribution, conf = 0.95, type = "perc")
BOOTSTRAP CONFIDENCE INTERVAL CALCULATIONS
Based on 1000 bootstrap replicates

CALL : 
boot.ci(boot.out = bootdistribution, conf = 0.95, type = "perc")

Intervals : 
Level     Percentile     
95%   (-0.0072,  0.0064 )  
Calculations and Intervals on Original Scale
