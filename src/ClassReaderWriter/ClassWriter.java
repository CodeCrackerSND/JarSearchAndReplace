package ClassReaderWriter;
import ClassReaderWriter.ClassReader;
import java.io.*;


public class ClassWriter {
    public ByteArrayOutputStream bos;
    public DataOutputStream dos;
    public ClassReader theclass;


    public byte[] GetClassBytes(ClassReader classread) throws IOException
    {
try
{
    this.theclass = classread;
    bos = new ByteArrayOutputStream();
    dos = new DataOutputStream(bos);  
    WriteClass();
    byte[] bytes = bos.toByteArray();
    dos.close();
    bos.close();
    return bytes;
}
catch(Exception exc)
{
return new byte[0];
}
    }


    public void WriteClass() throws IOException
    {
    dos.writeInt(theclass.magic);// write something!
    dos.writeShort(theclass.minor_version);
    dos.writeShort(theclass.major_version);
    dos.writeShort(theclass.cp_count);
    dos.write(theclass.cpbytes, 0, theclass.cpbytes.length);

    dos.writeShort(theclass.access_flags);
    dos.writeShort(theclass.this_class);
    dos.writeShort(theclass.super_class);
    dos.writeShort(theclass.interfaces_count);
    dos.write(theclass.interfacesbytes, 0, theclass.interfacesbytes.length);

    dos.writeShort(theclass.fields_count);
    dos.write(theclass.fieldsbytes, 0, theclass.fieldsbytes.length);

    dos.writeShort(theclass.methods_count);
    dos.write(theclass.methodsbytes, 0, theclass.methodsbytes.length);

    dos.writeShort(theclass.attributes_count);
    dos.write(theclass.attributesbytes, 0, theclass.attributesbytes.length);

}
}

