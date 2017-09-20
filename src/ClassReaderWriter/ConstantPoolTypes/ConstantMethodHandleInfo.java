package ClassReaderWriter.ConstantPoolTypes;

import java.io.IOException;
import ClassReaderWriter.u2;
import ClassReaderWriter.PosDataInputStream;


/**
 * The class for the {@code CONSTANT_MethodHandle_info} structure in constant pool.
 * The {@code CONSTANT_Utf8_info} structure has the following format:
 *
 * <pre>
 *    CONSTANT_MethodHandle_info {
 *        u1 tag;
 *        u2 reference_kind;
 *        u2 reference_index;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 8.0
 * @see <a href="http://www.docjar.com/html/api/com/sun/tools/classfile/ConstantPool.java.html">
 * VM Spec: The CONSTANT_MethodHandle_info Structure
 * </a>
 */
public class ConstantMethodHandleInfo extends AbstractCPInfo {

    private final u2 reference_kind;
    private final u2 reference_index;

    public ConstantMethodHandleInfo(final PosDataInputStream posDataInputStream)
            throws IOException, Exception {
        super();
        this.tag.value = AbstractCPInfo.CONSTANT_MethodHandle;

        this.startPos = posDataInputStream.getPos() - 1;
        this.length = 4;

        this.reference_kind = new u2();
        this.reference_kind.value = posDataInputStream.readUnsignedByte();
        this.reference_index = new u2();
        this.reference_index.value = posDataInputStream.readUnsignedShort();
    }

    @Override
    public String getName() {
        return "MethodHandle";
    }

    @Override
    public String getDescription() {
        return String.format("ConstantMethodHandleInfo: Start Position: [%d], length: [%d], value: reference_kind=[%d], name_and_type_index=[%d].", this.startPos, this.length, this.reference_kind.value, this.reference_index.value);
    }

    /**
     * Get the value of {@bootstrap method reference_kind}.
     *
     * @return The value of {@code reference_kind}
     */
    public int getReferenceKind() {
        return this.reference_kind.value;
    }

    /**
     * Get the value of {@code ReferenceIndex}.
     *
     * @return The value of {@code ReferenceIndex}
     */
    public int getReferenceIndex() {
        return this.reference_index.value;
    }
}
