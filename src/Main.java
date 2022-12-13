import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        FileReader fr = new FileReader("commands.txt");
        BufferedReader br = new BufferedReader(fr);
        while (true) {
            if (!br.ready()) break;
            String s = br.readLine();
            String[] splitterString = s.split(" ");
            String ins = splitterString[0];
            String pid = splitterString[1];
            switch (ins) {
                case "create_process" -> signals.create_process(pid, splitterString[2]);
                case "run_process" -> signals.run_process(pid);
                case "block_process" -> signals.block_process(pid);
                case "unblock_process" -> signals.unblock_process(pid);
                case "kill_process" -> signals.kill_process(pid);
                case "show_context" -> signals.show_context(pid);
            }
        }
    }

    static class signals {
        static FileReader fr;
        static BufferedReader br;

        static void create_process(String pid, String instruction_file) throws IOException {
            fr = new FileReader(instruction_file);
            br = new BufferedReader(fr);

        }

        static void run_process(String pid) throws IOException {
            while (true) {
                if (!br.ready()) break;
                String s = br.readLine();
                String[] splitterString = s.split(" ");
                String ins = splitterString[0];
                int Temp = Integer.parseInt(splitterString[1]);
                switch (ins) {
                    case "load":

                        break;
                    case "sub":

                        break;
                    case "add":

                        break;
                    case "mul":

                        break;
                }
            }
        }

        static void block_process(String pid) {

        }

        static void unblock_process(String pid) {

        }

        static void kill_process(String pid) {

        }

        static void show_context(String pid) {
            System.out.print("Process ID : " + "\n" +
                    "Instruction Register : " + "\n" +
                    "Accumulator : " + "\t\t\t\t\t" + "Temp : " + "\n" +
                    "Program Counter : " + "\t\t\t\t" + "State : " + "\n");
        }

    }
}