package var;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Simplification {

    public static void main(String[] args) throws IOException {
        System.out.println(func());
        System.out.println(funcVar());
    }

    private static String func() throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream("lines.txt"));
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
            return output.toString();
        }
    }

    // Типы данных переменных очевидны, поэтому можно использовать var
    private static String funcVar() throws IOException {
        var output = new StringBuilder();
        try (var inputStream = new BufferedInputStream(new FileInputStream("lines.txt"));
             var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            var line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
            return output.toString();
        }
    }

    //Пример серьезнее
    boolean find(Map<? extends String, ? extends Integer> mapA,
              Map<? extends String, ? extends Integer> mapB,
              int value) {
        String keyA = null;
        for (Map.Entry<? extends String, ? extends Integer> entry: mapA.entrySet()) {
            if (entry.getValue().equals(value)) {
                keyA = entry.getKey();
                break;
            }
        }

        if (keyA == null) {
            return false;
        }

        String keyB = null;
        int stepCount = 0;
        for (Map.Entry<? extends String, ? extends Integer> entry: mapB.entrySet()) {
            if (entry.getValue().equals(value)) {
                keyB = entry.getKey();
                stepCount++;
                break;
            }
        }
        System.out.println(stepCount);
        return keyA.equals(keyB);
    }

    //применяем var
    boolean findVar(Map<? extends String, ? extends Integer> mapA,
                 Map<? extends String, ? extends Integer> mapB,
                 int value) {
        String keyA = null;
        for (var entry: mapA.entrySet()) {
            if (entry.getValue().equals(value)) {
                keyA = entry.getKey();
                break;
            }
        }

        if (keyA == null) {
            return false;
        }

        String keyB = null;
        int stepCount = 0;
        for (var entry: mapB.entrySet()) {
            if (entry.getValue().equals(value)) {
                keyB = entry.getKey();
                stepCount++;
                break;
            }
        }
        System.out.println(stepCount);
        return keyA.equals(keyB);
    }


    //пример, в котором var не подойдет, хотя Idea тут поможет и методы надо зазывать норально
    void funcNotGood() {
        var val = getSomething(); // не понятно, что тут возврашается
        System.out.println(val);
    }

    //может быть где-то далеко, в другом классе
    private String getSomething() {
        return "";
    }
}
