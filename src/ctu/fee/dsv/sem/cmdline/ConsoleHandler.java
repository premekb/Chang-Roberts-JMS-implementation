package ctu.fee.dsv.sem.cmdline;

import ctu.fee.dsv.sem.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Copied from Moodle example.
 */
public class ConsoleHandler implements Runnable {

    private boolean reading = true;
    private BufferedReader reader = null;
    private PrintStream out = System.out;
    private PrintStream err = System.err;

    private final Node node;


    public ConsoleHandler(Node node) {
        this.node = node;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }


    private void parse_commandline(String commandline) {
        String[] splitCmdline = commandline.split(" ");
        if (commandline.equals("?")) {
            System.out.print("? - this help");
            System.out.print("get - get the content of the shared variable");
            System.out.print("set {content} - set the content of the shared variable");
            System.out.print("s - print node status");
        } else if (commandline.equals("get")) {
            System.out.println("Shared variable content: " + node.getSharedVariable().getData());
        }
         else if (splitCmdline.length == 2 && splitCmdline[0].equals("set"))
            {
                node.setSharedVariable(splitCmdline[1]);
            }
         else {
            // do nothing
            System.out.print("Unrecognized command.");
        }
    }


    @Override
    public void run() {
        String commandline = "";
        while (reading == true) {
            commandline = "";
            System.out.print("\ncmd > ");
            try {
                commandline = reader.readLine();
                parse_commandline(commandline);
            } catch (IOException e) {
                err.println("ConsoleHandler - error in rading console input.");
                e.printStackTrace();
                reading = false;
            }
        }
        System.out.println("Closing ConsoleHandler.");
    }
}