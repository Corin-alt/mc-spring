package fr.corentin.mcSpring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "players")
public class PlayerData {
    @Id
    private UUID uuid;
    private int coins;

    public PlayerData(UUID uuid, int coins) {
        this.uuid = uuid;
        this.coins = coins;
    }

    public PlayerData() {}

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}