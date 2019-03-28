package main.java.com.epam.calculations;

import main.java.com.epam.Road;

public interface CostCalculations<T extends Road> {

    public Integer calculate(T road);

}
