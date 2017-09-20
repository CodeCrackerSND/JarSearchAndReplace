package ClassReaderWriter.ConstantPoolTypes;

import java.io.IOException;
import ClassReaderWriter.PosDataInputStream;

/**
 * The class for the {@code CONSTANT_Integer_info} structure in constant pool.
 * The {@code CONSTANT_Integer_info} structure has the following format:
 *
 * <pre>
 *    CONSTANT_Integer_info {
 *        u1 tag;
 *        u4 bytes;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#21942">
 * VM Spec: The CONSTANT_Integer_info Structure
 * </a>
 */
public class ConstantIntegerInfo extends AbstractCPInfo {

    private final int integerValue;

    public ConstantIntegerInfo(final PosDataInputStream posDataInputStream)
            throws IOException {
        super();
        this.tag.value = AbstractCPInfo.CONSTANT_Integer;

        this.startPos = posDataInputStream.getPos() - 1;
        this.length = 5;

        this.integerValue = posDataInputStream.readInt();
    }

    @Override
    public String getName() {
        return "Integer";
    }

    @Override
    public String getDescription() {
        return String.format("ConstantIntegerInfo: Start Position: [%d], length: [%d], value: [%d].", this.startPos, this.length, this.integerValue);
    }

    /**
     * Get the value of {@link java.lang.Integer}.
     *
     * @return The integer value
     */
    public int getValue() {
        return this.integerValue;
    }
}
