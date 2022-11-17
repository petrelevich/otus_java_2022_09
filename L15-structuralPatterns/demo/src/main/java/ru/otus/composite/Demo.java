package ru.otus.composite;

/**
 * <img src="http://starcraft.7x.ru/site.starcraft.ru/strategy/part_01_screen01-1.gif"/>
 */
public class Demo {
    public static void main(String[] args) {
        Unit marine1 = new Marine();
        Unit marine2 = new Marine();
        Unit marine3 = new Marine();

        Unit tank = new Tank();

        // Можем объединить юниты в группу
        var group = new Group();
        group.addUnit(marine1);
        group.addUnit(marine2);
        group.addUnit(marine3);
        group.addUnit(tank);

        System.out.println("first group:");
        group.move();

        // Можем и группу добавить в другую группу
        var group2 = new Group();
        group2.addUnit(group);
        group2.addUnit(new Tank());

        System.out.println("second group:");
        group2.move();
    }
}
