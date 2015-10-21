package dependencies.activities;


import javafx.scene.Node;

public abstract class ActivityBackStack extends ActivityTransition {

    Record head, tail;
    int numRecord;

    @Override
    public void add(Activity activity) {
        doAddActivity(activity);
    }

    @Override
    public ActivityTransition back() {
        doBackActivity();
        return this;
    }

    private void doAddActivity(Activity activity) {
        Record record = new Record();
        record.activity = activity;
        addRecord(record);
    }

    private void doBackActivity() {
        if (!tail.equals(head)) {
            tail = tail.prev;
        }
        numRecord--;

    }

    private void addRecord(Record record) {
        if (head == null) {
            head = tail = record;
        } else {
            record.prev = tail;
            tail.next = record;
            tail = record;
        }
        numRecord++;
    }

    static final class Record {
        Activity activity;
        Node content;
        Record prev, next;
    }
}
