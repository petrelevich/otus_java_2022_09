package ru.otus.xml.sax;

public class Share {

    private String ticker;
    private double last;
    private String date;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
}
