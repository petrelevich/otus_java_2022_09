package ru.otus.xml.dom;


public class Share {

    private final String ticker;
    private final double last;
    private final String date;

    public Share(String ticker, double last, String date) {
        this.ticker = ticker;
        this.last = last;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Share{" +
                "ticker='" + ticker + '\'' +
                ", last=" + last +
                ", date='" + date + '\'' +
                '}';
    }

    public String getTicker() {
        return ticker;
    }

    public double getLast() {
        return last;
    }

    public String getDate() {
        return date;
    }
}
