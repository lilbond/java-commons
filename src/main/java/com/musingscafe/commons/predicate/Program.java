package com.musingscafe.commons.predicate;

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

        And and = new And(conditionals.toArray(new Conditional[conditionals.size()]));
        System.out.println(and.test());

        Or or = new Or(conditionals.toArray(new Conditional[conditionals.size()]));
        System.out.println(or.test());

        Conditional conditional = Condition
                .test(() -> 1 == 1)
                .and(() -> "hello".equals("Hello".toLowerCase()))
                .or(() -> 2 != 3).build();


        final Supplier<Boolean> predicate = () -> conditional.test();
        final Supplier<String> ifTrue = () -> "passed";
        final Supplier<String> ifFalse = () -> "failed";
        System.out.println(FunctionUtils.execute(predicate, ifTrue, ifFalse));
    }
}
