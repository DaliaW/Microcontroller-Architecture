package Components;
public class RegisterFile {
    static Register[] registers;
    int size;

    public RegisterFile(int size)
    {
        this.size = size;
        registers = new Register[size];

        for(int i = 0; i < size; i++) {
            registers[i] = new Register();
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
            registers[i].setRegister(i, value);
            registers[i].setData(Integer.parseInt(value));
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
