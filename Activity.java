package dependencies.activities;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public class Activity extends ContextWrapper {

    static protected final int INITIALIZING = 0;
    static protected final int CREATED = 1;
    static protected final int STOPPED = 2;
    static protected final int STARTED = 3;
    static protected final int RESUMED = 4;

    protected FragmentManagerImpl fragmentManager;
    protected ActionBar actionBar;
    private boolean homeAsUp=false;


    protected int state = INITIALIZING;

    public void onCreate() {
        fragmentManager = new FragmentManagerImpl(this);
        state = CREATED;
    }

    protected void onStart() {
        state = STARTED;
    }

    protected void onResume() {
        state = RESUMED;
        for(Fragment fragment:fragmentManager.added){
            if(fragment.state<Fragment.RESUMED) {
                fragment.onResume();
            }
        }
    }

    protected void onPause() {

    }

    protected void onStop() {
        state = STOPPED;

    }

    protected void onDestroy() {
    }

    public void setContentView(URL content) {
        FXMLLoader loader = new FXMLLoader(content);
        loader.setController(this);
        try {
            this.context.window.setContentView(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
     }

    public ActionBar getActionBar() {
        return actionBar;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    protected void setHomeAsUp(boolean homeAsUp) {
        this.homeAsUp = homeAsUp;
        if(homeAsUp){
            actionBar.showHomeAsUp();
            actionBar.setOnHomeButtonAction(evt->{
                onBackPressed();
            });
        }
    }

    protected boolean isHomeAsUp() {
        return homeAsUp;
    }
}
