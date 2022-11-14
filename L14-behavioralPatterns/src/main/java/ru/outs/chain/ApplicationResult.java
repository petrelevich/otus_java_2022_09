package ru.outs.chain;

public class ApplicationResult extends ApplicationProcessor {

    @Override
    protected void processInternal(Application application) {
        application.addHistoryRecord("Результат выдан");
    }

    @Override
    public String getProcessorName() {
        return "Выдача результата";
    }
}
