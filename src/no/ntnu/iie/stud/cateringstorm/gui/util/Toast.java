package no.ntnu.iie.stud.cateringstorm.gui.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Not our code, retrieved from https://github.com/schnie/android-toasts-for-swing/
 * Displays a small text popup (The code has been modified for our use)
 */


public class Toast extends JDialog {
    public static final int LENGTH_SHORT = 3000;
    public static final int LENGTH_LONG = 6000;
    public static final Color ERROR_RED = new Color(121, 0, 0);
    public static final Color SUCCESS_GREEN = new Color(22, 127, 57);
    public static final Color NORMAL_BLACK = new Color(0, 0, 0);
    private final float MAX_OPACITY = 0.8f;
    private final float OPACITY_INCREMENT = 0.05f;
    private final int FADE_REFRESH_RATE = 20;
    private final int WINDOW_RADIUS = 15;
    private final int CHARACTER_LENGTH_MULTIPLIER = 7;
    private final int DISTANCE_FROM_PARENT_BOTTOM = 100;
    private final double DISTANCE_PERCENT_FROM_PARENT_BOTTOM = 0.2;
    private Component mOwner;
    private String mText;
    private int mDuration;
    private Color mBackgroundColor = Color.BLACK;
    private Color mForegroundColor = Color.WHITE;

    public Toast(JFrame owner) {
        super(owner);
        mOwner = owner;
    }

    public Toast(JDialog owner) {
        super(owner);
        mOwner = owner;
    }

    public static Toast makeText(JFrame owner, String text) {
        return makeText(owner, text, LENGTH_SHORT);
    }

    public static Toast makeText(JDialog owner, String text) {
        return makeText(owner, text, LENGTH_SHORT);
    }

    public static Toast makeText(JFrame owner, String text, Style style) {
        return makeText(owner, text, LENGTH_SHORT, style);
    }

    public static Toast makeText(JDialog owner, String text, Style style) {
        return makeText(owner, text, LENGTH_SHORT, style);
    }

    public static Toast makeText(JFrame owner, String text, int duration) {
        return makeText(owner, text, duration, Style.NORMAL);
    }

    public static Toast makeText(JDialog owner, String text, int duration) {
        return makeText(owner, text, duration, Style.NORMAL);
    }

    public static Toast makeText(JFrame owner, String text, int duration, Style style) {
        Toast toast = new Toast(owner);
        return initializeToast(toast, text, duration, style);
    }

    public static Toast makeText(JDialog owner, String text, int duration, Style style) {
        Toast toast = new Toast(owner);
        return initializeToast(toast, text, duration, style);
    }

    private static Toast initializeToast(Toast toast, String text, int duration, Style style) {
        toast.mText = text;
        toast.mDuration = duration;

        if (style == Style.SUCCESS)
            toast.mBackgroundColor = SUCCESS_GREEN;
        if (style == Style.ERROR)
            toast.mBackgroundColor = ERROR_RED;
        if (style == Style.NORMAL)
            toast.mBackgroundColor = NORMAL_BLACK;
        return toast;
    }

    private void createGUI() {
        setLayout(new GridBagLayout());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), WINDOW_RADIUS, WINDOW_RADIUS));
            }
        });

        setAlwaysOnTop(true);
        setUndecorated(true);
        setFocusableWindowState(false);
        setModalityType(ModalityType.MODELESS);
        setSize(mText.length() * CHARACTER_LENGTH_MULTIPLIER, 25);
        getContentPane().setBackground(mBackgroundColor);

        JLabel label = new JLabel(mText);
        label.setForeground(mForegroundColor);
        add(label);
    }

    public void fadeIn() {
        final Timer timer = new Timer(FADE_REFRESH_RATE, null);
        timer.setRepeats(true);
        timer.addActionListener(new ActionListener() {
            private float opacity = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += OPACITY_INCREMENT;
                setOpacity(Math.min(opacity, MAX_OPACITY));
                if (opacity >= MAX_OPACITY) {
                    timer.stop();
                }
            }
        });

        setOpacity(0);
        timer.start();

        setLocation(getToastLocation());
        setVisible(true);
    }

    public void fadeOut() {
        final Timer timer = new Timer(FADE_REFRESH_RATE, null);
        timer.setRepeats(true);
        timer.addActionListener(new ActionListener() {
            private float opacity = MAX_OPACITY;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= OPACITY_INCREMENT;
                setOpacity(Math.max(opacity, 0));
                if (opacity <= 0) {
                    timer.stop();
                    setVisible(false);
                    dispose();
                }
            }
        });

        setOpacity(MAX_OPACITY);
        timer.start();
    }

    private Point getToastLocation() {
        Point ownerLoc = mOwner.getLocation();
        int parentSizeY = mOwner.getHeight();
        int x = (int) (ownerLoc.getX() + ((mOwner.getWidth() - this.getWidth()) / 2));
        //int y = (int) (ownerLoc.getY() + (mOwner.getHeight() - this.getHeight()) - DISTANCE_FROM_PARENT_BOTTOM);

        int percent = (int) (parentSizeY * DISTANCE_PERCENT_FROM_PARENT_BOTTOM);
        int y = (int) (ownerLoc.getY() + (mOwner.getHeight() - this.getHeight()) - percent);
        return new Point(x, y);
    }

    public void setText(String text) {
        mText = text;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    @Override
    public void setBackground(Color backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    @Override
    public void setForeground(Color foregroundColor) {
        mForegroundColor = foregroundColor;
    }

    public void display() {
        new Thread(() -> {
            try {
                createGUI();
                fadeIn();
                Thread.sleep(mDuration);
                fadeOut();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public enum Style {NORMAL, SUCCESS, ERROR}
}