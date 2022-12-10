package ru.otus.l12.polymorphism;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SimplePolymorpism {
    public static void main(String[] args) {
        // Плохо
        ArrayList list1 = new ArrayList();
        LinkedList list2 = new LinkedList();

        doSomethingWithListBad(list1);
        doSomethingWithListBad(list2);

        // Хорошо
        List list3 = new ArrayList();
        List list4 = new LinkedList();
        //List list5 = new DIYArrayList();

        doSomethingWithListGood(list3);
        doSomethingWithListGood(list4);
    }

    private static void doSomethingWithListBad(ArrayList list) {
        System.out.println("doSomethingWithListBad(ArrayList list)");
        for(var item : list){
            System.out.println(item);
        }
    }

    private static void doSomethingWithListBad(LinkedList list) {
        System.out.println("doSomethingWithListBad(LinkedList list)");
        for(var item : list){
            System.out.println(item);
        }
    }

    private static void doSomethingWithListGood(List list) {
        System.out.println("doSomethingWithListGood(List list)");
        for(var item : list){
            System.out.println(item);
        }
    }
}
