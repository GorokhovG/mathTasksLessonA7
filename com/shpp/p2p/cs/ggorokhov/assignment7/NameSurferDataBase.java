package com.shpp.p2p.cs.ggorokhov.assignment7;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class NameSurferDataBase implements NameSurferConstants {

    private HashMap<String, String> dataLine = new HashMap<>();

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String fileName) {
        try {
            /* Open the file for reading. */
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            /* Process the file by adding one magnet per line. */
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                String[] dataArr = line.split(" ", 2);

                dataLine.put(dataArr[0].toLowerCase(), line);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/* Method: findEntry(name) */

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        name = name.toLowerCase();
        String result;

        // exception for empty line
        if (dataLine == null || name.equals(""))
            return null;

        result = dataLine.get(name);

        // exception for random errors
        if (result == null)
            return null;

        return new NameSurferEntry(dataLine.get(name));
    }
}

