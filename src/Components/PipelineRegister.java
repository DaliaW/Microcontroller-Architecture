package Components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PipelineRegister {
    private Queue<ArrayList<String>> pipelineRegister;

    public PipelineRegister(){
        pipelineRegister = new LinkedList<ArrayList<String>>();

    }

    public Queue<ArrayList<String>> getPipelineRegister(){
        return pipelineRegister;
    }
    public void AddToReg(ArrayList value){
        pipelineRegister.add(value);
    }
    public ArrayList<String> RemoveFromPipeReg(){
        return pipelineRegister.remove();
    }

    public boolean isRegEmpty(){
        return pipelineRegister.isEmpty();
    }

    public void ToString(){
        System.out.println(pipelineRegister.toString());
    }

}
