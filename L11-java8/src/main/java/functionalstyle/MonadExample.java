package functionalstyle;

import java.util.ArrayList;
import java.util.Optional;

public class MonadExample {
    public static void main(String[] args) {
        var monadExample = new MonadExample();

        var result = monadExample.function("test");
        System.out.println(result);

        result = monadExample.function(null);
        System.out.println(result);

        result = monadExample.functionWrong(null);
        System.out.println(result);


        System.out.println("------------------");
        result = monadExample.functionNorm("functionNorm");
        System.out.println(result);

        result = monadExample.functionNorm(null);
        System.out.println(result);
    }

    private String function(String str) {
        return Optional.ofNullable(str)
                .map(val -> "!" + val.toUpperCase())
                .map(param -> param + "+addStr")
                .orElse("param is NULL");
    }

    //некорректное использование монады
    private String functionWrong(String str) {
        Optional<String> optional = anyFunction(str);

        if (optional.isPresent()) {
            return optional.get() + "+addStr";
        }
        return "param is NULL";
    }

    //но так уже нормально
    private String functionNorm(String str) {
        var list = new ArrayList<String>();
        var listUpper = new ArrayList<String>();

        Optional<String> optional = anyFunction(str);

        if (optional.isPresent()) {
            list.add(optional.get());
            listUpper.add(optional.get().toUpperCase());

            System.out.println(list);
            System.out.println(listUpper);

            return optional.get() + "+addStr";
        }
        return "param is NULL";
    }

    //типовое применение - возврат значения, которое может быть null
    private Optional<String> anyFunction(String val) {
        return Optional.ofNullable(val);
    }
}
