package mctouchbar.widget;

import com.thizzer.jtouchbar.item.TouchBarItem;

import java.util.HashMap;

public class Widget {

    public TouchBarItem touchBarItem;
    public String ID = "fortnite";

    public HashMap<String, Object> config = new HashMap<>();

    public Widget() {
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public void tick() {

    }
}
