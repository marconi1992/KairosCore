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
        try {
            Activity activityInstance = (Activity) activity.newInstance();
            activityInstance.context = this;
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
