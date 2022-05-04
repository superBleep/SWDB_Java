package sys.serv;

import sys.logs.Log;
import weapons.Blaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class BlasterService {
    ArrayList<Blaster> v = new ArrayList<>();

    //create
    public void addB(Blaster b) throws IOException {
        Log.log("Adding blaster: " + b.getName());
        v.add(b);
    }

    //read
    public void listB() throws IOException {
        Log.log("Listing blasters");
        v.forEach(System.out::println);
    }

    //delete
    public void removeB_index(int i) throws IOException {
        Log.log("Removing blaster " + i);
        v.remove(i);
    }
    public void removeB_name(String name) throws IOException {
        Log.log("Removing blaster " + name);
        Predicate<Blaster> filter = (Blaster b) -> (b.getName().equalsIgnoreCase(name));
        v.removeIf(filter);
    }
}
