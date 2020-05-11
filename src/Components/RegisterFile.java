package Components;
public class RegisterFile {
    public static Register[] registers;
    //int size;

    public RegisterFile()
    {
        //this.size = size;
        registers = new Register[16];

        for(int i = 0; i < 16; ++i) {
            registers[i] = new Register(16);
            registers[i].setData(i);
            registers[i].setRegister(i,""+i);
        }
    }

    public static int readRegister(int index)
    {
        registers[index].read=true;
        return registers[index].getdata();

    }

    public static void writeRegister(int i, String value)
    {
        if(i==0) {
            System.out.println("register 0 cannot be written into");

        }


        else {
            registers[i].setData(Integer.parseInt(value));
            registers[i].setRegister(i, value);
            registers[i].write=true;
        }}

    public static String ToString()
    {
        String r = "";
        for(int i = 0; i < 16; i++)
            r += "[" + registers[i].ToString()  +"]";
        return r;
    }


}
