package dependencies.activities;


public class ContextWrapper extends Context {

    protected Context context;

    @Override
    public void startActivity(Class<?> activity) {
        context.startActivity(activity);
    }

    @Override
    public void onBackPressed() {
        context.onBackPressed();
    }


}
