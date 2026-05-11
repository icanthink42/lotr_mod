package org.neelemv.lotr_craft.faction;

import org.neelemv.lotr_craft.entity.HobbitKind;
import org.neelemv.lotr_craft.entity.LotrFactioned;
import org.neelemv.lotr_craft.entity.LotrHobbitEntity;
import org.neelemv.lotr_craft.entity.LotrHumanoidNpcEntity;
import org.neelemv.lotr_craft.network.LotrNetworking;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public final class PlayerAlignmentKills {
    private PlayerAlignmentKills() {
    }

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(PlayerAlignmentKills::onDeath);
    }

    private static void onDeath(LivingEntity killed, DamageSource source) {
        if (!(killed instanceof LotrFactioned factionedKilled)) {
            return;
        }
        ServerPlayer killer = killer(killed, source);
        if (killer == null || killer == killed) {
            return;
        }
        LotrFaction killedFaction = factionedKilled.lotrFaction();
        if (!killedFaction.playerAllowed()) {
            return;
        }
        float value = killValue(killed);
        if (value <= 0.0F) {
            return;
        }
        boolean changed = false;
        for (LotrFaction faction : LotrFaction.values()) {
            if (!faction.playerAllowed() || faction.hasFixedAlignment()) {
                continue;
            }
            float change = alignmentChangeForKill(killedFaction, faction, value, killer);
            if (change != 0.0F) {
                PlayerAlignments.add(killer, faction, change);
                changed = true;
            }
        }
        if (changed) {
            LotrNetworking.syncFactionAlignments(killer);
        }
    }

    private static ServerPlayer killer(LivingEntity killed, DamageSource source) {
        if (source.getEntity() instanceof ServerPlayer player) {
            return player;
        }
        if (killed.getKillCredit() instanceof ServerPlayer player) {
            return player;
        }
        return null;
    }

    private static float alignmentChangeForKill(LotrFaction killedFaction, LotrFaction affectedFaction, float value, ServerPlayer player) {
        if (affectedFaction == killedFaction) {
            return scalePenalty(-value, PlayerAlignments.get(player, affectedFaction));
        }
        FactionRelation relation = LotrFactionRelations.relationBetween(killedFaction, affectedFaction);
        return switch (relation) {
            case ALLY, FRIEND -> scalePenalty(-value, PlayerAlignments.get(player, affectedFaction));
            case ENEMY, MORTAL_ENEMY -> value;
            case NEUTRAL -> 0.0F;
        };
    }

    private static float scalePenalty(float penalty, float currentAlignment) {
        if (currentAlignment <= 0.0F || penalty >= 0.0F) {
            return penalty;
        }
        float factor = Math.clamp(currentAlignment / 50.0F, 1.0F, 20.0F);
        return penalty * factor;
    }

    private static float killValue(LivingEntity killed) {
        if (killed instanceof LotrHobbitEntity hobbit) {
            return switch (hobbit.kind()) {
                case HOBBIT_SHIRRIFF -> 5.0F;
                case HOBBIT_BOUNDER, HOBBIT_BARTENDER, HOBBIT_ORCHARDER, HOBBIT_FARMER, HOBBIT_FARMHAND,
                        BREE_HOBBIT_INNKEEPER, BREE_HOBBIT_BAKER, BREE_HOBBIT_BUTCHER, BREE_HOBBIT_BREWER,
                        BREE_HOBBIT_FLORIST -> 2.0F;
                case HOBBIT, BREE_HOBBIT -> 1.0F;
            };
        }
        if (killed instanceof LotrHumanoidNpcEntity npc) {
            String id = npc.kind().id();
            if (containsAny(id, "captain", "chieftain", "warlord", "commander", "marshal", "lord")) {
                return 5.0F;
            }
            if (containsAny(id, "warrior", "soldier", "guard", "archer", "crossbower", "sapper", "bombardier", "berserker",
                    "uruk", "scout", "knight", "axeman", "marine", "levyman", "ranger", "bounder", "shirriff", "trader",
                    "merchant", "blacksmith", "smith", "farmer", "farmhand", "baker", "butcher", "brewer", "mason",
                    "florist", "lumberman", "miner", "scavenger", "slaver", "hunter", "huntsman", "hutmaker", "shaman",
                    "vintner", "vinehand", "vinekeeper", "stablemaster", "market", "orcharder", "goldsmith", "fishmonger",
                    "greengrocer", "meadhost", "bartender", "innkeeper")) {
                return 2.0F;
            }
            return 1.0F;
        }
        return 1.0F;
    }

    private static boolean containsAny(String id, String... needles) {
        for (String needle : needles) {
            if (id.contains(needle)) {
                return true;
            }
        }
        return false;
    }
}
