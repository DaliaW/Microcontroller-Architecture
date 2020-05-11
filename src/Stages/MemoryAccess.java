package Stages;

import Processor.Processor;

import java.util.ArrayList;

public class MemoryAccess {
    public static ArrayList<String> MemAccess(String ALUresult, String ReadData2, int  MemWrite,int  MemRead) {


        System.out.println("**************************** memory access ****************************");
        System.out.println("..........................................................................");

        ArrayList<String> arrli = new ArrayList<String>();
        if(MemRead == 1 || MemWrite == 1) {
            String ALUresultret = ALUresult;
            //System.out.println("ALU....>" + ALUresult);
            String ReadData2ret = "";
            int add = Integer.parseInt(ALUresultret);
            if (MemWrite == 1) { //sw
                ///put readdata2 in address
                Processor.c.writecashe(add + 1024, ReadData2);
            }
            if (MemRead == 1) { //lw

                ReadData2ret = Processor.c.readcashe(add + 1024);
            }
            arrli.add(ALUresultret);
            arrli.add(ReadData2ret);


            System.out.println("ALUresult: " + ALUresult + " ,ReadData2: " + ReadData2);



        }
        Processor.memacc = true;
        System.out.println("**************************** finished memory access ****************************");
        System.out.println("..........................................................................");
        return arrli;

    }

}
