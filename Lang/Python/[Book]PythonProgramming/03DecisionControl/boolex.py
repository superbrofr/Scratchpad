list = ["Life", "The Universe", "Everything", "Jack", "Jill", "Life", "Jill"]

copy = list[:] # copies the list

copy.sort()
prev = copy[0]
del copy[0]

count = 0

# go through list
while count < len(copy) and copy[count] != prev:
	prev = copy[count]
	count = count + 1
	
if count < len(copy):
	print "First Match:", prev