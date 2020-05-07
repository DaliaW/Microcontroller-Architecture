package Processor;

import Components.*;
import Stages.*;

import java.util.Arrays;

import static Stages.InstructionDecode.rt;

public class Processor {
        //InstructionMemory IM;
        static int clockcycle=1;
//	static int pc;
        InstructionFetch IF;
        InstructionDecode ID;
        Execute EXC;
        MemoryAccess MA;
        WriteBack WB;
        public static Cache c;
        public static boolean fetch=false;
        public static boolean decode=false;
        public static boolean exec=false;
        public static boolean memacc=false;
        public static boolean writeback ;
        public static Memory m;
        public static PC pc;
        public static int ReadData1;
        public static int ReadData2;
        public static int RegDst;
        public static int RegWrite ;
        public static String ALUOp;
        public static int ALUSrc;
        public static int PCSrc;
        public static int MemRead;
        public static int MemWrite;
        public static int MemToReg;
        public static int Branch;
        public static RegisterFile registerFile;


        public static void main(String[] args) {
                registerFile = new RegisterFile(16);
                ALUSrc = 0; ALUOp = ""; RegWrite = 0; RegDst = 0; PCSrc = 0;
                MemRead=0;MemToReg=0;MemWrite = 0;Branch = 0;

                pc = new PC();
                m = new Memory();
                c = new Cache();
                m.Load(); //Load Memory
                c.Load(); //Load Cache

                for(int i = 0 ; i < m.getNumberOfInstructions();i++) {
                        System.out.println("Register File: " + registerFile.ToString()+"\n");
                        InstructionFetch.InstFetch(i);
                        InstructionDecode.InstDecode(InstructionFetch.Instruction);
                        Execute.Execute(ALUOp,ALUSrc,ReadData1,ReadData2,pc.getPc());
                        MemoryAccess.MemAccess(ALU.r+"",ReadData2+"",MemWrite,MemRead);
                        WriteBack.WriteBack(ALU.r+"",rt,RegWrite,MemToReg+"",RegDst+"",registerFile);
                        System.out.println("Clock Cycle: "+i);
                }
        }
}
