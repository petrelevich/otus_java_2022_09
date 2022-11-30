package ru.otus.xml.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author sergey
 * created on 25.09.18.
 */
@XmlRootElement(name = "shares")
public class Shares {

    private List<Share> shares;

    public List<Share> getShares() {
        return shares;
    }

    @XmlElement(name = "share")
    public void setShares(List<Share> shares) {
        this.shares = shares;
    }

    @Override
    public String toString() {
        return "Shares{" +
                "shares=" + shares +
                '}';
    }
}
