package ncu.cc.digger.tasks;

public class DiggerTaskResult {
    private final boolean success;
    private final Object result;

    public DiggerTaskResult(boolean success, Object result) {
        this.success = success;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getResult() {
        return result;
    }
}
