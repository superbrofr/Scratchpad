# use input("Message") or raw_input("Message")

# input() expects a python instruction - so everything must be python coded
#	after this the passed instruction is evaluated and the result returned to the script

# raw_input() accepts any type of input and returns a string

# it might be useful to create a class to handle retrieving data from the user

class ainput: # can't use 'input' because it's a function name
	def __init__(self, msg = ""):
		self.data = ""
		if not(msg == ""):
			self.ask(msg)
			
	def ask(self, msg, req = 0):
		if req == 0:
			self.data = raw_input(msg)
		else:
			self.data = raw_inuput(msg + " (Require) ")
			
		if ((req == 1) and (self.data == "")):
			self.ask(msg, req)
			
	def getString(self):
		return self.data
	
	def getInteger(self):
		return int(self.data)
		
	def getNumber(self):
		return float(self.data)
		
		
name = ainput("First name: ").getString()
age = ainput("Age: ").getInteger()
print name, age