package ClassReaderWriter;

import java.io.IOException;
import ClassReaderWriter.u2;
import java.io.DataOutputStream;

/**
 * The class for the {@code ConstantValue} attribute.
 * The {@code ConstantValue} attribute has the following format:
 *
 * <pre>
 *    ConstantValue_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *        u2 constantvalue_index;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1405">
 * VM Spec: The ConstantValue Attribute
 * </a>
 */
public class AttributeConstantValue extends AttributeInfo {
    private transient final u2 constantvalue_index;

    AttributeConstantValue(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream)
        throws IOException, Exception
    {
        super(nameIndex, type, posDataInputStream);
        
        if (this.attribute_length.value != 2)
        {
            throw new Exception(String.format("The attribute_length of AttributeConstantValue is not 2, it is %d.", this.attribute_length.value));
        }

        this.constantvalue_index = new u2();
        this.constantvalue_index.value = posDataInputStream.readUnsignedShort();
        
        super.checkSize(posDataInputStream.getPos());
    }

       public void WriteAttribute(DataOutputStream dos)
       throws IOException, Exception {
        dos.writeShort((short)this.constantvalue_index.value);
        }
    
    /**
     * Get the value of {@code constantvalue_index}.
     *
     * @return The value of {@code constantvalue_index}
     */
    public int getConstantValueIndex()
    {
        return this.constantvalue_index.value;
    }
}
