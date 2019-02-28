package rueppellii.backend2.tribes.testController.valami;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        Cat cat = new Cat();
        List<Animal> animals = new ArrayList<>();

        animals.add(dog);
        animals.add(cat);

        for (Animal a : animals) {
            a.say();
        }
    }
}
