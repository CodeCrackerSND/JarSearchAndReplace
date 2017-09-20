package ClassReaderWriter;
import ClassReaderWriter.ClassReader;
import ClassReaderWriter.ClassWriter;
import java.io.*;

public class utils {

    public static void main(String[] args) throws IOException,Exception
    {

File f = new File("c:\\StringCompare.class");
byte[] array = getBytesFromFile(f);
ClassReader clsread = new ClassReader();
boolean isok = clsread.init(array);
if (isok)
{
short strindex = clsread.WriteString("test2");
String ge = ShortToString((short)256);
System.out.println(ge);
ClassWriter clswrite = new ClassWriter();
byte[] classbytes = clswrite.GetClassBytes(clsread);
if (classbytes.length>0)
{
String filename = "C:\\newfile.class";
WriteBytesToFile(filename,classbytes);
}
}
    }

    
    public static byte[] BytesFromString(String hexstring)
    {
    if (hexstring==null||hexstring.equalsIgnoreCase("")||hexstring.length()<2)
    return new byte[0];
    String processed = hexstring.replaceAll("\n", "");
    processed = processed.replaceAll(" ", "");
    if (processed==null||processed.equalsIgnoreCase("")||processed.length()<2)
    return new byte[0];
    
    int bytelength = processed.length()/2;
    byte[] newbytes = new byte[bytelength];
    
    for (int i=0;i<bytelength;i++)
    {
    String currenthex = processed.substring(i*2, i*2+2);
    newbytes[i]=(byte)Integer.parseInt(currenthex, (int)0x10);
    }
    return newbytes;
    }
    
        public static String getByteDataHexView(final byte[] data) {
        if (data == null) {
            return "";
        }
        if (data.length < 1) {
            return "";
        }

        final StringBuilder sb = new StringBuilder(data.length * 5);
        final int length = data.length;
        int i;
        int lineBreakCounter = 0;
        for (i = 0; i < length; i++) {
            sb.append(String.format(" %02X", data[i]));
            lineBreakCounter++;
            if (lineBreakCounter == 16) {
                sb.append('\n');
                lineBreakCounter = 0;
            }
        }

        return sb.toString();
    }
    
    public static String ClasswithouthExtension(String classname)
    {
    int lastpoint = classname.lastIndexOf('.');
    String currentdir = classname.substring(0,lastpoint);
    return currentdir;
    }


public static String ShortToString(short toconvert) {
if (toconvert<=0x0ff)
{
byte b = (byte)(toconvert & 0xff);
String sb = String.format("%02X", b);
return sb;
}

 byte[] ret = new byte[2];

 ret[0] = (byte)(toconvert & 0xff);
 ret[1] = (byte)((toconvert >> 8) & 0xff);
String sb = String.format("%02X", ret[1])+String.format("%02X", ret[0]);
return sb.toString();
}

	public static void WriteBytesToFile(String filename, byte[] bytearray) {
 
		FileOutputStream fop = null;
		File file;
 
		try {
 
			file = new File(filename);
			fop = new FileOutputStream(file);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			fop.write(bytearray);
			fop.flush();
			fop.close();
 
		} catch (IOException e) {

		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    // Returns the contents of the file in a byte array.
    public static byte[] getBytesFromFile(File file) throws IOException {       
        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large!");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        return bytes;
    }



}
