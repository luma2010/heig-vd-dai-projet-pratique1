package ch.heigvd.dai.commands;

import picocli.CommandLine;
import ch.heigvd.dai.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CommandLine.Command(name="Rename", description="Rename a variable in a file.")
public class Rename implements Runnable {
    @CommandLine.ParentCommand
    protected Main parent;

    @CommandLine.Parameters(
            paramLabel = "<varName>",
            description = "Name of the variable to change"
    )
    private String varName;

    @CommandLine.Parameters(
            paramLabel = "<newVarname>",
            description = "New name of the variable"
    )
    private String newVarname;

    @Override
    public void run() {

        try (InputStream is = new FileInputStream(parent.getFileName());
             Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(reader);
        ) {
            boolean varExists = false;
            boolean newVarExists = false;
            List<String> lines = new ArrayList<>();
            String line;

            // Première passe pour vérifier l'existence de varName et newVarname
            while ((line = br.readLine()) != null) {
                String[] part = line.split("=");
                if (Objects.equals(part[0], varName)) {
                    varExists = true;
                }
                if (Objects.equals(part[0], newVarname)) {
                    newVarExists = true;
                }
                lines.add(line + "\n");
            }

            // Si varName existe, et que newVarname n'existe pas, on procède au renommage
            if (varExists && !newVarExists) {

                try (OutputStream os = new FileOutputStream(parent.getFileName());
                     Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                     BufferedWriter bw = new BufferedWriter(writer)) {

                    for (int i = 0; i < lines.size(); i++) {
                        String[] part = lines.get(i).split("=");
                        if (Objects.equals(part[0], varName)) {
                            part[0] = newVarname;
                            String newVar = part[0] + "=" + part[1];
                            lines.set(i, newVar);
                        }
                        bw.write(lines.get(i));
                    }
                    bw.flush();
                } catch (IOException e) {
                    System.out.println("Exception " + e);
                }
                System.out.println("Variable " + varName + " is now " + newVarname);

            } else if(!varExists) {
                System.out.println("Variable " + varName + " not found, no changes made.");
            } else {
                System.out.println("Variable " + varName + " not changed because " + newVarname + " already exists.");
            }

        } catch (IOException e) {
            System.out.println("Exception " + e);
        }
    }
}
