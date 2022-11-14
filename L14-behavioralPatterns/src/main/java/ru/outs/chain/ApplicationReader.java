package ru.outs.chain;

public class ApplicationReader extends ApplicationProcessor {

    @Override
    protected void processInternal(Application application) {
        application.addHistoryRecord("Заявление рассмотрено");
    }

    @Override
    public String getProcessorName() {
        return "Рассмотрение заявления";
    }
}
