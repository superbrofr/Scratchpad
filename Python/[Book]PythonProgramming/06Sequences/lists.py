h = "hello"
list = ["h", "e", "l", "l", "o"]

print h[0:4] # start at position 0 then print 4 chars

print list[0:4]

print "List:", list
list.append("!")
print "List:", list
list.insert(1, "X")
print "List:", list
del list[1]
print "List:", list

list.sort()
print "List:", list