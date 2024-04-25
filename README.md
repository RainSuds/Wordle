Wordle Java

Main
Purpose: Entry point of the application.
Responsibilities:
Initialize and launch the game window using JavaFX.
Create instances of game components like GameBoard.

GameBoard
Purpose: Manages the main game logic and user interface.
Responsibilities:
Incorporate Log, SelectWord, ShadowData, and Stats as part of the game workflow.
Handle the overall layout and interaction logic.
Display the game grid and keyboard interface using JavaFX components.


Log (File IO)
Purpose: Tracks and logs game actions, can read in file to read progress and updates the file with every move.
Responsibilities:
Maintain an array or list of all actions (e.g., moves made by the player).
Keep a record of all letters used, possibly using a HashSet to avoid duplicates and facilitate easy checks.
Log user moves and outcomes for each round.

SelectWord (File IO)
Purpose: Handles the selection of a random word for the game by reading in a txt file.
Responsibilities:
Check if user input is in a predefined word list.
Use a constructor or method to return a random word from the list, ensuring no repetition in consecutive games.

ShadowData (Object)
Purpose: Manages a shadow representation of game data.
Responsibilities:
Implement a growing 2D array (or an alternative data structure) to represent the state of the game board.
Keep track of guesses and their statuses (correct position, wrong position, not in word).

Stats
Purpose: Tracks and displays statistics of the game.
Responsibilities:
Track total play time, possibly using a timer.
Calculate and display the win rate.
Keep track of current streak and maximum streak.
Provide a distribution of guesses (e.g., how many games were won in 3 guesses, 4 guesses, etc.).