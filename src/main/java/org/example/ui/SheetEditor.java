package org.example.ui;

import org.example.Main;
import org.example.model.ExerciseDay;
import org.example.model.ExerciseSheet;
import org.example.persistence.JsonHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SheetEditor extends JPanel {
    private ExerciseSheet sheet;
    private JPanel daysContainer;
    private Main mainFrame;

    public SheetEditor(ExerciseSheet sheet, Main mainFrame) {
        this.sheet = sheet;
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        JTextField sheetNameField = new JTextField(sheet.getName(), 20);
        sheetNameField.setHorizontalAlignment(JTextField.CENTER);
        sheetNameField.setFont(sheetNameField.getFont().deriveFont(Font.BOLD, 16f));
        sheetNameField.getDocument().addUndoableEditListener(e -> sheet.setName(sheetNameField.getText()));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton addDayButton = new JButton("Add Day");
        JButton backButton = new JButton("Back to Menu");
        buttons.add(addDayButton);
        buttons.add(saveButton);
        buttons.add(backButton);

        topPanel.add(sheetNameField, BorderLayout.CENTER);
        topPanel.add(buttons, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        daysContainer = new JPanel();
        daysContainer.setLayout(new BoxLayout(daysContainer, BoxLayout.X_AXIS));
        refreshDayPanels();
        add(new JScrollPane(daysContainer), BorderLayout.CENTER);

        addDayButton.addActionListener(e -> {
            int dayNumber = sheet.getDays().size() + 1;
            sheet.addDay(new ExerciseDay("Day " + dayNumber));
            refreshDayPanels();
        });

        saveButton.addActionListener(e -> saveSheet());

        backButton.addActionListener(e -> mainFrame.showMainMenu());
    }

    private void refreshDayPanels() {
        daysContainer.removeAll();
        for (ExerciseDay day : sheet.getDays()) {
            Runnable deleteCallback = () -> {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete '" + day.getName() + "'?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    sheet.getDays().remove(day);
                    refreshDayPanels();
                }
            };

            DayPanel dayPanel = new DayPanel(day, deleteCallback);
            daysContainer.add(dayPanel);
            daysContainer.add(Box.createRigidArea(new Dimension(10, 0))); // Spacer
        }
        daysContainer.revalidate();
        daysContainer.repaint();
    }

    private void saveSheet() {
        if (sheet.getName() == null || sheet.getName().trim().isEmpty() || sheet.getName().equals("New Sheet Name")) {
            JOptionPane.showMessageDialog(this, "Enter a valid sheet name before saving.", "Invalid Name", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            JsonHandler.saveSheet(sheet);
            JOptionPane.showMessageDialog(this, "Sheet '" + sheet.getName() + "' saved successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving sheet: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}