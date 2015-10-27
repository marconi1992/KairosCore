package dependencies.activities;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * Created by Felipe on 17/08/2015.
 */
public class BackStackRecord extends FragmentTransaction {

    static final int ATTACH = 7;
    static final int ADD = 1;
    static final int REMOVE=2;

    final FragmentManagerImpl fm;
    Op head, tail;
    int numOp;

    public BackStackRecord(FragmentManagerImpl fm) {
        this.fm = fm;
    }

    @Override
    public FragmentTransaction attach(Fragment f) {
        Op op = new Op();
        op.fragment = f;
        op.cmd = ATTACH;
        addOp(op);
        return this;
    }

    @Override
    public FragmentTransaction add(String containerId, Fragment f) {
        add(containerId, f, null);
        return this;
    }

    @Override
    public FragmentTransaction add(String containerId, Fragment f, String tag) {
        doAddOp(containerId, f, tag, ADD);
        return this;
    }

    @Override
    public FragmentTransaction replace(String containerId, Fragment f) {
        replace(containerId, f, null);
        return this;
    }

    @Override
    public FragmentTransaction replace(String containerId, Fragment f, String tag) {
        for(Fragment fragment : fm.added){
            if(tag!=null){
                if(fragment.tag.equals(tag) && fragment.containerId.equals(containerId)){
                    remove(fragment);
                }
            }else{
                if(fragment.containerId.equals(containerId)){
                    remove(fragment);
                }
            }
        }
        add(containerId,f);
        return null;
    }

    @Override
    public FragmentTransaction remove(Fragment f) {
        doAddOp(f.containerId, f, null, REMOVE);
        return this;
    }

    @Override
    public int commit() {

        do {
            head.prev=null;
            Pane nodeHost = (Pane) fm.activityHost.context.window.getDecorView().lookup("#" + head.fragment.containerId);
            if (head.cmd == ADD) {
                nodeHost.getChildren().add(head.fragment);
                FXMLLoader loader = new FXMLLoader();
                loader.setRoot(head.fragment);
                loader.setController(head.fragment);
                head.fragment.onCreateView(loader);
                head.cmd=ATTACH;
                fm.added.add(head.fragment);
                head.fragment.onResume();
            }else if(head.cmd==REMOVE){
                head.fragment.onPause();
                head.fragment.onStop();
                head.fragment.onDestroyView();
                nodeHost.getChildren().remove(head.fragment);
                head.fragment.onDestroy();
                fm.added.add(head.fragment);
                head.fragment.onDetach();
            }
        } while ( (head=head.next)!=null);
        return 0;
    }


    private void addOp(Op op) {
        if (head == null) {
            head = tail = op;
        } else {
            op.prev = tail;
            tail.next = op;
            tail = op;
        }
        numOp++;

    }

    private void doAddOp(String containerId, Fragment f, String tag, int cmd) {
        f.fragmentManager = fm;
        if (tag != null) {
            f.tag = tag;
        } else {
            f.tag = Fragment.class.getSimpleName();
        }
        if (!containerId.equals("")) {
            f.fragmentId = f.containerId = containerId;
        }
        Op op = new Op();
        op.fragment = f;
        op.cmd = cmd;
        addOp(op);
    }


    static final class Op {

        Op next;
        Op prev;
        int cmd;
        Fragment fragment;
    }

}
