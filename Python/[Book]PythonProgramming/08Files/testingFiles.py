# test if something exists
import os
print os.path.exists("test.txt")
print os.path.exists("sldkfjs.txt")

# on windows, the backslashes in the path will escape the next character
# use two backslashes (escape the escape), or use 'raw' - e.g. os.path.exists(r"C:\Something\somethingelse")

# os also has stuff like isfile(), isdir(), ismount(), islink() and realpath()
print "Real Path:", os.path.realpath("test.txt")