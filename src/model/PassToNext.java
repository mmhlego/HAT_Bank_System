package model;

import java.awt.*;
import java.awt.event.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class PassToNext {

    public static void NextField(TextField tf, int maxLength, boolean Prevent) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue,
                    final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
                if (tf.getText().length() == maxLength) {
                    Next();
                }
                if (Prevent) {
                    PreventWord(tf);
                }
            }
        });
    }

    private static void Next() {
        try {
            Robot Switcher = new Robot();
            Switcher.keyPress(KeyEvent.VK_TAB);
            Switcher.keyRelease(KeyEvent.VK_TAB);
        } catch (AWTException e1) {

        }
    }

    private static void PreventWord(TextField tf) {
        String txt = tf.getText();
        if (txt.length() > 0 && !(txt.charAt(txt.length() - 1) >= 48 && txt.charAt(txt.length() - 1) <= 57)) {
            tf.setText(txt.substring(0, txt.length() - 1));
        }
    }
}
