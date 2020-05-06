package Components;

public class PC {
    private int pc;

    public PC(){
        pc = 0;
    }

    public int getPc(){
        return pc;
    }


    public void setPc(int pc){
        assert pc%4==0;
        this.pc = pc;
    }
    public void reset(){
        pc = 0;
    }


}
