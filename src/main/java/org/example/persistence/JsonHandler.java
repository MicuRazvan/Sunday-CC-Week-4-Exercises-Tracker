package org.example.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.ExerciseSheet;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonHandler {
    private static final String SAVE_DIR = "saves/";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveSheet(ExerciseSheet sheet) throws IOException {
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (FileWriter writer = new FileWriter(SAVE_DIR + sheet.getName() + ".json")) {
            gson.toJson(sheet, writer);
        }
    }

    public static ExerciseSheet loadSheet(File file) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, ExerciseSheet.class);
        }
    }

    public static File[] getSavedFiles() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            return new File[0];
        }
        return dir.listFiles((d, name) -> name.endsWith(".json"));
    }
}