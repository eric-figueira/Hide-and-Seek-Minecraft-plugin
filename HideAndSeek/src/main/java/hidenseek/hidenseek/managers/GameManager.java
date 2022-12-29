package hidenseek.hidenseek.managers;

import hidenseek.hidenseek.HideNSeek;
import hidenseek.hidenseek.tasks.gameStartCountdown;
import hidenseek.hidenseek.tasks.seekerReleaseCountdown;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private GameState gameState;
    private gameStartCountdown gameStartCountdown;
    private seekerReleaseCountdown seekerReleaseCountdown;
    private final HideNSeek plugin;

    private final ArrayList<Player> seekers = new ArrayList<>();

    private boolean canFirstSeekerMove = false;

    public boolean getCanFirstSeekerMove() { return this.canFirstSeekerMove; }
    public void setCanFirstSeekerMove(boolean canMove) { this.canFirstSeekerMove = canMove; }

    public GameManager (HideNSeek pl) {
        this.plugin = pl;
    }

    public GameState getGameState() { return this.gameState; }
    public ArrayList<Player> getSeekers() { return this.seekers; }


    public void setGameState(GameState gs) {

        this.gameState = gs;

        switch (gs) {
            case STARTING:
                this.gameStartCountdown = new gameStartCountdown(this);
                this.gameStartCountdown.runTaskTimer(this.plugin, 0, 20);
                break;
            case STARTED:
                if (this.gameStartCountdown != null) this.gameStartCountdown.cancel();
                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Game Started!");

                // Change every players gamemode to adventure
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.setGameMode(GameMode.ADVENTURE);
                    p.getInventory().clear();
                }

                // Clear the seekers array
                this.seekers.clear();

                // Get random player
                Random random = new Random();
                int index = random.nextInt(Bukkit.getOnlinePlayers().size());
                Player seeker = new ArrayList<>(Bukkit.getOnlinePlayers()).get(index);

                // Add to seeker array
                this.seekers.add(seeker);
                this.makePlayerSeeker(seeker);

                break;
            case STOPPED:
                Bukkit.broadcastMessage(ChatColor.RED + "Game stopped!");
                break;
        }
    }

    public void makePlayerSeeker(Player p) {

        // Tp to spawn
        if (this.seekers.size() == 1) {
            // Its the first player, so it must be teleported to the first spawn
            Location l = this.plugin.getConfig().getLocation("firstspawn");
            p.teleport(l);
        }
        else {
            // Its another seeker that not the first one
            Location l = this.plugin.getConfig().getLocation("othersspawn");
            p.teleport(l);
        }

        // Get a stick
        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta meta = stick.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Seeker");
        meta.addEnchant(Enchantment.SWEEPING_EDGE, 255, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stick.setItemMeta(meta);
        p.getInventory().addItem(stick);

        // Title
        p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "You Are now a Seeker", ChatColor.WHITE + "Find the other players", 20, 40, 20);


        // Add blindness to the first seeker and starting a 30 seconds timer
        if (this.seekers.size() == 1) {
            Player f = this.seekers.get(0);
            f.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 3));
            this.seekerReleaseCountdown = new seekerReleaseCountdown(this);
            this.seekerReleaseCountdown.runTaskTimer(this.plugin, 0, 20);
            if (this.canFirstSeekerMove)
                f.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }
}
