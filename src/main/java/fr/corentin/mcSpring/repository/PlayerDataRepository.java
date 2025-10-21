package fr.corentin.mcSpring.repository;

import fr.corentin.mcSpring.model.PlayerData;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerDataRepository extends JpaRepository<@NotNull PlayerData, @NotNull UUID> {
    //custom method in addition to default methods already present in JpaRepository
    @Query("SELECT COUNT(p) FROM PlayerData p WHERE p.coins >= :minCoins")
    long countPlayersWithMinCoins(@Param("minCoins") int minCoins);
}