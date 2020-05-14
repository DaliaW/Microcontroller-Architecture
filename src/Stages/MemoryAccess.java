package Stages;

import Processor.Processor;

import java.util.ArrayList;

public class MemoryAccess {
    public static ArrayList<String> MemAccess(String ALUresult, String ReadData2, int  MemWrite,int  MemRead) {

        System.out.println("**************************** memory access ****************************");
        System.out.println("..........................................................................");


        String ALUresultret = ALUresult;
        String ReadData2ret = ReadData2;
        ArrayList<String> arrli = new ArrayList<String>();
        if(MemRead == 1 || MemWrite == 1) {

            //System.out.println("ALU....>" + ALUresult);

            int add = Integer.parseInt(ALUresultret);
            if (MemWrite == 1) { //sw
                ///put readdata2 in address

                Processor.c.writecashe(add + 1024, ReadData2);

            }
            if (MemRead == 1) { //lw

                ReadData2ret = Processor.c.readcashe(add + 1024);
            }



            System.out.println("ALUresult: " + ALUresult + " ,ReadData2: " + ReadData2ret);

            System.out.println("**************************** finished memory access ****************************");
            System.out.println("..........................................................................");



        }
        arrli.add(ALUresultret);
        arrli.add(ReadData2ret);
        Processor.memacc = true;

        return arrli;

    }

}
