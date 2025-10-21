package fr.corentin.mcSpring.listener;

import fr.corentin.mcSpring.service.PlayerDataService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final PlayerDataService playerDataService;

    public PlayerJoinListener(PlayerDataService playerDataService) {
        this.playerDataService = playerDataService;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUuid = player.getUniqueId();

        playerDataService.addCoins(playerUuid, 10);

        int totalCoins = playerDataService.getCoins(playerUuid);

        System.out.println("[JPA] " + player.getName() + " reveived " + 10 +
                " coins (Total: " + totalCoins + ")");


        long players = playerDataService.countPlayersWithMinCoins(50);

        System.out.println("[JPA] " + players + " players have more than 50 coins");

    }
}