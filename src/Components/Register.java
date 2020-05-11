package Components;

public class Register {
    private String register;
    private int data;
    private int size;
    boolean read;
    boolean write;

    public Register(int size){
        this.size = size;
    }



    public String getRegister(){
        return register;
    }

    public void setData(int v){
        this.data = v;
    }
    public int getdata(){
        return this.data;
    }

    public void setRegister(int i, String name){
        this.register = name;
    }

    public String ToString() {
        String r = Integer.toBinaryString(data);
        while(r.length() < size)
            r = "0" + r;
        if(register != null)
            r = register + ": " + r;
        return r + " (DEC = "+data+", BIN = "+ Integer.toBinaryString(data) +")";
    }
}
