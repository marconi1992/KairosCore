package dependencies.activities;

import javafx.stage.Stage;

public class ActivityFactory extends Context {


    public ActivityFactory(Stage root) {
        if (this.window == null) {
            this.window = new SimpleWindowManager(root);
        }
    }

    @Override
    public void startActivity(Class<?> activity) {
            startActivity(new Intent(null,activity));
    }

    @Override
    public void startActivity(Intent intent) {
        try {
            Activity activityInstance = (Activity) intent.activity.newInstance();
            activityInstance.context = this;
            activityInstance.intent=intent;
            activityInstance.onCreate();
            this.window.add(activityInstance);

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        this.window.back();
    }


}
