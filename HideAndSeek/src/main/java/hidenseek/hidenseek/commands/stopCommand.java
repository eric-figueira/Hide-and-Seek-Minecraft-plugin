package hidenseek.hidenseek.commands;

import hidenseek.hidenseek.HideNSeek;
import hidenseek.hidenseek.managers.GameManager;
import hidenseek.hidenseek.managers.GameState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class stopCommand implements CommandExecutor {

    private final GameManager gameManager;
    private final HideNSeek plugin;

    public stopCommand (HideNSeek pl, GameManager gm) {
        this.plugin = pl;
        this.gameManager = gm;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (this.gameManager.getGameState() == GameState.STOPPED || this.gameManager.getGameState() == null)
            sender.sendMessage(ChatColor.RED + "There is no game started");
        else
            this.gameManager.setGameState(GameState.STOPPED);

        return true;
    }
}
