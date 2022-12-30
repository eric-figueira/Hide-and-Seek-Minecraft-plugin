package hidenseek.hidenseek.managers;

import hidenseek.hidenseek.HideNSeek;
import hidenseek.hidenseek.tasks.gameCountdown;
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
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private GameState gameState;
    private gameStartCountdown gameStartCountdown;
    private seekerReleaseCountdown seekerReleaseCountdown;
    private gameCountdown gameCountdown;
    private final HideNSeek plugin;

    public gameCountdown getGameCountdown() { return this.gameCountdown; }
    public seekerReleaseCountdown getSeekerReleaseCountdown() { return this.seekerReleaseCountdown; }

    private final ArrayList<Player> seekers = new ArrayList<>();

    private boolean canFirstSeekerMove;
    private boolean isGameFinished;

    public boolean getCanFirstSeekerMove() { return this.canFirstSeekerMove; }
    public void setCanFirstSeekerMove(boolean canMove) { this.canFirstSeekerMove = canMove; }
    public void setIsGameFinished(boolean isGameFinished) { this.isGameFinished = isGameFinished; }

    public GameManager (HideNSeek pl) {
        this.plugin = pl;
    }

    public GameState getGameState() { return this.gameState; }
    public ArrayList<Player> getSeekers() { return this.seekers; }
    private final Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();
    private Team t = null;


    public void setGameState(GameState gs) {

        this.gameState = gs;

        switch (gs) {
            case STARTING:
                this.gameStartCountdown = new gameStartCountdown(this);
                this.gameStartCountdown.runTaskTimer(this.plugin, 0, 20);
                break;
            case STARTED:
                if (this.gameStartCountdown != null) this.gameStartCountdown.cancel();
                if (this.gameCountdown != null) this.gameCountdown.cancel();

                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Game Started!");

                this.t = this.score.getTeam("hidenames");
                if (t == null) {
                    t = score.registerNewTeam("hidenames");
                    t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
                }

                // Change every players gamemode to adventure and remove all potion effects
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.setGameMode(GameMode.ADVENTURE);
                    p.getInventory().clear();
                    t.addEntry(p.getDisplayName());
                    for (PotionEffect effect : p.getActivePotionEffects())
                        p.removePotionEffect(effect.getType());
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

                this.isGameFinished = false;

                break;
            case STOPPED:
                if (!this.isGameFinished) {
                    Bukkit.broadcastMessage(ChatColor.RED + "Game stopped!");
                    if (this.seekers.get(0).hasPotionEffect(PotionEffectType.BLINDNESS)) this.seekers.get(0).removePotionEffect(PotionEffectType.BLINDNESS);
                }
                else {
                    Bukkit.broadcastMessage(ChatColor.BLUE + "====================");
                    Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD +"The Game Finished!");
                    if (this.seekers.size() == Bukkit.getOnlinePlayers().size())
                        Bukkit.broadcastMessage(ChatColor.WHITE + "Winner: Seekers");
                    else
                        Bukkit.broadcastMessage(ChatColor.WHITE + "Winner: Hiders");
                    Bukkit.broadcastMessage(ChatColor.BLUE + "====================");
                }
                if (this.t != null) this.t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);

                if (this.gameCountdown != null) this.gameCountdown.cancel();
                if (this.seekerReleaseCountdown != null) this.seekerReleaseCountdown.cancel();
                if (this.gameStartCountdown != null) this.gameStartCountdown.cancel();

                break;
        }
    }

    public void makePlayerSeeker(Player p) {

        // Tp to spawn
        Location l = this.plugin.getConfig().getLocation("firstspawn");
        p.teleport(l);

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
            this.setCanFirstSeekerMove(false);
            f.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 3));
            this.seekerReleaseCountdown = new seekerReleaseCountdown(this);
            this.seekerReleaseCountdown.runTaskTimer(this.plugin, 0, 20);
            if (this.canFirstSeekerMove)
                f.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }

    public void startGameCountdown() {
        this.gameCountdown = new gameCountdown(this);
        this.gameCountdown.runTaskTimer(this.plugin, 0, 20);
    }
}
