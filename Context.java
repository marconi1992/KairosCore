package dependencies.activities;

/**
 * Created by Felipe on 09/09/2015.
 */
public abstract class Context {

    protected ActivityTransition window;

    public abstract void startActivity(Class<?> activity);
    public abstract void startActivity(Intent intent);
    public abstract void onBackPressed();
}
