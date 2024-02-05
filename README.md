## Simple Card Game Using Multi-Threading
The idea of this game is very simple. Every player has a pack of cards to their left and right which they pick up from and discard to. The game continues until a player has a hand full of cards that have the same value.

#### Structure
The 'cardsTest' directory contains all of the source code (and associated bytecode) and the 'cards.jar' is the executable for this game

#### How to run the JUnit 5 test suite
To run our test suite, navigate to the directory where the 'cardTest' folder is and execute the following command:

java -cp .:lib/junit-platform-suite-engine-1.10.1.jar:lib/junit-platform-console-standalone-1.10.1.jar org.junit.platform.console.ConsoleLauncher execute --select-class TestSuite
