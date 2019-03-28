package main.java.com.epam;

import main.java.com.epam.api.GpsNavigator;
import main.java.com.epam.api.Path;
import main.java.com.epam.calculations.CostCalculations;
import main.java.com.epam.calculations.SimpleCostCalculator;
import main.java.com.epam.exeptions.NoPossibleRoads;
import main.java.com.epam.exeptions.NotCorrectData;
import java.util.ArrayList;
import java.util.List;

/**
 * This class app demonstrates how your implementation of {@link GpsNavigator} is intended to be used.
 */
public class ExampleApp {

    private static int BEGIN = 0;
    private static int END = 1;
    private static int LENGTH = 2;
    private static int COST = 3;

    public static void main(String[] args) {
        final GpsNavigator navigator = new StubGpsNavigator<Road>();
        navigator.readData("C:\\Users\\Manya\\IdeaProjects\\GPS_EPAM\\src\\main\\java\\com\\epam\\road_map");
        ((StubGpsNavigator) navigator).setRoads(getRoads(((StubGpsNavigator) navigator).getData()));
        ((StubGpsNavigator) navigator).setCalculator(new SimpleCostCalculator());

        final Path path = navigator.findPath("C", "A");
        System.out.println(path);
    }

    private static List<Road> getRoads(List<String> lines){
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

    private static class StubGpsNavigator <T extends Road> implements GpsNavigator {

        private List<T> roads;
        private List<String> data;
        private CostCalculations calculator;

        @Override
        public void readData(String filePath) {
            // Read data from road_map.
            DataReader reader = new DataReader();

            try {
                data = reader.readFromFile(filePath);
            } catch (NotCorrectData e){
                System.out.println(e.getMessage());
            }
        }

        public List<T> getRoads() {
            return roads;
        }

        public void setRoads(List<T> roads) {
            this.roads = roads;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public CostCalculations getCalculator() {
            return calculator;
        }

        public void setCalculator(CostCalculations calculator) {
            this.calculator = calculator;
        }

        @Override
        public Path findPath(String pointA, String pointB) {

            List<String> path = new ArrayList<>();
            int cost = 0;

            try {
                    String current = pointA;
                    while (!current.equals(pointB)) {
                        List<T> possibleRoads = findPossibleRoads(current);
                        if (possibleRoads == null || !canMoveToPoint(pointB)) {
                            throw new NoPossibleRoads("exception");
                        }
                        Road currentRoad = possibleRoads.get(0);
                        int minCost = currentRoad.getLength() * currentRoad.getCost();
                        for (T road : possibleRoads) {
                            if (calculator.calculate(road) <= minCost && road.getEnd().equals(pointB)) {
                                minCost = road.getLength();
                                currentRoad = road;
                            } else if (calculator.calculate(road) < minCost) {
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

        private List<T> findPossibleRoads(String begin){
            List<T> possibleRoads = new ArrayList<>();
            for (T road: roads) {
                if (road.getBegin().equals(begin)){
                    possibleRoads.add(road);
                }
            }
            return possibleRoads;
        }

        private boolean canMoveToPoint(String point){
            for (T road: roads) {
                if (road.getEnd().equals(point)){
                    return true;
                }
            }
            return false;
        }
    }
}
