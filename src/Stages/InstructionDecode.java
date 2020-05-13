package Stages;

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

    public static ArrayList<String> InstDecode(String instruction){
        Processor.decode = true;
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
        String TypeRs,TypeRt,TypeRd = "";


        funct = instruction.substring(13,16);
        System.out.println("**************************** decoding ****************************");
        System.out.println("..................................................................");


        if(opCode.equals("0000")){
            //R Type
            TypeRs = getType(rs);
            TypeRt = getType(rt);
            System.out.println("Type of Register rs: "+TypeRs);
            System.out.println("Type of Register rt: "+TypeRt);

            rd = instruction.substring(10,13);
            TypeRd = getType(rd);
            System.out.println("Type of Register rd: "+TypeRd);


            a.remove(3);
            a.add(rd);
            a.add(funct);

            if(!rd.equals("000")){
                //////
//                Processor.ReadData1 = Processor.registerFile.readRegister(Integer.parseInt(rs,2));
//                Processor.ReadData2 = Processor.registerFile.readRegister(Integer.parseInt(rt,2));

                System.out.println();
                output = "opCode:" + opCode + "|rs:"+rs+"|rt:"+rt+"|rd:"
                        +rd+"|funct:"+funct;

                System.out.println(output);
                System.out.println();

            }else{
                System.out.println("can not access register zero");
            }

        }else if(opCode.equals("0010")){//J type (jump)
            //RegWrite = false;

            String j = a.get(1)+""+a.get(2)+""+a.get(a.size()-1);
            a.remove(1);
            a.remove(2);
            a.remove(a.size()-1);
            //add to arraylist the immediate of jump
            a.add(j);
            System.out.println();

            output = "opCode:" + opCode + "|immediate: "+j;
            System.out.println(output);
            System.out.println();


        }else if(opCode.equals("1000")||opCode.equals("1011")||opCode.equals("1001")){//I type (lw/sw/addi)

            output = "opCode:" + opCode + "|rs:"+rs+"|rt:"+rt+"|immediate "
                    +a.get(3);
            System.out.println();

            System.out.println(output);
            System.out.println();



        }else if(opCode.equals("1100")){//(BEQ) branch
            output = "opCode:" + opCode + "|rs:"+rs+"|rt:"+rt+"|immediate "
                    +a.get(3);
            System.out.println();

            System.out.println(output);
            System.out.println();



        }
        ControlUnit(opCode);

        a.add(ALUOp);
        a.add(RegWrite+"");
        a.add(RegDst+"");
        a.add(MemToReg+"");
        a.add(MemRead+"");
        a.add(MemWrite+"");
        a.add(Branch+"");
        a.add(Jump+"");

        System.out.println("*************************** finished decoding ****************************");
        System.out.println("..........................................................................");
        return a;
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
            MemToReg = 0;
            MemRead = 0;
            MemWrite = 0;
            Branch = 0;
            Jump = 0;
            ALUOp = "10";
            System.out.println("WB controls: MemToReg: "+MemToReg+" ,RegWrite: "+RegWrite+"\n" +
                    "MEM controls: MemRead: "+MemRead+", MemWrite: "+MemWrite+", Branch: "+Branch+", Jump: "+Jump+"\n" +
                    "EX controls: RegDest: "+RegDst+", ALUOp: "+ALUOp+", ALUSrc: "+ALUSrc);

            ALUControl();
            System.out.println("ALU OPERATION: "+ALUOperation);
        }
        //I-type has 3 types (i.e. lw/sw/addi), each type has a different op code
        else if(opCode.equals("1001")){ //addi
            ALUOp = "00";
            RegWrite =1;
            RegDst = 0;
            MemToReg = 0;
            MemRead = 0;
            MemWrite = 0;
            Branch = 0;
            Jump = 0;
            System.out.println("WB controls: MemToReg: "+MemToReg+", RegWrite: "+RegWrite+"\n" +
                    "MEM controls: MemRead: "+MemRead+", MemWrite: "+MemWrite+", Branch: "+Branch+", Jump: "+Jump+"\n" );

        }else if(opCode.equals("1000")){ //lw
            ALUOp = "00";
            RegWrite =1;
            RegDst = 0;
            MemToReg = 1;
            MemRead = 1;
            MemWrite = 0;
            Branch = 0;
            Jump = 0;
            System.out.println("WB controls: MemToReg: "+MemToReg+", RegWrite: "+RegWrite+"\n" +
                    "MEM controls: MemRead: "+MemRead+", MemWrite: "+MemWrite+", Branch: "+Branch+", Jump: "+Jump+"\n" );
        }else if(opCode.equals("1011")){ //sw
            ALUOp = "00";
            RegWrite =0;
            RegDst = 0;
            MemToReg = 1;
            MemRead = 0;
            MemWrite = 1;
            Branch = 0;
            Jump = 0;
            System.out.println("WB controls: MemToReg: "+MemToReg+", RegWrite: "+RegWrite+"\n" +
                    "MEM controls: MemRead: "+MemRead+", MemWrite: "+MemWrite+", Branch: "+Branch+", Jump: "+Jump+"\n" );
        }
        else if(opCode.equals("1100")){ //Conditional Branch
            ALUOp ="01";
            RegWrite =0;
            RegDst = 0;
            MemToReg = 0;
            MemRead = 0;
            MemWrite = 0;
            Branch = 1;
            Jump = 0;
            System.out.println("WB controls: MemToReg: "+MemToReg+", RegWrite: "+RegWrite+"\n" +
                    "MEM controls: MemRead: "+MemRead+", MemWrite: "+MemWrite+", Branch: "+Branch+", Jump: "+Jump+"\n" );


        }
        else {// J type //opCode : 0010
            ALUOp = "xx";
            RegWrite =0;
            RegDst = 0;
            MemToReg = 0;
            MemRead = 0;
            MemWrite = 0;
            Branch = 0;
            Jump = 1;
            System.out.println("WB controls: MemToReg: "+MemToReg+", RegWrite: "+RegWrite+"\n" +
                    "MEM controls: MemRead: "+MemRead+", MemWrite: "+MemWrite+", Branch: "+Branch+", Jump: "+Jump+"\n" );
        }


    }


    //helper method to know the type of the registers in the instruction provided
    public static String getType(String s){
        String regType = "";
        int m = Integer.parseInt(s);
        if(m == 0){
            regType = "zero , Always 0";
            RegWrite = 0;
        }
        if(m == 1){
            regType = "reserved for use by the assembler";
            RegWrite = 0;
        }
        if(m>= 2 && m<=3){
            regType = "Stores results";
        }
        if(m>= 4 && m<=7){
            regType = "Stores arguments";
        }
        if(m>=8 && m<=15){
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
