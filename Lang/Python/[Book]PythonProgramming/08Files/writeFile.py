# overwrite text file
outputFileText = "Here's some text."
open("writeTo.txt", "w").write(outputFileText)

# append to text file
outputFileText = "\nmy appending" # append doesn't add a line break for you
open("writeTo.txt", "a").write(outputFileText)