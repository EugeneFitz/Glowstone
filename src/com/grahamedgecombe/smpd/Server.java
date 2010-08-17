package com.grahamedgecombe.smpd;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.grahamedgecombe.smpd.net.MinecraftPipelineFactory;

public final class Server {

	private static final Logger logger = Logger.getLogger(Server.class.getName());

	public static void main(String[] args) {
		try {
			Server server = new Server();
			server.bind(new InetSocketAddress(25565));
			server.start();
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "Error during server startup.", t);
		}
	}

	private final ServerBootstrap bootstrap = new ServerBootstrap();
	private final ExecutorService executor = Executors.newCachedThreadPool();
	private final ChannelGroup group = new DefaultChannelGroup();

	public Server() {
		logger.info("Starting minecraft-smpd...");
		init();
	}

	private void init() {
		ChannelFactory factory = new NioServerSocketChannelFactory(executor, executor);
		bootstrap.setFactory(factory);
		
		ChannelPipelineFactory pipelineFactory = new MinecraftPipelineFactory(group);
		bootstrap.setPipelineFactory(pipelineFactory);
	}

	public void bind(SocketAddress address) {
		logger.info("Binding to address: " + address + "...");
		group.add(bootstrap.bind(address));
	}

	public void start() {
		logger.info("Ready for connections.");
	}

}
