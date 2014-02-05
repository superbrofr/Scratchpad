import numpy as np
import cv2
import argparse
import cPickle
import glob

class RGBHistogram:
	# a histogram can be created for each image, creating a descriptor
	def __init__(self, bins):
		self.bins = bins # a list of 3 integers (number of bins for each of the three colour channels)

	def describe(self, image):
		# create a 3D RGB histogram,
		# normalise (convert from pixel counts to percentages) to remove effects of image size
		hist = cv2.calcHist([image], [0, 1, 2], None, self.bins, [0, 256, 0, 256, 0, 256])
		hist = cv2.normalize(hist)
		return hist.flatten()

class ImgSearch:
	def __init__(self, index):
		self.index = index

	def search(self, queryFeatures):
		results = {}

		for (k, features) in self.index.items():
			# compute and store the chi-squared distance between indexed features and query features
			d = self.chi2_distance(features, queryFeatures)
			results[k] = d

		results = sorted([(v, k) for (k, v) in results.items()])
		return results

	def chi2_distance(self, histA, histB, eps=1e-10):
		# tiny epsilon  value is used to avoid divide by zero errors
		d = 0.5 * np.sum([((a - b) ** 2) / (a + b + eps) for (a, b) in zip(histA, histB)])
		return d

def index():
	index = {} # key = image file name; value = image descriptor (i.e., histogram)

	# index all images
	desc = RGBHistogram([8, 8, 8])
	for path in glob.glob(args["dataset"] + "/*.png"):
		k = path[path.rfind("/") + 1:]
		image = cv2.imread(path)
		features = desc.describe(image)
		index[k] = features

	# write index to disc
	f = open(args["index"], "w")
	f.write(cPickle.dumps(index))
	f.close()


ap = argparse.ArgumentParser()
ap.add_argument("-d", "--dataset", required=True, help="Path to image directory")
ap.add_argument("-i", "--index", required=True, help="Location at which to store/load image index")
args = vars(ap.parse_args())

index()

index = cPickle.loads(open(args["index"]).read())
searcher = ImgSearch(index)

# use each image as a query in turn
for (query, queryFeatures) in index.items():
	results = searcher.search(queryFeatures)

	path = args["dataset"] + "/%s" % (query)
	queryImage = cv2.imread(path)
	cv2.imshow("Query", queryImage)
	print "query: %s " % (query)

	# list top 5 matches
	for j in xrange(0, 5):
		(score, imageName) = results[j]
		path = args["dataset"] + "/%s" % (imageName)
		result = cv2.imread(path)
		print "\t%d. %s : %.3f" % (j + 1, imageName, score)

