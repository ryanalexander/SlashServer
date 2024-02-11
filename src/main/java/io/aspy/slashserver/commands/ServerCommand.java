package io.aspy.slashserver.commands;

import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import io.aspy.slashserver.SlashServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.awt.*;

public class ServerCommand implements RawCommand {
    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player)) {
            invocation.source().sendMessage(Component.text("Only players can use this command.").color(TextColor.color(Color.RED.getRGB())));
            return;
        }
        Player player = (Player) invocation.source();
        String commandName = invocation.alias();

        // Find registered server from command name
        RegisteredServer server = SlashServer.getInstance().getServer().getServer(commandName).orElse(null);

        if (server == null) { // This should not happen. But may if a server is removed while the server is running.
            player.sendMessage(Component.text("Server not found.").color(TextColor.color(Color.RED.getRGB())));
            return;
        }

        player.createConnectionRequest(server).fireAndForget();

    }
}
