# ⚽ Football Manager Simulator  
### Paradigmas de Programação Project

---

## 📌 About The Project

This project was developed for the **Paradigms of Programming** course and consists of a complete **football management and match simulation system** implemented in **Java**.

The system models real-world football structures such as clubs, players, leagues, seasons, matches, and standings, while also providing a dynamic match simulation engine capable of generating realistic in-game events.

The architecture was designed following **Object-Oriented Programming principles** and clean modular design, ensuring extensibility, maintainability, and separation of concerns.

---

## 🚀 Key Features

- ✅ Club and player management  
- ✅ League and season creation  
- ✅ Match scheduling system  
- ✅ Dynamic match simulation engine  
- ✅ In-game events generation:
  - Goals
  - Fouls
  - Yellow/Red cards
  - Injuries
  - Substitutions  
- ✅ Player and match statistics tracking  
- ✅ Head-to-head comparison system  
- ✅ JSON data import for teams and players  
- ✅ HTML report generation  

---

## 🏗️ System Architecture

The project follows a **modular layered architecture**, organized into clearly separated packages:

### 📂 `src/`

- **interfaces/**  
  Defines system contracts (Engine, Events, Menu, etc.), promoting abstraction and decoupling.

- **models/**  
  Core domain entities:
  - Club  
  - Player  
  - Team  
  - Match  
  - League  
  - Season  
  - Standing  

- **simulator/**  
  Responsible for match simulation logic:
  - Event system  
  - EventManager  
  - GoalEvent, FoulEvent, CardEvent, InjuryEvent, SubstitutionEvent  
  - Simulation Strategy implementation  

- **stats/**  
  Statistical analysis classes:
  - MatchStats  
  - PlayerStats  
  - HeadToHead  

- **menus/**  
  Game engine control and user interaction logic.

- **utils/**  
  - JSON import utilities  
  - HTML report generators  

---

## 📁 Additional Project Structure

- `JSONfiles/` → JSON data used to populate clubs and players  
- `MA_Resources/` → External API contracts library  
- `dist/` → Generated Javadoc documentation  
- `nbproject/` → NetBeans configuration files  
- `Reports (PDF)` → Academic reports submitted for evaluation  

---

## 🧠 Programming Concepts Applied

- Object-Oriented Programming  
  - Encapsulation  
  - Inheritance  
  - Polymorphism  
  - Abstraction  

- Interface-based design  
- Strategy Pattern (Match Simulation Strategy)  
- Event-driven architecture  
- Separation of concerns  
- Modular package organization  

---

## 🛠️ Technologies & Tools

### 💻 Languages
- **Java**
- **JSON**

### 🧰 Tools
- **NetBeans IDE**
- **Javadoc**
- **Apache Ant (build.xml)**

---

## ▶️ How to Run

1. Open the project in **NetBeans IDE**
2. Build the project using `build.xml` (Ant)
3. Run the `Main` class inside the `simulator` package

Alternatively:
```bash
ant clean
ant build
ant run
```

---

# 🇵🇹 Versão em Português

## 📌 Sobre o Projeto

Este projeto foi desenvolvido no âmbito da unidade curricular de **Paradigmas de Programação** e consiste num sistema completo de **gestão e simulação de futebol** implementado em **Java**.

O sistema modela estruturas reais do futebol como clubes, jogadores, ligas, épocas, partidas e classificações, incluindo um motor de simulação capaz de gerar eventos dinâmicos durante o jogo.

A arquitetura foi desenvolvida seguindo os princípios da **Programação Orientada a Objetos**, garantindo modularidade, extensibilidade e organização clara do código.

---

## 🚀 Funcionalidades Principais

- ✅ Gestão de clubes e jogadores  
- ✅ Criação de ligas e épocas  
- ✅ Sistema de calendário de jogos  
- ✅ Motor de simulação de partidas  
- ✅ Geração de eventos em tempo de jogo:
  - Golos  
  - Faltas  
  - Cartões amarelos/vermelhos  
  - Lesões  
  - Substituições  
- ✅ Estatísticas de jogadores e partidas  
- ✅ Sistema Head-to-Head  
- ✅ Importação de dados via JSON  
- ✅ Geração de relatórios em HTML  

---

## 🧠 Conceitos Aplicados

- Encapsulamento  
- Herança  
- Polimorfismo  
- Abstração  
- Interfaces e contratos  
- Padrão Strategy  
- Arquitetura orientada a eventos  
- Organização modular  

---

## 👨‍💻 Academic Context

Course: **Paradigmas de Programação**  
Degree: Computer Science / Software Engineering  
Language: Java  
Project Type: Academic Practical Assignment  

---

## 📄 Documentation

Full technical documentation is available in the `dist/` folder (Javadoc generated).

---

## 📌 Author
Luís Garcês
João Lima
---
Developed as part of an academic group project.
