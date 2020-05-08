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

    public static ArrayList<String> WriteBack(String ALUresult,String ReadData,int RegWrite, int MemToReg, int RegDst, RegisterFile r ) {
        System.out.println("**************************** Write Back ****************************");
        ArrayList<String> arrli = new ArrayList<String>();

        if(MemToReg == 1 && RegDst == 1){

        String   WriteData = "";
        int AluResInt=Integer.parseInt(ALUresult);
        int readdataint=Integer.parseInt(ReadData);
        WriteData=ALUresult;

        int regDst = Integer.parseInt(InstructionDecode.rd);
        RegisterFile.writeRegister(regDst, WriteData);

//        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;"+regDst);
//        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;"+InstructionDecode.rd);
//        String.format("%016d", Integer.parseInt(Integer.toBinaryString(RegisterFile.readRegister(regDst))));
        System.out.println("WRITE DATA: "+String.format("%016d", Integer.parseInt(Integer.toBinaryString(RegisterFile.readRegister(regDst)))));

        arrli.add(WriteData);}
        System.out.println("**************************** finished Write Back ****************************");

        return arrli;
    }
}
