package ClassReaderWriter;

import ClassReaderWriter.u2;
import ClassReaderWriter.PosDataInputStream;
import ClassReaderWriter.AttributeInfo;
import java.io.DataOutputStream;

import java.io.IOException;

/**
 * The class for the {@code SourceFile} attribute.
 * The {@code SourceFile} attribute has the following format:
 *
 * <pre>
 *    Synthetic_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#80128">
 * VM Spec: The Synthetic Attribute
 * </a>
 */
public class AttributeSynthetic extends AttributeInfo {

    AttributeSynthetic(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream)
            throws IOException, Exception {
        super(nameIndex, type, posDataInputStream);

        if (this.attribute_length.value != 0) {
            throw new Exception(String.format("The attribute_length of AttributeSynthetic is not 0, it is %d.", this.attribute_length.value));
        }

        super.checkSize(posDataInputStream.getPos());
    }
    
           public void WriteAttribute(DataOutputStream dos)
       throws IOException, Exception {

        }
}
