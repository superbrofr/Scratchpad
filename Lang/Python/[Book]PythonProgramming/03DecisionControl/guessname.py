guess = raw_input("Guess my name: ")
count = 1

while((count != 3) and (guess != "charlie")):
	guess = raw_input("Guess again: ")
	count += 1
	
if(guess == "charlie"):
	print "Nice work!"
else:
	print "Nope, sorry."