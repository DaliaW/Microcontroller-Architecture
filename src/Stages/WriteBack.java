package Stages;

import Components.ALU;
import Components.RegisterFile;
import Processor.Processor;

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


        if(RegWrite == 1){

            if(MemToReg == 1){ //lw
                Processor.m.readDataMemory(Integer.parseInt(ReadData));
                int regDst = Integer.parseInt(InstructionDecode.rt,2);
                String WriteData=ReadData;
                System.out.println("Destination Register: "+regDst +", Data to be Written: "+ALUresult);
                RegisterFile.writeRegister(regDst, ALUresult);
                //String.format("%016d", Integer.parseInt(Integer.toBinaryString(RegisterFile.readRegister(regDst))));
                System.out.println("Written Data in Register File: "+RegisterFile.readRegister(regDst)+" in Decimal, "+
                        String.format("%016d", Integer.parseInt(Integer.toBinaryString(RegisterFile.readRegister(regDst))))+" in Binary");

                arrli.add(WriteData);
            }
            else{
                String   WriteData = "";
                int AluResInt=Integer.parseInt(ALUresult);
                int readdataint=Integer.parseInt(ReadData);
                //WriteData=ALUresult;

                int regDst = Integer.parseInt(InstructionDecode.rd,2);

                System.out.println("Destination Register: "+regDst +", Data to be Written: "+ALUresult);
                RegisterFile.writeRegister(regDst, ALUresult);
                //String.format("%016d", Integer.parseInt(Integer.toBinaryString(RegisterFile.readRegister(regDst))));
                System.out.println("Written Data in Register File: "+RegisterFile.readRegister(regDst)+" in Decimal, "+
                        String.format("%016d", Integer.parseInt(Integer.toBinaryString(RegisterFile.readRegister(regDst))))+" in Binary");

                arrli.add(WriteData);
            }

        }
        System.out.println("**************************** finished Write Back ****************************");

        Processor.writeback = true;

        return arrli;
    }
}
