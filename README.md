# 🧩 Maze Challenge — Competitive Turn‑Based Labyrinth Simulator

## ✨ Overview

**Maze Challenge** is a competitive, turn‑based game simulator where players race toward the heart of a mysterious labyrinth to claim a hidden treasure. While the objective is straightforward, the journey is anything but simple: traps, riddles, unpredictable events, and strategic decisions shape every match.

Developed as part of an academic challenge, this project emphasizes **game logic**, **event simulation**, **external data integration**, and **player interaction**, all implemented in a **modular Java architecture** built on custom **Abstract Data Types (ADTs)**.

---

## 🗺️ Game Concept

The game world is a labyrinth modeled as an **undirected graph**:

* **Divisions (Rooms)** → vertices
* **Hallways (Corridors)** → weighted edges

Players take turns navigating the maze, choosing paths and facing challenges that may accelerate—or completely derail—their progress.

### 🔄 Turn System

Gameplay unfolds in sequential turns. On each turn, the active player selects a valid move based on the hallways connected to their current division. However, movement alone does not guarantee success:

* Each **Division** may impose a challenge.
* Each **Hallway** may trigger a random event.

These behaviors are defined by the core `IDivision` and `IHallway` interfaces, ensuring extensibility and clean separation of concerns.

---

## 🏗️ Architecture & Core Components

The system is designed around a set of key interfaces, prioritizing modularity, scalability, and persistence (via **Jackson annotations** for JSON serialization).

### 🧠 1. The Labyrinth (`IMaze`, `IDivision`, `IHallway`)

* **Maze (`IMaze`)**: Extends a custom `NetworkADT` (graph structure). Manages divisions, hallways, and connectivity.
* **Division (`IDivision`)**: Base interface for all room types. Defines the method:

  ```java
  getComportament(IMaze maze, IPlayer player)
  ```

  which encapsulates room‑specific logic, interaction, and movement outcomes.
* **Hallway (`IHallway`)**: Represents connections between divisions. Traversing a hallway triggers a random `IEvent`.

---

## 🚪 Room Types & Challenges

Each room enforces a unique rule set, requiring players to adapt their strategy.

| 🏷️ Room Type    | 🧩 Class           | ⚙️ Challenge Logic                                                                                                                                                                                    |
| ---------------- | ------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Lever Rooms**  | `LeverDivision`    | A dynamic set of levers is generated: half lead to a neighboring division, half are traps (`null`). The lever array is **shuffled**, and an incorrect choice forces the player to remain in the room. |
| **Riddle Rooms** | `QuestionDivision` | The player must correctly answer a multiple‑choice question. A correct answer allows progression; a wrong answer requires retrying on a future turn.                                                  |
| **Goal Room**    | `GoalDivision`     | Triggers immediate victory and ends the game for the player.                                                                                                                                          |

---

## ❓ Riddle Management & Persistence

Riddle Rooms rely on the **`QuestionManager`** to ensure variety and fairness.

* 📂 **External Data**: Questions are loaded from an external **JSON file**, including options and the correct answer index.
* 🔀 **Randomization Rule**:

  1. Questions are loaded into a `ListADT`.
  2. The list is **shuffled**.
  3. Questions are transferred into a `LinkedQueue`.

This guarantees that no question is repeated until all others have been used once.

---

## 🎲 Corridor Events (`IEvent`)

Every hallway traversal may trigger a random event, managed by the **`EventManager`** and applied through `IEvent.apply()`.

| ⚡ Event Effect     | 🧩 Class            | 📜 Rule Implementation                                                          |
| ------------------ | ------------------- | ------------------------------------------------------------------------------- |
| **Extra Turns**    | `ExtraPlays`        | Grants the player additional moves in the current round.                        |
| **Stunned**        | `StunnedPlays`      | Prevents the player from acting for several turns.                              |
| **Swap Position**  | `SwapTwoPlayers`    | Forces the active player to choose another player and instantly swap positions. |
| **Global Shuffle** | `ShuffleAllPlayers` | Randomly reassigns the positions of all players.                                |
| **Move Backward**  | `RollBack`          | Moves the player back to their previously visited division.                     |

> ⚠️ **Critical Rollback Rule**
> If a `RollBack` event is triggered immediately after a **position‑swapping** event (`SwapTwoPlayers` or `ShuffleAllPlayers`), the rollback is **suppressed** and a new random event is generated. This prevents invalid history rollbacks caused by teleportation‑style events.

---

## 🏆 Objective

Be the **first player** to reach the **Goal Division** at the center of the maze and claim the hidden treasure.

---

## 💾 External Files

The game heavily relies on external files to support dynamic content and persistence:

* 📘 **Questions.json** — Stores riddles, possible answers, and correct indices (managed by `QuestionManager`).
* 🗺️ **Maps.json** — Saves generated or custom mazes for future sessions (managed by `MapExporter` / `MapImporter`).
* 📜 **History Files** (e.g., `Game_X.json`) — Logs completed games for later review (managed by `GameHistoryImporter`).

---

## 🌟 Features

* 🧱 **Dynamic Maze Generation** — Unique mazes generated at game start (10–90 divisions).
* 🔁 **Turn‑Based Progression** — Fair and deterministic sequencing managed by `GameManager`.
* 🧠 **Interactive Divisions** — Rooms enforce lever or riddle‑based challenges.
* 🎲 **High Unpredictability** — Random corridor events introduce strategic depth and chaos.
* 💾 **Full Persistence** — Save/load complex maze structures and game data via JSON.
* 📊 **Player Tracking** — Detailed movement and event history stored using a custom `Stack`.

---

## ▶️ How to Run

1. ⚙️ **Compile**
   Compile the entire project, ensuring all custom data structures (`ListADT`, `NetworkADT`, etc.) and dependencies (e.g., Jackson) are included.

2. 🚀 **Run**
   Execute the `Main` class `main` method.

3. 🎮 **Start a New Game**
   Select **"1 - New Game"** from the main menu.

4. 🛠️ **Configure**
   Generate a random map, define a custom size, or load a saved map.

5. 🧑‍🤝‍🧑 **Play**
   Configure player names and types (Human/Bot) and begin the competitive maze challenge.

---
## NOTE
The only game language available, for now, is Portuguese
