package main.java.com.epam.calculations;

import main.java.com.epam.Road;

public class SimpleCostCalculator implements CostCalculations<Road>{

    @Override
    public Integer calculate(Road road) {
        return road.getLength() * road.getCost();
    }
}
