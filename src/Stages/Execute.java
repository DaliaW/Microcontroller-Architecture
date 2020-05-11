package Stages;

import Components.ALU;
import Processor.Processor;

import java.util.ArrayList;

import static Stages.InstructionDecode.ALUOperation;
import static Stages.InstructionDecode.a;

public class Execute {
    /*
    This method takes in the ALUOp from ContUnit method, the first operand and
    the second operand from InstDecode method as an input. computes and outputs the result.

     – Inputs: ALUOp (2-bits), ALUSrc. (1-bits), ReadData1 (32-bits), ReadData2 (32-bits),
       SignExtend (32-bits), PC incremented by 4. (32-bits).

     – Outputs: ALUresult (32-bits) , ZeroFlag (1-bit), BranchAddressResult (32-bits), Read-
       Data2 (32-bits), PC incremented by 4. (32-bits).*/
    public static ArrayList<String> Execute(String ALUOp, int ALUSrc, int ReadData1, int ReadData2, int pc) {

        System.out.println("**************************** executing ****************************");
        System.out.println("..........................................................................");
        ArrayList<String> arr  = new ArrayList<String>();

        if(ALUOp.equals("01")){ //Branch
            if(ReadData1 != ReadData2){ //BNEQ
                pc  = InstructionFetch.ProgCount() + Integer.parseInt(a.get(3)) << 2;

            }else if(ReadData1 > ReadData2){ //Branch on greater than
                pc  = InstructionFetch.ProgCount() + Integer.parseInt(a.get(3)) << 2;

            }
            else{ //they're equal then Ignore and increment pc by 4
                InstructionFetch.ProgCount();
            }


            System.out.println("**************************** finished executing ****************************");
            System.out.println("..........................................................................");

        }
        else if(ALUOp.equals("00")){
            Processor.MemWrite = 1;


            System.out.println("**************************** finished executing ****************************");
            System.out.println("..........................................................................");

        }
        else if (ALUOp.equals("10")) {
            //funct
            //ALU.ALUEvaluator1(ALUOperation,ReadData1,ReadData2);
            arr = ALU.ALUEvaluator1(ALUOperation,ReadData1,ReadData2);
            } else
            System.out.println("invalid operator");

        System.out.println("**************************** finished executing ****************************");
        System.out.println("..........................................................................");

        Processor.exec = true;
        return arr;


    }



    }





