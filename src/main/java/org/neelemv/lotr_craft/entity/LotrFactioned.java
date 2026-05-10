package org.neelemv.lotr_craft.entity;

import org.neelemv.lotr_craft.faction.FactionRelation;
import org.neelemv.lotr_craft.faction.LotrFaction;
import org.neelemv.lotr_craft.faction.LotrFactionRelations;

public interface LotrFactioned {
    LotrFaction lotrFaction();

    static boolean areEnemies(LotrFactioned first, LotrFactioned second) {
        FactionRelation relation = LotrFactionRelations.relationBetween(first.lotrFaction(), second.lotrFaction());
        return relation == FactionRelation.ENEMY || relation == FactionRelation.MORTAL_ENEMY;
    }

    static boolean areAllied(LotrFactioned first, LotrFactioned second) {
        FactionRelation relation = LotrFactionRelations.relationBetween(first.lotrFaction(), second.lotrFaction());
        return relation == FactionRelation.ALLY;
    }

    static boolean areFriendly(LotrFactioned first, LotrFactioned second) {
        FactionRelation relation = LotrFactionRelations.relationBetween(first.lotrFaction(), second.lotrFaction());
        return relation == FactionRelation.ALLY || relation == FactionRelation.FRIEND;
    }
}
