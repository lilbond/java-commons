package com.musingscafe.commons;

import com.musingscafe.commons.function.Exec;
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
        List<Conditional> conditionals = new ArrayList<>();
        conditionals.add(() -> 1 == 1);
        conditionals.add(() -> 1 == 1);
        conditionals.add(() -> 1 == 2);
        conditionals.add(() -> 1 == 1);
        conditionals.add(() -> 1 == 1);

        conditionals.stream().filter(e -> e.test());

        And and = new And(conditionals.toArray(new Conditional[conditionals.size()]));
        //System.out.println(and.execute());

        Or or = new Or(conditionals.toArray(new Conditional[conditionals.size()]));
        //System.out.println(or.execute());

        Program program = new Program();
        Conditional conditional = Condition
                .test(() -> program.isTrue())
                .and(() -> "hello".equals("Hello".toLowerCase()))
                .or(() -> 2 != 3).build();


        final Supplier<Boolean> predicate = () -> program.isFalse();
        final Worker mapProperties = () -> program.doSomething();
        final Worker setFailure = () -> program.doNothing();
//        final Supplier<String> ifTrue = () -> "passed";
//        final Supplier<String> ifFalse = () -> "failed";

        //System.out.println(Suppliers.test(predicate, ifTrue, ifFalse));

        //Suppliers.execute(predicate, mapProperties, setFailure);
        Exec.apply(predicate, mapProperties).orElse(()-> program.isTrue(), setFailure).execute();
        System.out.println(Suppliers.apply(predicate, () -> 1).orElse(() -> program.isTrue(), () -> 2).get());
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
}
