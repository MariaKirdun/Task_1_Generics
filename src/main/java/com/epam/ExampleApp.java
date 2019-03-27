package main.java.com.epam;

import main.java.com.epam.api.GpsNavigator;
import main.java.com.epam.api.Path;
import main.java.com.epam.exeptions.NoPossibleRoads;
import main.java.com.epam.exeptions.NotCorrectData;

import javax.xml.stream.FactoryConfigurationError;
import java.util.ArrayList;
import java.util.List;

/**
 * This class app demonstrates how your implementation of {@link GpsNavigator} is intended to be used.
 */
public class ExampleApp {

    public static void main(String[] args) {
        final GpsNavigator navigator = new StubGpsNavigator<Road>();
        navigator.readData("C:\\Users\\Manya\\IdeaProjects\\GPS_EPAM\\src\\main\\java\\com\\epam\\road_map");

        final Path path = navigator.findPath("C", "A");
        System.out.println(path);
    }

    private static class StubGpsNavigator <T extends Road> implements GpsNavigator {

        private List<T> roads;

        private static int BEGIN = 0;
        private static int END = 1;
        private static int LENGTH = 2;
        private static int COST = 3;

        @Override
        public void readData(String filePath) {
            // Read data from road_map.
            DataReader reader = new DataReader();

            try {
                List<String> data = reader.readFromFile(filePath);
                roads = getRoads(data);
            } catch (NotCorrectData e){
                System.out.println(e.getMessage());
            }
        }


        private List<T> getRoads(List<String> lines){
            List<T> roads = new ArrayList<>();
            for (String line: lines) {
                String[] lineData = line.split(" ");
                T road = (T) new Road();
                road.setBegin(lineData[BEGIN]);
                road.setEnd(lineData[END]);
                road.setLength(Integer.parseInt(lineData[LENGTH]));
                road.setCost(Integer.parseInt(lineData[COST]));
                roads.add(road);
            }
            return roads;
        }


        @Override
        public Path findPath(String pointA, String pointB) {

            List<String> path = new ArrayList<>();
            int cost = 0;

            try {
                    String current = pointA;
                    while (!current.equals(pointB)) {
                        List<Road> possibleRoads = findPossibleRoads(current);
                        if (possibleRoads == null || !canMoveToPoint(pointB)) {
                            throw new NoPossibleRoads("exception");
                        }
                        Road currentRoad = possibleRoads.get(0);
                        int minCost = currentRoad.getLength() * currentRoad.getCost();
                        for (Road road : possibleRoads) {
                            if (getCost(road) <= minCost && road.getEnd().equals(pointB)) {
                                minCost = road.getLength();
                                currentRoad = road;
                            } else if (getCost(road) < minCost) {
                                minCost = road.getLength();
                                currentRoad = road;
                            }
                        }
                        path.add(currentRoad.getBegin());
                        cost += currentRoad.getCost() * currentRoad.getLength();
                        current = currentRoad.getEnd();
                    }
                    path.add(current);

            } catch (NoPossibleRoads e){
                System.out.println(e.getMessage());
            }

            return new Path(path,cost);
        }

        private Integer getCost(Road road){
            return road.getLength() * road.getCost();
        }

        private List<Road> findPossibleRoads(String begin){
            List<Road> possibleRoads = new ArrayList<>();
            for (Road road: roads) {
                if (road.getBegin().equals(begin)){
                    possibleRoads.add(road);
                }
            }
            return possibleRoads;
        }

        private boolean canMoveToPoint(String point){
            for (Road road: roads) {
                if (road.getEnd().equals(point)){
                    return true;
                }
            }
            return false;
        }
    }
}
