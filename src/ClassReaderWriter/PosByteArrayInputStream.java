package ClassReaderWriter;

import java.io.ByteArrayInputStream;

public class PosByteArrayInputStream extends ByteArrayInputStream
{
    /**
     * Creates a new instance of PosByteArrayInputStream
     * @param buf 
     */
    public PosByteArrayInputStream(final byte[] buf)
    {
        super(buf);
    }
    
    int getPos()
    {
        return this.pos;
    }
}
