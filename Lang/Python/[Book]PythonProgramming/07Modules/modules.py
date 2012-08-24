# USING IMPORT STATEMENTS
# import time

# def main():
	# currTime = time.localtime()
	# print "Year:", currTime[0]
	
# if(__name__ == '__main__'):
	# main()
	
# alternatively
# import time as t
# def main():
	# currTime = t.localtime()
	# print "Year:", currTime[0]
	
# if(__name__ == '__main__'):
	# main()
	
# OR, if you need just one function from a module
# from time import localtime
# def main():
	# currTime = localtime()
	# print currTime[0]
	
# if(__name__ == '__main__'):
	# main()
	
# alternatively
from time import localtime as lt
def main():
	currTime = lt()
	print currTime[0]
	
if(__name__ == '__main__'):
	main()