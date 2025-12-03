# Maze Challenge — Competitive Turn-Based Labyrinth Simulator
## Overview
Maze Challenge is a competitive, turn-based game simulator where players race to reach the center of a mysterious labyrinth and claim the hidden treasure. Although the objective is simple, the path to victory is filled with traps, puzzles, unpredictable events, and strategic decision-making.
Every choice matters, and the labyrinth itself may change the course of the game.
This project was developed as part of an academic challenge, focusing on game logic, event simulation, external data integration, and player interaction.

## Game Concept
The game takes place in a maze composed of rooms and corridors. Players take turns navigating the labyrinth while encountering different types of obstacles and events that affect their progress.

### Turn System

Gameplay unfolds in sequential turns.
On each turn, the active player chooses a valid move based on the available paths from their current position
However, moving alone does not guarantee progress — each location may impose additional challenges.

## Room Types & Challenges
### Lever Rooms
  - Some rooms contain mechanical levers.
  - The player must attempt to activate the lever.

### Riddle Rooms
   - These rooms challenge players with logic questions loaded from an external JSON file.

### The JSON file includes:
   - multiple questions
   - possible answers
   - one correct answer for each question

### Rules:
A question is chosen randomly.
A question can only repeat after all have been used at least once.
Correct answers let the player proceed, wrong answers require them to retry on a future turn.

## Corridor Events

Some corridors contain random events that trigger when a player crosses them. These events add unpredictability and strategic depth.

### Possible effects include:
- gaining extra turns
- swapping positions with a chosen player
- moving backward a certain number of spaces
- being unable to play for several turns
- swapping the positions of all players
- If a backward movement is triggered after swapping positions with another player, the player can only move back up to the position they swapped from.

## Objective
Be the first player to reach the center of the maze and claim the treasure.

## External files
The game relies on:
- A JSON file containing riddles and possible answers. This supports randomization and ensures replayability.

## Features
- Dynamic maze with interactive elements
- Turn-based system with fair sequential progression
- Randomized riddles loaded from external files
- Multiple event types affecting gameplay flow
- Modular architecture for easy expansion
- Suitable for both simulation and competitive play

 ## How to Run

(You may fill this in when your implementation is complete)

A correct activation unlocks a blocked passage.

An incorrect choice keeps the passage locked, forcing the player to try again on a future turn.
