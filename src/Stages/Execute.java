package Stages;

import Components.ALU;
import Processor.Processor;

import java.util.ArrayList;

import static Processor.Processor.registerFile;
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
    public static ArrayList<String> Execute(String ALUOp, String ReadData1, String ReadData2, int pc) {

        int rs1 = registerFile.readRegister(Integer.parseInt(ReadData1,2));
        int rt1 = registerFile.readRegister(Integer.parseInt(ReadData2,2));

        System.out.println("**************************** executing ****************************");
        System.out.println("..........................................................................");
        ArrayList<String> arr  = new ArrayList<String>();

        if(ALUOp.equals("01")){ //Branch
            if(rs1 != rt1){ //BNEQ
                pc  = InstructionFetch.ProgCount() + Integer.parseInt(a.get(3)) << 2;

            }else if(rs1 > rt1){ //Branch on greater than
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
            arr = ALU.ALUEvaluator1(ALUOperation,rs1,rt1);
        } else
            System.out.println("invalid operator");

        System.out.println("**************************** finished executing ****************************");
        System.out.println("..........................................................................");

        Processor.exec = true;
        return arr;


    }



}





