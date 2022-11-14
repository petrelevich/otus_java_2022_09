package ru.outs.chain;

abstract class ApplicationProcessor {
    private ApplicationProcessor next;

    private ApplicationProcessor getNext() {
        return next;
    }

    void setNext(ApplicationProcessor next) {
        this.next = next;
    }

    void process(Application application) {
        before();
        processInternal(application);
        after();
        if (getNext() != null) {
            getNext().process(application);
        }
    }

    protected abstract void processInternal(Application application);

    public abstract String getProcessorName();

    private void before() {
        System.out.println("before:" + getProcessorName());
    }

    private void after() {
        System.out.println("after:" + getProcessorName());
    }
}
