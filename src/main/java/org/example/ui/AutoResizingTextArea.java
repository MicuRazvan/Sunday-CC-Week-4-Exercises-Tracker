package org.example.ui;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AutoResizingTextArea extends JTextArea {

    public AutoResizingTextArea(String text, int style) {
        super(text);
        setFont(getFont().deriveFont(style, 12f));

        setEditable(false);
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(true);
        setFocusable(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = getParent();
                if (parent != null) {
                    MouseEvent newEvent = SwingUtilities.convertMouseEvent(AutoResizingTextArea.this, e, parent);
                    parent.dispatchEvent(newEvent);
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        int width = getWidth() > 0 ? getWidth() : 280;
        return getPreferredSizeForWidth(width);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    private Dimension getPreferredSizeForWidth(int width) {
        View view = getUI().getRootView(this);
        view.setSize(width, 0);

        float preferredHeight = view.getPreferredSpan(View.Y_AXIS);

        float preferredWidth = view.getPreferredSpan(View.X_AXIS);

        Insets insets = getInsets();
        int finalWidth = (int) Math.ceil(preferredWidth) + insets.left + insets.right;
        int finalHeight = (int) Math.ceil(preferredHeight) + insets.top + insets.bottom;

        return new Dimension(finalWidth, finalHeight);
    }
}
