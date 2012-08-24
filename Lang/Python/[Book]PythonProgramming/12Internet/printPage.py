import urllib

pageText = urllib.urlopen("http://www.google.com").read()
print pageText