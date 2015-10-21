package dependencies.activities;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Created by Felipe on 20/10/2015.
 */
public class SimpleWindowManager extends WindowManager {

    public SimpleWindowManager(Stage window) {
        super(window);
    }

    @Override
    public void add(Activity activity) {
        super.add(activity);
        tail.content=windowPane.get(windowPane.size()-1);
        if(tail!=head){
            windowPane.remove(tail.prev.content);
        }
    }

    @Override
    public void setContentView(Node content) {
        super.setContentView(content);
        if(content instanceof Region) {
            checkChangeSize(((Region) content).getPrefWidth(), ((Region) content).getPrefHeight());
        }
    }

    @Override
    public ActivityTransition back() {
        if(tail.prev!=null){
            windowPane.remove(tail.content);
            windowPane.add(tail.prev.content);
        }
        checkChangeSize(((Region) tail.prev.content).getPrefWidth(), ((Region) tail.prev.content).getPrefHeight());
        return super.back();

    }



    private void checkChangeSize(double width,double height){
        if(width!=window.getWidth() || height!=window.getHeight()){
            window.hide();
            window.setWidth(width);
            window.setHeight(height);
            window.centerOnScreen();
            ((Stage)window).show();
        }

    }
}
