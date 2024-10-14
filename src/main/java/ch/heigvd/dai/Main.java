package ch.heigvd.dai;

import ch.heigvd.dai.commands.*;
import java.io.File;
import picocli.CommandLine;

@CommandLine.Command(
        description = "VarMod is a variable modifier for .txt file",
        version = "1.0.0",
        subcommands = {
                Add.class,
                Modify.class,
                Delete.class,
                Rename.class
        },
        scope = CommandLine.ScopeType.INHERIT,
        mixinStandardHelpOptions = true)
public class Main {

    @CommandLine.Parameters(
            index = "0",
            description = "The name of the file containig variables")
    protected String fileName;

    public String getFileName(){
        return this.fileName;
    }

    public static void main(String[] args){
        String jarFilename = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
        int exitCode = new CommandLine(new Main()).setCommandName(jarFilename).execute(args);

        System.exit(exitCode);
    }

}