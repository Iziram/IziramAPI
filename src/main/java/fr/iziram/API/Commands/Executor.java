package fr.iziram.API.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class Executor implements CommandExecutor {

	private final HashMap<String, BiConsumer<CommandSender, String[]>> commandMap = new HashMap<>();
	private String defaultCommand = "help";
	private BiConsumer<CommandSender, String[]> invalidCommand = (commandSender, ignored) -> {
		commandSender.sendMessage(ChatColor.RED + "Cette commande n'existe pas.");
	};

	public Executor setCommand(String cmd, BiConsumer<CommandSender, String[]> consumer) {
		if (!commandMap.containsKey(cmd)) commandMap.put(cmd, consumer);
		else commandMap.replace(cmd, consumer);
		return this;
	}

	public Executor removeCommand(String cmd) {
		commandMap.remove(cmd);
		return this;
	}

	public BiConsumer<CommandSender, String[]> getCommand(String cmd) {
		return commandMap.getOrDefault(cmd, getInvalidCommand());

	}

	public BiConsumer<CommandSender, String[]> getDefaultCommand() {
		return commandMap.get(defaultCommand);
	}

	public Executor setDefaultCommand(String command) {
		if (commandMap.containsKey(command)) {
			defaultCommand = command;
		}

		return this;
	}

	public BiConsumer<CommandSender, String[]> getInvalidCommand() {
		return invalidCommand;
	}

	public Executor setInvalidCommand(BiConsumer<CommandSender, String[]> command) {
		this.invalidCommand = command;
		return this;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command ignored, String ignored1, String[] args) {

		if (args.length > 0) {
			String cmd = args[0];
			getCommand(cmd).accept(commandSender, args);
		} else {
			getDefaultCommand().accept(commandSender, args);
		}

		return false;
	}

	public boolean hasCommand(String cmd) {
		return commandMap.containsKey(cmd);
	}
}
