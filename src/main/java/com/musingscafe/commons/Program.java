package com.musingscafe.commons;

import com.musingscafe.commons.function.Exec;
import com.musingscafe.commons.function.Func;
import com.musingscafe.commons.function.Suppliers;
import com.musingscafe.commons.api.Worker;
import com.musingscafe.commons.predicate.And;
import com.musingscafe.commons.predicate.Condition;
import com.musingscafe.commons.api.Conditional;
import com.musingscafe.commons.predicate.Or;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by ayadav on 2/17/17.
 */
public class Program {
    public static void main(String[] args) {
        final Program program = new Program();

        // Sample to create sequential list of credentials
        final List<Conditional> conditionals = new ArrayList<>();
        conditionals.add(() -> 1 == 1);
        conditionals.add(() -> 1 == 1);
        conditionals.add(() -> 1 == 2);
        conditionals.add(() -> 1 == 1);
        conditionals.add(() -> 1 == 1);

        // And sample
        final And and = new And(conditionals.toArray(new Conditional[conditionals.size()]));
        System.out.println(and.test());

        // Or condition sample
        final Or or = new Or(conditionals.toArray(new Conditional[conditionals.size()]));
        System.out.println(or.test());

        // Composite conditional
        final Conditional conditional = Condition
                .test(program::isTrue)
                .and(() -> "hello".equals("Hello".toLowerCase()))
                .or(() -> 2 != 3).build();
        System.out.println(conditional.test());

        // sample for -> if(true) then execute
        final Supplier<Boolean> predicate = program::isFalse;
        final Worker mapProperties = program::doSomething;
        final Worker setFailure = program::doNothing;
        Func.execute(predicate, mapProperties, setFailure);

        // sample for -> if(true) return x else return y
        final Supplier<String> ifTrue = () -> "passed";
        final Supplier<String> ifFalse = () -> "failed";
        System.out.println(Func.supply(predicate, ifTrue, ifFalse));


        // sample for -> if(true) { x(); return;} else { y();}
        Func.apply(predicate, mapProperties)
                .orElse(() -> 1 == 2, () -> System.out.println("1st else"))
                .orElse(() -> 1 == 2, () -> System.out.println("2nd else"))
                .orElse(() -> 3 == 3, () -> System.out.println("3rd else"))
                .execute();

        // sample for -> if/else-if/else
        final int value = Func.apply(predicate, () -> 1)
                .orElse(() -> 1 == 2, () -> 2)
                .orElse(() -> 2 == 3, () -> 3)
                .orElse(() -> 3 == 3, () -> 4)
                .getOrElse(5);

        System.out.println(value);

        // sample for executing with try
        Func.applyWithTry(program::isTrue, program::throwOnExec, program::handle).execute();
    }

    private boolean isTrue() {
        return true;
    }

    private boolean isFalse() {
        return false;
    }

    private void doSomething() {
        System.out.println("doSomething");
    }
    private void doNothing() {
        System.out.println("doNothing");
    }

    private void throwOnExec() {
        throw new RuntimeException("Intentionally throwing :) ");
    }

    private void handle(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }
}
