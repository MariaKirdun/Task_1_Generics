package main.java.com.epam;

import main.java.com.epam.exeptions.NotCorrectData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private static int BEGIN = 0;
    private static int END = 1;
    private static int LENGTH = 2;
    private static int COST = 3;

    public List<Road> read(String path){
        List<String> data = new ArrayList<>();
        try {
            data = readFromFile(path);

        } catch (NotCorrectData e){
            System.out.println(e.getMessage());
        }
        return getRoads(data);
    }
    private List<String> readFromFile(String path) throws NotCorrectData{

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


    private List<Road> getRoads(List<String> lines){
        List<Road> roads = new ArrayList<>();
        for (String line: lines) {
            String[] lineData = line.split(" ");
            Road road = new Road();
            road.setBegin(lineData[BEGIN]);
            road.setEnd(lineData[END]);
            road.setLength(Integer.parseInt(lineData[LENGTH]));
            road.setCost(Integer.parseInt(lineData[COST]));
            roads.add(road);
        }
        return roads;
    }

}
