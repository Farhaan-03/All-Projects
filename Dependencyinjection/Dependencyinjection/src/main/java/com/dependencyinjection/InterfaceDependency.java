package com.dependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;

public class InterfaceDependency {
    public static void main(String[] args) {

        Car car =new Car();
        car.setEngine(new petrolEngine());
        car.setEngine(new DieselEngine());

    }
}


class Car {
    Engine engine;

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
        engine.start();
    }
}

interface Engine{
    public void start();
}

class petrolEngine implements Engine{

    @Override
    public void start() {
        System.out.println("petrol engine started");
    }
}
class DieselEngine implements Engine{

    @Override
    public void start() {
        System.out.println("Diesel engine started");
    }
}

