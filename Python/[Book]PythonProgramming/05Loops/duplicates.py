list = [4, 5, 7, 8, 9, 1, 0, 7, 10]
list.sort()
prev = list[0]
del list[0]
for i in list:
	if prev == i:
		print "Duplicate of", prev, "found."
	prev = i