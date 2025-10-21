package fr.corentin.mcSpring;

import fr.corentin.mcSpring.config.SpringDataConfig;
import fr.corentin.mcSpring.listener.PlayerJoinListener;
import fr.corentin.mcSpring.service.PlayerDataService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.URL;
import java.net.URLClassLoader;

public final class MCSpring extends JavaPlugin {

    private static MCSpring instance;
    private AnnotationConfigApplicationContext context;
    private PlayerDataService playerDataService;

    @Override
    public void onEnable() {
        instance = this;

        try {
            // Initialize Spring context with custom ClassLoader
            initializeSpringContext();

            // Retrieve the PlayerDataService bean from Spring context
            playerDataService = context.getBean(PlayerDataService.class);

            getLogger().info("Spring context started, H2 database initialized!");

            // Register Bukkit event listeners
            registerListeners();

        } catch (Exception e) {
            getLogger().severe("Failed to initialize Spring context: " + e.getMessage());
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        closeSpringContext();
    }

    private void initializeSpringContext() throws Exception {
        // Get the plugin JAR file as a URL for the ClassLoader
        URL jarUrl = getFile().toURI().toURL();

        // Create a custom ClassLoader that loads from the plugin JAR
        // Parent ClassLoader is the plugin's ClassLoader (which has access to Bukkit API)
        URLClassLoader springLoader = new URLClassLoader(
                new URL[]{jarUrl},
                getClass().getClassLoader()
        );

        // Set the thread's context ClassLoader so Spring uses it for class loading
        Thread.currentThread().setContextClassLoader(springLoader);

        // Create and configure the Spring context
        context = new AnnotationConfigApplicationContext();
        context.setClassLoader(springLoader);
        context.register(SpringDataConfig.class);
        context.refresh(); // Initialize all beans
    }

    private void closeSpringContext() {
        if (context != null) {
            try {
                context.close();
            } catch (Exception e) {
                getLogger().warning("Error while closing Spring context: " + e.getMessage());
            }
        }
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(
                new PlayerJoinListener(playerDataService),
                this
        );
    }

    public static MCSpring getInstance() {
        return instance;
    }
}