package ch.heigvd.dai.commands;

import picocli.CommandLine;
import ch.heigvd.dai.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@CommandLine.Command(name="Add", description="Add a variable to a file.")
public class Add implements Runnable {
    @CommandLine.ParentCommand protected Main parent;

    @CommandLine.Parameters(
            paramLabel = "<varName>",
            description = "Name of the variable to add"
    )
    private String varName;

    @CommandLine.Parameters(
            paramLabel = "<varValue>",
            description = "Value of the variable"
    )
    private String varValue;

    @Override
    public void run(){
        try{
            InputStream is = new FileInputStream(parent.getFileName());
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);

            List<String> lines = new ArrayList<String>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line+"\n");
            }
            br.close();

            OutputStream os = new FileOutputStream(parent.getFileName());
            Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(writer);
            for(int i = 0; i < lines.size(); i++){
                bw.write(lines.get(i));
            }
            String variable = varName+"="+varValue+"\n";
            bw.write(variable);
            bw.flush();
        }catch (IOException e){
            System.out.println("Exeption "+e);
        }
    }
}
