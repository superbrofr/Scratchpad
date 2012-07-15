class Car:
	def brake(self):
		print("Brakes")
		
	def accelerate(self):
		print("Accellerating")
		
	def setOwner(self, name):
		self._owner = name # a single underscore indicates the variable should be private - doesn't enforce anything, just a note
	
	def getOwner(self):
		return self._owner
	
	# consolidates the getter/setter above so that "car.owner = ..." and "print car.owner" can be called without explicit use of the getter/setter methods
	owner = property(getOwner, setOwner)
		
car1 = Car() # without parentheses, this would just create a copy of the class definition
car2 = Car()
car1.brake()

car1.colour = "Red" # you can set attributes to the class without defining it within the class itself - it's a better idea to define getters and setters to keep things consistent within instances
print car1.colour

# print "Owner:", car1.getOwner() # creates an error - owner hasn't been set yet

car1.setOwner("Me, I own it!")
print "Owner:", car1.getOwner()


# with the one line getter/setter, car.owner can be called, and the getter/setter is called transparently:
car1.owner = "Me!"
print car1.owner


# EXTENDING A CLASS - just pass in the parent class in paretheses in the class definition line
class SecondCar(Car):
	def __init__(self): # constructor header - add extra parentheses as needed
		print "Class constructed!"

	def someMethod(self):
		print "Extended!"