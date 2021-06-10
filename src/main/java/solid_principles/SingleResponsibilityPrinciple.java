package main.java.solid_principles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/*
    (Avoid God object that contains everything – Anti=Pattern techinique)
     Focus on Seperation of Concerns
    "There should never be more than one reason for a class to change."
    In other words, every class should have only one responsibility.”
    The save() method was moved from Journal to a new class, Persistence, to have 1 responsibility per class.

*/
public class SingleResponsibilityPrinciple{
    public static void main(String[] args) throws Exception {
        Journal j = new Journal();
        j.addEntry("Woke up late");
        j.addEntry("Went for a ride");
        System.out.println(j); //invokes toString()

        Persistence persistence =  new Persistence();
        persistence.save(j,"test123.txt", true);
    }
}
class Journal {

    private final List<String> entries= new ArrayList<>();
    private static int count = 0;

    public void addEntry(String text){
        entries.add(text);
    }

    public void removeEntry(int index){
        entries.remove(index);
    }

    @Override
    public String toString(){
        return String.join(System.lineSeparator(), entries);
    }

    /*
    //Persistence
    public void save(String fileName, Boolean overwrite) throws FileNotFoundException {
        if (overwrite || new File(fileName).exists()) {
            try (PrintStream out = new PrintStream(fileName)){
                out.println(toString());
            }
        }
    }
    */
}

class Persistence {
    public void save(Journal journal, String fileName, Boolean overwrite) throws FileNotFoundException {
        if (overwrite || new File(fileName).exists()) {
            try (PrintStream out = new PrintStream(fileName)) {
                out.println(journal.toString());
            }
        }
    }
}