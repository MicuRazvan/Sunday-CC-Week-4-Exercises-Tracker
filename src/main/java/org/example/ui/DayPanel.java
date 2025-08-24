package org.example.ui;

import org.example.model.ExerciseDay;
import org.example.model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DayPanel extends JPanel {
    private ExerciseDay day;
    private JPanel itemsContainer;
    private ItemPanel selectedItemPanel;
    private List<ItemPanel> itemPanels = new ArrayList<>();

    public DayPanel(ExerciseDay day, Runnable deleteCallback) {
        this.day = day;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setMinimumSize(new Dimension(250, 200));
        setPreferredSize(new Dimension(300, 400));

        JTextField dayNameField = new JTextField(day.getName());
        dayNameField.setHorizontalAlignment(JTextField.CENTER);
        dayNameField.setFont(dayNameField.getFont().deriveFont(Font.BOLD, 14f));
        dayNameField.getDocument().addUndoableEditListener(e -> day.setName(dayNameField.getText()));
        add(dayNameField, BorderLayout.NORTH);

        itemsContainer = new JPanel();
        itemsContainer.setLayout(new BoxLayout(itemsContainer, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(itemsContainer,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setPreferredSize(new Dimension(280, 0)); // Ensure consistent width
        refreshItemPanels();
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton addItemButton = new JButton("Add Item");
        addItemButton.setMargin(new Insets(2, 5, 2, 5));
        JButton deleteDayButton = new JButton("Delete Day");
        deleteDayButton.setMargin(new Insets(2, 5, 2, 5));
        buttonPanel.add(addItemButton);
        buttonPanel.add(deleteDayButton);

        addItemButton.addActionListener(e -> addItem());
        deleteDayButton.addActionListener(e -> deleteCallback.run());

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshItemPanels() {
        itemsContainer.removeAll();
        itemPanels.clear();
        selectedItemPanel = null;

        for (Item item : day.getItems()) {
            ItemPanel itemPanel = new ItemPanel(item, () -> {
                day.removeItem(item);
                refreshItemPanels();
            });

            itemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setSelectedItem(itemPanel);
                    if (e.getClickCount() == 2) {
                        editItem(itemPanel);
                    }
                }
            });

            itemsContainer.add(itemPanel);
            itemsContainer.add(Box.createRigidArea(new Dimension(0, 5)));
            itemPanels.add(itemPanel);
        }

        itemsContainer.revalidate();
        itemsContainer.repaint();
    }

    private void setSelectedItem(ItemPanel itemPanel) {
        if (selectedItemPanel != null) {
            selectedItemPanel.setSelected(false);
        }
        selectedItemPanel = itemPanel;
        selectedItemPanel.setSelected(true);
    }

    private void addItem() {
        Item newItem = new Item("New Item", "http://", "Description...");
        day.addItem(newItem);
        refreshItemPanels();
    }

    private void editItem(ItemPanel itemPanel) {
        Item item = itemPanel.getItem();
        JTextField nameField = new JTextField(item.getName());
        JTextField linkField = new JTextField(item.getLink());
        JTextArea descriptionArea = new JTextArea(item.getDescription(), 4, 30);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Link:"));
        panel.add(linkField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Item",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            item.setName(nameField.getText());
            item.setLink(linkField.getText());
            item.setDescription(descriptionArea.getText());
            refreshItemPanels();
        }
    }
}