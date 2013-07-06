import shutil
# shutil.move("firstLocation.txt", "newlocation.txt")
# shutil.copy("original.txt", "copy.txt")
shutil.copy("test.txt", "test2.txt")

# to perform a recursive copy use copytree()
# shutil.copytree("dir1", "dir2")

# to performa a recursive remove use rmtree()
# shutil.rmtree("someDirectory")

# to remove an individual file use remove()
import os
os.remove("test2.txt")