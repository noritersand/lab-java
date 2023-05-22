package builtin.heritance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaticMethodsHeritance {
    @Test
    void shouldBeEquals() {
        Parent ins1 = new Child();
        assertEquals("Child", ins1.print());
        assertEquals("Parent", Parent.staticPrint());

        assertEquals("Child", Child.staticPrint());
    }
}

class Parent {
    public String print() {
        return "Parent";
    }

    public static String staticPrint() {
        return "Parent";
    }
}

class Child extends Parent {
    public String print() {
        return "Child";
    }

    public static String staticPrint() {
        return "Child";
    }
}