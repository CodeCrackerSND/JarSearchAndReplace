package ClassReaderWriter.ConstantPoolTypes;

import java.io.IOException;
import ClassReaderWriter.PosDataInputStream;

/**
 * The class for the {@code CONSTANT_Double_info} structure in constant pool.
 * The {@code CONSTANT_Double_info} structure has the following format:
 *
 * <pre>
 *    CONSTANT_Double_info {
 *        u1 tag;
 *        u4 high_bytes;
 *        u4 low_bytes;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1348">
 * VM Spec:  The CONSTANT_Double_info Structure
 * </a>
 */
public class ConstantDoubleInfo extends AbstractCPInfo {
    //private u4 high_bytes;
    //private u4 low_bytes;
    
    private final double doubleValue;
    
    public ConstantDoubleInfo(final PosDataInputStream posDataInputStream)
        throws IOException
    {
        super();
        this.tag.value = AbstractCPInfo.CONSTANT_Double;

        this.startPos = posDataInputStream.getPos() - 1;
        this.length = 9;
        
        this.doubleValue = posDataInputStream.readDouble();
    }
    
    @Override
    public String getName()
    {
        return "Double";
    }

    @Override
    public String getDescription()
    {
        return String.format("ConstantDoubleInfo: Start Position: [%d], length: [%d], value: [%f].", this.startPos, this.length, this.doubleValue);
    }
    
    /**
     * Get the value of {@link java.lang.Double}.
     *
     * @return The double value
     */
    public double getValue()
    {
        return this.doubleValue;
    }
}
