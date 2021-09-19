package fr.iziram.API.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * Cette classe permet d'ajouter facilement des commandes à partir d'une command principale
 * Voir utilisation sur le Wiki
 */
public class Executor implements CommandExecutor {

	private final HashMap<String, BiConsumer<CommandSender, String[]>> commandMap = new HashMap<>();
	private String defaultCommand = "help";
	private BiConsumer<CommandSender, String[]> invalidCommand = (commandSender, ignored) -> {
		commandSender.sendMessage(ChatColor.RED + "Cette commande n'existe pas.");
	};

	/**
	 * Cette fonction permet d'ajouter / remplacer une commande dans l'executor
	 * example : /iziram test 1234 | iziram = commande principale / test = cmd / 1234 = params
	 * @param cmd → identifiant de la commande
	 * @param consumer → BiConsumer utilisant un CommandSender et la liste des Arguments
	 *                    (attention, le premier argument sera égal à cmd)
	 * @return MenuBuilder → renvoie l'instance Executor afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public Executor setCommand(String cmd, BiConsumer<CommandSender, String[]> consumer) {
		if (!commandMap.containsKey(cmd)) commandMap.put(cmd, consumer);
		else commandMap.replace(cmd, consumer);
		return this;
	}

	/**
	 * Cette fonction permet supprimer une commande de l'executor
	 * @param cmd → identifiant de la commande
	 * @return MenuBuilder → renvoie l'instance Executor afin de pouvoir enchaîner les instructions (voir wiki)
	 */
	public Executor removeCommand(String cmd) {
		commandMap.remove(cmd);
		return this;
	}
	/**
	 * Cette fonction permet récupérer une commande à partir de l'executor
	 * @param cmd → identifiant de la commande
	 * @return → BiConsumer utilisant un CommandSender et la liste des Arguments
	 */
	public BiConsumer<CommandSender, String[]> getCommand(String cmd) {
		return commandMap.getOrDefault(cmd, getInvalidCommand());

	}

	/**
	 * Cette fonction permet récupérer la commande par défaut de l'executor
	 * @return → BiConsumer utilisant un CommandSender et la liste des Arguments
	 */
	public BiConsumer<CommandSender, String[]> getDefaultCommand() {
		return commandMap.get(defaultCommand);
	}

	/**
	 * Cette fonction permet changer la commande par défaut de l'executor
	 * @param command → BiConsumer utilisant un CommandSender et la liste des Arguments
	 *                   (attention, le premier argument sera égal à cmd)
	 * @return → BiConsumer utilisant un CommandSender et la liste des Arguments
	 */
	public Executor setDefaultCommand(String command) {
		if (commandMap.containsKey(command)) {
			defaultCommand = command;
		}

		return this;
	}
	/**
	 * Cette fonction permet récupérer la commande utilisée lorsque qu'une commande non reconnue est utilisée
	 * @return → BiConsumer utilisant un CommandSender et la liste des Arguments
	 */
	public BiConsumer<CommandSender, String[]> getInvalidCommand() {
		return invalidCommand;
	}
	/**
	 * Cette fonction permet changer la commande utilisée lorsque qu'une commande non reconnue est utilisée
	 * @param command → BiConsumer utilisant un CommandSender et la liste des Arguments
	 *                   (attention, le premier argument sera égal à cmd)
	 * @return → BiConsumer utilisant un CommandSender et la liste des Arguments
	 */
	public Executor setInvalidCommand(BiConsumer<CommandSender, String[]> command) {
		this.invalidCommand = command;
		return this;
	}

	/**
	 Cette fonction remplace l'executor normal.
	 */
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

	/**
	 * Cette fonction permet de savoir si une commande existe dans l'executor
	 * @param cmd → identifiant de la commande
	 * @return → renvoie un boolean, True si la commande existe sinon False
	 */
	public boolean hasCommand(String cmd) {
		return commandMap.containsKey(cmd);
	}
}
