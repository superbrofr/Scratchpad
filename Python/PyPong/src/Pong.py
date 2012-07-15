import pygame, sys, time, random
from pygame.locals import *
from Constants import *

# Setup
pygame.init()
clock = pygame.time.Clock()

mainWin = pygame.display.set_mode((WINDOWWIDTH, WINDOWHEIGHT), 0, 32)
pygame.display.set_caption("PyPong")

player1 = {"rect":pygame.Rect(BORDERL + PADDLEINDENT, BORDERT, PADDLEWIDTH, PADDLEHEIGHT), "colour":WHITE, "dir":MNO, "score":0}
player2 = {"rect":pygame.Rect(BORDERR - PADDLEINDENT - PADDLEWIDTH, BORDERT, PADDLEWIDTH, PADDLEHEIGHT), "colour":WHITE, "dir":MNO, "score":0}
ball = {"rect":pygame.Rect(300, 400, BALLSIZE, BALLSIZE), "colour":WHITE, "xmov":-1, "ymov":-1}

def drawPlayer(player):
  pygame.draw.rect(mainWin, player["colour"], player["rect"])

def drawPlayers():
  drawPlayer(player1)
  drawPlayer(player2)

def drawball(ball):
  pygame.draw.rect(mainWin, ball["colour"], ball["rect"])

# Rests the ball position to a random position around the center area of the screen
def resetBall(ball):
  newX = random.randint(LEFTX_SPAWN_LIM, RIGHTX_SPAWN_LIM)
  newY = random.randint(TOPY_SPAWN_LIM, BOTY_SPAWN_LIM)
  ball["rect"].center = (newX, newY)

  goLeft = random.randint(0, 1)
  if(goLeft == 1):
    ball["xmov"] = -1
  else:
    ball["xmov"] = 1
  
  goUp = random.randint(0, 1)
  if(goUp == 1):
    ball["ymov"] = -1
  else:
    ball["ymov"] = 1
  
def drawSeparator():
  totalHeight = WINDOWHEIGHT - (2 * BORDER)
  currHeight = 0
  y = BORDERT
  x = (WINDOWWIDTH / 2) - 1
  while(currHeight < (totalHeight - DOTLINESIZE)):
    pygame.draw.line(mainWin, WHITE, (x, y), (x, y + DOTLINESIZE), 2)
    y += DOTLINESIZE + LINEGAP
    currHeight += DOTLINESIZE + LINEGAP
  pygame.draw.line(mainWin, WHITE, (x, y), (x, BORDERB), 2)

def drawBorder():
  pygame.draw.lines(mainWin, WHITE, True, ((BORDER, BORDER), (WINDOWWIDTH - BORDER, BORDER), (WINDOWWIDTH - BORDER, WINDOWHEIGHT - BORDER), (BORDER, WINDOWHEIGHT - BORDER)))

def drawScore(player, playerNo = 1):
  font = pygame.font.Font(None, 24)
  text = font.render(str(player["score"]), 1, WHITE)
  textRect = text.get_rect()
  if(playerNo == 1):
    textRect.center = P1SCORELOC
  elif(playerNo == 2):
    textRect.center = P2SCORELOC
  mainWin.blit(text, textRect)

def drawScores():
  drawScore(player1, playerNo = 1)
  drawScore(player2, playerNo = 2)

def updateDisplay():
  mainWin.fill(BLACK)
  drawball(ball)
  drawPlayers()
  drawBorder()
  drawSeparator()
  drawScores()
  pygame.display.update()

# Check for quitting/keys pressed
def checkEvents():
  for e in pygame.event.get():
    if(e.type == QUIT):
        pygame.quit()
        sys.exit()
    elif(e.type == KEYDOWN):
      if(e.key == K_DOWN):
        player1["dir"] = MDOWN
      elif(e.key == K_UP):
        player1["dir"] = MUP
      elif(e.key == K_ESCAPE):
        pygame.quit()
        sys.exit()
    elif(e.type == KEYUP):
      if((e.key == K_DOWN) or (e.key == K_UP)):
        player1["dir"] = MNO

# Moves 'ball' in the direction it is recorded as going in according to 'xmov' and 'ymov' dictionary entries
def moveBall(ball):
  ball["rect"].move_ip(ball["xmov"], ball["ymov"])
  ballTop = ball["rect"].top
  ballBot = ball["rect"].bottom

  if(ballTop < BORDERT):
    ball["rect"].top = BORDERT
    ball["ymov"] = -1 * ball["ymov"]
  elif(ballBot > BORDERB):
    ball["rect"].bottom = BORDERB
    ball["ymov"] = -1 * ball["ymov"]

# Move 'paddle' in the direction it is recorded as going in
def movePaddle(paddle):
  x = 0
  y = paddle["dir"] * PMOVESPEED
  newTop = paddle["rect"].top + y
  newBottom = paddle["rect"].bottom + y
  
  if(newTop < BORDERT):
    paddle["rect"].top = BORDERT
  elif(newBottom > BORDERB):
    paddle["rect"].bottom = BORDERB
  else:
    paddle["rect"].move_ip(0, paddle["dir"] * PMOVESPEED)

# Performs AI calculations for the paddle, then moves it as normal
def moveAIPaddle(paddle):
  if(ball["rect"].top < paddle["rect"].top):
    paddle["dir"] = MUP
  elif(ball["rect"].bottom > paddle["rect"].bottom):
    paddle["dir"] = MDOWN
  else:
    paddle["dir"] = MNO
  movePaddle(paddle)

def movePaddles():
  movePaddle(player1)
  moveAIPaddle(player2)

def checkCollisions():
  # Check collisions with paddles
  if(player1["rect"].colliderect(ball["rect"])):
    if(ball["rect"].left < (player1["rect"].right - abs(ball["xmov"]))):
      ball["ymov"] = -1 * ball["ymov"] # ball hit top of paddle
    else:
      ball["xmov"] = (-1 * ball["xmov"]) + 1 # ball hit side of paddle

  if(player2["rect"].colliderect(ball["rect"])):
    if(ball["rect"].right > (player2["rect"].left + abs(ball["xmov"]))):
      ball["ymov"] = -1 * ball["ymov"] # ball hit top of paddle
    else:
      ball["xmov"] = (-1 * ball["xmov"]) - 1 # ball hit side of paddle

  # Check ball going out of bounds (needing a reset)
  if((ball["rect"].right <= 0)):
    player2["score"] += 1
    resetBall(ball)
  elif(ball["rect"].left >= WINDOWWIDTH):
    player1["score"] += 1
    resetBall(ball)

# Game loop
resetBall(ball)
while(True):
  checkEvents()
  moveBall(ball)
  movePaddles()
  checkCollisions()
  updateDisplay()
  
  clock.tick(FRAMERATE)