package Stages;

import Components.ALU;
import Components.RegisterFile;

import java.util.ArrayList;

public class WriteBack {
//• WriteBack: This method takes in the destination register from InstDecode method,
    //the result data from Execute method as an input. writes the result in the destination register
    //and sets the corresponding ﬂag for register writing.
    //– Inputs: ALUresult (32-bits) ,ReadData [from data memory] (32-bits)),
    //MemToReg (1bit), RegDst (1-bit).
    //– Outputs: WriteData (32-bits).

    public static ArrayList<String> WriteBack(String ALUresult,String ReadData, String MemToReg, String RegDst, RegisterFile r ) {
        ArrayList<String> arrli = new ArrayList<String>();
        String   WriteData = "";
        int AluResInt=Integer.parseInt(ALUresult,2);
        int readdataint=Integer.parseInt(ReadData,2);

        if(MemToReg.equals("0")) {  //Value of register Write Data is from ALU

            WriteData=ALUresult;
            if(RegDst.equals("0")) {  //Destination register comes from rt ﬁeld.
                int regDst = Integer.parseInt(InstructionDecode.rd,2);
                RegisterFile.writeRegister(regDst, ALU.r +"");

            }
            if(RegDst.equals("1")) {	  // Destination register comes from the rd ﬁeld.
                int regDst = Integer.parseInt(InstructionDecode.rd,2);
                RegisterFile.writeRegister(regDst, ALU.r +"");
            }

        }
        if(MemToReg.equals("1")) {   //Value of register Write Data is memory Read Data.

            WriteData=ReadData;
            if(RegDst.equals("0")) {  //Destination register comes from rt ﬁeld.
                int regDst = Integer.parseInt(InstructionDecode.rd,2);
                RegisterFile.writeRegister(regDst, ALU.r +"");
            }
            if(RegDst.equals("1")) {	// Destination register comes from the rd ﬁeld.
                int regDst = Integer.parseInt(InstructionDecode.rd,2);
                RegisterFile.writeRegister(regDst, ALU.r +"");
            }
        }





        arrli.add(WriteData);

        return arrli;
    }
}
