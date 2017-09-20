package ClassReaderWriter.ConstantPoolTypes;

import java.io.IOException;
import ClassReaderWriter.u2;
import ClassReaderWriter.PosDataInputStream;


/**
 * The class for the {@code CONSTANT_InvokeDynamic_info} structure in constant pool.
 * The {@code CONSTANT_InvokeDynamic_info} structure has the following format:
 *
 * <pre>
 *    CONSTANT_Utf8_info {
 *        u1 tag;
 *        u2 bootstrap_method_attr_index;
 *        u2 name_and_type_index;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 8.0
 * @see <a href="http://www.docjar.com/html/api/com/sun/tools/classfile/ConstantPool.java.html">
 * VM Spec: The CONSTANT_InvokeDynamic_info Structure
 * </a>
 */
public class ConstantInvokeDynamicInfo extends AbstractCPInfo {

    private final u2 bootstrap_method_attr_index;
    private final u2 name_and_type_index;

    public ConstantInvokeDynamicInfo(final PosDataInputStream posDataInputStream)
            throws IOException, Exception {
        super();
        this.tag.value = AbstractCPInfo.CONSTANT_InvokeDynamic;

        this.startPos = posDataInputStream.getPos() - 1;
        this.length = 5;

        this.bootstrap_method_attr_index = new u2();
        this.bootstrap_method_attr_index.value = posDataInputStream.readUnsignedShort();
        this.name_and_type_index = new u2();
        this.name_and_type_index.value = posDataInputStream.readUnsignedShort();
    }

    @Override
    public String getName() {
        return "InvokeDynamic";
    }

    @Override
    public String getDescription() {
        return String.format("ConstantInvokeDynamicInfo: Start Position: [%d], length: [%d], value: bootstrap_method_attr_index=[%d], name_and_type_index=[%d].", this.startPos, this.length, this.bootstrap_method_attr_index.value, this.name_and_type_index.value);
    }

    /**
     * Get the value of {@bootstrap method attr_index}.
     *
     * @return The value of {@code attr_index}
     */
    public int getBootStrapMethodAttrIndex() {
        return this.bootstrap_method_attr_index.value;
    }

    /**
     * Get the value of {@code name_and_type_index}.
     *
     * @return The value of {@code name_and_type_index}
     */
    public int getNameAndTypeIndex() {
        return this.name_and_type_index.value;
    }
}
