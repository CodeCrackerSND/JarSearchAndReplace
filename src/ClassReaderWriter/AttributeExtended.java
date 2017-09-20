package ClassReaderWriter;

import java.io.IOException;
import ClassReaderWriter.AttributeInfo;
import ClassReaderWriter.u2;
import ClassReaderWriter.PosDataInputStream;
import java.io.DataOutputStream;

/**
 * The class for the Extended attribute. Non-standard attribute, all of the
 * attributes which are not defined in the VM Spec will be represented by this
 * class.
 *
 * <pre>
 *    attribute_info {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *        u1 info[attribute_length];
 *    }
 * </pre>
 *
 * The {@code info} is the raw byte array data.
 *
 *
 * @author Amos Shi
 * @since JDK 6.0
 */
public class AttributeExtended extends AttributeInfo {

    transient private byte[] rawData;

    AttributeExtended(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream)
            throws IOException, Exception {
        super(nameIndex, type, posDataInputStream);

        if (this.attribute_length.value > 0) {
            this.rawData = new byte[this.attribute_length.value];
            posDataInputStream.read(this.rawData);
        }

        super.checkSize(posDataInputStream.getPos());
    }

    
       public void WriteAttribute(DataOutputStream dos)
       throws IOException, Exception {
       if (this.attribute_length.value > 0)
       {
       dos.write(this.rawData, 0, this.rawData.length);
       }
    }
    
    /**
     * Get the value of {@code info}, in raw data format.
     *
     * @return The value of {@code info}
     */
    public byte[] getRawData() {
        return this.rawData;
    }
}
