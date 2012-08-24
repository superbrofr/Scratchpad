number = raw_input("Number: ")
number = float(number)

if((number % 2) == 0):
	print number, "is even."
elif((number % 2) == 1):
	print number, "is odd."
else:
	print "What the?"