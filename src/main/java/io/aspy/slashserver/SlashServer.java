package io.aspy.slashserver;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import io.aspy.slashserver.commands.ServerCommand;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class SlashServer {
    private static SlashServer instance;
    private ProxyServer server;
    private Logger logger;

    @Inject
    public SlashServer(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
        instance = this;
    }

    @Subscribe
    public void onProxyInit(ProxyInitializeEvent e) {
        Collection<RegisteredServer> servers = server.getAllServers();
        String[] serverNames = servers.stream().map(RegisteredServer::getServerInfo).map(ServerInfo::getName).toArray(String[]::new);
        CommandManager commandManager = server.getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder(serverNames[0]).aliases(serverNames).plugin(this).build();
        commandManager.register(commandMeta, new ServerCommand());
    }

    public ProxyServer getServer() {
        return server;
    }

    public static SlashServer getInstance() {
        return instance;
    }

}
