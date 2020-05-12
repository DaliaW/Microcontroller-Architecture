package Processor;

import Components.*;
import Stages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static Stages.InstructionDecode.rt;

public class Processor {
    public static int clockcycle=1;
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


    public Processor() {


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

        int instructionCycle=5+ m.getNumberOfInstructions()-1;
        //System.out.println(instructionCycle);
        String instruction = "";
        for(int i = 0 ; i < instructionCycle;i++) {
            int tempPc = pc.getPc();
            if (i<m.getNumberOfInstructions()+4 && memacc) { // memory access to write back
                ArrayList<String> InstMemAcc = qmem.RemoveFromPipeReg();
                System.out.println(InstMemAcc.toString());

//                String  ALUresultret= InstMemAcc.get(0);
//                String ReadData2ret	=	InstMemAcc.get(1);
//                String RegWrite1	=	InstMemAcc.get(9);
//                String RegDst1 = InstMemAcc.get(10);
//                String MemToReg1 = InstMemAcc.get(11);
//
//                String instnumb = InstMemAcc.get(InstMemAcc.size()-1);
//                System.out.println("...............................................");
//                System.out.println("write back stage of instruction: "+instnumb);
//
//                ArrayList<String> InstWriteBack = WB.WriteBack(ALUresultret , ReadData2ret,Integer.parseInt(RegWrite1), Integer.parseInt(MemToReg1), Integer.parseInt(RegDst1), registerFile);
                System.out.println("Register File: " + registerFile.ToString()+"\n");
                memacc=false;


            }
            if (i<m.getNumberOfInstructions()+3 && exec) { //decode to memory access
//                ArrayList<String> InstExec = qexec.RemoveFromPipeReg();
//                String operationName = InstExec.get(0);
//                String readData1 = InstExec.get(1);
//                String  readData2  = InstExec.get(2);
//                String  result  = InstExec.get(3);
//                String  FlagZero  = InstExec.get(4);
//                String opcode=InstExec.get(5);
//                String rsValue=InstExec.get(6);
//                String rtValue=InstExec.get(7);
//                String rd= InstExec.get(8);
//                String func =InstExec.get(9);
//                String Aluop = InstExec.get(10);
//                String RegWrite = InstExec.get(11);
//                String RegDst = InstExec.get(12);
//                String  MemToReg = InstExec.get(13);
//                String MemRead = InstExec.get(14);
//                String MemWrite = InstExec.get(15);
//                String Branch = InstExec.get(16);
//                String Jump  = InstExec.get(17);
//                String instnumb=InstExec.get(18);
//                System.out.println("memory access stage of instruction: " + instnumb);
//
//                ArrayList<String> InstMemAcc = MA.MemAccess(result , readData2 , Integer.parseInt(MemWrite), Integer.parseInt(MemRead));
//                InstMemAcc.add(operationName);
//                InstMemAcc.add(readData1);
//                //InstMemAcc.add(readData2);
//                //InstMemAcc.add(result);
//                InstMemAcc.add(FlagZero);
//                InstMemAcc.add(opcode);
//                InstMemAcc.add(rsValue);
//                InstMemAcc.add(rtValue);
//                InstMemAcc.add(rd);
//                InstMemAcc.add(func);
//                InstMemAcc.add(Aluop);
//                InstMemAcc.add(RegWrite);
//                InstMemAcc.add( RegDst);
//                InstMemAcc.add(  MemToReg);
//                InstMemAcc.add(MemRead);
//                InstMemAcc.add(  MemWrite);
//                InstMemAcc.add( Branch);
//                InstMemAcc.add( Jump);
//                InstMemAcc.add(instnumb);
//                qmem.AddToReg(InstMemAcc);
//                exec = false;
            }
            if (i<m.getNumberOfInstructions()+2 && decode) { //decode to execute
                ArrayList<String> instDec = qdecode.RemoveFromPipeReg();
                String opcode=instDec.get(0);
                String rsValue=instDec.get(1);
                String rtValue=instDec.get(2);
                String rd= instDec.get(3);
                String func = instDec.get(4);
                String Aluop = instDec.get(5);
                String RegWrite = instDec.get(6);
                String RegDst = instDec.get(7);
                String  MemToReg = instDec.get(8);
                String MemRead = instDec.get(9);
                String MemWrite = instDec.get(10);
                String Branch = instDec.get(11);
                String Jump  = instDec.get(12);
                String instnumb=instDec.get(13);
                System.out.println("executing instruction: " + instnumb);
                ArrayList<String> InstExec = EXC.Execute(Aluop,rsValue, rtValue, pc.getPc());
                InstExec.add(opcode);
                InstExec.add(rsValue);
                InstExec.add(rtValue);
                InstExec.add(rd);
                InstExec.add(func);
                InstExec.add(Aluop);
                InstExec.add(RegWrite);
                InstExec.add(RegDst);
                InstExec.add(MemToReg);
                InstExec.add(MemRead);
                InstExec.add(MemWrite);
                InstExec.add(Branch);
                InstExec.add(Jump);
                InstExec.add(instnumb);
                qexec.AddToReg(InstExec);
                decode = false;

            }

            if (i<m.getNumberOfInstructions()+1 && fetch) { //fetch to decode
                System.out.println("Decoding instruction: " + pc.getPc());
                ArrayList<String> instDec = ID.InstDecode(qFetch.remove());
                instDec.add(Integer.toString(pc.pc)); //no. of instruction
                qdecode.AddToReg(instDec);
                qdecode.ToString();
                fetch = false;

            }


            if(i<m.getNumberOfInstructions()) {
                System.out.println("fetching instruction: " + (i + 1));
                //IF.InstFetch(pc.pc);
                qFetch.add(IF.InstFetch(pc.pc));

            }
            System.out.println("clock cycle: " + clockcycle);
            clockcycle++;
            pc.pc++;
        }

    }
    public static void main(String[]args) {
        Processor p =new Processor();

    }
}
