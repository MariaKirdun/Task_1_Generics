package main.java.com.epam;

import main.java.com.epam.api.GpsNavigator;
import main.java.com.epam.api.Path;
import main.java.com.epam.exeptions.NoPossibleRoads;
import java.util.ArrayList;
import java.util.List;

/**
 * This class app demonstrates how your implementation of {@link GpsNavigator} is intended to be used.
 */
public class ExampleApp {

    public static void main(String[] args) {
        final GpsNavigator navigator = new StubGpsNavigator();
        navigator.readData("C:\\Users\\Manya\\IdeaProjects\\GPS_EPAM\\src\\main\\java\\com\\epam\\road_map");

        final Path path = navigator.findPath("C", "A");
        System.out.println(path);
    }

    private static class StubGpsNavigator implements GpsNavigator {

        private List<Road> roads;

        @Override
        public void readData(String filePath) {
            // Read data from road_map.
            DataReader reader = new DataReader();
            roads = reader.read(filePath);
        }

        @Override
        public Path findPath(String pointA, String pointB) {

            List<String> path = new ArrayList<>();
            int cost = 0;

            try {
                    String current = pointA;
                    while (!current.equals(pointB)) {
                        List<Road> possibleRoads = findRoads(current);
                        if (possibleRoads == null || !canMoveToPoint(pointB)) {
                            throw new NoPossibleRoads("exception");
                        }
                        Road currentRoad = possibleRoads.get(0);
                        int minCost = currentRoad.getLength() * currentRoad.getCost();
                        for (Road road : possibleRoads) {
                            if (road.getLength() * road.getCost() <= minCost && road.getEnd().equals(pointB)) {
                                minCost = road.getLength();
                                currentRoad = road;
                            } else if (road.getLength() * road.getCost() < minCost) {
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

        private List<Road> findRoads(String begin){
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
