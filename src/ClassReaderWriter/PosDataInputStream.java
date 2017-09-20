package ClassReaderWriter;

import java.io.*;
import java.io.DataInputStream;

public class PosDataInputStream extends DataInputStream {

    /** Creates a new instance of PosDataInputStream
     * @param in 
     */
    public PosDataInputStream(final PosByteArrayInputStream in) {
        super(in);
    }

    /**
     *
     * @return  The index of the next character to read from the input stream
     *          buffer, or <code>-1</code> if there is internal error, the 
     *          input stream is not <code>PosByteArrayInputStream</code>.
     */
    public int getPos() {
        int pos = -1;
        if (this.in instanceof PosByteArrayInputStream) {
            pos = ((PosByteArrayInputStream) this.in).getPos();
        }

        return pos;
    }


    public short readShortLittleEndian()  throws IOException,Exception {
    byte[] shortbytes = new byte[2];
    this.read(shortbytes, 0, shortbytes.length);
    short x = java.nio.ByteBuffer.wrap(shortbytes).order(java.nio.ByteOrder.LITTLE_ENDIAN).getShort();
    return x;
    }

    public int readIntLittleEndian()  throws IOException,Exception {
    byte[] intbytes = new byte[4];
    this.read(intbytes, 0, intbytes.length);
    int x = java.nio.ByteBuffer.wrap(intbytes).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
    return x;
    }

}
