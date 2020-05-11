package Processor;

import Components.*;
import Stages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static Stages.InstructionDecode.rt;

public class Processor {
        static int clockcycle=1;
        static InstructionFetch IF;
        static InstructionDecode ID;
        static Execute EXC;
        static MemoryAccess MA;
        static WriteBack WB;
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
        public static int Jump;


        static Queue<String> qFetch;
        static PipelineRegister qdecode;
        static PipelineRegister qexec;
        static PipelineRegister qmem;
        static PipelineRegister qwb;

        public static RegisterFile registerFile;


        public static void main(String[] args) {


                registerFile = new RegisterFile();
                ALUSrc = 0; ALUOp = ""; RegWrite = 0; RegDst = 0; PCSrc = 0;
                MemRead=0;MemToReg=0;MemWrite = 0;Branch = 0;
                qFetch = new LinkedList<String>();
                qdecode = new PipelineRegister();
                qexec = new PipelineRegister();
                qmem = new PipelineRegister();
                qwb = new PipelineRegister();
                pc = new PC();
                m = new Memory();
                c = new Cache();
                m.Load(); //Load Memory
                c.Load(); //Load Cache

                System.out.println("Register File before any changes: " + registerFile.ToString()+"\n");


                String instruction = "";
                for(int i = 0 ; i < m.getNumberOfInstructions();i++) {
                        int tempPc = pc.getPc();
                        if (memacc) { // memory access to write back
                                ArrayList<String> InstMemAcc = qmem.RemoveFromPipeReg();
                                String instnumb = InstMemAcc.get(InstMemAcc.size()-1);
                                System.out.println("...............................................");
                                System.out.println("write back stage of instruction: "+instnumb);

                                ArrayList<String> InstWriteBack = WB.WriteBack(ALU.r + "", rt, RegWrite, MemToReg, RegDst, registerFile);
                                System.out.println("Register File: " + registerFile.ToString()+"\n");



                        }
                        if (exec) { //decode to memory access
                                ArrayList<String> InstExec = qexec.RemoveFromPipeReg();
                                String instnumb = InstExec.get(5);
                                System.out.println("memory access stage of instruction: " + instnumb);

                                ArrayList<String> InstMemAcc = MA.MemAccess(ALU.r + "", ReadData2 + "", MemWrite, MemRead);
                                InstMemAcc.add(instnumb);
                                qmem.AddToReg(InstMemAcc);
                                exec = false;
                        }
                        if (decode) { //decode to execute
                                ArrayList<String> instDec = qdecode.RemoveFromPipeReg();
                                String instnumb = instDec.get(5);
                                System.out.println("executing instruction: " + instnumb);
                                ArrayList<String> InstExec = EXC.Execute(ALUOp, ALUSrc, ReadData1, ReadData2, pc.getPc());
                                InstExec.add(instnumb);
                                qexec.AddToReg(InstExec);
                                decode = false;

                        }

                        if (fetch) { //fetch to decode
                                System.out.println("Decoding instruction: " + pc.getPc());
                                ArrayList<String> instDec = ID.InstDecode(qFetch.remove());
                                instDec.add(Integer.toString(pc.pc)); //no. of instruction
                                qdecode.AddToReg(instDec);
                                fetch = false;

                        }

                        System.out.println("fetching instruction: " + (i + 1));
                        //IF.InstFetch(pc.pc);
                        qFetch.add(IF.InstFetch(pc.pc));
                        System.out.println("clock cycle: " + clockcycle);
                        clockcycle++;
                        pc.pc++;

                }
                        while(qmem.isRegEmpty()){
                                int tempPc = pc.getPc();
                                if (memacc) { // memory access to write back
                                        ArrayList<String> InstMemAcc = qmem.RemoveFromPipeReg();
                                        String instnumb = InstMemAcc.get(2);
                                        System.out.println("write back stage of instruction");

                                        ArrayList<String> InstWriteBack = WB.WriteBack(ALU.r + "", rt, RegWrite, MemToReg, RegDst, registerFile);
                                        String WriteData = InstWriteBack.get(0);
                                        System.out.println("Write data =" + WriteData);

                                }
                                if (exec) { //decode to memory access
                                        ArrayList<String> InstExec = qexec.RemoveFromPipeReg();
                                        String instnumb = InstExec.get(5);
                                        System.out.println("memory access stage of instruction: " + instnumb);

                                        ArrayList<String> InstMemAcc = MA.MemAccess(ALU.r + "", ReadData2 + "", MemWrite, MemRead);
                                        InstMemAcc.add(instnumb);
                                        qmem.AddToReg(InstMemAcc);
                                        exec = false;
                                }
                                if (decode) { //decode to execute
                                        ArrayList<String> instDec = qdecode.RemoveFromPipeReg();
                                        String instnumb = instDec.get(5);
                                        System.out.println("executing instruction: " + instnumb);
                                        ArrayList<String> InstExec = EXC.Execute(ALUOp, ALUSrc, ReadData1, ReadData2, pc.getPc());
                                        InstExec.add(instnumb);
                                        qexec.AddToReg(InstExec);
                                        decode = false;

                                }

                                if (fetch) { //fetch to decode
                                        System.out.println("Decoding instruction: " + pc.getPc());
                                        ArrayList<String> instDec = ID.InstDecode(qFetch.remove());
                                        instDec.add(Integer.toString(pc.pc)); //no. of instruction
                                        qdecode.AddToReg(instDec);
                                        fetch = false;

                                }
                                System.out.println("clock cycle: "+clockcycle);
                                clockcycle++;
                        }

                        while(qexec.isRegEmpty()){
                                int tempPc = pc.getPc();

                                if (memacc) { // memory access to write back
                                        ArrayList<String> InstMemAcc = qmem.RemoveFromPipeReg();
                                        String instnumb = InstMemAcc.get(2);
                                        System.out.println("write back stage of instruction: "+ instnumb);

                                        ArrayList<String> InstWriteBack = WB.WriteBack(ALU.r + "", rt, RegWrite, MemToReg, RegDst, registerFile);
                                        String WriteData = InstWriteBack.get(0);
                                        System.out.println("Write data =" + WriteData);

                                }
                                if (exec) { //decode to memory access
                                        ArrayList<String> InstExec = qexec.RemoveFromPipeReg();
                                        String instnumb = InstExec.get(5);
                                        System.out.println("memory access stage of instruction: " + instnumb);

                                        ArrayList<String> InstMemAcc = MA.MemAccess(ALU.r + "", ReadData2 + "", MemWrite, MemRead);
                                        InstMemAcc.add(instnumb);
                                        qmem.AddToReg(InstMemAcc);
                                        exec = false;
                                }
                                if (decode) { //decode to execute
                                        ArrayList<String> instDec = qdecode.RemoveFromPipeReg();
                                        String instnumb = instDec.get(5);
                                        System.out.println("executing instruction: " + instnumb);
                                        ArrayList<String> InstExec = EXC.Execute(ALUOp, ALUSrc, ReadData1, ReadData2, pc.getPc());
                                        InstExec.add(instnumb);
                                        qexec.AddToReg(InstExec);
                                        decode = false;

                                }

                                if (fetch) { //fetch to decode
                                        System.out.println("Decoding instruction: " + pc.getPc());
                                        ArrayList<String> instDec = ID.InstDecode(qFetch.remove());
                                        instDec.add(Integer.toString(pc.pc)); //no. of instruction
                                        qdecode.AddToReg(instDec);
                                        fetch = false;

                                }
                                System.out.println("clock cycle: "+clockcycle);
                                clockcycle++;
                        }
        }
}
