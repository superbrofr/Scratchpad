import urllib2
url = "http://www.google.com"

try:
	req = urllib2.Request(url)
	response = urllib2.urlopen(req)
	the_page = response.read()
	print the_page
except:
	print "Something is wrong here."