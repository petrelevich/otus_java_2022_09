package ru.otus.l12.solid;

/**
 * @author sergey
 * created on 09.09.19.
 */
public class DependencyInversion {

    interface SteeringWheel {

    }

    interface Engine {

    }

    /*неправильно*/
    class CarInvalid {
        private SteeringWheel steeringWheel;
        private Engine engine;

        public CarInvalid() {
            this.steeringWheel = new GenericWheel();
            this.engine = new GasEngine();
        }
    }

    /*Правильное применение принципа*/
    class Car {
        private SteeringWheel steeringWheel;
        private Engine engine;

        public Car(SteeringWheel steeringWheel, Engine engine) {
            this.steeringWheel = steeringWheel;
            this.engine = engine;
        }
    }

    class GasEngine implements Engine {
    }

    class GenericWheel implements SteeringWheel {
    }
}


