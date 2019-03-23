/*
An Armor class that holds all relevant information for a piece of armor.
type: the type of armor, e.g. 'helmet', 'chest', 'leggings', etc.
name: the name of the armor
armorValue: the armor class value of the piece of armor
cost: the amount (in crowns) that the armor costs
*/

public class Armor implements Comparable<Armor> {
    private final String type;
    private final String name;
    private final int armorValue;
    private final int cost;

    Armor(String type, String name, int cost, int armorValue) {
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.armorValue = armorValue;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getArmorValue() {
        return armorValue;
    }

    public String toString() {
        return getType() + " " + getName() + " " + getCost() + " " + getArmorValue();
    }

    /*
    We want to store in a max heap, so we reverse the comparable evaluations.
    If this armor piece has a greater value than the other piece, we want
    it higher in the priority queue and therefore return -1.
    A Java PriorityQueue by default is a min heap, so a value of -1 will
    place it higher in the heap.
    This allows us to get a max heap, where our priority is based on
    an armor's value per crowns spent (armorValue divided by cost).
    */
    public int compareTo(Armor other) {
        double thisValuePerCrown = this.getArmorValue() / (double) this.getCost();
        double otherValuePerCrown = other.getArmorValue() / (double) other.getCost();
        if(thisValuePerCrown > otherValuePerCrown) {
            return -1;
        }
        else if(thisValuePerCrown == otherValuePerCrown) {
            return 0;
        }
        else {
            return 1;
        }
    }
}
