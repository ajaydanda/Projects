> theta_estimator = function(n,theta){	# Function to calculate estimator MLE & MOME
+   distribution= runif(n,min=0,max=theta)
+   samples=runif(n,min = 0,max = theta)	# Ensuring that the  samples remains same
+   theta1_estimator = max(samples)	# MLE Estimator
+   theta2_estimator= 2*mean(samples)	# MOME Estimator
# returning the pair of values theta1 and theta 2 computed from the same sample set
+   answer = c(theta1=theta1_estimator,theta2=theta2_estimator) 
+   return (answer)
+ }

> mse_estimator = function(n,theta){
+   r=replicate(1000,theta_estimator(n,theta))
+   theta1_value=r[1,]		#theta 1 parameters are returned
+   theta2_value=r[2,]		#theta 2 parameters are returned
+   mse1=mean((theta1_value-theta)^2)
+   mse2=mean((theta2_value-theta)^2)
+   answer = c(mse_theta1=mse1,mse_theta2=mse2)
+   return (answer)
+ }

# Plotting the MSE vs Theta Graph for n and θ
> mse_plot = function(n, height){
+   cumulative_answers_trials1 = c()
+   for (theta_given in c(1,5,50,100)){
+     cumulative_answers_trials1 = c(cumulative_answers_trials1,mse_estimator(n,theta_given))
+   }
+   theta = c(1,5,50,100)
+   mse.theta1 = cumulative_answers_trials1[c(TRUE,FALSE)]
+   mse.theta2 = cumulative_answers_trials1[c(FALSE,TRUE)]
+   plot(x=theta,y=mse.theta2,type="b",pch=18, col="blue",lty=2,ylab="Mean Squared Error",xlab="Theta")
+   lines(x=theta,y=mse.theta1,type="b",pch=19,col="red")
+   legend(1, height, legend=c("MSE(Theta_1)", "MSE(Theta_2)"),col=c("red", "blue"), lty=1:2, cex=0.8)
+ }




> dataset = c(21.72,14.65,50.42,28.78,11.23) 	# The given Data set
> negative_log_estimator = function(theta,data){	# Maximum Likelihood Function
+   answer = sum(log(theta*data^(-theta-1)))
+   return (-answer)
+ }
> mle = optim(par=1.041,fn=negative_log_estimator,method="BFGS",hessian=T, data=dataset)
> mle
$par
[1] 0.3233876

$value
[1] 26.10585

$counts
function gradient 
      22        9 

$convergence
[1] 0

> standard_error = sqrt(diag(solve(mle$hessian))) 	# Calculating Standard Error
> standard_error
[1] 0.1446219

> confidence_interval = function(SE,n,alpha){	# Confidence Interval Function
+   d = dataset
+   ci=mle$par+c(-1,1)*qnorm(1-(alpha/2))*SE
+   return (ci)
+ }
> a=confidence_interval(SE=standard_error,n=length(dataset),alpha=0.05)
> a
[1] 0.03993379 0.60684138

> qqnorm(dataset)				
> qqline(dataset)
