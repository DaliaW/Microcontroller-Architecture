package Stages;

import Components.Register;
import Components.RegisterFile;
import Processor.Processor;

import java.util.ArrayList;

import static Processor.Processor.*;

public class InstructionDecode {
    public static String output,rs,rt,rd;
    public static String funct;
    public static String opCode;
    public static ArrayList<String> a;

    public static String ALUOperation;

    /*
    This method takes in the instruction from InstFetch method as an input,
    decodes and outputs the ALUOp code, the first operand, the second operand.
    – Inputs: Instruction (32-bits), PC incremented by 4. (32-bits)
    – Outputs: Control Signals ( all 8 different Signals), ReadData1 (32-bits), ReadData2 (32-
      bits), PC incremented by 4. (32-bits).
     */

    public static void InstDecode(String instruction){
        // System.out.println(s);
        a = new ArrayList<String>();
        opCode = instruction.substring(0,4);
        //ContUnit(opCode);
        rs = instruction.substring(4,7);
        rt = instruction.substring(7,10);
        a.add(opCode);
        a.add(rs);
        a.add(rt);
        a.add(instruction.substring(10,16));
        //System.out.println("my instruction");       System.out.println(a.toString());
        String Type = "";


        funct = instruction.substring(13,16);
        System.out.println("**************************** decoding ****************************");
        System.out.println("..................................................................");

        if(opCode.equals("0000")){
            //R Type
            rd = instruction.substring(10,13);



            //System.out.println("rs "+ReadData1+"rt "+ReadData2);

            Type = getType(rd);
            if(!rd.equals("000")){
                //////
                Processor.ReadData1 = Processor.registerFile.readRegister(Integer.parseInt(rs,2));
                Processor.ReadData2 = Processor.registerFile.readRegister(Integer.parseInt(rt,2));

                output = "opCode:" + opCode + "|rs:"+rs+"|rt:"+rt+"|rd:"
                        +rd+"|funct:"+funct;
                System.out.println("Type of Register: "+Type);
                System.out.println(output);
                //System.out.println("RegWrite: "+ RegWrite +"\n"+"RegDst: "+RegDst);
            }else{
                //RegWrite = false;
                System.out.println("can not access register zero");
                //System.out.println("RegWrite: "+RegWrite);
            }

        }else if(opCode.equals("0010")){//J type (jump)
            //RegWrite = false;
            output = "opCode:" + opCode + "|immediate: "+a.get(1)+""+a.get(2);
            System.out.println(output);

        }else if(opCode.equals("1001")){//I type (lw,sw)
            //RegWrite = true;
            //System.out.println("RegWrite: "+RegWrite);
            output = "opCode:" + opCode + "|rs:"+rs+"|rt:"+rt+"|immediate "
                    +a.get(3);
            System.out.println(output);


        }else if(opCode.equals("1100")){//(BEQ) branch
            output = "opCode:" + opCode + "|rs:"+rs+"|rt:"+rt+"|immediate "
                    +a.get(3);
            System.out.println(output);


        }
        ControlUnit(opCode);
        //RegWrite = false;

        System.out.println("*************************** finished decoding ****************************");
        System.out.println("..........................................................................");

    }

    /*
    This method takes in the OpCode code from InstDecode method as an input,
    changes the control signals according to the provided table outputs all 8 control unit signals
    to the ALU Control.
     */
    public static void ControlUnit(String opCode){
        if(opCode.equals("0000")){ //R type
            RegWrite = 1;
            RegDst = 1;
            MemToReg = 1;
            ALUOp = "10";
            System.out.println("WB controls: MemToReg: "+MemToReg+" ,RegWrite: "+RegWrite+"\n" +
                    "MEM controls: MemRead: "+MemRead+", MemWrite: "+MemWrite+", Branch: "+Branch+"\n" +
                    "EX controls: RegDest: "+RegDst+", ALUOp: "+ALUOp+", ALUSrc: "+ALUSrc);
        }
        else if(opCode.equals("1001")){ //I-type
            RegWrite = 1;
            MemRead = 1;
            System.out.println("WB controls: MemToReg: "+MemToReg+", RegWrite: "+RegWrite+"\n" +
                    "MEM controls: MemRead: "+MemRead+", MemWrite: "+MemWrite+", Branch: "+Branch+"\n" );

        }
        else if(opCode.equals("1100")){ //Conditional Branch
            ALUOp ="01";
            Branch = 1;
            System.out.println("WB controls: MemToReg: "+"Don't care"+", RegWrite: "+RegWrite+"\n" +
                    "MEM controls: MemRead: "+MemRead+", MemWrite: "+MemWrite+", Branch: "+Branch+"\n" );


        }
        else {// J type
            System.out.println("WB controls: MemToReg: "+MemToReg+" RegWrite: "+RegWrite+"\n" +
                    "MEM controls: MemRead: "+MemRead+" , MemWrite: "+MemWrite+", Branch: "+Branch+"\n");
        }
        ALUControl();
        System.out.println("ALU OPERATION: "+ALUOperation);

    }


    //helper method to know the type of the registers in the instruction provided
    public static String getType(String s){
        String regType = "";
        int m = Integer.parseInt(s);
        if(m == 0){
            regType = "zero , Can not be modified";
            RegWrite = 0;
        }
        if(m == 1){
            regType = "reserved for use by the assembler";
            RegWrite = 0;
        }
        if(m>= 2 && m<=3){
            regType = "Function result";
        }
        if(m>=8 && m<=15){
            regType = "Temporary registers";
        }
        if(m>=16 && m<=23){
            regType = "Saved registers";
        }
        if(m>=24 && m<=25){
            regType = "Temporary registers";
        }

        return regType;
    }
    ///////////////////////////////////////// helper to know operation type to pass it to the ALU
    public static void ALUControl(){
        if(ALUOp.equals("00")){// lw/sw
            MemRead = 1;
            MemWrite = 1;
        }
        else if(ALUOp.equals("01")){//BEQ
            Branch = 1;
            PCSrc = pc.getPc()+4;
        }
        else if(ALUOp.equals("10")){ //R-type instruction
            if(funct.equals("000")){
                ALUOperation = "0011"; //ADD
            }
            else if(funct.equals("001")) {
                ALUOperation = "0110"; //SUB
            }
            else if(funct.equals("010")) {
                ALUOperation = "0000"; //AND
            }
            else if(funct.equals("011")) {
                ALUOperation = "0001"; //OR
            }
            else if(funct.equals("110")) {
                ALUOperation = "0111";  //SLT
            }
            else if (funct.equals("111")){ //MULT
                ALUOperation = "0010";
            }
            else if (funct.equals("101")){ //SRL
                ALUOperation = "1110";
            }
            else if (funct.equals("100")){ //SLL
                ALUOperation = "0101";
            }
        }

    }


}
