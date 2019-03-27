package main.java.com.epam.calculations;

public class DayCostCalculator implements CostCalculations{

    @Override
    public Integer calculate(Integer length, Integer cost) {
        return length * cost;
    }
}
