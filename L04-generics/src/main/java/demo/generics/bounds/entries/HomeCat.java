package demo.generics.bounds.entries;

public class HomeCat extends Cat {
    private final String name;

    public HomeCat( String name ) {
        this.name = name;
    }

    public void sitOnBoss() {

    }

    @Override
    public String toString() {
        return "HomeCat, name:" + name;
    }
}
