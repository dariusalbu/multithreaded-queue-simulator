# Multithreaded Queue Simulator

A real-time Java application that simulates multi-queue, multi-client service systems (such as supermarket checkouts, bank counters, or server task dispatchers) using concurrent programming and thread management.

---

## Features
* **Thread-Safe Simulation:** Each queue operates on an independent thread processing tasks concurrently via `ExecutorService`.
* **Dynamic Dispatching Strategies:**
    * **Shortest Queue Strategy:** Directs incoming clients to the queue with the fewest tasks.
    * **Shortest Time Strategy:** Directs incoming clients to the queue with the lowest total estimated waiting time.
* **Real-Time Visuals & Logging:** Tracks client movement into queues live using custom Java Swing graphics (`Graphics2D`) and generates execution output logs (`log.txt`).
* **Automated Analytics:** Computes average waiting time, average service time, and system peak hours upon simulation completion.

---

## Project Structure
```text
multithreaded-queue-simulator/
├── src/
│   └── main/
│       └── java/
│           └── org/example/
│               ├── Main.java
│               ├── business/
│               │   ├── Scheduler.java
│               │   ├── SimulationManager.java
│               │   ├── Strategy.java
│               │   ├── ShortestQueueStrategy.java
│               │   └── ShortestTimeStrategy.java
│               ├── model/
│               │   ├── Task.java
│               │   ├── Server.java
│               │   └── SimulationData.java
│               └── gui/
│                   ├── SimulationFrame.java
│                   └── SimulationPanel.java
├── .gitignore
├── pom.xml
└── README.md