package Stages;

import Components.ALU;
import Processor.Processor;

import static Components.ALU.*;
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
    public static void Execute(String ALUOp, int ALUSrc, int ReadData1, int ReadData2, int pc) {
        System.out.println("EXECUTING............................................");
        if(ALUOp.equals("01")){ //BEQ
            if(ReadData1 == ReadData2){
                pc  = InstructionFetch.ProgCount() + Integer.parseInt(a.get(3)) << 2;

            }else{ //they're not equal then Ignore and increment pc by 4
                InstructionFetch.ProgCount();
            }
            System.out.println("FINISHED EXECUTING..............................................");
        }
        else if(ALUOp.equals("00")){
            Processor.MemWrite = 1;
            System.out.println("FINISHED EXECUTING..............................................");
        }
        else if (ALUOp.equals("10")) {
            //funct
            ALU.ALUEvaluator1(ALUOperation,ReadData1,ReadData2);
            } else
                System.out.println("invalid operator");

            System.out.println("Operation Name: "+ operation+"\n"
                    +"ReadData1: "+ReadData1+"\n"+"ReadData2: "+ReadData2+"\n"
                    +"Output: "+r+"\n"+"Z-Flag Value: "+flagZero+" ,PC: "+Processor.pc);

            System.out.println("FINISHED EXECUTING..............................................");

        }



    }





