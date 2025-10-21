package fr.corentin.mcSpring.service;

import fr.corentin.mcSpring.model.PlayerData;
import fr.corentin.mcSpring.repository.PlayerDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PlayerDataService {
    private final PlayerDataRepository repo;

    public PlayerDataService(PlayerDataRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void addCoins(UUID uuid, int amount) {
        PlayerData player = repo.findById(uuid).orElse(new PlayerData(uuid, 0));
        player.setCoins(player.getCoins() + amount);
        repo.save(player);
    }

    @Transactional(readOnly = true)
    public int getCoins(UUID uuid) {
        return repo.findById(uuid).map(PlayerData::getCoins).orElse(0);
    }
    
    @Transactional(readOnly = true)
    public long countPlayersWithMinCoins(int minCoins) {
        return repo.countPlayersWithMinCoins(minCoins);
    }
}