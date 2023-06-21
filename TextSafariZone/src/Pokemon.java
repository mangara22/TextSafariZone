public class Pokemon {

    private final String name;
    private final String type;

    public Pokemon(String[] line){
        name = line[0];
        type = line[1];
    }

    public String toString() {return name + " [" + type + "]";}

    public String getName() {return name;}
    public String getType() {return type;}
}