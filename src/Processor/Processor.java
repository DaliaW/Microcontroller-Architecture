package Processor;

import Components.*;
import Stages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static Stages.InstructionDecode.*;

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
    static PipelineRegister qmemtoWB;
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
        qmemtoWB = new PipelineRegister();
        qwb = new PipelineRegister();
        pc = new PC();
        m = new Memory();
        c = new Cache();
        m.Load(); //Load Memory
        c.Load(); //Load Cache

        System.out.println("Register File before any changes: " + registerFile.ToString()+"\n");

        int instructionCycle=5+ m.getNumberOfInstructions()-1;

        for(int i = 0 ; i < instructionCycle;i++) {
            int tempPc = pc.getPc();

            if (i<m.getNumberOfInstructions()+4 && memacc) {// memory access to write back
                ArrayList<String> InstMemAcc = qmem.RemoveFromPipeReg();

                if(RegDst == 1) {
                    //destination Req --> R-type
                    String ALUresultret = InstMemAcc.get(0);
                    String ReadData = InstMemAcc.get(1);  //Data coming from Data Memory
                    String rd = InstMemAcc.get(3);
                    String ALUOp = InstMemAcc.get(4);

                    String RegWrite = InstMemAcc.get(5);
                    String RegDst = InstMemAcc.get(6);
                    String MemToReg = InstMemAcc.get(7);

                    String instnumb = InstMemAcc.get(InstMemAcc.size() - 1);
                    System.out.println("...............................................");
                    System.out.println("write back stage of instruction: " + instnumb);

                    ArrayList<String> InstWriteBack = WB.WriteBack(ALUresultret, ReadData, rd, Integer.parseInt(RegWrite), Integer.parseInt(MemToReg), Integer.parseInt(RegDst), registerFile);

                    System.out.println("Register File: " + registerFile.ToString() + "\n");
                    memacc = false;
                }
                else
                {
                    String result = InstMemAcc.get(0);
                    String ReadData2 = InstMemAcc.get(1);
                    String Aluop=InstMemAcc.get(2);
                    String RegWrite = InstMemAcc.get(3);
                    String RegDst = InstMemAcc.get(4);
                    String MemToReg = InstMemAcc.get(5);
                    String MemRead =InstMemAcc.get(6);
                    String MemWrite=InstMemAcc.get(7);
                    String Branch=InstMemAcc.get(8);
                    String Jump=InstMemAcc.get(9);
                    String instnumb=InstMemAcc.get(InstMemAcc.size()-1);

                    System.out.println("...............................................");
                    System.out.println("write back stage of instruction: " + instnumb);

                    ArrayList<String> InstWriteBack = WB.WriteBack(result, ReadData2, rd, Integer.parseInt(RegWrite), Integer.parseInt(MemToReg), Integer.parseInt(RegDst), registerFile);

                    System.out.println("Register File: " + registerFile.ToString() + "\n");
                    memacc = false;

                }



            }
            if (i<m.getNumberOfInstructions()+3 && exec) { //execute to memory access
                if(opCode.equals("0000")){ //R-type
                    ArrayList<String> InstExec = qexec.RemoveFromPipeReg();

                    String operation = InstExec.get(0);
                String readData1 = InstExec.get(1);
                String  readData2  = InstExec.get(2);
                String  result  = InstExec.get(3);
                String  FlagZero  = InstExec.get(4);

                String rd= InstExec.get(5);

                String Aluop = InstExec.get(6);
                String RegWrite = InstExec.get(7);
                String RegDst = InstExec.get(8);
                String  MemToReg = InstExec.get(9);
                String MemRead = InstExec.get(10);
                String MemWrite = InstExec.get(11);
                String Branch = InstExec.get(12);

                String instnumb=InstExec.get(InstExec.size()-1);
                System.out.println("memory access stage of instruction: " + instnumb);

                ArrayList<String> InstMemAcc = MA.MemAccess(result , readData2 , Integer.parseInt(MemWrite), Integer.parseInt(MemRead));

                InstMemAcc.add(FlagZero);
                InstMemAcc.add(rd);
                InstMemAcc.add(Aluop);
                InstMemAcc.add(RegWrite);
                InstMemAcc.add( RegDst);
                InstMemAcc.add(  MemToReg);
                InstMemAcc.add(MemRead);
                InstMemAcc.add(  MemWrite);
                InstMemAcc.add( Branch);
                InstMemAcc.add(instnumb);

                qmem.AddToReg(InstMemAcc);
                }

                if(opCode.equals("1000")||opCode.equals("1011")||opCode.equals("1001")||opCode.equals("1100")){
                    //I-type
                    ArrayList<String> InstExec = qexec.RemoveFromPipeReg();
                    String  result  = InstExec.get(0);
                    String  readData2  = InstExec.get(1);
                    String  pc  = InstExec.get(2);
                    String Aluop = InstExec.get(3);
                    String RegWrite = InstExec.get(4);
                    String RegDst = InstExec.get(5);
                    String  MemToReg = InstExec.get(6);
                    String MemRead = InstExec.get(7);
                    String MemWrite = InstExec.get(8);
                    String Branch = InstExec.get(9);
                    String instnumb=InstExec.get(InstExec.size()-1);
                    System.out.println("memory access stage of instruction: " + instnumb);
                    ArrayList<String> InstMemAcc = MA.MemAccess(result , readData2 , Integer.parseInt(MemWrite), Integer.parseInt(MemRead));
                    InstMemAcc.add(Aluop);
                    InstMemAcc.add(RegWrite);
                    InstMemAcc.add(RegDst);
                    InstMemAcc.add(MemToReg);
                    InstMemAcc.add(MemRead);
                    InstMemAcc.add(MemWrite);
                    InstMemAcc.add(Branch);
                    InstMemAcc.add(instnumb);

                    qmem.AddToReg(InstMemAcc);

                }

                exec = false;
            }
            if (i<m.getNumberOfInstructions()+2 && decode) { //decode to execute
                ArrayList<String> instDec = qdecode.RemoveFromPipeReg();
                String rsValue = "",rtValue = "",Aluop = "",RegWrite = "",
                        RegDst = "",MemToReg = "",MemRead = "",MemWrite = "",Branch = "",Jump = "";

                String opcode=instDec.get(0);
                
                if(opcode.equals("0000")){ //R-type

                rsValue=instDec.get(1);
                rtValue=instDec.get(2);
                String rd= instDec.get(3);
                String func = instDec.get(4);
                Aluop = instDec.get(5);
                RegWrite = instDec.get(6);
                RegDst = instDec.get(7);
                MemToReg = instDec.get(8);
                MemRead = instDec.get(9);
                MemWrite = instDec.get(10);
                Branch = instDec.get(11);
                Jump  = instDec.get(12);
                    String instnumb=instDec.get(instDec.size()-1);
                    System.out.println("executing instruction: " + instnumb);

                    ArrayList<String> InstExec = EXC.Execute(Aluop,rsValue,rtValue,"", pc.getPc());
                    InstExec.add(rd);
                    InstExec.add(Aluop);
                    InstExec.add(RegWrite);
                    InstExec.add(RegDst);
                    InstExec.add(MemToReg);
                    InstExec.add(MemRead);
                    InstExec.add(MemWrite);
                    InstExec.add(Branch);
                    InstExec.add(Jump);
                    InstExec.add(opcode);
                    InstExec.add(instnumb);
                    qexec.AddToReg(InstExec);
                }
                else if(opcode.equals("0010")){ //jump
                    String j = instDec.get(1);
                    Aluop = instDec.get(2);
                    RegWrite = instDec.get(3);
                    RegDst = instDec.get(4);
                    MemToReg = instDec.get(5);
                    MemRead = instDec.get(6);
                    MemWrite = instDec.get(7);
                    Branch = instDec.get(8);
                    Jump  = instDec.get(9);
                    String instnumb=instDec.get(instDec.size()-1);
                    System.out.println("executing instruction: " + instnumb);
                    Aluop = instDec.get(2);
                    ArrayList<String> InstExec = EXC.Execute(Aluop,rsValue,rtValue,j, pc.getPc());
                    InstExec.add(Aluop);
                    InstExec.add(RegWrite);
                    InstExec.add(RegDst);
                    InstExec.add(MemToReg);
                    InstExec.add(MemRead);
                    InstExec.add(MemWrite);
                    InstExec.add(Branch);
                    InstExec.add(Jump);
                    InstExec.add(opcode);
                    InstExec.add(instnumb);
                    qexec.AddToReg(InstExec);
                }
                else if(opcode.equals("1000")||opcode.equals("1011")||opcode.equals("1001")||opcode.equals("1100")){

                    opcode = instDec.get(0);
                    rsValue=instDec.get(1);
                    rtValue=instDec.get(2);
                    String immediate = instDec.get(3);
                    Aluop = instDec.get(4);
                    RegWrite = instDec.get(5);
                    RegDst = instDec.get(6);
                    MemToReg = instDec.get(7);
                    MemRead = instDec.get(8);
                    MemWrite = instDec.get(9);
                    Branch = instDec.get(10);
                    Jump  = instDec.get(11);

                    String instnumb=instDec.get(instDec.size()-1);
                    System.out.println("executing instruction: " + instnumb);

                    ArrayList<String> InstExec = EXC.Execute(Aluop,rsValue,rtValue,immediate, pc.getPc());
                    InstExec.add(Aluop);
                    InstExec.add(RegWrite);
                    InstExec.add(RegDst);
                    InstExec.add(MemToReg);
                    InstExec.add(MemRead);
                    InstExec.add(MemWrite);
                    InstExec.add(Branch);
                    InstExec.add(Jump);
                    InstExec.add(opcode);
                    InstExec.add(instnumb);

                    qexec.AddToReg(InstExec);

                }

                decode = false;

            }

            if (i<m.getNumberOfInstructions()+1 && fetch) { //fetch to decode
                System.out.println("Decoding instruction: " + pc.getPc());
                ArrayList<String> instDec = ID.InstDecode(qFetch.remove());
                instDec.add(Integer.toString(pc.pc)); //no. of instruction
                qdecode.AddToReg(instDec);

                fetch = false;

            }


            if(i<m.getNumberOfInstructions()) {
                System.out.println("fetching instruction: " + (i + 1));
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
