# 🗝️ Jogo do Labirinto do Tesouro

🇵🇹 Português · [🇬🇧 English](README.md)

![Java](https://img.shields.io/badge/Java-Estruturas%20de%20Dados-orange?logo=openjdk&logoColor=white)
![Grafos](https://img.shields.io/badge/Grafos-Caminho%20mais%20curto-blueviolet)
![Gson](https://img.shields.io/badge/Gson-2.13.2-lightblue)
![Tipo](https://img.shields.io/badge/Tipo-Projeto%20Universit%C3%A1rio-lightgrey)

Jogo **por turnos e multijogador** de corrida num labirinto, executado na consola. Os jogadores (humanos e/ou bots) começam em entradas diferentes e competem para chegar à câmara do tesouro, resolvendo enigmas e alavancas e lidando com eventos aleatórios nos corredores. O labirinto é modelado como um **grafo**, e os bots podem navegar com uma estratégia de **caminho mais curto**.

> 🎓 **Contexto académico** – Projeto da unidade curricular de Estruturas de Dados, Licenciatura em Engenharia Informática · Outubro – Dezembro 2025.
> O objetivo principal foi escolher e aplicar a _estrutura de dados certa para cada problema_, em vez de recorrer por defeito às coleções de `java.util`.

---

## ✨ Funcionalidades

- **Mapa baseado em grafo** – câmaras são vértices, corredores são arestas. Os mapas são carregados de ficheiros JSON (câmaras + corredores) com **Gson**.
- **3 níveis de dificuldade** (fácil / médio / difícil), cada um definido por um mapa JSON. O mapa difícil incluído tem 18 câmaras, 6 entradas e 1 câmara do tesouro.
- **Jogadores mistos** – qualquer combinação de **Humano**, **Bot (Aleatório)** e **Bot (Caminho Mais Curto)**.
- **Desafios dentro das câmaras**
  - 🧩 **Enigmas** – perguntas de escolha múltipla de dois bancos: _Cultura Geral_ (114 perguntas) e _Edição Estudante de Informática – Estruturas de Dados_ (40 perguntas).
  - 🔧 **Alavancas** – escolher a alavanca certa entre três.
  - Falhar um desafio **prende** o jogador na câmara até o resolver.
- **Eventos aleatórios nos corredores** – `LOSE_TURN`, `GAIN_TURN`, `RETREAT_POSITION`, `SWAP_POSITION_WITH`, `SWAP_ALL_POSITIONS`.
- **Relatório final** – quando alguém chega ao tesouro, o jogo apresenta o histórico completo de movimentos/eventos de cada jogador.

## 🧠 Estruturas de dados e conceitos aplicados

| Necessidade no jogo                                | Estrutura / conceito                                                                                                                                                                      |
| -------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Topologia do labirinto                             | `Graph<IChamber>` (matriz de adjacência), estendido por `Labyrinth`, com uma matriz paralela `IHall[][]` que guarda o corredor (e o seu evento) de cada aresta                            |
| Ordem de jogo justa, com turnos extra/penalizações | `CircularArrayQueue<IPlayer>`                                                                                                                                                             |
| Vizinhos, entradas, enigmas                        | `ArrayUnorderedList`                                                                                                                                                                      |
| Histórico de ações por jogador (relatório final)   | `DoubleUnorderedLinkedList<String>`                                                                                                                                                       |
| Navegação do bot até ao tesouro                    | Iterador de caminho mais curto sobre o grafo (`iteratorShortestPath`)                                                                                                                     |
| Extensibilidade                                    | Interfaces (`ILabyrinth`, `IChamber`, `IHall`, `IPlayer`, `IChallenge`, `IEvent`) + hierarquia abstrata/concreta (`Player` → `HumanPlayer`, `BotPlayer`; `Challenge` → `Riddle`, `Lever`) |

Toda a lógica central assenta na biblioteca de estruturas de dados da unidade curricular (`ED_25_26_DataStructures.jar`) e não em coleções `java.util`. Todas as classes estão documentadas com Javadoc – abre [`docs/javadoc/index.html`](docs/javadoc/index.html).

> ℹ️ **Nota sobre o caminho mais curto:** o labirinto é um grafo _não pesado_, pelo que o caminho mais curto é o que passa por menos câmaras (uma pesquisa em largura fornecida pela biblioteca de grafos). Uma variante pesada (p. ex. Dijkstra sobre uma `Network`) consta do roadmap abaixo.

## 🗂️ Estrutura do projeto

```
src/
├── Game/         # Ponto de entrada, menus, ciclo de jogo (GameControler)
├── Labyrinth/    # Labyrinth (grafo), Chamber, Hall, tipos
├── Player/       # Player, HumanPlayer, BotPlayer, StrategyType
├── Challenges/   # Riddle, Lever, Event, EventType
└── IO/           # LabyrinthLoader, RiddleLoader, RiddleQuestion
docs/javadoc/     # Javadoc gerado
libs/             # ED_25_26_DataStructures.jar, gson-2.13.2.jar
*.json            # Mapas do labirinto e bancos de enigmas
```

## 🚀 Como executar

**Requisitos:** JDK 23+ (o JAR de estruturas de dados incluído foi compilado para Java 23). Executa todos os comandos a partir da raiz do repositório.

```bash
git clone <url-do-repositório>
cd labyrinth-treasure-game
```

Compila e executa (Linux/macOS – no Windows troca `:` por `;`):

```bash
javac -cp "libs/*" -d out $(find src -name "*.java")
java  -cp "out:libs/*" Game.Game
```

Ou abre o projeto no **IntelliJ IDEA**, adiciona os JARs de `libs/` e executa `Game.Game`.

## 🎮 Como jogar

1. Escolhe o **modo de jogo** (Cultura Geral ou Edição Estudante de Informática) e a **dificuldade**.
2. Configura cada jogador: nome, tipo (Humano / Bot Aleatório / Bot Caminho Mais Curto) e entrada inicial.
3. Na tua vez, escolhe para que câmara te queres mover. Os corredores podem despoletar um evento; as câmaras podem ter um enigma ou uma alavanca.
4. Ganha o primeiro jogador a entrar na **câmara do tesouro**.

## 🔭 Roadmap

- [ ] Adicionar testes JUnit ao grafo, aos _loaders_ e às estratégias dos bots.
- [ ] Corredores pesados (`Network` + Dijkstra) para os bots ponderarem o risco dos eventos.
- [ ] Separar a lógica de jogo da I/O na consola (p. ex. para permitir uma GUI).

## 👥 Autores

- **Maria Leonor Araújo** – [LinkedIn](https://www.linkedin.com/in/leonor-araújo-18155138b)
- **Miguel Ferreira** – [LinkedIn](https://www.linkedin.com/in/miguel-brand%C3%A3o-45a004427)

## 📄 Licença

Projeto educativo – sem licença definida. Contacta os autores antes de reutilizar o código.
