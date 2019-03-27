package main.java.com.epam;

public class Road {

    private String begin;
    private String end;
    private int length;
    private int cost;

    public Road(String begin, String end, int length, int cost) {
        this.begin = begin;
        this.end = end;
        this.length = length;
        this.cost = cost;
    }

    public Road() {
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public int getLength() {
        return length;
    }

    public int getCost() {
        return cost;
    }


    @Override
    public String toString() {
        return this.begin + " " +
                this.end + " " +
                this.length + " " +
                this.cost;
    }
}
