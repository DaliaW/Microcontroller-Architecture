package Stages;

import Components.PC;
import Processor.Processor;

public class InstructionFetch {
    public static String Instruction;

    public static void InstFetch(int i) {
        Instruction = Processor.c.readcashe(Processor.pc.getPc());
        System.out.println("Instruction Fetched: "+Instruction);
        System.out.println("Next PC: "+ProgCount());
    }

    /*
    This method points to the next instruction to be executed. PC is incremented
    by 4 after each instruction is executed. A branch instruction alters the flow of control by
    modifying the PC.
     */
    public static int ProgCount() {
        return Processor.pc.getPc()+4;
    }
}
