#Setting working directory for easy access
setwd('C:/Users/Ajay/Projects/MiniProject 6')
Alldata<-read.csv('prostate_cancer.csv')

#vesinv is a categorical variable
Alldata$vesinv=factor(Alldata$vesinv)


#psa is the response
#we would like to see which of these variables could be used as accurate predictors for the response

psa=Alldata[,2]
cancervol=Alldata[,3]
weight=Alldata[,4]
age=Alldata[,5]
benpros=Alldata[,6]
vesinv=Alldata[,7]
capspen=Alldata[,8]
gleason=Alldata[,9]

#EXPLORATORY ANALYSIS OF RESPONSE (PSA LEVEL)
hist(psa, xlab="PSA Level",main= "Histogram of PSA Level",breaks=20)
qqnorm(psa)
qqline(psa)
boxplot(psa)
boxplot(log(psa))

#QUANTITATIVE VARIABLES EXPLORATORY ANALYSIS

#scatterplots between psa and other variables:

#scatterplots and correlation between:



#psa and capspen:############################good######################
plot(capspen,psa,main="Scatterplot of PSA level against weight")
abline(lm(psa~capspen))
cor(psa,capspen)


#psa and gleason:############################good##################
plot(gleason,psa,main="Scatterplot of PSA level against weight")
abline(lm(psa~gleason))
cor(psa,gleason)

#Single for loop for histograms
for (j in 1:9) {
  hist(Alldata[,j], xlab=colnames(Alldata)[j],
       main=paste("Histogram of",colnames(Alldata[j])),
       col="lightblue",breaks=20)
}

prostate.cor = cor(Alldata[,2:8])
round(prostate.cor,3)

#We are interested in the first line which is correlation between PSA and other elements

#PSA has stronger correlations with quatitative variables cancervol, capspen, and gleason
#it is strongly correlated to the vesinv qualitative variable as well



#Pairs for all scatterplots
pairs(~psa + cancervol + weight + age + benpros  + capspen + gleason, data = Alldata)
#log PSA
pairs(~psa + cancervol + capspen + gleason, data = Alldata)


#Qualitative variable exploratory analysis : vesinv

#Boxplots

boxplot(psa~vesinv)

#we have decided to use log(psa) as the new transformed response
#We have decided to exclude the following variables as predictors: weight, age and benpros

#Now let us look at the relation between the response and each predictor one by one

#quantitative 
y=log(psa)
#cancervol and response(y)
plot(cancervol,y)
fit1 = lm(y ~ cancervol, data = Alldata)
abline(fit1)

#capspen and response(y)
plot(capspen,y)
fit2 = lm(y ~ capspen, data = Alldata)
abline(fit2)

#gleason and response(y)
plot(gleason,y)
fit3 = lm(y ~ gleason, data = Alldata)
abline(fit3)

#Checking correlations once again with newly transformed response log(psa), out of curiosity to make sure
#no adverse changes has occured
Alldata2=Alldata
Alldata$psa=log(psa)
prostate.cor = cor(Alldata[c(2,3,4,5,6,8,9)])
round(prostate.cor,3)


#qualitative:
boxplot(y~vesinv)

#Building first with quantitative variables and qualitative variable
#First we use all three variables: cancervol, capspen, and gleason
fit4=lm(y~cancervol+capspen+gleason+vesinv)
fit4
#summary of the model
summary(fit4)
#Based on the summary, it seems very clear that capspen is not required for the model
#Let us continue the tests with nested models 

#We know that these three variables have significant correlation with each other so we need to check whether all of these
#are necessary

#Let us reduce the model ,removing capspen 
fit5=lm(y~cancervol+gleason+vesinv)
#removing both capspen and gleason
fit6=lm(y~cancervol+vesinv)

#Now first performing partial F test to check the significance of capspen (fit4, fit5)
anova(fit4,fit5)
#Clearly capspen is not needed and is redundant

#Now let us check if gleason is needed performing partial F test to check the significance of capspen (fit5, fit6)
anova(fit5,fit6)
#It appears that gleason is an important predictor and no statistically singificant evidence against it

#Just for the sake of curiosity, let us test whether the categorical variable vesinv can be ignored
fit7=lm(y~cancervol+gleason)
anova(fit5,fit7)
#Evidence against vesinv is also not strong enough

#Hence we accept fit5 as a preliminary model
summary(fit5)

#Let us check how our fit5 compares with the automatic stepwise model selection procedures based on AIC

# Forward selection based on AIC

fit8.forward <- step(lm(y ~ 1, data = Alldata2), 
                      scope = list(upper = ~cancervol+capspen+gleason+vesinv),
                      direction = "forward")

#Backward elimination based on AIC

fit9.backward <- step(lm(y~cancervol+capspen+gleason+vesinv, data = Alldata2), 
                       scope = list(lower = ~1), direction = "backward")

#Both forward and backward

fit10.both <- step(lm(y ~ 1, data = Alldata2), 
                   scope = list(lower = ~1, upper = ~cancervol+capspen+gleason+vesinv),
                   direction = "both")

#Our preliminary model is exactly the same as those produced by automatic stepwise model selection procedures 
# based on AIC

#Hence we accept our model and perform the diagnostics
#The model selected is: cancervol+gleason+vesinv
#fit5(preliminary model), fit8.forward(Forward selection based on AIC), fit9.backward(Backward elimination based on AIC)
#and fit10.both(forward/backward) all follow this same model

summary(fit5)
#the summary tells us that the intercept is of no particular interest


# residual plot
plot(fitted(fit5), resid(fit5))
abline(h = 0)
#No trend in the residuals

# plot of absolute residuals

plot(fitted(fit5), abs(resid(fit5)))
#Still no trend

# normal QQ plot
qqnorm(resid(fit5))
qqline(resid(fit5))
#The residuals approximate a normal distribution


#All assumptions hold
# This preliminary model passes the diagnostics. So we can take this as our final model.
