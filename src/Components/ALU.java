package Components;

import java.util.ArrayList;

public class ALU {
    public static int flagZero = 0;
    public static String operationBIN;
    public static String Name;
    int o1;
    int o2;
    public static int r ;

    public ALU(String operation,int o1,int o2) {
        operationBIN=operation;
        this.o1=o1;
        this.o2=o2;
    }

    public static ArrayList<String> ALUEvaluator1(String operation, int o1, int o2) {
        ArrayList<String> a  = new ArrayList<String>();

        if(operation.equals("0011")) {   //Add
            Name = "Add";
            r=addOp(o1,o2);
            if (r==0)
                flagZero=1;
            else flagZero =0;
        }

        else if(operation.equals("0110")) {   //sub
            Name = "SUB";
            r=subOp (o1,o2);
            if (r==0)
                flagZero=1;
            else flagZero =0;
        }
        else if(operation.equals("0010")) {  //mult
            Name = "MULT";
            r=multOp(o1,o2);
            if (r==0) {
                flagZero=1;
            }
            else flagZero =0;
        }
        else  if(operation.equals("0000")) {   //AND
            Name = "AND";
            r=ANDOp(o1,o2);
            if (r==0) {
                flagZero=1;
            }
            else flagZero =0;
        }
        else if(operation.equals("0001")) {   //OR
            Name = "OR";
            r=OROp(o1,o2);
            if (r==0) {
                flagZero=1;
            }
            else flagZero =0;
        }
        else if(operation.equals("0101")) {   //SLL
            Name = "SLL";
            r=sllOp(o1,o2);
            if (r==0)
                flagZero=1;
            else flagZero =0;
        }
        else if(operation.equals("1110")) {   //SRL
            Name = "SRL";
            r=srlOp(o1,o2);
            if (r==0)
                flagZero=1;
            else flagZero =0;
        }
        else if(operation.equals("0111")) {
            //SLT
            Name = "SLT";
            r=sltOp(o1,o2);
            if (r==0)
                flagZero=1;
            else flagZero =0;
        }
        else {
            Name = "invalid operation !";
            System.out.println("operation not available");
        }
        a.add(operation);
        a.add(""+o1);
        a.add(""+o2);
        a.add(""+r);
        a.add(""+flagZero);

        String o1Bin = Integer.toBinaryString(o1);
        String o2Bin = Integer.toBinaryString(o2);
        String res = Integer.toBinaryString(r);
//        String.format("%016d", Integer.parseInt(Integer.toBinaryString(RegisterFile.readRegister(regDst))));

        System.out.println("Operation Name: "+Name+"\n"
                +"ReadData1 in BIN: "+String.format("%016d", Integer.parseInt(o1Bin))+" ,ReadData1 in DEC: "+o1+"\n"
                +"ReadData2 in BIN: "+String.format("%016d", Integer.parseInt(o2Bin))+" ,ReadData2 in DEC: "+o2+"\n"
                +"ALUResult: "+String.format("%016d", Integer.parseInt(res)) +" in BIN, "+r+" in DEC"+"\n"+"Z-Flag Value: "+flagZero);

        return a;
    }

    //Arithmetic Instructions

    public static int addOp(int o1,int o2) {
        return (o1+o2);
    }

    public static int subOp(int o1,int o2) {
        return (o1-o2);
    }
    public static int multOp(int o1,int o2) {
        return (o1*o2);
    }

    // Logical Instructions

    public static  int ANDOp(int a,int b) {
        return (a & b);
    }

    public static int OROp (int a,int b) {
        return (a | b);

    }
    public static int sllOp (int a,int b) { //Shift bits of op1 left by distance op2; fills with zero bits on the right-hand side
        return (a << b);

    }
    public static int srlOp (int a,int b) { //Shift bits of op1 right by distance op2; fills with zero bits on the left-hand side
        return (a >>> b);

    }
    // Comparison Instructions

    public static int sltOp(int o1,int o2) {
        int z=0;
        if(o2>o1) {
            return 1;
        }
        return z;
    }
    private static int NOR(int operand1, int operand2) {
        return  ~(operand1 | operand2);
    }


}








