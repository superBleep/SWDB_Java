package sys.serv;

import characters.Character;
import characters.ForceUser;
import characters.JediSith;
import enums.StarWarsEra;
import sys.csv.WriteCsv;
import sys.logs.Log;
import weapons.Lightsaber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Predicate;

public class JediSithService {
    ArrayList<JediSith> v = new ArrayList<>();
    HashMap<Lightsaber, JediSith> map = new HashMap<>();
    WriteCsv wcsv = WriteCsv.getInstance();

    //create
    public void addJS(JediSith js, boolean read) throws IOException {
        Log.log("Adding jedi/sith: " + js.getName());
        if(!read) {
            wcsv.jedisith(js);
        }
        v.add(js);
    }

    //read
    public JediSith getJS(int i) {
        return v.get(i);
    }

    public void listJS() throws IOException {
        Log.log("Listing jedi/siths");
        v.forEach(System.out::println);
    }
    public void listJS_name() throws IOException {
        Log.log("Listing jedi/siths by name");
        v.stream()
                .sorted((p1,p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .forEach(System.out::println);
    }
    public void listJS_planet() throws IOException {
        Log.log("Listing jedi/siths by planet");
        v.stream()
                .sorted((p1,p2) -> p1.getPlanet().compareToIgnoreCase(p2.getPlanet()))
                .forEach(System.out::println);
    }
    public void listJS_born() throws IOException {
        Log.log("Listing jedi/siths by date of birth");
        v.stream()
                .sorted((p1,p2) -> {
                    //same era, return year
                    if(p1.getBorn().getEra().name().equals(p2.getBorn().getEra().name()))
                        //BBY, greater means older
                        if(p1.getBorn().getEra().name().equals(StarWarsEra.BBY.toString()))
                            return p1.getBorn().getYear().compareTo(p2.getBorn().getYear());
                        //ABY, greater means younger
                        else
                            return p1.getBorn().getYear().compareTo(p2.getBorn().getYear());
                    //different era
                    else
                        if(p1.getBorn().getEra().equals(StarWarsEra.BBY) && p2.getBorn().getEra().equals(StarWarsEra.ABY))
                            return -1;
                        else
                            return 1;
                })
                .forEach(System.out::println);
    }
    public void listJS_bounty() throws IOException {
        Log.log("Listing jedi/siths by bounty");
        v.stream()
                .sorted(Comparator.comparing(Character::getBounty))
                .forEach(System.out::println);
    }
    public void listJS_yrs_practice() throws IOException {
        Log.log("Listing jedi/siths by years of practice");
        v.stream()
                .sorted(Comparator.comparing(ForceUser::getYrs_practice))
                .forEach(System.out::println);
    }
    public void listJedi() throws IOException {
        Log.log("Listing jedi");
        Predicate<JediSith> filter = (JediSith js) -> (js.getType().name().equals("Jedi"));
        v.stream()
                .filter(filter)
                .forEach(System.out::println);
    }
    public void listSith() throws IOException {
        Log.log("Listing siths");
        Predicate<JediSith> filter = (JediSith js) -> (js.getType().name().equals("Sith"));
        v.stream()
                .filter(filter)
                .forEach(System.out::println);
    }

    public void listMap() throws IOException {
        Log.log("Listing jedi/sith - lightsabers binding");
        map.forEach((l, js) -> System.out.println(
                js.getType() + ": " + js.getRank() + ", " + js.getName() + "\n" +
                "Lightsaber: " + l.getType() + ", " + l.getHilt() + ", " + l.getColor()
        ));
    }

    //update
    public void addLightsaber(Lightsaber l, int i) throws IOException {
        if(map.containsKey(l)) {
            Log.log("Can't add: lightsaber already attached to someone");
        }
        else {
            Log.log("Adding lightsaber " + l.getHilt() + " to jedi/sith " + i);
            v.get(i).setLightsaber(l);
            map.put(l, v.get(i));
        }
    }
    public void replaceOwner(Lightsaber l, JediSith js) throws IOException {
        if(!map.containsKey(l))
            Log.log("Can't replace: lightsaber isn't mapped to a jedi/sith");
        else {
            Log.log("Changing owner of lightsaber " + l.getHilt() + " to " + js.getName());
            map.replace(l, js);
        }
    }

    //delete
    public void removeJS_index(int i) throws IOException {
        Log.log("Removing jedi/sith " + i);
        v.remove(i);
    }
    public void removeJS_name(String name) throws IOException {
        Log.log("Removing jedi/sith " + name);
        Predicate<JediSith> filter = (JediSith js) -> (js.getName().equalsIgnoreCase(name));
        v.removeIf(filter);
    }
    public void removeJS_planet(String planet) throws IOException {
        Log.log("Removing jedi/sith " + planet);
        Predicate<JediSith> filter = (JediSith js) -> (js.getPlanet().equalsIgnoreCase(planet));
        v.removeIf(filter);
    }
    public void removeJS_era(StarWarsEra era) throws IOException {
        Log.log("Removing jedi/sith born" + era);
        Predicate<JediSith> filter = (JediSith js) -> (js.getBorn().getEra().equals(era));
        v.removeIf(filter);
    }
}
