package view.util;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import org.jdesktop.swingx.JXFormattedTextField;

public class ComponentFactory
{

    public static JFormattedTextField newInput(String inputHint, Dimension dim, ActionListener listener)
    {
        JXFormattedTextField txtField = new JXFormattedTextField(inputHint);
        txtField.setPreferredSize(dim);
        txtField.addActionListener(listener);
        return txtField;
    }

    public static JLabel newLabel(String string)
    {
        JLabel label = new JLabel(string);
        label.setPreferredSize(Size.Medium);
        return label;
    }

    public static JLabel newLabel()
    {
        return newLabel(null);
    }

    public static JButton newButton(String string, ActionListener listener, Dimension dimension)
    {
        JButton button = new JButton(string);
        button.setFocusable(false);
        button.setPreferredSize(dimension);
        button.addActionListener(listener);
        return button;
    }

    public static JButton newButton(ImageIcon img, ActionListener listener)
    {
        JButton button = new JButton(img);
        button.setFocusable(false);
        button.setPreferredSize(Size.Small);
        button.addActionListener(listener);
        return button;
    }
}
