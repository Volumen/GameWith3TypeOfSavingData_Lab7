# GameWith3TypeOfSavingData_Lab7
Simple game that uses 3 tipe of data storage

A circle divided into two parts is drawn on the screen. 
After the start, the game waits randomly for a few seconds and displays a toast "start". 
The player who first touches his part is the first to score 5 points, the second scores 3 points. 
The player who touches his part will get a negative point earlier and will be blocked until the end of the round. 
The game consists of five rounds, the player wins with the most points after all rounds. 
This game stores data in three ways:  
SharedPreferences - number of players, names of players, number of rounds  
SQLite - history of the game  
Bundle - current state of the game
