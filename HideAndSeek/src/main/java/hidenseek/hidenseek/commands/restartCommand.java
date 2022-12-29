package hidenseek.hidenseek.commands;

import hidenseek.hidenseek.HideNSeek;
import hidenseek.hidenseek.managers.GameManager;
import hidenseek.hidenseek.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class restartCommand implements CommandExecutor {

    private final GameManager gameManager;
    private final HideNSeek plugin;

    public restartCommand (HideNSeek pl, GameManager gm) {
        this.plugin = pl;
        this.gameManager = gm;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Testing if the spawns have been set
        Location f = this.plugin.getConfig().getLocation("firstspawn");
        Location o = this.plugin.getConfig().getLocation("othersspawn");

        if (f == null) Bukkit.broadcastMessage(ChatColor.RED + "The first seeker's spawn has not been set yet!");
        else if (o == null) Bukkit.broadcastMessage(ChatColor.RED + "The spawn for the others seekers has not been set yet!");
        else {
            if (this.gameManager.getGameState() == GameState.STARTING)
                sender.sendMessage(ChatColor.RED + "This game is already starting!");
            else {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Restaring the game!");
                this.gameManager.setGameState(GameState.STARTING);
            }
        }

        return true;
    }
}
