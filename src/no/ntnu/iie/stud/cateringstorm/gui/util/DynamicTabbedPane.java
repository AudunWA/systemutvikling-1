package no.ntnu.iie.stud.cateringstorm.gui.util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Extension of JTabbedPane which dynamically loads tabs as they are used.
 */
public class DynamicTabbedPane extends JTabbedPane {
    private ArrayList<Class<Component>> classes;
    private HashMap<Integer, Object> instances;

    public DynamicTabbedPane() {
        classes = new ArrayList<>();
        instances = new HashMap<>();

        addChangeListener(e -> {
            refresh();
        });
    }

    public void addTab(String title, Icon icon, Class componentClass) {
        // Not working
        //if(!componentClass.isAssignableFrom(Component.class)) {
        //    throw new IllegalArgumentException("componentClass has to extend Component.");
        //}

        classes.add(componentClass);
        addTab(title, icon, (Component) null);

        if (classes.size() == 1) {
            refresh();
        }
    }

    private void refresh() {
        int selectedIndex = getSelectedIndex();
        if (!instances.containsKey(selectedIndex)) {
            try {
                // Create instance, then load
                Component instance = classes.get(selectedIndex).newInstance();
                instances.put(selectedIndex, instance);

                // Instance created, load it
                setComponentAt(selectedIndex, (Component) instances.get(selectedIndex));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
