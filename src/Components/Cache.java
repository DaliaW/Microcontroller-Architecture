package Components;

import Components.Memory;
import Processor.Processor;

public class Cache {
    static boolean[] Valid_bit =new boolean [1536]; //by default false
    static int Tag ;
    static int Index ;
    static String []Data =new String[1536] ;
    static int []cache = new int [1536];
    //  static  Memory mem = new Memory();
    // static HashMap< Integer, String>hashMap= new HashMap<>();
    //static  String memory[]=Memory.Mem;



    //int casheused;



    public static String readcashe(int address)
    {
        Index =address%1536 ;
        Tag =address/1536;
        if(Valid_bit[Index]==true && cache[Index]==Tag){ //hit
            System.out.println("hit");
            return Data[address];
            //	System.out.println("hit");
        }
        else{
            //mis
            cache[Index]=Tag;
            Data[Index]= Processor.m.readDataMemory(address);
            Valid_bit[Index]=true ;
            System.out.println("miss");
        }
        return Data[address];

    }

    public static void writecashe(int address, String value)
    {
        Index =address%1536 ;
        Tag =address/1536;
        if(Valid_bit[Index]==true && cache[Index]==Tag){ //hit
            Data[Index] = value;
            Memory.writeDataMemory(address , value);

        }
        else
            System.out.println("cannot find in cache");
            Memory.writeDataMemory(address , value);

    }

    public static int size(){
        return cache.length;
    }

    public static void Load(){
        for(int i = 0 ; i < 1536; i++){
            if(Memory.Mem[i] != null)
                Data[i] = Memory.Mem[i];
        }
    }




}

