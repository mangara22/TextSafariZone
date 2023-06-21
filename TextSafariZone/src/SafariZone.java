import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class SafariZone {

    //ANSI Colors
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String WHITE_BOLD = "\033[1;37m";

    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Pokemon> allPokemon = new ArrayList<>(75);
    private static final ArrayList<Zone> allZones = new ArrayList<>(5);
    private static final String[] zoneNames = {"Desert", "Forest", "Grasslands", "Tundra", "Aquatic"};
    private static final ArrayList<Pokemon> safariPokemon = new ArrayList<>();
    private static int pokeballs = 30;
    private static int berries = 5;
    private static int bait = 20;
    private static int mud = 20;
    private static int displayRates = 10;

    //the important stuff
    public static ArrayList<Pokemon> getAllPokemon() {return allPokemon;}
    public static ArrayList<Zone> getAllZones() {return allZones;}

    /**
     * helps to "pause" the program to add some level of detail to this basic game
     * @param milliseconds number in ms for how long to delay the program
     */
    private static void delay(int milliseconds) {
        try{
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    private static void printSafariPokemon() {
        System.out.println(YELLOW + "+=====YOUR SAFARI POKÉMON=====+" + RESET);
        if(safariPokemon.size() == 0){
            System.out.println(WHITE + "No Pokémon caught yet." + RESET);
        }
        for(Pokemon p : safariPokemon){
            System.out.println(WHITE + p + RESET);
        }
    }

    /**
     * loads 75 Pokémon from a .txt file
     * @param name either K, J, or M depending on user input, represents 2 Pokémon regions and one mixed
     */
    public static void loadAllPokemon(String name) {
        try{
            String fileName = switch (name) {
                case "K" -> "kanto";
                case "J" -> "johto";
                case "M" -> "pokemon";
                default -> "";
            };
            Scanner scanner = new Scanner(new File(fileName + ".txt"));
            for(int i = 0; i < 75; i++){
                String s = scanner.nextLine();
                String[] input = s.split(",");
                allPokemon.add(new Pokemon(input));
            }
            scanner.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File Not Found!");
            System.exit(1);
        }
    }

    /**
     * loads all 5 zones by making a new zone with a name, and then populating it with 15 Pokémon from the list "allPokémon"
     */
    public static void loadAllZones() {
        for(int i = 0; i < 5; i++){
            allZones.add(new Zone(zoneNames[i]));
        }
        /*
        Indices:
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

    /**
     * starts and continues the Safari Zone game by asking for commands and displaying important aspects of the game
     */
    public static void safariZone() {
        System.out.println(YELLOW_BOLD + "+=====Welcome to the SAFARI ZONE!=====+" + RESET);
        System.out.println("You will enter the zone with " + WHITE_BOLD + pokeballs + RESET + " pokéballs along with some bait, mud, and berries. \nYour adventure will end when you run out of pokéballs or you exceed " + WHITE_BOLD + "100" + RESET + " steps.");
        System.out.print(YELLOW_BOLD + ">>" + RESET + "Would you like to enter? (Y/N): ");
        String choice = scanner.nextLine().toUpperCase();
        if(choice.equals("N")){
            System.out.println(RED_BOLD + "That's fine :(, bye!" + RESET);
            System.exit(0);
        }
        System.out.print(YELLOW_BOLD + ">>" + RESET + "What Pokémon region would you like? Kanto(K) Johto(J) Mix(M): ");
        String region = scanner.nextLine().toUpperCase();
        loadAllPokemon(region);
        loadAllZones();
        long start = System.currentTimeMillis();
        int steps = 0; //every 10-20 steps, change zone
        Zone currentZone = allZones.get(random.nextInt(allZones.size())); //get random zone
        boolean zoneChange = true;
        int totalSteps = 0;
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
                System.out.print(WHITE_BOLD + "Loading a new zone" + RESET);
                delay(1000);
                for(int i = 0; i < 3; i++){
                    System.out.print(WHITE_BOLD + "." + RESET);
                    delay(1000);
                }
                switch (currentZone.getzName()) {
                    case "Desert" -> {
                        zone = YELLOW_BOLD + currentZone.getzName() + RESET;
                        System.out.println("now entering the " + zone + " zone!");
                        delay(1000);
                        System.out.println(YELLOW + "All you see are hills of sand..lots of sand..." + RESET);
                        delay(1500);
                        System.out.println(YELLOW + currentZone + RESET);
                    }
                    case "Forest" -> {
                        zone = PURPLE_BOLD + currentZone.getzName() + RESET;
                        System.out.println("now entering the " + zone + " zone!");
                        delay(1000);
                        System.out.println(PURPLE + "You enter a lush forest area..." + RESET);
                        delay(1500);
                        System.out.println(PURPLE + currentZone + RESET);
                    }
                    case "Grasslands" -> {
                        zone = GREEN_BOLD + currentZone.getzName() + RESET;
                        System.out.println("now entering the " + zone + " zone!");
                        delay(1000);
                        System.out.println(GREEN + "You see a big grassy open area, with towering mountains in the distance..." + RESET);
                        delay(1500);
                        System.out.println(GREEN + currentZone + RESET);
                    }
                    case "Tundra" -> {
                        zone = CYAN_BOLD + currentZone.getzName() + RESET;
                        System.out.println("now entering the " + zone + " zone!");
                        delay(1000);
                        System.out.println(CYAN + "It's..really..cold..here..." + RESET);
                        delay(1500);
                        System.out.println(CYAN + currentZone + RESET);
                    }
                    case "Aquatic" -> {
                        zone = BLUE_BOLD + currentZone.getzName() + RESET;
                        System.out.println("now entering the " + zone + " zone!");
                        delay(1000);
                        System.out.println(BLUE + "You see nothing but some beaches and a lot of water..." + RESET);
                        delay(1500);
                        System.out.println(BLUE + currentZone + RESET);
                    }
                }
                zoneChange = false;
            }
            if(steps == target){ //choose new random zone
                steps = 0;
                currentZone = allZones.get(random.nextInt(allZones.size()));
                zoneChange = true;
            }
            int encounter = random.nextInt(2); //0 for no encounter, 1 for an encounter
            int item = random.nextInt(8); //0-1 for pokéball, 2-3 for bait, 4-5 for mud, 6 for berry, 7 no item
            String input = options().toUpperCase();
            switch (input) {
                case "M" -> {
                    System.out.print("You take a step...");
                    delay(500);
                }
                case "L" -> {
                    if (item == 0 || item == 1) {
                        System.out.println(RED + "You found a pokéball!" + RESET);
                        pokeballs++;
                    } else if (item == 2 || item == 3) {
                        System.out.println(RED + "You found some bait!" + RESET);
                        bait += random.nextInt(1,4);
                    }
                    else if (item == 4 || item == 5) {
                        System.out.println(RED + "You found some balls of mud!" + RESET);
                        mud += random.nextInt(1,4);
                    }
                    else if (item == 6) {
                        System.out.println(YELLOW + "You found a berry!" + RESET);
                        berries++;
                    }
                    else System.out.println(WHITE + "There is no item to take." + RESET);
                    continue;
                }
                case "S" -> {
                    stats(zone, totalSteps);
                    continue;
                }
                case "Q" -> {
                    System.out.println(RED_BOLD + "You quit your adventure." + RESET);
                    break label;
                }
            }
            if(encounter == 1){
                System.out.println(YELLOW + "It's a BATTLE!" + RESET);
                int value = battle(currentZone);
                if(value == -1){
                    break;
                }
            }
            else{
                System.out.println(WHITE + "Nothing there." + RESET);
            }
            totalSteps++;
            steps++;
        }
        long end = System.currentTimeMillis();
        long timeSpent = end - start;
        System.out.println(YELLOW_BOLD + "Your adventure has ended, here are some stats: " + RESET);
        stats(zone, totalSteps);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeSpent);
        long seconds = (TimeUnit.MILLISECONDS.toSeconds(timeSpent)) % 60;
        System.out.print("Total Time spent: " + WHITE_BOLD + minutes + RESET + " minutes");
        System.out.println(", " + WHITE_BOLD + seconds + RESET + " seconds");
    }

    /**
     * prints out the commands for the user to use during the game
     * @return - the user's input in uppercase
     */
    public static String options() {
        System.out.println(YELLOW_BOLD + "+=====OPTIONS=====+" + RESET);
        System.out.print(YELLOW_BOLD + ">>" + RESET + "Move(M) Look(L) Stats(S) Quit(Q): ");
        return scanner.nextLine().toUpperCase();
    }

    /**
     * prints out user stats
     * @param zone the user's current zone
     * @param totalSteps the user's total steps taken in the game
     */
    public static void stats(String zone, int totalSteps) {
        System.out.println(YELLOW_BOLD + "+=====STATS=====+" + RESET);
        System.out.println("Current zone: " + zone);
        System.out.println("Current steps: " + WHITE_BOLD + totalSteps + RESET);
        System.out.println("Total Pokémon caught: " + WHITE_BOLD + safariPokemon.size() + RESET);
        System.out.println("Total pokéballs: " + WHITE_BOLD + pokeballs + RESET);
        System.out.println("Total bait: " + WHITE_BOLD + bait + RESET);
        System.out.println("Total mud: " + WHITE_BOLD + mud + RESET);
        System.out.println("Total berries: " + WHITE_BOLD + berries + RESET);
        System.out.println("Total display rates uses left: " + WHITE_BOLD + displayRates + RESET);
        printSafariPokemon();
    }

    /**
     * starts and continues a Pokémon battle by displaying opponent, user commands, etc.
     * @param current the user's current zone they're in, to choose a random Pokémon native to that current zone
     * @return an integer representing the outcome of the battle: 0 for not caught, 1 for caught, -1 for out of pokéballs
     */
    public static int battle(Zone current) {
        Pokemon opponent = current.getZonePokemon().get(random.nextInt(15)); //choose random Pokémon from indices 0-14
        System.out.println("A wild " + RED + opponent.getName() + RESET + " has appeared!");
        int fleeRate = random.nextInt(6); //4-5 flees
        int catchRate = random.nextInt(5); //0-2 miss, 3-4 for catch
        boolean displayOn = false;
        if(displayRates > 0){
            System.out.print(YELLOW_BOLD + ">>" + RESET + "You have " + WHITE_BOLD + displayRates + RESET + " uses left, would you like to display catch/flee rates? (Y/N): ");
            String display = scanner.nextLine().toUpperCase();
            if(display.equals("Y")){
                displayOn = true;
                System.out.println(WHITE_BOLD + "Catch/Flee rates will be shown during the entire battle." + RESET);
                displayRates--;
            }
        }
        else System.out.println(RED + "You have no display rates uses left!" + RESET);
        while(true){
            if(displayOn){
                System.out.println(WHITE + "CATCH RATE: " + catchRate + RESET);
                System.out.println(WHITE + "FLEE RATE: " + fleeRate + RESET);
            }
            System.out.println("--------" + RED + opponent + RESET + "--------");
            System.out.print(YELLOW_BOLD + ">>" + RESET + "What would you like to do? Catch(C) Bait(T) Mud(M) Berry(B) Run(R): ");
            String choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "C" -> {
                    if (pokeballs > 0) {
                        System.out.print("You threw a pokéball...");
                        delay(1000);
                        for(int i = 1; i <= 3; i++){
                            System.out.print(i + "..");
                            delay(1000);
                        }
                        if(fleeRate >= 4) {
                            System.out.println(RED + opponent.getName() + RESET + " is took quick and ran away!");
                            return 0;
                        }
                        else if (catchRate >= 3) {
                            System.out.println("you caught " + RED + opponent.getName() + RESET + "!");
                            safariPokemon.add(opponent);
                            pokeballs--;
                            return 1;
                        }
                        System.out.println(RED + opponent.getName() + RESET + " broke out of the pokéball!");
                        pokeballs--;
                        catchRate--;
                        fleeRate++;
                    } else {
                        System.out.println(YELLOW + "You have no more pokéballs left!" + RESET);
                        return -1;
                    }
                }
                case "T" -> {
                    if(bait > 0) {
                        System.out.println(WHITE_BOLD + "You threw some bait! Flee rate decreased but catch rate decreased too." + RESET);
                        bait--;
                        fleeRate--;
                        catchRate--;
                    } else System.out.println(YELLOW + "You have no more bait left!");
                }
                case "M" -> {
                    if(mud > 0) {
                        System.out.println(WHITE_BOLD + "You threw some mud! Catch rate increased but flee rate increased too.");
                        mud--;
                        catchRate++;
                        fleeRate++;
                    } else System.out.println(YELLOW + "You have no more mud left!");
                }
                case "B" -> {
                    if (berries > 0) {
                        System.out.println(WHITE_BOLD + "You threw a berry! Catch rate increased and flee rate decreased." + RESET);
                        berries--;
                        catchRate++;
                        fleeRate--;
                    } else System.out.println(YELLOW + "You have no more berries left!" + RESET);
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