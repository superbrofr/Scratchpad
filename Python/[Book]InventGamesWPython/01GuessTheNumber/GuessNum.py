import random

guessCnt = 0

myName = input("What's your name? ")

number = random.randint(1, 20)
print("Well, " + myName + ", I am thinking of a number between 1 and 20.")

while(guessCnt < 6):
	guess = int(input("Take a guess: "))
	guessCnt += 1
	
	if(guess < number):
		print("Your guess is too low.")
	elif(guess > number):
		print("Your guess is too high.")
	elif(guess == number):
		break
		
if(guess == number):
	print("Nice! You guessed it in " + str(guessCnt) + " guesses!")
elif(guess != number):
	print("Bummer, you didn't get it. I was thinking of " + str(number) + "...")