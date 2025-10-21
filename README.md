# MC-Spring

A Minecraft plugin which integrate of **Spring Framework** with **Paper/Spigot** servers. This project showcases how to use Spring Data JPA, dependency injection, and transaction management within a Minecraft plugin environment.

## Features

- **Spring Framework Integration** - Full Spring context running inside a Bukkit plugin
- **Spring Data JPA** - Repository pattern with automatic query generation
- **H2 Database** - Embedded database for persistent storage
- **Player Coin System** - Example feature tracking player coins
- **Transaction Management** - Proper ACID transactions with `@Transactional`

## Getting Started

### Prerequisites

- Java 17 or higher
- Paper/Spigot server 1.20.4

## Project Structure

```
src/main/java/fr/corentin/mcSpring/
├── MCSpring.java                    # Main plugin class
├── config/
│   └── SpringDataConfig.java       # Spring configuration
├── listener/
│   └── PlayerJoinListener.java     # Event handlers
├── model/
│   └── PlayerData.java             # JPA entity
├── repository/
│   └── PlayerDataRepository.java   # Spring Data repository
└── service/
    └── PlayerDataService.java      # Business logic layer
```

## Author

Corentin

