package ru.outs.chain;

public class ApplicationInput extends ApplicationProcessor {

    @Override
    protected void processInternal(Application application) {
        application.addHistoryRecord("Заявление принято");
    }

    @Override
    public String getProcessorName() {
        return "Прием заявления";
    }
}
