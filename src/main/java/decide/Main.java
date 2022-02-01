package decide;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        // Read all input from standard input
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br;
            if (args.length != 0) { // Has user supplied path to input?
                br = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(args[0]))));
            } else { // Else read from stdin (perhaps user has mounted stdin to stream of their choice)
                br = new BufferedReader(new InputStreamReader(System.in));
            }
            while (br.ready()) {
                sb.append(br.readLine() + "\n");
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        // System.out.println("This was the read input: ");
        // // Print read input:
        // System.out.println(sb.toString());

        // Construct input object using what has been read from stdin and an input parser
        Input input = Input.parse(sb.toString());

        // Calculate the conditions met vector (cmv) for the input
        ConditionsMetVector cmv = new ConditionsMetVector(input);

        // Use the logical connector matrix (lcm) of the input along with the cmv to calculate the
        // preliminary unlocking matrix (pum)
        PreliminaryUnlockingMatrix pum = new PreliminaryUnlockingMatrix(input, cmv);

        // Use the preliminary unlocking vector (puv) of the input along with the pum to calculate
        // the final unlocking vector (fuv)
        FinalUnlockingVector fuv = new FinalUnlockingVector(input.puv, pum);

        // Finally calculate the output launch boolean and print the result
        Output output = new Output(fuv);
        System.out.println(output.launch);

        // Alternatively for those that fancy one-liners:
        // System.out.println(new Output(new FinalUnlockingVector(input.puv, new PreliminaryUnlockingMatrix(input, new ConditionsMetVector(input)))));
    }
}
