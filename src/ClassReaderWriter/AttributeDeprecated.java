package ClassReaderWriter;

import java.io.IOException;
import ClassReaderWriter.AttributeInfo;
import ClassReaderWriter.u2;
import ClassReaderWriter.PosDataInputStream;
import java.io.DataOutputStream;

/**
 * The class for the {@code Deprecated} attribute.
 * The {@code Deprecated} attribute has the following format:
 *
 * <pre>
 *    Deprecated_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#78232">
 * VM Spec: The Deprecated Attribute
 * </a>
 */
public class AttributeDeprecated extends AttributeInfo {

    AttributeDeprecated(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream)
            throws IOException, Exception {
        super(nameIndex, type, posDataInputStream);

        if (this.attribute_length.value != 0) {
            throw new Exception(String.format("The attribute_length of AttributeDeprecated is not 0, it is %d.", this.attribute_length.value));
        }

        super.checkSize(posDataInputStream.getPos());
    }
    
       public void WriteAttribute(DataOutputStream dos)
       throws IOException, Exception {

        }
}
