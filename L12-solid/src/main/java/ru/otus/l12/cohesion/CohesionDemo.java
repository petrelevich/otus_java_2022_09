package ru.otus.l12.cohesion;

/**
 * @author spv
 * created on 11.02.20.
 */

// Где Cohesion больше: A или B (класс B ниже)?

class A {
    private String message;
    // Инициализация message опущена

    public void process() {
        //...
        send();
    }










    private void send() {
        // ... Здесь может быть какая-то логика...
        System.out.println(
                "Send: " + this.message);
    }
}



class B {
    private String message;
    // Инициализация message опущена

//    public  B(String message){
//        this.message = message;
//    }
    public void process() {
        //...
        Helper.send(this.message);
    }

//    private void send(String message) {
//        // ... Здесь может быть какая-то логика...
//        System.out.println(
//                "Send: " + message);
//    }
}

// См. еще код ниже

class Helper {
    public static void send(String message) {
        // ... Здесь может быть какая-то логика...
        System.out.println(
                "Send: " + message);
    }
}












