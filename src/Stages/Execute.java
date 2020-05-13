package Stages;

import Components.ALU;
import Components.PC;
import Processor.Processor;

import java.util.ArrayList;

import static Processor.Processor.*;
import static Stages.InstructionDecode.*;

public class Execute {
    /*
    This method takes in the ALUOp from ContUnit method, the first operand and
    the second operand from InstDecode method as an input. computes and outputs the result.

     – Inputs: ALUOp (2-bits), ALUSrc. (1-bits), ReadData1 (32-bits), ReadData2 (32-bits),
       SignExtend (32-bits), PC incremented by 4. (32-bits).

     – Outputs: ALUresult (32-bits) , ZeroFlag (1-bit), BranchAddressResult (32-bits), Read-
       Data2 (32-bits), PC incremented by 4. (32-bits).*/
    public static ArrayList<String> Execute(String ALUOp, String ReadData1, String ReadData2,String Immediate, int pc) {



        System.out.println("**************************** executing ****************************");
        System.out.println("..........................................................................");
        ArrayList<String> arr  = new ArrayList<String>();

        if(ALUOp.equals("01")){ //Branch
            int rs1 = registerFile.readRegister(Integer.parseInt(ReadData1,2));
            int rt1 = registerFile.readRegister(Integer.parseInt(ReadData2,2));

            if(rs1 != rt1){ //BNEQ
                int Newpc  = PC.pc+4 + Integer.parseInt(Immediate) << 2;
                pc = Newpc;
                System.out.println("NEW PC: "+Newpc);

            }else if(rs1 > rt1){ //Branch on greater than
                int Newpc  = PC.pc + Integer.parseInt(Immediate) << 2;
                pc = Newpc;
                System.out.println("NEW PC: "+Newpc);

            }
            else{ //they're equal then Ignore and increment pc by 4
                int Newpc = PC.pc +4;
                pc = Newpc;
                System.out.println("NEW PC: "+Newpc);

            }


            System.out.println("**************************** finished executing ****************************");
            System.out.println("..........................................................................");

        }
        if(ALUOp.equals("00")){ //  lw/sw/addi
            if(opCode.equals("1000")){ //lw
                Processor.MemRead = 1;
                System.out.println();
                System.out.println("Operation Name: LW"+", MemRead Flag: "+MemRead);
                System.out.println();

            }else if(opCode.equals("1001")){
                //addi
                int imm = Integer.parseInt(Immediate);
                int rs1 = registerFile.readRegister(Integer.parseInt(ReadData1,2));
                int res = imm + rs1;
                System.out.println("Operation Name: ADDi"+"\n"
                        +"ReadData1 in BIN: "+String.format("%016d", Integer.parseInt(Integer.toBinaryString(rs1)))+" ,ReadData1 in DEC: "+rs1+"\n"
                        +"ReadData2 in BIN: "+String.format("%016d", Integer.parseInt(Immediate))+" ,ReadData2 in DEC: "+imm+"\n"
                        +"ALUResult: "+String.format("%016d", Integer.parseInt(Integer.toBinaryString(res))) +" in BIN, "+res+" in DEC"+"\n");
                arr.add(res+"");
                arr.add(ReadData2);
                arr.add(pc+"");
                System.out.println("arr addi "+ arr.toString());
            }else { //sw
            Processor.MemWrite = 1;
                System.out.println();
            System.out.println("Operation Name: SW"+", MemWrite Flag: "+MemWrite);
                System.out.println();
        }


            System.out.println("**************************** finished executing ****************************");
            System.out.println("..........................................................................");

        }
        if(ALUOp.equals("xx")){ //jump
            int Newpc  = pc * 4 +(Integer.parseInt(Immediate) << 2);
            pc = Newpc;
            System.out.println("Jump Instruction: "+"\n"+"NEW PC: "+ String.format("%016d",Integer.parseInt(Integer.toBinaryString(Newpc))));
            arr.add(pc+"");
            System.out.println("**************************** finished executing ****************************");
            System.out.println("..........................................................................");
        }
        else if (ALUOp.equals("10")) {
            int rs1 = registerFile.readRegister(Integer.parseInt(ReadData1,2));
            int rt1 = registerFile.readRegister(Integer.parseInt(ReadData2,2));
            //funct
            //ALU.ALUEvaluator1(ALUOperation,ReadData1,ReadData2);
            arr = ALU.ALUEvaluator1(ALUOperation,rs1,rt1);
            System.out.println("**************************** finished executing ****************************");
            System.out.println("..........................................................................");
        }



        Processor.exec = true;
        return arr;


    }



}





