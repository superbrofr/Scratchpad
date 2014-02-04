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

# create 2D histograms
fig = plt.figure()

# create 2D color histogram for green and blue
ax = fig.add_subplot(131)
hist = cv2.calcHist([chans[0], chans[1]], [0, 1], None, [32, 32], [0, 256, 0, 256])
p = ax.imshow(hist, interpolation="nearest")
ax.set_title("2D Histogram for Green and Blue")
plt.colorbar(p)

# create 2D color histogram for green and red
ax = fig.add_subplot(132)
hist = cv2.calcHist([chans[0], chans[2]], [0, 1], None, [32, 32], [0, 256, 0, 256])
p = ax.imshow(hist, interpolation="nearest")
ax.set_title("2D Histogram for Green and Red")
plt.colorbar(p)

# create 2D color histogram for blue and red
ax = fig.add_subplot(133)
hist = cv2.calcHist([chans[1], chans[2]], [0, 1], None, [32, 32], [0, 256, 0, 256])
p = ax.imshow(hist, interpolation="nearest")
ax.set_title("2D Histogram for Blue and Red")
plt.colorbar(p)

# create 3D histogram
hist = cv2.calcHist([image], [0, 1, 2], None, [8, 8, 8], [0, 256, 0, 256, 0, 256])

plt.show()
# cv2.waitKey(0)

