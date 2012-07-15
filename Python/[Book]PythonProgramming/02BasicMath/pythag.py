import math

side1 = float(raw_input("Side 1 Length: "))
side2 = float(raw_input("Side 2 Length: "))

side3 = math.sqrt((side1 ** 2) + (side2 ** 2))

print "Side 3", round(side3, 2)