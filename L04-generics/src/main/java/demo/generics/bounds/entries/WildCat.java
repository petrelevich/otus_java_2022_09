package demo.generics.bounds.entries;

public class WildCat extends Cat {
    private String name;

    public WildCat( String name ) {
        this.name = name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "WildCat, name:" + name;
    }
}
