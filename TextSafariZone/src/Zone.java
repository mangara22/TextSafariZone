import java.util.*;
public class Zone {

    private final String zName;
    private final ArrayList<Pokemon> zonePokemon = new ArrayList<>();

    public Zone(String name){
        zName = name;
    }

    public ArrayList<Pokemon> getZonePokemon() {return zonePokemon;}

    public String getzName() {return zName;}

    public String toString() {
        StringBuilder value = new StringBuilder();
        value.append("+=====").append(zName).append("=====+\n");
        for(Pokemon p : zonePokemon){
            value.append(p).append("\n");
        }
        return value.toString();
    }
}
