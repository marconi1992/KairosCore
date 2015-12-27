package dependencies.activities;


public class ContextWrapper extends Context {

    protected Context context;
    protected Intent intent;
    @Override
    public void startActivity(Class<?> activity) {
        context.startActivity(activity);
    }

    @Override
    public void startActivity(Intent intent) {
        context.startActivity(intent);
    }

    public Intent getIntent() {
        return intent;
    }

    @Override
    public void onBackPressed() {
        context.onBackPressed();
    }


}
