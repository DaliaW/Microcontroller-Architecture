package Processor;

import Components.*;
import Stages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static Stages.InstructionDecode.opCode;
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
        //System.out.println(instructionCycle);

        for(int i = 0 ; i < instructionCycle;i++) {
            int tempPc = pc.getPc();
            if (i<m.getNumberOfInstructions()+4 && memacc) { // memory access to write back
                ArrayList<String> InstMemAcc = qmem.RemoveFromPipeReg();
                System.out.println(InstMemAcc.toString());

                String  ALUresultret= InstMemAcc.get(0);
                String ReadData	=	InstMemAcc.get(1);  //Data coming from Data Memory
                //String ReadData1 = InstMemAcc.get(2);
                //String ReadData2 = InstMemAcc.get(3);
                //String result = InstMemAcc.get(4);
                String rd = InstMemAcc.get(3);
                String ALUOp = InstMemAcc.get(4);

                String RegWrite	=	InstMemAcc.get(5);
                String RegDst = InstMemAcc.get(6);
                String MemToReg = InstMemAcc.get(7);

                String instnumb = InstMemAcc.get(InstMemAcc.size()-1);
                System.out.println("...............................................");
                System.out.println("write back stage of instruction: "+instnumb);

                ArrayList<String> InstWriteBack = WB.WriteBack(ALUresultret , ReadData,rd,Integer.parseInt(RegWrite), Integer.parseInt(MemToReg), Integer.parseInt(RegDst), registerFile);
///////////////////////////////////////////////////////////////////////
                System.out.println("WRITEBACK");
                System.out.println(InstWriteBack.toString());

                System.out.println("Register File: " + registerFile.ToString()+"\n");
                memacc=false;


            }
            if (i<m.getNumberOfInstructions()+3 && exec) { //execute to memory access
                ArrayList<String> InstExec = qexec.RemoveFromPipeReg();
                if(opCode.equals("0000")){ //R-type
                String operation = InstExec.get(0);
                String readData1 = InstExec.get(1);
                String  readData2  = InstExec.get(2);
                String  result  = InstExec.get(3);
                String  FlagZero  = InstExec.get(4);

                //String rsValue=InstExec.get(6);
                //String rtValue=InstExec.get(7);
                String rd= InstExec.get(5);
                //String func =InstExec.get(9);
                String Aluop = InstExec.get(6);
                String RegWrite = InstExec.get(7);
                String RegDst = InstExec.get(8);
                String  MemToReg = InstExec.get(9);
                String MemRead = InstExec.get(10);
                String MemWrite = InstExec.get(11);
                String Branch = InstExec.get(12);
                String Jump  = InstExec.get(13);
                String instnumb=InstExec.get(InstExec.size()-1);
                System.out.println("memory access stage of instruction: " + instnumb);

                ArrayList<String> InstMemAcc = MA.MemAccess(result , readData2 , Integer.parseInt(MemWrite), Integer.parseInt(MemRead));
                //InstMemAcc.add(operation);
                //InstMemAcc.add(readData1);
                //InstMemAcc.add(readData2);
                //InstMemAcc.add(result);
                InstMemAcc.add(FlagZero);
                //InstMemAcc.add(opcode);
                //InstMemAcc.add(rsValue);
                //InstMemAcc.add(rtValue);
                InstMemAcc.add(rd);
                //InstMemAcc.add(func);
                InstMemAcc.add(Aluop);
                InstMemAcc.add(RegWrite);
                InstMemAcc.add( RegDst);
                InstMemAcc.add(  MemToReg);
                InstMemAcc.add(MemRead);
                InstMemAcc.add(  MemWrite);
                InstMemAcc.add( Branch);
                InstMemAcc.add( Jump);
                InstMemAcc.add(instnumb);

                qmem.AddToReg(InstMemAcc);
                }
                ////////////////////////
                System.out.println("QMEM");
                qmem.ToString();
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
                    //(operation,operand1,operand2,result,FlagZero,rd,Aluop,RegWrite,RegDst,MemToReg,MemRead,MemWrite,Branch,Jump)
                    InstExec.add(instnumb);
                    qexec.AddToReg(InstExec);
                }
                if(opcode.equals("0010")){ //jump
                    String j = instDec.get(1);
                    String instnumb=instDec.get(instDec.size()-1);
                    System.out.println("executing instruction: " + instnumb);
                    Aluop = instDec.get(5);
                    rsValue=instDec.get(1);
                    rtValue=instDec.get(2);
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
                if(opcode.equals("1000")||opcode.equals("1011")||opcode.equals("1001")||opcode.equals("1100")){
                    Aluop = instDec.get(5);
                    //BEQ/ADDi/BNEQ/lw/sw
                    rsValue=instDec.get(1);
                    rtValue=instDec.get(2);
                    String immediate = instDec.get(3);
                    String instnumb=instDec.get(instDec.size()-1);
                    System.out.println("executing instruction: " + instnumb);

                    ArrayList<String> InstExec = EXC.Execute(Aluop,rsValue,rtValue,"", pc.getPc());
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

                ///////////////////////
                System.out.println("QEXEC");
                qexec.ToString();
                decode = false;

            }

            if (i<m.getNumberOfInstructions()+1 && fetch) { //fetch to decode
                System.out.println("Decoding instruction: " + pc.getPc());
                ArrayList<String> instDec = ID.InstDecode(qFetch.remove());
                instDec.add(Integer.toString(pc.pc)); //no. of instruction
                qdecode.AddToReg(instDec);
                /////////////////////////////////
                System.out.println("QDECODE");
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
