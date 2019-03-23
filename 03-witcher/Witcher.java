/*
A Witcher class that equips and stores armor information.
A full set of armor requires one piece of armor of each type (chest,
leggings, helmet, boots) as well as an extra piece that can be of any type.
Here, we call the extra piece "misc".
We also store and update the current total armor cost,
and current total armor value.
*/

public class Witcher {
    private Armor chest;
    private Armor leggings;
    private Armor helmet;
    private Armor boots;
    private Armor misc;
    private int totalArmorCost;
    private int totalArmorValue;

    Witcher() {
        chest = null;
        leggings = null;
        helmet = null;
        boots = null;
        misc = null;
        totalArmorCost = 0;
        totalArmorValue = 0;
    }

    public void equipChest(Armor chest) {
        //if witcher is already equipping something,
        if(this.chest != null) {
            //then remove its armor value
            totalArmorValue -= this.chest.getArmorValue();
            totalArmorCost -= this.chest.getCost();
            //equip the new piece
            this.chest = chest;
            //increment total armor value by new equipped piece
            totalArmorValue += chest.getArmorValue();
            totalArmorValue += chest.getCost();
        }
        //otherwise if we want to unequip a piece
        else if(chest == null) {
            //remove the armor value from currently equipped piece
            totalArmorValue -= this.chest.getArmorValue();
            totalArmorCost -= this.chest.getCost();
            //unequip the item
            this.chest = null;
        }
        //otherwise if witcher has nothing equipped here
        //i.e. this.chest == null
        else {
            //then equip the new item
            this.chest = chest;
            //and increment total armor value by equipped piece
            totalArmorValue += chest.getArmorValue();
            totalArmorCost += chest.getCost();
        }
    }

    public void equipLeggings(Armor leggings) {
        if(this.leggings != null) {
            totalArmorValue -= this.leggings.getArmorValue();
            totalArmorCost -= this.leggings.getCost();
            this.leggings = leggings;
            totalArmorValue += leggings.getArmorValue();
            totalArmorCost += leggings.getCost();
        }
        else if(leggings == null) {
            totalArmorValue -= this.leggings.getArmorValue();
            totalArmorCost -= this.leggings.getCost();
            this.leggings = null;
        }
        else {
            this.leggings = leggings;
            totalArmorValue += leggings.getArmorValue();
            totalArmorCost += leggings.getCost();
        }
    }

    public void equipHelmet(Armor helmet) {
        if(this.helmet != null) {
            totalArmorValue -= this.helmet.getArmorValue();
            totalArmorCost -= this.helmet.getCost();
            this.helmet = helmet;
            totalArmorValue += helmet.getArmorValue();
            totalArmorCost += helmet.getCost();
        }
        else if(helmet == null) {
            totalArmorValue -= this.helmet.getArmorValue();
            totalArmorCost -= this.helmet.getCost();
            this.helmet = null;
        }
        else {
            this.helmet = helmet;
            totalArmorValue += helmet.getArmorValue();
            totalArmorCost += helmet.getCost();
        }
    }

    public void equipBoots(Armor boots) {
        if(this.boots != null) {
            totalArmorValue -= this.boots.getArmorValue();
            totalArmorCost -= this.boots.getCost();;
            this.boots = boots;
            totalArmorValue += boots.getArmorValue();
            totalArmorCost += boots.getCost();
        }
        else if(boots == null) {
            totalArmorValue -= this.boots.getArmorValue();
            totalArmorValue -= this.boots.getCost();
            this.boots = null;
        }
        else {
            this.boots = boots;
            totalArmorValue += boots.getArmorValue();
            totalArmorCost += boots.getCost();
        }
    }

    public void equipMisc(Armor misc) {
        if(this.misc != null) {
            totalArmorValue -= this.misc.getArmorValue();
            totalArmorCost -= this.misc.getCost();
            this.misc = misc;
            totalArmorValue += misc.getArmorValue();
            totalArmorCost += misc.getCost();
        }
        else if(misc == null) {
            totalArmorValue -= this.misc.getArmorValue();
            totalArmorCost -= this.misc.getCost();
            this.misc = null;
        }
        else {
            this.misc = misc;
            totalArmorValue += misc.getArmorValue();
            totalArmorCost += misc.getCost();
        }
    }

    public Armor getChest() {
        return chest;
    }

    public Armor getLeggings() {
        return leggings;
    }

    public Armor getHelmet() {
        return helmet;
    }

    public Armor getBoots() {
        return boots;
    }

    public Armor getMisc() {
        return misc;
    }

    public int getTotalArmorCost() {
        return totalArmorCost;
    }

    public int getTotalArmorValue() {
        return totalArmorValue;
    }
}
