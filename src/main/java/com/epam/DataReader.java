package main.java.com.epam;

import main.java.com.epam.exeptions.NotCorrectData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    public List<String> readFromFile(String path) throws NotCorrectData{

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))){

            String readLine;

            while ((readLine = reader.readLine()) != null){
                lines.add(readLine);
            }

        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e){
            throw new NotCorrectData(e.getMessage());
        }
        return lines;
    }

}
