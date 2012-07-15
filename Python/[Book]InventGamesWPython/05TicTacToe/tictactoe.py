import random

print("Welcome to TicTac Toe!")

def playAgain():
	return input("Do you want to play again? (yes or no) ").lower().startswith("y")

def inputPlayerLetter():
	letter = ""
	while(not((letter == "X") or (letter == "O"))):
		letter = input("Do you want to be X or O? ").upper()
	if(letter == 'X'):
		return["X", "O"]
	else:
		return["O", "X"]
		
def whoGoesFirst():
	if random.randint(0, 1) == 0:
		return "computer"
	else:
		return "player"
		
def drawBoard(board):
		print('   |   |')
		print(' ' + board[7] + ' | ' + board[8] + ' | ' + board[9])
		print('   |   |')
		print('-----------')
		print('   |   |')
		print(' ' + board[4] + ' | ' + board[5] + ' | ' + board[6])
		print('   |   |')
		print('-----------')
		print('   |   |')
		print(' ' + board[1] + ' | ' + board[2] + ' | ' + board[3])
		print('   |   |')
		
def getBoardCopy(board):
	dupe = []
	for i in board:
		dupe.append(i)
	return dupe
		
def isSpaceFree(board, move):
	return board[move] == " "
	
def chooseRandomMoveFromList(board, movesList):
	possibleMoves = []
	for i in movesList:
		if(isSpaceFree(board, i)):
			possibleMoves.append(i)
			
	if(len(possibleMoves) != 0):
		return random.choice(possibleMoves)
	else:
		return None
		
def getPlayerMove(board):
	move = " "
	while((move not in "1 2 3 4 5 6 7 8 9".split()) or (not(isSpaceFree(board, int(move))))):
		move = input("What is your next move? (1 - 9) ")
	return int(move)
	
def getComputerMove(board, computerLetter):
	if(computerLetter == "X"):
		playerLetter = "O"
	else:
		playerLetter = "X"
		
	# check if any moves will immediately win
	for i in range(1, 10):
		copy = getBoardCopy(board)
		if(isSpaceFree(copy, i)):
			makeMove(copy, computerLetter, i)
			if(isWinner(copy, computerLetter)):
				return i
				
	# check if player could win next move - block if so
	for i in range(1, 10):
		copy = getBoardCopy(board)
		if(isSpaceFree(copy, i)):
			makeMove(copy, playerLetter, i)
			if(isWinner(copy, playerLetter)):
				return i
	
	# take a corner if possible
	move = chooseRandomMoveFromList(board, [1, 3, 7, 9])
	if(move != None):
		return move
	
	# take center if possible
	if(isSpaceFree(board, 5)):
		return 5
	
	# take a random side
	return chooseRandomMoveFromList(board, [[2, 4, 6, 8]])
	
def makeMove(board, letter, move)	:
	board[move] = letter
	
def isWinner(bo, le):
		return ((bo[7] == le and bo[8] == le and bo[9] == le) or # across the top
		(bo[4] == le and bo[5] == le and bo[6] == le) or # across the middle
		(bo[1] == le and bo[2] == le and bo[3] == le) or # across the bottom
		(bo[7] == le and bo[4] == le and bo[1] == le) or # down the left side
		(bo[8] == le and bo[5] == le and bo[2] == le) or # down the middle
		(bo[9] == le and bo[6] == le and bo[3] == le) or # down the right side
		(bo[7] == le and bo[5] == le and bo[3] == le) or # diagonal
		(bo[9] == le and bo[5] == le and bo[1] == le)) # diagonal

def isBoardFull(board):
	for i in range(1, 10):
		if(isSpaceFree(board, i)):
			return False
	return True
		
while True:
	theBoard = [" "] * 10 # we ignore index 0
	playerLetter, computerLetter = inputPlayerLetter()
	turn = whoGoesFirst()
	print("The " + turn + " will go first.")
	gameIsPlaying = True
	
	while(gameIsPlaying):
		if(turn == "player"):
			drawBoard(theBoard)
			move = getPlayerMove(theBoard)
			makeMove(theBoard, playerLetter, move)
			
			if(isWinner(theBoard, playerLetter)):
				drawBoard(theBoard)
				print("You win!")
				gameIsPlaying = False
			else:
				if(isBoardFull(theBoard)):
					drawBoard(theBoard)
					print("Tie!")
					break
				else:
					turn = "computer"
		else: # computers' turn
			move = getComputerMove(theBoard, computerLetter)
			makeMove(theBoard, computerLetter, move)
			
			if(isWinner(theBoard, computerLetter)):
				drawBoard(theBoard)
				print("You lose!")
				gameIsPlaying = False
			else:
				if(isBoardFull(theBoard)):
					drawBoard(theBoard)
					print("Tie!")
					break
				else:
					turn = "player"
		
	if(not(playAgain())):
		break