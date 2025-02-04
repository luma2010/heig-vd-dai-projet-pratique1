package ch.heigvd.dai.commands;

import picocli.CommandLine;
import ch.heigvd.dai.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CommandLine.Command(name="Modify", description="Modify the value of a variable in a file.")
public class Modify implements Runnable {
    @CommandLine.ParentCommand
    protected Main parent;

    @CommandLine.Parameters(
            paramLabel = "<varName>",
            description = "Name of the variable to change"
    )
    private String varName;

    @CommandLine.Parameters(
            paramLabel = "<newVarValue>",
            description = "New value of the variable"
    )
    private String newVarValue;

    @Override
    public void run() {
        try (InputStream is = new FileInputStream(parent.getFileName());
             Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(reader);) {

            boolean varExists = false;
            List<String> lines = new ArrayList<String>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] part = line.split("=");
                if (Objects.equals(varName, part[0])) {
                    varExists = true;
                }
                lines.add(line + "\n");
            }
            br.close();

            if (varExists) {
                try (OutputStream os = new FileOutputStream(parent.getFileName());
                     Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                     BufferedWriter bw = new BufferedWriter(writer);) {
                    for (int i = 0; i < lines.size(); i++) {
                        String[] part = lines.get(i).split("\\=");
                        if (Objects.equals(part[0], varName)) {
                            part[1] = newVarValue + "\n";
                            String newVar = part[0] + "=" + part[1];
                            lines.set(i, newVar);
                        }
                        bw.write(lines.get(i));
                    }
                    bw.flush();
                } catch (IOException e) {
                    System.out.println("Exeption " + e);
                }
                System.out.println("Value of variable " + varName + " is now " + newVarValue);
            }else{
                System.out.println("Variable " + varName + " not found, no changes made.");
            }


        } catch (IOException e) {
            System.out.println("Exeption " + e);
        }
    }
}