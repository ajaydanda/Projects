  probcoverage=function(n,lambda)
    {
    sample1<-rexp(n,lambda)
    actualmean=1/lambda
    estimatedmean=mean(sample1)
    estimatedlambda=1/estimatedmean
    estimatedsd=mean(sample1)
    
    Zci=estimatedmean+c(-1,1)*qnorm(1-(1-.95)/2)*(estimatedsd/sqrt(n))
    
    meanboot=function(n,estimatedlambda){
      bootsample=rexp(n,estimatedlambda)
      meanofbootsample=mean(bootsample)
      return(meanofbootsample)
    }
    
    library(boot)
    bootdist=replicate(999,meanboot(n,estimatedlambda))
    percCI=sort(bootdist)[c(25,975)]
    
    if(Zci[1]<=actualmean && Zci[2]>=actualmean){
      acceptzci=1
      } 
    else{
      acceptzci=0
    }
    if(percCI[1]<=actualmean && percCI[2]>=actualmean){
      acceptCI=1
    } 
    else{
      acceptCI=0
    }
    finalcoverage=c(acceptzci,acceptCI)
    return(finalcoverage)
    
  }
  
   nval=c(5,10,30,100)
  lambdavalues=c(0.01,0.1,1,10)
  for (n in nval) {
    for (lambda in lambdavalues) {
      cat("(n,lambda)",n,lambda)
      coverageprop=replicate(5000, probcoverage(n,lambda))
      cat("\n Coverage probabilities for Z CI and Parametric Bootstrap respectively are:")
      meancoverage=rowMeans(coverageprop)
      cat("\n",meancoverage)
      cat("\n")
      
    }
  }