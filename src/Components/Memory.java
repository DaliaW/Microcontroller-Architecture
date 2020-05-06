package Components;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Memory {
    public static  String[] Mem;
    int numberOfInstructions;
    static List<String> inst = Collections.emptyList(); //instructions
    public static List<String> instructions = Collections.emptyList(); //program

    public Memory()
    {

    }

    public String getInstruction(int index)
    {
        return Mem[index];
    }

    public void setInstruction(int index, String value )
    {
        Mem[index] = value;
        numberOfInstructions++;
    }
    public int getNumberOfInstructions()
    {
        return numberOfInstructions;
    }

    public String readDataMemory(int index)
    {
        return Mem[index];

    }

    public static void writeDataMemory(int index, String value)
    {
        Mem[index] = value;

    }
    public String [] getMem() {
        return Mem;
    }

    public int length() {
        return Mem.length * 4;
    }

    public void Load(){
        Mem = new String[2048];

        try {
            String file = "program.txt";
            instructions =  Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 0; i < instructions.size(); i++) {
            instructions.get(i).split(" ");
            numberOfInstructions ++;
        }

        for(int i = 0 ; i< numberOfInstructions ; i++){
            Mem[i] = instructions.get(i);
        }

        System.out.println("instruction memory: ");

        System.out.println(Arrays.toString(Mem));

    }
}