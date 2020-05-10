package Stages;

import Processor.Processor;

public class InstructionFetch {
    public static String Instruction;
    public static String pcInst;

    public static String InstFetch(int i) {
        Instruction = Processor.c.readcashe(i);
        System.out.println("Instruction Fetched: "+Instruction);
        pcInst = String.format("%016d", Integer.parseInt(Integer.toBinaryString(Processor.pc.pc)));
        System.out.println("Next PC: "+pcInst);
        Processor.fetch = true;
        return Instruction;
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
