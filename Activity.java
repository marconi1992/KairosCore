package dependencies.activities;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public class Activity extends ContextWrapper {


    protected void onCreate() {

    }

    public void setContentView(URL content) {
        FXMLLoader loader=new FXMLLoader(content);
        loader.setController(this);
        try {
            this.context.window.setContentView(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
