# Nim.java
Game of Nim with AI player that uses Q-learning

Nim is a 2-player game that starts with three piles of objects. Players alternate removing any number of objects from a single pile (a player may not remove objects from multiple piles on a single turn). The player who is forced to take the last object loses (in some variants, the player who takes the last object wins, but in our version, they lose).

The program allows the user to specify the starting configuration of how many objects are in each of the three piles, then use Q-learning to figure out the optimal strategy for both players simultaneously. After the Q-learning finishes, the program allows the user to play as many games of Nim against the computer as they want, with the user picking if they want to go first or let the computer go first.
