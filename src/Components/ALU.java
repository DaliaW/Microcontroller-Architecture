package Components;
public class ALU {
    public static int flagZero = 0;
    public static String operation;
    int o1;
    int o2;
    public static int r ;

    public ALU(String operation,int o1,int o2) {
        this.operation=operation;
        this.o1=o1;
        this.o2=o2;
    }

    public static void ALUEvaluator1( String operation,int o1,int o2) {

        if(operation.equals("0000")) {   //Add

            r=addOp(o1,o2);
            if (r==0)
                flagZero=1;
        }

        else if(operation.equals("0001")) {   //sub
            r=subOp (o1,o2);
            if (r==0)
                flagZero=1;
        }
        else if(operation.equals("0010")) {  //mult
            r=multOp(o1,o2);
            if (r==0) {
                flagZero=1;
            }
        }
        else  if(operation.equals("0011")) {   //AND
            r=ANDOp(o1,o2);
            if (r==0) {
                flagZero=1;
            }
        }
        else if(operation.equals("0100")) {   //OR
            r=OROp(o1,o2);
            if (r==0) {
                flagZero=1;
            }
        }
        else if(operation.equals("0101")) {   //SLL
            r=sllOp(o1,o2);
            if (r==0)
                flagZero=1;
        }
        else if(operation.equals("0110")) {   //SRL
            r=srlOp(o1,o2);
            if (r==0)
                flagZero=1;
        }
        else if(operation.equals("0111")) {   //SLT
            r=sltOp(o1,o2);
            if (r==0)
                flagZero=1;
        }
        else {
            System.out.println("operation not available");
        }
        String o1Bin = Integer.toBinaryString(o1);
        String o2Bin = Integer.toBinaryString(o2);
        String res = Integer.toBinaryString(r);

        System.out.println("Operation Name: "+operation+"\n"
                +"ReadData1: "+o1Bin+"\n"+"ReadData2: "+o2Bin+"\n"
                +"Output: "+res+"\n"+"Z-Flag Value: "+flagZero);

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
    public static int sllOp (int a,int b) {
        return (a << b);

    }
    public static int srlOp (int a,int b) {
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







