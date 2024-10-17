package ch.heigvd.dai.commands;

import picocli.CommandLine;
import ch.heigvd.dai.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CommandLine.Command(name = "Delete", description = "Delete a variable from a file")
public class Delete implements Runnable {
    @CommandLine.ParentCommand protected Main parent;

    @CommandLine.Parameters(
            paramLabel = "<varName>",
            description = "Name of the variable to delete"
    )
    private String varName;

    @Override
    public void run() {
        try (InputStream in = new FileInputStream(parent.getFileName());
             Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(reader)) {

            boolean varExists = false;
            List<String> lines = new ArrayList<>();
            String line;

            // Lecture du fichier et vérification de l'existence de la variable
            while ((line = br.readLine()) != null) {
                String[] part = line.split("\\=");
                if (Objects.equals(part[0], varName)) {
                    varExists = true;
                } else {
                    lines.add(line + "\n");
                }
            }

            // Si la variable existe, on réécrit le fichier sans elle.
            if (varExists) {
                try (OutputStream os = new FileOutputStream(parent.getFileName());
                     Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                     BufferedWriter bw = new BufferedWriter(writer)) {
                    for (String s : lines) {
                        bw.write(s);
                    }
                    bw.flush();
                } catch (IOException e) {
                    System.out.println("Exception: " + e);
                }
                System.out.println("Variable " + varName + " has been deleted.");
            } else {
                System.out.println("Variable " + varName + " not found, nothing changed.");
            }

        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }
}
