import java.util.*;
import java.io.*;

public class SafariZone {

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    public static final Random random = new Random();
    public static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Pokemon> allPokemon;
    private static final ArrayList<Zone> allZones = new ArrayList<>(5);
    public static final String[] zoneNames = {"Desert", "Forest", "Grasslands", "Tundra", "Aquatic"};
    public static int pokeballs = 30;
    public static int berries = 5;
    public static int displayRates = 10; //max number of times to display catch/flee rate

    public static ArrayList<Pokemon> getAllPokemon() {return allPokemon;}

    public static ArrayList<Zone> getAllZones() {return allZones;}

    public static void loadAllPokemon(String name) {
        try{
            String fileName = switch (name) {
                case "K" -> "kanto";
                case "J" -> "johto";
                case "M" -> "pokemon";
                default -> "";
            };
            Scanner scanner = new Scanner(new File(fileName + ".txt"));
            int num = scanner.nextInt();
            scanner.nextLine();
            allPokemon = new ArrayList<>(num);
            for(int i = 0; i < num; i++){
                String s = scanner.nextLine();
                String[] input = s.split(",");
                Pokemon p = new Pokemon(input);
                allPokemon.add(p);
            }
            scanner.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File Not Found!");
            System.exit(1);
        }
    }

    public static void loadAllZones() {
        for(int i = 0; i < 5; i++){
            allZones.add(new Zone(zoneNames[i]));
        }
        /*
        0-14: Desert
        15-29: Forest
        30-44: Grasslands
        45-59: Tundra
        60-74: Aquatic
         */
        int counter = 0;
        for(Zone z : allZones){
            for(int j = 0; j <= 14; j++){
                Pokemon rp = allPokemon.get(j+counter);
                z.getZonePokemon().add(rp);
            }
            counter+=15;
        }
    }

    public static void safariZone() {
        System.out.println(YELLOW_BOLD + "+=====Welcome to the SAFARI ZONE!=====+" + RESET);
        System.out.println("You will enter the zone with " + WHITE_BOLD + pokeballs + RESET + " pokéballs and "
                + WHITE_BOLD + berries + RESET + " berries, your time is up when you run out of pokéballs or you exceed " + WHITE_BOLD + "100" + RESET + " steps.");
        System.out.print(YELLOW_BOLD + ">>" + RESET + "Would you like to enter? (Y/N): ");
        String choice = scanner.nextLine().toUpperCase();
        if(choice.equals("N")){
            System.out.println(RED_BOLD + "That's fine, bye!" + RESET);
            System.exit(0);
        }
        System.out.print(YELLOW_BOLD + ">>" + RESET + "What Pokémon region would you like? Kanto(K) Johto(J) Mix(M): ");
        String region = scanner.nextLine().toUpperCase();
        loadAllPokemon(region);
        loadAllZones();
        Pokemon player = allPokemon.get(random.nextInt(allPokemon.size())); //randomly choose player's Pokémon
        System.out.println("You have been assigned a random Pokémon to use on this adventure: " + YELLOW + player + RESET);
        int steps = 0; //every 10-20 steps, change zone
        Zone currentZone = allZones.get(random.nextInt(allZones.size()));
        boolean zoneChange = true;
        int total = 0; //total caught
        int totalSteps = 0; //can also end if 100 steps has been reached
        String zone = "--";
        label:
        while(true){
            if(totalSteps >= 100){
                System.out.println(RED_BOLD + "Limit of 100 steps has been reached!" + RESET);
                break;
            }
            if(pokeballs <= 0){
                System.out.println(RED_BOLD + "You have no more pokéballs left!" + RESET);
                break;
            }
            int target = random.nextInt(10,20);
            if(zoneChange){
                try{
                    System.out.print("Loading a new zone");
                    Thread.sleep(1000);
                    for(int i = 0; i < 3; i++){
                        System.out.print(".");
                        Thread.sleep(2000);
                    }
                    switch (currentZone.getzName()) {
                        case "Desert" -> {
                            zone = YELLOW_BOLD + currentZone.getzName() + RESET;
                            System.out.println("now entering the " + zone + " zone!");
                            System.out.println(YELLOW + currentZone + RESET);
                        }
                        case "Forest" -> {
                            zone = PURPLE_BOLD + currentZone.getzName() + RESET;
                            System.out.println("now entering the " + zone + " zone!");
                            System.out.println(PURPLE + currentZone + RESET);
                        }
                        case "Grasslands" -> {
                            zone = GREEN_BOLD + currentZone.getzName() + RESET;
                            System.out.println("now entering the " + zone + " zone!");
                            System.out.println(GREEN + currentZone + RESET);
                        }
                        case "Tundra" -> {
                            zone = CYAN_BOLD + currentZone.getzName() + RESET;
                            System.out.println("now entering the " + zone + " zone!");
                            System.out.println(CYAN + currentZone + RESET);
                        }
                        case "Aquatic" -> {
                            zone = BLUE_BOLD + currentZone.getzName() + RESET;
                            System.out.println("now entering the " + zone + " zone!");
                            System.out.println(BLUE + currentZone + RESET);
                        }
                    }
                    zoneChange = false;
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            if(steps == target){
                steps = 0;
                currentZone = allZones.get(random.nextInt(allZones.size()));
                zoneChange = true;
            }
            int encounter = random.nextInt(2); //0 for no encounter, 1 for an encounter
            int item = random.nextInt(4); //0-1 no item, 2 for pokéball, 3 for berry
            String input = options().toUpperCase();
            switch (input) {
                case "M":
                    System.out.print("You take a step...");
                    try{
                        Thread.sleep(500);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
                case "L":
                    if (item == 2) {
                        System.out.println(RED + "You found a pokéball!" + RESET);
                        ++pokeballs;
                    }
                    else if(item == 3){
                        System.out.println(RED + "You found a berry!" + RESET);
                        ++berries;
                    }
                    else System.out.println(WHITE + "There is no item to take." + RESET);
                    continue;
                case "S":
                    stats(zone, totalSteps, total, player);
                    continue;
                case "Q":
                    break label;
            }
            if(encounter == 1){
                System.out.println(YELLOW + "It's a BATTLE!" + RESET);
                int value = battle(currentZone, player);
                if(value == -1){
                    break;
                }
                total += value;
            }
            else{
                System.out.println(WHITE + "Nothing there." + RESET);
            }
            totalSteps++;
            steps++;
        }
        System.out.println(YELLOW_BOLD + "Your adventure has ended, here are some stats: " + RESET);
        stats(zone, totalSteps, total, player);
    }

    public static String options() {
        System.out.println(YELLOW_BOLD + "+=====OPTIONS=====+" + RESET);
        System.out.print(YELLOW_BOLD + ">>" + RESET + "Move(M) Look(L) Stats(S) Quit(Q): ");
        return scanner.nextLine().toUpperCase();
    }

    public static void stats(String zone, int totalSteps, int total, Pokemon player) {
        System.out.println(YELLOW_BOLD + "+=====STATS=====+" + RESET);
        System.out.println("Your pokémon: " + YELLOW + player + RESET);
        System.out.println("Current zone: " + zone);
        System.out.println("Current steps: " + WHITE_BOLD + totalSteps + RESET);
        System.out.println("Total caught: " + WHITE_BOLD + total + RESET);
        System.out.println("Total pokéballs: " + WHITE_BOLD + pokeballs + RESET);
        System.out.println("Total berries: " + WHITE_BOLD + berries + RESET);
        System.out.println("Total display rates uses left: " + WHITE_BOLD + displayRates + RESET);
    }

    public static int battle(Zone current, Pokemon player) {
        Pokemon opponent = current.getZonePokemon().get(random.nextInt(15)); //choose random Pokémon from indices 0-14
        System.out.println("A wild " + RED + opponent.getName() + RESET + " has appeared!");
        System.out.println("You sent out " + YELLOW + player + RESET + "!");
        int fleeRate = random.nextInt(6); //5 flees
        int catchRate = random.nextInt(4); //0 miss, 1-3 for catch
        boolean displayOn = false;
        if(displayRates > 0){
            System.out.print(YELLOW_BOLD + ">>" + RESET + "You have " + WHITE_BOLD + displayRates + RESET + " uses left, would you like to display catch/flee rates? (Y/N): ");
            String display = scanner.nextLine().toUpperCase();
            if(display.equals("Y")){
                displayOn = true;
                System.out.println(RED + "Catch/Flee rates will be shown during the entire battle." + RESET);
                --displayRates;
            }
        }
        else System.out.println(RED + "You have no display rates uses left!" + RESET);
        while(true){
            if(displayOn){
                System.out.println(WHITE + "CATCH RATE: " + catchRate + RESET);
                System.out.println(WHITE + "FLEE RATE: " + fleeRate + RESET);
            }
            System.out.println("--------" + RED + opponent + RESET + "--------\nVS.");
            System.out.println("--------" + YELLOW + player + RESET + "--------");
            System.out.print(YELLOW_BOLD + ">>" + RESET + "What would you like to do? Catch(C) Berry(B) Run(R): ");
            String choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "C" -> {
                    if (pokeballs > 0) {
                        System.out.print("You threw a pokéball...");
                        try{
                            Thread.sleep(1000);
                            for(int i = 1; i <= 3; i++){
                                System.out.print(i + "..");
                                Thread.sleep(1000);
                            }
                        }
                        catch(InterruptedException e){
                            e.printStackTrace();
                        }
                        if(fleeRate == 5){
                            System.out.println(RED + opponent.getName() + RESET + " is took quick and ran away!");
                            return 0;
                        }
                        if (catchRate > 0) {
                            System.out.println("you caught " + RED + opponent.getName() + RESET + "!");
                            pokeballs--;
                            return 1;
                        }
                        System.out.println(YELLOW + "your pokéball missed!" + RESET);
                        pokeballs--;
                        catchRate--;
                        fleeRate++;
                    } else {
                        System.out.println(YELLOW + "You have no more pokéballs left!" + RESET);
                        return -1;
                    }
                }
                case "B" -> {
                    if (berries > 0) {
                        System.out.println(RED + "You threw a berry! Catch rate increased and flee rate decreased." + RESET);
                        catchRate++;
                        fleeRate--;
                        --berries;
                    } else {
                        System.out.println(YELLOW + "You have no more berries left!" + RESET);
                        continue;
                    }
                }
                case "R" -> {
                    System.out.println("You ran away from " + RED + opponent.getName() + RESET + ".");
                    return 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        safariZone();
    }
}