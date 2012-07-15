MAX_KEY_SIZE = 26

def getMode():
	while(True):
		mode = input("Encrypt or decrypt? ").lower()
		if(mode in "encrypt e decrypt d".split()):
			return mode
		else:
			print("Enter something valid, dude.")
			
def getMessage():
	return input("Enter your message: ")
	
def getKey():
	key = 0
	while(True):
		key = int(input("Enter the key number (1 - %s) " %(MAX_KEY_SIZE)))
		if((key >= 1) and (key <= MAX_KEY_SIZE)):
			return key
			
def getTranslatedMessage(mode, message, key):
	if(mode [0] == "d"):
		key = -key
	translated = ""
	
	for symbol in message:
		if(symbol.isalpha()):
			num = ord(symbol)
			num += key
			
			if(symbol.isupper()):
				if(num > ord("Z")):
					num -= 26
				elif(num < ord("A")):
					num += 26
			elif(symbol.islower()):
				if(num > ord("z")):
					num -= 26
				elif(num < ord("a")):
					num += 26
			
			translated += chr(num)
		else:
			translated += symbol
	return translated




mode = getMode()
message = getMessage()
key = getKey()

print("Your translated text is:")
print(getTranslatedMessage(mode, message, key))