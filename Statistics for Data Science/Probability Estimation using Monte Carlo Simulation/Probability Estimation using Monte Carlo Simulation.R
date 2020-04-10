circle = function(long){
  m=rep(0,long)
  count=0
  for(i in 1:long){
    x=runif(2,0,1)
    if(sqrt(x[1]*x[1] + x[2]*x[2]) <= 1){
      count=count+1
    }
    proba=count/i
    piCap = proba*4
    m[i]=piCap
  }
  return(m)
}
n=10000
sim=circle(n)
initial = 1
plot(sim[initial:n],type="l")
lines(rep(pi,n)[initial:n],col="red")


