package ru.otus.l12.solid;

/**
 * @author sergey
 * created on 09.09.19.
 */
public class LiskovSubstitution {

    /*
    Классический пример нарушения принципа -
        квадрат наследует прямоугольник
     */

    public static void main(String[] args) {
        new LiskovSubstitution().apply();
    }

    private void apply() {
        double height = 2.0;
        double width = 3.0;

        Rectangle rectangle = new Rectangle(); // factory.createRectangle()
        rectangle.setHeight(height);
        rectangle.setWidth(width);

        System.out.println("--- Rectangle rectangle = new Rectangle();");
        System.out.println("height:" + height + " rectangle.height:" + rectangle.height);
        System.out.println("width:" + width + " rectangle.width:" + rectangle.width);

        Rectangle rectangleStrange = new Square(); // factory.createRectangle()
        rectangleStrange.setHeight(height);
        rectangleStrange.setWidth(width);

        System.out.println();
        System.out.println("--- Rectangle rectangleStrange = new Square();");
        System.out.println("height:" + height + " rectangleStrange.height:" + rectangleStrange.height);
        System.out.println("width:" + width + " rectangleStrange.width:" + rectangleStrange.width);
    }

    private class Rectangle {
        private double height;
        private double width;

        public double area() {
            return this.height * this.width;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public void setWidth(double width) {
            this.width = width;
        }
    }

    private class Square extends Rectangle {
        @Override
        public void setHeight(double height) {
            super.setHeight(height);
            super.setWidth(height);
        }

        @Override
        public void setWidth(double width) {
            this.setHeight(width);
        }
    }
}
