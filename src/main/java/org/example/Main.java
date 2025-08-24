package org.example;

import org.example.model.ExerciseSheet;
import org.example.persistence.JsonHandler;
import org.example.ui.SheetEditor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {
        setTitle("Exercise Tracker");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, "MENU");

        add(mainPanel);
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton newSheetButton = new JButton("New Sheet");
        newSheetButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 0;
        menuPanel.add(newSheetButton, gbc);

        JButton loadButton = new JButton("Load Sheet");
        loadButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 1;
        menuPanel.add(loadButton, gbc);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 2;
        menuPanel.add(exitButton, gbc);

        newSheetButton.addActionListener(e -> showEditorPanel(new ExerciseSheet("New Sheet Name")));

        loadButton.addActionListener(e -> loadSheet());

        exitButton.addActionListener(e -> System.exit(0));

        return menuPanel;
    }

    private void loadSheet() {
        File[] savedFiles = JsonHandler.getSavedFiles();
        if (savedFiles == null || savedFiles.length == 0) {
            JOptionPane.showMessageDialog(this, "No saved sheets found.", "Load", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Object[] fileNames = Arrays.stream(savedFiles).map(File::getName).toArray();
        String selectedFileName = (String) JOptionPane.showInputDialog(
                this, "Select a sheet to load:", "Load Sheet",
                JOptionPane.PLAIN_MESSAGE, null, fileNames, fileNames[0]);

        if (selectedFileName != null) {
            try {
                File fileToLoad = new File("saves/" + selectedFileName);
                ExerciseSheet sheet = JsonHandler.loadSheet(fileToLoad);
                showEditorPanel(sheet);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void showEditorPanel(ExerciseSheet sheet) {
        SheetEditor editorPanel = new SheetEditor(sheet, this);
        mainPanel.add(editorPanel, "EDITOR");
        cardLayout.show(mainPanel, "EDITOR");
    }

    public void showMainMenu() {
        cardLayout.show(mainPanel, "MENU");
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof SheetEditor) {
                mainPanel.remove(comp);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}