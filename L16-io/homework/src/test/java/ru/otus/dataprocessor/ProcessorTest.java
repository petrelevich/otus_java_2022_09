package ru.otus.dataprocessor;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ProcessorTest {

    //Надо реализовать методы классов и убедиться, что тест проходит
    //сам тест менять нельзя

    @Test
    @Disabled // Эту аннотацию надо убрать
    @DisplayName("Из файла читается json, обрабатывается, результат сериализуется в строку")
    void processingTest(@TempDir Path tempDir) throws IOException {
        System.out.println(tempDir);

        //given
        var inputDataFileName = "inputData.json";
        var outputDataFileName = "outputData.json";
        var fullOutputFilePath = String.format("%s%s%s",tempDir, File.separator, outputDataFileName);

        var loader = new ResourcesFileLoader(inputDataFileName);
        var processor = new ProcessorAggregator();
        var serializer = new FileSerializer(fullOutputFilePath);

        //when
        var loadedMeasurements = loader.load();
        var aggregatedMeasurements = processor.process(loadedMeasurements);
        serializer.serialize(aggregatedMeasurements);

        //then
        assertThat(loadedMeasurements.size()).isEqualTo(9);
        assertThat(aggregatedMeasurements.entrySet().size()).isEqualTo(3);

        var serializedOutput = Files.readString(Paths.get(fullOutputFilePath));
        //обратите внимание: важен порядок ключей
        assertThat(serializedOutput).isEqualTo("{\"val1\":3.0,\"val2\":30.0,\"val3\":33.0}");
    }
}
