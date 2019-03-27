package main.java.com.epam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private static int BEGIN = 0;
    private static int END = 1;
    private static int LENGTH = 2;
    private static int COST = 3;

    List<Road> read(String path) {

        List<Road> roads = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))){

            List<String> lines = new ArrayList<>();
            String readLine;

            while ((readLine = reader.readLine()) != null){
                lines.add(readLine);
            }


            for (String line: lines) {
                String[] lineData = line.split(" ");
                Road road = new Road();
                road.setBegin(lineData[BEGIN]);
                road.setEnd(lineData[END]);
                road.setLength(Integer.parseInt(lineData[LENGTH]));
                road.setCost(Integer.parseInt(lineData[COST]));
                roads.add(road);
            }

        } catch (FileNotFoundException e){

        } catch (IOException e){

        }

        return roads;
    }



}
