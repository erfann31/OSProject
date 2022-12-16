import java.io.*;
import java.util.ArrayList;

public class Main {
    static ArrayList<signals.process> processes = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        FileReader fr = new FileReader("commands.txt");
        BufferedReader br = new BufferedReader(fr);
        int a = 0;
        while (br.ready()) {
            String s = br.readLine();
            System.out.println("Loop" + a++ + " - " + s);

            String[] splitterString = s.split(" ");
            String ins = splitterString[0];
            String pid = splitterString[1];

            switch (ins) {
                case "create_process" -> new signals().create_process(pid, splitterString[2]);
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


        void create_process(String pid, String instruction_file) {
            if (!process.contains(pid)) {
                processes.add(new process(pid, instruction_file));
            } else System.out.println("duplicate process!");
        }

        static void run_process(String pid) throws IOException {
            if (process.contains(pid)) {
                for (process p : processes)
                    if (p.getID().equals(pid)) {
                        fr = new FileReader(p.getInstructionFile());
                        br = new BufferedReader(fr);
                        if (p.getState() == process.state.blocked) System.out.println("this process is blocked");
                        else {
                            for (int i = 0; i < p.pc; i++) {
                                br.readLine();
                            }
                            if (!br.ready()) {
                                p.resetPc();
                                System.out.println("Process has finished running");
                            } else {
                                p.setState(3);
                                String s = br.readLine();
                                String[] splitterString = s.split(" ");
                                String ins = splitterString[0];
                                int Temp = Integer.parseInt(splitterString[1]);
                                p.setInstRegister(s);
                                p.setTempRegister(Temp);
                                switch (ins) {
                                    case "load" -> p.setAccRegister(Temp);
                                    case "sub" -> p.setAccRegister(p.getAccRegister() - p.getTempRegister());
                                    case "add" -> p.setAccRegister(p.getAccRegister() + p.getTempRegister());
                                    case "mul" -> p.setAccRegister(p.getAccRegister() * p.getTempRegister());
                                }
                                p.addPc();
                                p.setState(1);
                            }
                        }
                    }
            } else System.out.println("process doesn't exist");
        }


        static void block_process(String pid) {
            if (process.contains(pid)) {
                for (process p : processes)
                    if (p.getID().equals(pid)) {
                        p.setState(2);
                        break;
                    }
            } else System.out.println("process doesn't exist");
        }

        static void unblock_process(String pid) {
            if (process.contains(pid)) {
                for (process p : processes)
                    if (p.getID().equals(pid)) {
                        p.setState(1);
                        break;
                    }
            } else System.out.println("process doesn't exist");
        }

        static void kill_process(String pid) {
            if (process.contains(pid)) {
                for (process p : processes)
                    if (p.getID().equals(pid)) {
                        processes.remove(p);
                        break;
                    }
            } else System.out.println("process doesn't exist");
        }

        static void show_context(String pid) {
            if (process.contains(pid)) {
                for (process p : processes)
                    if (p.getID().equals(pid)) {
                        System.out.print("Process ID : " + p.getID() + "\n" +
                                "Instruction Register : " + p.getInstRegister() + "\n" +
                                "Accumulator : " + p.getAccRegister() + "\t\t\t\t\t" + "Temp : " + p.getTempRegister() + "\n" +
                                "Program Counter : " + p.getPc() + "\t\t\t\t" + "State : " + p.getState() + "\n");
                        break;
                    }
            } else System.out.println("process doesn't exist");
        }

        class process {
            float tempRegister;
            float accRegister;
            String instRegister;
            static state state;
            String ID;
            String InstructionFile;
            int pc;

            public enum state {
                ready, running, blocked
            }

            process(String id, String instructionFile) {
                this.ID = id;
                this.setState(1);
                this.pc = 0;
                this.tempRegister = 0;
                this.accRegister = 0;
                this.InstructionFile = instructionFile;
                this.instRegister = null;
            }

            static boolean contains(String id) {
                for (process p : processes)
                    if (p.getID().equals(id))
                        return true;
                return false;
            }

            public state getState() {
                return state;
            }

            public int getPc() {
                return pc;
            }

            private String getID() {
                return ID;
            }

            public float getTempRegister() {
                return tempRegister;
            }

            public float getAccRegister() {
                return accRegister;
            }

            public String getInstRegister() {
                return instRegister;
            }

            public String getInstructionFile() {
                return InstructionFile;
            }

            public void setTempRegister(float tempRegister) {
                this.tempRegister = tempRegister;
            }

            public void setAccRegister(float accRegister) {
                this.accRegister = accRegister;
            }

            public void setInstRegister(String instRegister) {
                this.instRegister = instRegister;
            }

            public void setState(int state) {
                if (state == 1)
                    this.state = process.state.ready;
                else if (state == 2)
                    this.state = process.state.blocked;
                else if (state == 3)
                    this.state = process.state.running;
            }

            public void addPc() {
                this.pc++;
            }

            public void resetPc() {
                this.pc = 0;
            }
        }
    }
}
