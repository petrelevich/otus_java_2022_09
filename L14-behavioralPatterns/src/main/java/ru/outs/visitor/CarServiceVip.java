package ru.outs.visitor;

public class CarServiceVip implements Visitor {
    @Override
    public void visit(Engine item) {
        System.out.println("Дорого и красиво:" + item.checkEngine());
    }

    @Override
    public void visit(Transmission item) {
        System.out.println("Дорого и красиво:" + item.refreshOil());
    }

    @Override
    public void visit(Brake item) {
        System.out.println("Дорого и красиво:" + item.replaceBrakePad());
    }
}
