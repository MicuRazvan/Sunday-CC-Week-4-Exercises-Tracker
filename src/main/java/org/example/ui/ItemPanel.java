package org.example.ui;

import org.example.model.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ItemPanel extends JPanel {

    private final Item item;
    private final Color defaultBackground;
    private final Runnable deleteCallback;

    public ItemPanel(Item item, Runnable deleteCallback) {
        this.item = item;
        this.deleteCallback = deleteCallback;
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                new EmptyBorder(5, 5, 5, 5)
        ));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        AutoResizingTextArea nameArea = new AutoResizingTextArea(item.getName(), Font.BOLD);
        AutoResizingTextArea linkArea = new AutoResizingTextArea(item.getLink(), Font.ITALIC);
        AutoResizingTextArea descriptionArea = new AutoResizingTextArea(item.getDescription(), Font.PLAIN);

        nameArea.setName("name");
        linkArea.setName("link");
        descriptionArea.setName("description");

        nameArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameArea.getPreferredSize().height));
        linkArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, linkArea.getPreferredSize().height));
        descriptionArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, descriptionArea.getPreferredSize().height));

        contentPanel.add(nameArea);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(linkArea);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(descriptionArea);

        add(contentPanel, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(deleteButton.getFont().deriveFont(Font.PLAIN, 10f));
        deleteButton.setMargin(new Insets(2, 5, 2, 5));
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this item?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                deleteCallback.run();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        this.defaultBackground = getBackground();
    }

    public Item getItem() {
        return item;
    }

    public void setSelected(boolean isSelected) {
        if (isSelected) {
            setBackground(SystemColor.controlHighlight);
        } else {
            setBackground(defaultBackground);
        }
        setOpaque(isSelected);
    }
}