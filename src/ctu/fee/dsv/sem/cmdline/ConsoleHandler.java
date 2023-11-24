package ctu.fee.dsv.sem.cmdline;

import ctu.fee.dsv.sem.Node;
import ctu.fee.dsv.sem.communication.messages.election.ElectMessage;

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
            System.out.print("election - start a new election");
            System.out.print("status - print node status");
            System.out.print("logout - exit with informing other nodes");
            System.out.print("terminate - exits without logout");
        } else if (commandline.equals("get")) {
            System.out.println("Shared variable content: " + node.getSharedVariable().getData());
        }
         else if (splitCmdline.length == 2 && splitCmdline[0].equals("set"))
            {
                node.setSharedVariable(splitCmdline[1]);
            }
        else if (commandline.equals("election"))
        {
            node.processMessage(new ElectMessage(node.getNeighbours().prev));
        }
        else if (commandline.equals("status"))
        {
            System.out.println(node.toString());
        }
        else if (commandline.equals("logout"))
        {
            node.logout();
            System.exit(0);
        }
        else if (commandline.equals("terminate"))
        {
            node.terminateWithoutLogout();
            System.exit(1);
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
