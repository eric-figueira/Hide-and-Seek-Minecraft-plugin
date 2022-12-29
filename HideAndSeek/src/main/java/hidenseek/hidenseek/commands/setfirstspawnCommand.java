package hidenseek.hidenseek.commands;

import hidenseek.hidenseek.HideNSeek;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setfirstspawnCommand implements CommandExecutor {

    private final HideNSeek plugin;

    public setfirstspawnCommand(HideNSeek pl) {
        this.plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            Location l = p.getLocation();

            this.plugin.getConfig().set("firstspawn", l);
            this.plugin.saveConfig();

            p.sendMessage(ChatColor.GREEN + "The first spawn has been successfully set!");
        }
        else
            sender.sendMessage("Only players can use this command!");

        return true;
    }
}
