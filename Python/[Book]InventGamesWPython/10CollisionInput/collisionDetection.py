import pygame, sys, random
from pygame.locals import *

def doRectsOverlap(rect1, rect2):
	for a, b in [(rect1, rect2), (rect2, rect1)]:
		if((isPointInsideRect(a.left, a.top, b)) or
			 (isPointInsideRect(a.left, a.bottom, b)) or
			 (isPointInsideRect(a.right, a.top, b)) or
			 (isPointInsideRect(a.right, a.bottom, b))):
			return True
	return False

def isPointInsideRect(x, y, rect):
	if((x > rect.left) and (x < rect.right) and (y > rect.top) and (y < rect.bottom)):
		return True
	else:
		return False
		
pygame.init()
mainClock = pygame.time.Clock()

WINDOWWIDTH = 400
WINDOWHEIGHT = 400

windowSurface = pygame.display.set_mode((WINDOWWIDTH, WINDOWHEIGHT), 0, 32)
pygame.display.set_caption("Collisions")

DOWNLEFT = 1
DOWNRIGHT = 3
UPLEFT = 7
UPRIGHT = 9

MOVESPEED = 4

BLACK = (0, 0, 0)
GREEN = (0, 255, 0)
WHITE = (255, 255, 255)

foodCounter = 0
NEWFOOD = 40
FOODSIZE = 20
bouncer = {"rect":pygame.Rect(300, 100, 50, 50), "dir":UPLEFT}
foods = []
for i in range(20):
	foods.append(pygame.Rect(random.randint(0, WINDOWWIDTH - FOODSIZE), random.randint(0, WINDOWHEIGHT - FOODSIZE), FOODSIZE, FOODSIZE))
	
while(True):
	for event in pygame.event.get():
		if(event.type == QUIT):
			pygame.quit()
			sys.exit()
		
	# add a new block every 40 frames
	foodCounter += 1
	if(foodCounter >= NEWFOOD):
		foodCounter = 0
		foods.append(pygame.Rect(random.randint(0, WINDOWWIDTH - FOODSIZE), random.randint(0, WINDOWHEIGHT - FOODSIZE), FOODSIZE, FOODSIZE))
		
	windowSurface.fill(BLACK)
	
	if(bouncer["dir"] == DOWNLEFT):
		bouncer["rect"].left -= MOVESPEED
		bouncer["rect"].top += MOVESPEED
	if(bouncer["dir"] == DOWNRIGHT):
		bouncer["rect"].left += MOVESPEED
		bouncer["rect"].top += MOVESPEED
	if(bouncer["dir"] == UPLEFT):
		bouncer["rect"].left -= MOVESPEED
		bouncer["rect"].top -= MOVESPEED
	if(bouncer["dir"] == UPRIGHT):
		bouncer["rect"].left += MOVESPEED
		bouncer["rect"].top -= MOVESPEED
		
	# check bouncer hasn't gone off edges
	if(bouncer["rect"].top < 0):
		if(bouncer["dir"] == UPLEFT):
			bouncer["dir"] = DOWNLEFT
		if(bouncer["dir"] == UPRIGHT):
			bouncer["dir"] = DOWNRIGHT
	if(bouncer["rect"].bottom > WINDOWHEIGHT):
		if(bouncer["dir"] == DOWNLEFT):
			bouncer["dir"] = UPLEFT
		if(bouncer["dir"] == DOWNRIGHT):
			bouncer["dir"] = UPRIGHT
	if(bouncer["rect"].left < 0):
		if(bouncer["dir"] == DOWNLEFT):
			bouncer["dir"] = DOWNRIGHT
		if(bouncer["dir"] == UPLEFT):
			bouncer["dir"] = UPRIGHT
	if(bouncer["rect"].right > WINDOWWIDTH):
		if(bouncer["dir"] == DOWNRIGHT):
			bouncer["dir"] = DOWNLEFT
		if(bouncer["dir"] == UPRIGHT):
			bouncer["dir"] = UPLEFT
			
	pygame.draw.rect(windowSurface, WHITE, bouncer["rect"])
	
	# check for collisions between food and bouncer
	for food in foods[:]: # we iterate over a COPY of the list, because you shouldn't change the list you're iterating over - we use the copied list to reference the original when removing stuff
	# essentially iterates over an anonymous copy of the foods[] list
		if(doRectsOverlap(bouncer["rect"], food)):
			foods.remove(food) # removes from actual list
			
	# draw food
	for i in range(len(foods)):
		pygame.draw.rect(windowSurface, GREEN, foods[i])
		
	pygame.display.update()
	mainClock.tick(40)