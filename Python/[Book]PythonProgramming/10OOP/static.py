class StaticSpam(object):
	def StaticNoSpam(): # no self-referential argument, because it's static and thus belongs to no specific instance
		print "No spam for you"
	
	NoSpam = staticmethod(StaticNoSpam)
	
StaticSpam.NoSpam()