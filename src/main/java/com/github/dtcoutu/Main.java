package com.github.dtcoutu;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static final String STATUS_JOINING = "JOINING";
    public static final String STATUS_LOST = "LOST";
    public static final String SNIPER_STATUS_NAME = "STATUS_NAME";

    private MainWindow ui;

    public Main() throws InvocationTargetException, InterruptedException {
        startUserInterface();
    }

    private void startUserInterface() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                ui = new MainWindow();
            }
        });
    }

    public static void main(String... args) throws InvocationTargetException, InterruptedException {
        Main main = new Main();
    }
}
