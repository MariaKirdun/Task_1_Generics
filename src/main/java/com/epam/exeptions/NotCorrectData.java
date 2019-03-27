package main.java.com.epam.exeptions;

import java.io.IOException;

public class NotCorrectData extends IOException {

    public NotCorrectData(String message){
        super(message);
    }
}
