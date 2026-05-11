package org.neelemv.lotr_craft.command;

import java.util.Collection;
import java.util.Locale;

import org.neelemv.lotr_craft.faction.LotrFaction;
import org.neelemv.lotr_craft.faction.PlayerAlignments;
import org.neelemv.lotr_craft.network.LotrNetworking;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.server.level.ServerPlayer;

public final class LotrCommands {
    private static final SimpleCommandExceptionType INVALID_FACTION = new SimpleCommandExceptionType(Component.literal("Unknown LOTR faction"));
    private static final Iterable<String> FACTION_SUGGESTIONS = playableFactionSuggestions();

    private LotrCommands() {
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(Commands.literal("alignment")
                .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
                .then(Commands.literal("set")
                        .then(Commands.argument("faction", StringArgumentType.word())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(FACTION_SUGGESTIONS, builder))
                                .then(Commands.argument("value", FloatArgumentType.floatArg())
                                        .executes(context -> setAlignment(
                                                context.getSource(),
                                                java.util.List.of(context.getSource().getPlayerOrException()),
                                                StringArgumentType.getString(context, "faction"),
                                                FloatArgumentType.getFloat(context, "value"))))))
                .then(Commands.literal("setplayer")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("faction", StringArgumentType.word())
                                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(FACTION_SUGGESTIONS, builder))
                                        .then(Commands.argument("value", FloatArgumentType.floatArg())
                                                .executes(context -> setAlignment(
                                                        context.getSource(),
                                                        EntityArgument.getPlayers(context, "targets"),
                                                        StringArgumentType.getString(context, "faction"),
                                                        FloatArgumentType.getFloat(context, "value")))))))));
    }

    private static int setAlignment(CommandSourceStack source, Collection<ServerPlayer> players, String factionName, float value) throws CommandSyntaxException {
        LotrFaction faction = parseFaction(factionName);
        for (ServerPlayer player : players) {
            PlayerAlignments.set(player, faction, value);
            LotrNetworking.syncFactionAlignments(player);
        }
        source.sendSuccess(() -> Component.literal("Set " + players.size() + " player(s) to " + format(value) + " alignment with " + faction.name().toLowerCase(Locale.ROOT)), true);
        return players.size();
    }

    private static LotrFaction parseFaction(String name) throws CommandSyntaxException {
        String normalized = name.toUpperCase(Locale.ROOT);
        for (LotrFaction faction : LotrFaction.values()) {
            if (faction.playerAllowed() && !faction.hasFixedAlignment() && faction.name().equals(normalized)) {
                return faction;
            }
        }
        throw INVALID_FACTION.create();
    }

    private static Iterable<String> playableFactionSuggestions() {
        java.util.List<String> suggestions = new java.util.ArrayList<>();
        for (LotrFaction faction : LotrFaction.values()) {
            if (faction.playerAllowed() && !faction.hasFixedAlignment()) {
                suggestions.add(faction.name().toLowerCase(Locale.ROOT));
            }
        }
        return java.util.List.copyOf(suggestions);
    }

    private static String format(float value) {
        return (value > 0.0F ? "+" : "") + String.format(Locale.ROOT, "%.1f", value);
    }
}
