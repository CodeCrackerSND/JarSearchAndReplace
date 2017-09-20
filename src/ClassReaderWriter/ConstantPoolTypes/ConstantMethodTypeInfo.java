package ClassReaderWriter.ConstantPoolTypes;

import java.io.IOException;
import ClassReaderWriter.u2;
import ClassReaderWriter.PosDataInputStream;


/**
 * The class for the {@code CONSTANT_MethodType_info} structure in constant pool.
 * The {@code CONSTANT_Utf8_info} structure has the following format:
 *
 * <pre>
 *    CONSTANT_MethodType_info {
 *        u1 tag;
 *        u2 descriptor_index;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 8.0
 * @see <a href="http://www.docjar.com/html/api/com/sun/tools/classfile/ConstantPool.java.html">
 * VM Spec: The CONSTANT_MethodType_info Structure
 * </a>
 */
public class ConstantMethodTypeInfo extends AbstractCPInfo {

    private final u2 descriptor_index;

    public ConstantMethodTypeInfo(final PosDataInputStream posDataInputStream)
            throws IOException, Exception {
        super();
        this.tag.value = AbstractCPInfo.CONSTANT_MethodType;

        this.startPos = posDataInputStream.getPos() - 1;
        this.length = 3;

        this.descriptor_index = new u2();
        this.descriptor_index.value = posDataInputStream.readUnsignedShort();
    }

    @Override
    public String getName() {
        return "MethodType";
    }

    @Override
    public String getDescription() {
        return String.format("ConstantMethodTypeInfo: Start Position: [%d], length: [%d], value: reference_kind=[%d]", this.startPos, this.length, this.descriptor_index.value);
    }

    /**
     * Get the value of {@bootstrap method DescriptorIndex}.
     *
     * @return The value of {@code DescriptorIndex}
     */
    public int getDescriptorIndex() {
        return this.descriptor_index.value;
    }


}
