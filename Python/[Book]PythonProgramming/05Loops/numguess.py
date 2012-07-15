print "Select a number between 100 and 1. After I guess, tell me how I did."
print "	1 = higher"
print "	2 = lower"
print "	3 = right"

guessCnt = 1;
guess = 50;
diff = 25;
response = int(raw_input("Is it " + str(guess) + "? "))
while((response != 3) and (guessCnt != 7)):
	if(response == 1):
		guess += diff
	elif(response == 2):
		guess -= diff
		
	response = int(raw_input("Is it " + str(guess) + "? "))
	guessCnt += 1
	if(diff != 1):
		diff /= 2
	
if(response == 3):
	print "Beat you."
else:
	print "Dammit."