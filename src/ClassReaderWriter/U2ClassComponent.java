package ClassReaderWriter;

import java.io.IOException;
import ClassReaderWriter.u2;
import ClassReaderWriter.PosDataInputStream;
import ClassReaderWriter.ClassComponent;

/**
 * Super class for all simple components which have only one 16-bit ({@link u2}) field in a {@code class} file.
 *
 * @author Amos Shi
 * @since JDK 6.0
 */
public class U2ClassComponent extends ClassComponent
{
    u2 value;

    U2ClassComponent()
    {
        this.value = new u2();
    }

    U2ClassComponent(final PosDataInputStream posDataInputStream)
        throws IOException
    {
        this();

        this.startPos = posDataInputStream.getPos();
        this.length = 2;
        
        this.value.value = posDataInputStream.readUnsignedShort();
    }

    /**
     * Get the {@link u2} vaue in {@link java.lang.Integer} format.
     *
     * @return Value of the {@link u2} component
     */
    public int getValue()
    {
        return this.value.value;
    }
}
