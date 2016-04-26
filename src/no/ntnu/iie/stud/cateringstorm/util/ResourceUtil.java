package no.ntnu.iie.stud.cateringstorm.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Helper class for loading application resources.
 */
public final class ResourceUtil {
    /**
     * The path of the map html file inside resources.
     */
    private static final String MAP_PATH = "html/map.html";

    /**
     * Helper class for setting the application icon.
     * @param frame The window to set the icon for.
     */
    public static void setApplicationIcon(Window frame) {
        ClassLoader loader = ResourceUtil.class.getClassLoader();
        if (loader == null) {
            System.err.println("Could not get class loader!");
        } else {
            try {
                frame.setIconImage(ImageIO.read(loader.getResourceAsStream("icons/food-apple.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Helper method for creating ImageIcons from project resources
     *
     * @param path The resource path (image)
     * @return An ImageIcon initialized with the loaded resource
     */
    public static ImageIcon getIconResource(String path) {
       // path = "icons/" + path;

        ClassLoader loader = ResourceUtil.class.getClassLoader();
        if (loader == null) {
            System.err.println("Could not get class loader!");
            return new ImageIcon();
        }

        URL resource = loader.getResource(path);
        if (resource == null) {
            System.err.println("Could not get resource \"" + path + "\"");
            return new ImageIcon();
        }
        return new ImageIcon(resource);
    }

    /**
     * Loads the map HTML from the project resources
     *
     * @return A String containing the HTML
     */
    public static String getMapHTML() {
        ClassLoader loader = ResourceUtil.class.getClassLoader();
        if (loader == null) {
            System.err.println("Could not get class loader!");
            return null;
        }

        return convertStreamToString(loader.getResourceAsStream(MAP_PATH));
    }

    /**
     * Reads an input stream into a string.
     * * @param stream The input stream to read from.
     * @return The read string.
     */
    private static String convertStreamToString(InputStream stream) {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
