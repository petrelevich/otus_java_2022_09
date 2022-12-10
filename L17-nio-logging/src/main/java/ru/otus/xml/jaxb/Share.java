package ru.otus.xml.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author sergey
 * created on 24.09.18.
 */

@XmlRootElement(name = "share")
public class Share {


    private String ticker;
    private double last;
    private String date;


    @XmlElement(name = "ticker")
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @XmlElement(name = "last")
    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    @XmlElement(name = "date")
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
