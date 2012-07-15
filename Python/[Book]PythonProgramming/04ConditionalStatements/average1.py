count = 0
sum = 0.0
number = 1.0

print "Enter 0 to stop inputting numbers."

while(number != 0):
	number = float(raw_input("Number: "))
	if(number != 0):
		count += 1
		sum += number
		
print "The average is", round(sum / count, 2)