# 🗝️ Labyrinth Treasure Game

[🇵🇹 Português](README.pt.md) · 🇬🇧 English

![Java](https://img.shields.io/badge/Java-Data%20Structures-orange?logo=openjdk&logoColor=white)
![Graphs](https://img.shields.io/badge/Graphs-Shortest%20Path-blueviolet)
![Gson](https://img.shields.io/badge/Gson-2.13.2-lightblue)
![Type](https://img.shields.io/badge/Type-University%20Project-lightgrey)

A turn-based, multiplayer **labyrinth race** played in the console. Players (humans and/or bots) start from different entrances and race to reach the treasure chamber, solving riddles and levers and dealing with random corridor events along the way. The labyrinth is modelled as a **graph**, and bots can navigate it using a **shortest-path** strategy.

> 🎓 **Academic context** – Data Structures course project, BSc in Computer Engineering · October – December 2025.
> The main goal was to choose and apply the _right data structure for each problem_ instead of defaulting to `java.util` collections.

---

## ✨ Features

- **Graph-based map** – chambers are vertices, halls (corridors) are edges. Maps are loaded from JSON files (chambers + halls) with **Gson**.
- **3 difficulty levels** (easy / medium / hard), each defined by its own JSON map. The included hard map has 18 chambers, 6 entrances and 1 treasure chamber.
- **Mixed players** – any combination of **Human**, **Bot (Random)** and **Bot (Shortest Path)**.
- **Challenges inside chambers**
  - 🧩 **Riddles** – multiple-choice questions drawn from two banks: _General Knowledge_ (114 questions) and _IT Student Edition – Data Structures_ (40 questions).
  - 🔧 **Levers** – pick the right lever out of three.
  - Failing a challenge **traps** the player in the chamber until it is solved.
- **Random corridor events** – `LOSE_TURN`, `GAIN_TURN`, `RETREAT_POSITION`, `SWAP_POSITION_WITH`, `SWAP_ALL_POSITIONS`.
- **Final report** – when someone reaches the treasure, the game prints each player's full move/event history.

## 🧠 Data structures & concepts used

| Need in the game                         | Structure / concept                                                                                                                                                                      |
| ---------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Labyrinth topology                       | `Graph<IChamber>` (adjacency matrix), extended by `Labyrinth`, plus a parallel `IHall[][]` matrix holding the hall (and its event) for each edge                                         |
| Fair turn order with bonus/penalty turns | `CircularArrayQueue<IPlayer>`                                                                                                                                                            |
| Neighbours, entrances, riddles           | `ArrayUnorderedList`                                                                                                                                                                     |
| Per-player action history (final report) | `DoubleUnorderedLinkedList<String>`                                                                                                                                                      |
| Bot navigation to the treasure           | Shortest-path iterator over the graph (`iteratorShortestPath`)                                                                                                                           |
| Extensibility                            | Interfaces (`ILabyrinth`, `IChamber`, `IHall`, `IPlayer`, `IChallenge`, `IEvent`) + abstract/concrete hierarchy (`Player` → `HumanPlayer`, `BotPlayer`; `Challenge` → `Riddle`, `Lever`) |

All core logic is built on the course's data-structures library (`ED_25_26_DataStructures.jar`) rather than on `java.util` collections. Every class is documented with Javadoc – open [`docs/javadoc/index.html`](docs/javadoc/index.html).

> ℹ️ **Note on the shortest path:** the maze is an _unweighted_ graph, so the shortest route is the one with the fewest chambers (a breadth-first style search provided by the graph library). A weighted variant (e.g. Dijkstra over a `Network`) is listed in the roadmap below.

## 🗂️ Project structure

```
src/
├── Game/         # Main entry point, menus, game loop (GameControler)
├── Labyrinth/    # Labyrinth (graph), Chamber, Hall, types
├── Player/       # Player, HumanPlayer, BotPlayer, StrategyType
├── Challenges/   # Riddle, Lever, Event, EventType
└── IO/           # LabyrinthLoader, RiddleLoader, RiddleQuestion
docs/javadoc/     # Generated Javadoc
libs/             # ED_25_26_DataStructures.jar, gson-2.13.2.jar
*.json            # Labyrinth maps and riddle banks
```

## 🚀 Getting started

**Requirements:** JDK 23+ (the bundled data-structures JAR is compiled for Java 23). Run all commands from the repository root.

```bash
git clone <repository-url>
cd labyrinth-treasure-game
```

Compile and run (Linux/macOS – on Windows replace `:` with `;`):

```bash
javac -cp "libs/*" -d out $(find src -name "*.java")
java  -cp "out:libs/*" Game.Game
```

Or open the project in **IntelliJ IDEA**, add the JARs from `libs/` and run `Game.Game`.

## 🎮 How to play

1. Choose a **game mode** (General Knowledge or IT Student Edition) and a **difficulty**.
2. Configure each player: name, type (Human / Bot Random / Bot Shortest Path) and starting entrance.
3. On your turn, choose which chamber to move to. Corridors may trigger an event; chambers may contain a riddle or lever.
4. The first player to enter the **treasure chamber** wins.

## 🔭 Roadmap

- [ ] Add JUnit tests for the graph, the loaders and the bot strategies.
- [ ] Weighted corridors (`Network` + Dijkstra) so bots can account for event risk.
- [ ] Separate game logic from console I/O (e.g. to enable a GUI).

## 👥 Authors

- **Maria Leonor Araújo** – [LinkedIn](https://www.linkedin.com/in/leonor-araújo-18155138b)
- **Miguel Ferreira** – [LinkedIn](https://www.linkedin.com/in/miguel-brand%C3%A3o-45a004427)

## 📄 License

Educational project – no license specified. Please contact the authors before reusing the code.
