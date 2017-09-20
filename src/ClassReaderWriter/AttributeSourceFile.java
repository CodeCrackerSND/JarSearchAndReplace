package ClassReaderWriter;

import java.io.IOException;
import ClassReaderWriter.u2;
import ClassReaderWriter.PosDataInputStream;
import ClassReaderWriter.AttributeInfo;
import java.io.DataOutputStream;

/**
 * The class for the {@code SourceFile} attribute.
 * The {@code SourceFile} attribute has the following format:
 *
 * <pre>
 *    SourceFile_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *        u2 sourcefile_index;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#79868">
 * VM Spec: The SourceFile Attribute
 * </a>
 */
public class AttributeSourceFile extends AttributeInfo {

    private transient final u2 sourcefile_index;

    AttributeSourceFile(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream)
            throws IOException, Exception {
        super(nameIndex, type, posDataInputStream);

        if (this.attribute_length.value != 2) {
            throw new Exception(String.format("The attribute_length of AttributeSourceFile is not 2, it is %d.", this.attribute_length.value));
        }

        this.sourcefile_index = new u2();
        this.sourcefile_index.value = posDataInputStream.readUnsignedShort();

        super.checkSize(posDataInputStream.getPos());
    }

       public void WriteAttribute(DataOutputStream dos)
       throws IOException, Exception {
        dos.writeShort((short)this.sourcefile_index.value);
        }
    
    /**
     * Get the value of {@code sourcefile_index}.
     *
     * @return The value of {@code sourcefile_index}
     */
    public int getSourcefileIndex() {
        return this.sourcefile_index.value;
    }
}
