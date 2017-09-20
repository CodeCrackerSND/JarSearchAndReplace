package ClassReaderWriter.ConstantPoolTypes;

import java.io.IOException;
import ClassReaderWriter.PosDataInputStream;
import ClassReaderWriter.u2;

/**
 * The class for the {@code CONSTANT_Class_info} structure in constant pool.
 * The {@code CONSTANT_Class_info} structure has the following format:
 *
 * <pre>
 *    CONSTANT_Class_info {
 *        u1 tag;
 *        u2 name_index;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1221">
 * VM Spec:  The CONSTANT_Class_info Structure
 * </a>
 */
public class ConstantClassInfo extends AbstractCPInfo {

    private final u2 name_index;

    public ConstantClassInfo(final PosDataInputStream posDataInputStream)
            throws IOException {
        super();
        this.tag.value = AbstractCPInfo.CONSTANT_Class;

        this.startPos = posDataInputStream.getPos() - 1;
        this.length = 3;

        this.name_index = new u2();
        this.name_index.value = posDataInputStream.readUnsignedShort();
    }

    @Override
    public String getName() {
        return "Class";
    }

    @Override
    public String getDescription() {
        return String.format("ConstantClassInfo: Start Position: [%d], length: [%d], value: name_index = [%d].", this.startPos, this.length, this.name_index.value);
    }

    /**
     * Get the value of {@code name_index}.
     *
     * @return The value of {@code name_index}
     */
    public int getNameIndex() {
        return this.name_index.value;
    }
}
