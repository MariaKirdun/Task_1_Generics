package main.java.com.epam.exeptions;

import java.io.IOException;

public class NoPossibleRoads extends IOException {
    public NoPossibleRoads(String message) {
        super(message);
    }
}
