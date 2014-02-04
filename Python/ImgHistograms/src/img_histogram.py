from matplotlib import pyplot as plt
import numpy as np
import argparse
import cv2

# load image
a = argparse.ArgumentParser()
a.add_argument("-i", "--img", required=True, help="Path to the image")
args = vars(a.parse_args())
image = cv2.imread(args["img"])
cv2.imshow("image", image)

# convert image to grayscale and create histogram
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
cv2.imshow("gray", gray)
hist = cv2.calcHist([gray], [0], None, [256], [0, 256]) # args: image, list of colour channel indexes (just 0 is grayscale), mask, number of histogram 'bins', range of possible pixel values
plt.figure()
plt.title("Grayscale Histogram")
plt.xlabel("Color bin")
plt.ylabel("Num. Pixels")
plt.xlim([0, 256])
plt.plot(hist)

plt.figure()
plt.title("Flattened Colour Histogram")
plt.xlabel("Color bin")
plt.ylabel("Num. Pixels")
features = []
# loop over image channels, create a histogram for each
chans = cv2.split(image) # split image into RGB channels
print 'Chans:', chans
colors = ("b", "g", "r") # reverse because the `split` function reverses them (i.e., RGB to BGR)
for (chan, color) in zip(chans, colors):
	hist = cv2.calcHist([chan], [0], None, [256], [0, 256])
	features.extend(hist)

	plt.plot(hist, color=color)
	plt.xlim([0, 256])

plt.show()
# cv2.waitKey(0)

