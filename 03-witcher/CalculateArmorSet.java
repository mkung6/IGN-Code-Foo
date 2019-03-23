import java.util.*;
import java.io.*;

public class CalculateArmorSet {
    static final String FILENAME = "./inventory.txt";
    static PriorityQueue<Armor> chestQue;
    static PriorityQueue<Armor> leggingsQue;
    static PriorityQueue<Armor> helmetQue;
    static PriorityQueue<Armor> bootsQue;
    //An auxiliary list to store all items that have been previously used
    static ArrayList<Armor> aux;
    //Amount of crowns we have to spend
    static final int CROWNS = 300;

    public static void main(String args[]) throws IOException {
        chestQue = new PriorityQueue<Armor>();
        leggingsQue = new PriorityQueue<Armor>();
        helmetQue = new PriorityQueue<Armor>();
        bootsQue = new PriorityQueue<Armor>();
        readInventory();
        Witcher geralt = new Witcher();
        aux = new ArrayList<Armor>();
        calculateEquipment(geralt);
        printValues(geralt);
    }

    public static void calculateEquipment(Witcher geralt) {
        // create a list to store pointers to all our queues
        // in order to help simplify logic
        ArrayList<PriorityQueue<Armor>> quePointer = new ArrayList<PriorityQueue<Armor>>();
        quePointer.add(chestQue);
        quePointer.add(leggingsQue);
        quePointer.add(helmetQue);
        quePointer.add(bootsQue);
        equipMax(geralt);
        equipMisc(geralt, quePointer);
        // keep swapping armor if we exceed CROWNS
        while (geralt.getTotalArmorCost() > CROWNS) {
            reEquip(geralt, quePointer);
            equipMisc(geralt, quePointer);
        }
    }

    public static void reEquip(Witcher geralt, ArrayList<PriorityQueue<Armor>> quePointer) {
        // find the next best armor available of any type and equip it
        // then remove it from its queue and add it to the auxiliary list
        int index = getMaxIndex(quePointer);
        // repopulate all queues if they are empty
        if (index == -1) {
            unloadAux(geralt);
            index = getMaxIndex(quePointer);
        }
        switch(quePointer.get(index).peek().getType()) {
            case "Chest":
                aux.add(quePointer.get(index).peek());
                geralt.equipChest(quePointer.get(index).poll());
                break;
            case "Leggings":
                aux.add(quePointer.get(index).peek());
                geralt.equipLeggings(quePointer.get(index).poll());
                break;
            case "Helmet":
                aux.add(quePointer.get(index).peek());
                geralt.equipHelmet(quePointer.get(index).poll());
                break;
            case "Boots":
                aux.add(quePointer.get(index).peek());
                geralt.equipBoots(quePointer.get(index).poll());
                break;
        }
    }

    //greedily equip the highest possible armor value that we can afford
    public static void equipMisc(Witcher geralt, ArrayList<PriorityQueue<Armor>> quePointer) {
        int amountLeft = 0;
        if (geralt.getMisc() != null) {
            //don't count currently equipped misc item, to calculate how much total we can spend
            amountLeft = CROWNS - geralt.getTotalArmorCost() - geralt.getMisc().getCost();
        }
        else {
            amountLeft = CROWNS - geralt.getTotalArmorCost();
        }
        //if we have a positive amount left, then we exhaustively search for an item to buy
        if (amountLeft > 0) {
            //create a temporary dummy armor at its lowest values
            Armor maxArmor = new Armor("temp", "temp", 0, 0);
            //then try to find the maximum armor value we can buy
            for(int i = 0; i < aux.size(); i++) {
                if(!identifyDuplicate(aux.get(i), geralt)) {
                    if(aux.get(i).getCost() <= amountLeft
                        && aux.get(i).getArmorValue() > maxArmor.getArmorValue())
                    {
                        maxArmor = aux.get(i);
                    }
                }
            }
            maxArmor = searchQue(chestQue, maxArmor, amountLeft, geralt);
            maxArmor = searchQue(leggingsQue, maxArmor, amountLeft, geralt);
            maxArmor = searchQue(helmetQue, maxArmor, amountLeft, geralt);
            maxArmor = searchQue(bootsQue, maxArmor, amountLeft, geralt);
            geralt.equipMisc(maxArmor);
        }
        //no need to do anything else, because cost > CROWNS
    }

    //search a queue for an armor we can buy with the highest armor value
    //receive witcher as an argument so we can check for duplicates
    public static Armor searchQue(PriorityQueue<Armor> que, Armor armor, int crowns, Witcher geralt) {
        Armor temp = null;
        for(int i = 0; i < que.toArray().length; i++) {
            temp = (Armor) que.toArray()[i];
            if(temp.getCost() <= crowns
                && temp.getArmorValue() > armor.getArmorValue()
                && !identifyDuplicate(temp, geralt))
            {
                armor = temp;
            }
        }
        return armor;
    }

    public static int getMaxIndex(ArrayList<PriorityQueue<Armor>> quePointer) {
        double max = 0;
        double value;
        int index = -1;
        for (int i = 0; i < quePointer.size(); i++) {
            // evaluate if there is an item left in the queue, otherwise we skip it
            if (quePointer.get(i).peek() != null) {
                value = quePointer.get(i).peek().getArmorValue() / (double) quePointer.get(i).peek().getCost();
                if (value > max) {
                    max = value;
                    index = i;
                }
            }
        }
        return index;
    }

    // Greedily select the maximum value of armor per cost
    // which is already stored at the head of our queue.
    // Add the armor to our auxiliary list before removing it from queue.
    public static void equipMax(Witcher geralt) {
        if (chestQue.peek() != null) {
            aux.add(chestQue.peek());
            geralt.equipChest(chestQue.poll());
        }
        if (leggingsQue.peek() != null) {
            aux.add(leggingsQue.peek());
            geralt.equipLeggings(leggingsQue.poll());
        }
        if (helmetQue.peek() != null) {
            aux.add(helmetQue.peek());
            geralt.equipHelmet(helmetQue.poll());
        }
        if (bootsQue.peek() != null) {
            aux.add(bootsQue.peek());
            geralt.equipBoots(bootsQue.poll());
        }
    }

    public static void unloadAux(Witcher geralt) {
        //unload auxiliary list and re-populate the corresponding priority queues with them
        //skipping over already equipped items
        for (int i = 0; i < aux.size(); i++) {
            if(!identifyDuplicate(aux.get(i), geralt)) {
                fillQue(aux.get(i));
                aux.remove(i);
            }
        }
    }

    // identify if we already have a certain piece of armor equipped
    public static boolean identifyDuplicate(Armor input, Witcher geralt) {
        switch(input.getType()) {
            case "Chest":
                if(input.getName() == geralt.getChest().getName()) {
                    return true;
                }
                break;
            case "Leggings":
                if(input.getName() == geralt.getLeggings().getName()) {
                    return true;
                }
                break;
            case "Helmet":
                if(input.getName() == geralt.getHelmet().getName()) {
                    return true;
                }
                break;
            case "Boots":
                if(input.getName() == geralt.getBoots().getName()) {
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    public static void readInventory() throws IOException {
        File file = new File(FILENAME);
        Scanner inputFile = new Scanner(file);
        while(inputFile.hasNext()) {
            String type = inputFile.next();
            StringBuilder str = new StringBuilder();
            while(!inputFile.hasNextInt()) {
                // get the name of the armor
                str.append(inputFile.next());
                if(!inputFile.hasNextInt()) {
                    str.append(" ");
                }
            }
            String name = str.toString();
            int cost = inputFile.nextInt();
            int av = inputFile.nextInt();
            Armor temp = new Armor(type, name, cost, av);
            fillQue(temp);
        }
         inputFile.close();
    }

    // place the armor in the correct priority queue based on armor type
    public static void fillQue(Armor input){
        switch(input.getType()) {
            case "Chest":
                chestQue.add(input);
                break;
            case "Leggings":
                leggingsQue.add(input);
                break;
            case "Helmet":
                helmetQue.add(input);
                break;
            case "Boots":
                bootsQue.add(input);
                break;
        }
    }

    public static void printValues(Witcher geralt) {
        int totalCost;
        System.out.println("Witcher Geralt's armor set:");
        System.out.println(
            "Chest: " + geralt.getChest().getName()
            + " cost: " + geralt.getChest().getCost()
            + " armor value: " + geralt.getChest().getArmorValue()
        );
        System.out.println(
            "Leggings: " + geralt.getLeggings().getName()
            + " cost: " + geralt.getLeggings().getCost()
            + " armor value: " + geralt.getLeggings().getArmorValue()
        );
        System.out.println(
            "Helmet: " + geralt.getHelmet().getName()
            + " cost: " + geralt.getHelmet().getCost()
            + " armor value: " + geralt.getHelmet().getArmorValue()
        );
        System.out.println(
            "Boots: " + geralt.getBoots().getName()
            + " cost: " + geralt.getBoots().getCost()
            + " armor value: " + geralt.getBoots().getArmorValue()
        );
        System.out.println(
            "Extra piece: " + geralt.getMisc().getType()
            + " name: " + geralt.getMisc().getName()
            + " cost: " + geralt.getMisc().getCost()
            + " armor value: " + geralt.getMisc().getArmorValue()
        );
        System.out.println("Total cost: " + geralt.getTotalArmorCost());
        System.out.println("Total armor value: " + geralt.getTotalArmorValue());
    }
}
