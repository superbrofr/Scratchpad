a = 1
s = 0

print "Enter numbers - 0 to quit: "

while(a != 0):
	print "Current sum:", s
	a = float(raw_input("Number: "))
	s += a

print "Total sum:", s