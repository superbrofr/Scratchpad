sum = 0.0

count = int(raw_input("How many numbers are you going to enter? "))
current_count = 0

while(current_count < count):
	current_count += 1
	print "Number", current_count
	number = input("Enter a number: ")
	sum += number
	
print "The average is ", round(sum / count, 2)