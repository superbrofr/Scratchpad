number = 78
guess = 0

while (guess != number):
	guess = int(raw_input("Guess: "))
	if guess > number:
		print "Too high."
	elif guess < number:
		print "Too low."
	else:
		print "Nice!"