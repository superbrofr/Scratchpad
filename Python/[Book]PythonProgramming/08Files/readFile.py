inputFileText = open("test.txt").read()
print inputFileText
# or, to open in read only mode
inputFileText = open("test.txt", "r").read()
print inputFileText

# use seek() and tell() to move about the file contents
f = open("test.txt", "r")
print f.tell() # print position in file
print f.read(10) # read the first 10 characters
print f.tell() # print new position (10)

print
f = open("test.txt", "r")
print f.tell()
f.seek(10) # go to 10 characters in
print f.tell()
f.seek(5) # go to 5 characters in
print f.tell()

print f
print "Closing file."
f.close()
print f

print
print "Reading by line"
f = open("test.txt", "r")
for i in f:
	print i,