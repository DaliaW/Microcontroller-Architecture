package Stages;

import Processor.Processor;

import java.util.ArrayList;

public class MemoryAccess {
    public static ArrayList<String> MemAccess(String ALUresult, String ReadData2, int  MemWrite,int  MemRead) {
        ArrayList<String> arrli = new ArrayList<String>();
        String ALUresultret=ALUresult;
        String ReadData2ret ="";
        int add= Integer.parseInt(ALUresult,2);
        if(MemWrite==1) { //sw
            ///put readdata2 in address
            Processor.c.writecashe(add+1024, ReadData2);
        }
        if(MemRead == 1) { //lw

            ReadData2ret=Processor.c.readcashe(add+1024);
        }
        arrli.add(ALUresultret);
        arrli.add(ReadData2ret);

        Processor.memacc=true;
        //d++;
        return arrli;

    }

}
