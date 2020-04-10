> data = read.csv("roadrace.csv",sep=",")
> Maine = table(data$Maine)
> barplot(Maine)
> summary(data$Maine)
 	Away Maine 
 	1417  4458 

> data = read.csv("roadrace.csv",sep=",")
> dataMaine<-data[which(data$Maine=='Maine'),]
> hist(dataMaine$Time..minutes.,ylim=c(0,5000))
> hist(dataMaine$Time..minutes.,ylim=c(0,2000))
> View(dataMaine)
> dataAway<-data[which(data$Maine=='Away'),]
> hist(dataAway$Time..minutes.,ylim=c(0,2000))
> hist(dataMaine$Time..minutes.,xlim=c(0,160),ylim=c(0,5000))
> hist(dataMaine$Time..minutes.,xlim=c(0,160),ylim=c(0,2000))
> hist(dataMaine$Time..minutes.,xlim=c(0,160),ylim=c(0,2000))
> hist(dataAway$Time..minutes.,xlim=c(0,160),ylim=c(0,2000))
> summary(dataMaine$Time..minutes.)
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
  30.57   50.00   57.03   58.20   64.24  152.17 
> summary(dataAway$Time..minutes.)
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
  27.78   49.15   56.92   57.82   64.83  133.71 
> IQR(dataMaine$Time..minutes.)
[1] 14.24775
> IQR(dataAway$Time..minutes.)
[1] 15.674
> range(dataMaine$Time..minutes.)
[1]  30.567 152.167
> range(dataAway$Time..minutes.)
[1]  27.782 133.710



> setwd("C:/Users/Satyum/Desktop/Statistics for Data Science/Project 2")
> data=read.csv("roadrace.csv",sep=",")
> boxplot(data$Time..minutes.~ data$Maine, main="Side by Side Boxplots", col="yellow")



> setwd("C:/Users/Satyum/Desktop/Statistics for Data Science/Project 2")
> data=read.csv("roadrace.csv",sep=",")
> boxplot(data$Age~ data$Sex, main="Side by Side Boxplots Age and Sex", col="red")

> summary(dataMale$Age)
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
   9.00   30.00   41.00   40.45   51.00   83.00 
> IQR(dataMale$Age)
[1] 21
> range(dataMale$Age)
[1]  9 83


> summary(dataFemale$Age)
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
   7.00   28.00   36.00   37.24   46.00   86.00



range(dataFemale$Age)
[1]  7 86
> IQR(dataFemale$Age)
[1] 18

> setwd("D:/Ajay/Ajay MSCS Semester 1/SMDS/Project 2")
> data=read.csv("motorcycle.csv", sep = ',')
> boxplot(data$Fatal.Motorcycle.Accidents, col = 'yellow')
> summary(data$Fatal.Motorcycle.Accidents)
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
   0.00    6.00   13.50   17.02   23.00   60.00 
> IQR(data$Fatal.Motorcycle.Accidents)
[1] 17
> hist(data$Fatal.Motorcycle.Accidents, xlab='Fatal Accidents', main='Histogram of Fatal Motorcycle Accidents', col = 'yellow')
