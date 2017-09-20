package ClassReaderWriter;

import java.io.IOException;
import ClassReaderWriter.AttributeInfo;
import ClassReaderWriter.u2;
import ClassReaderWriter.PosDataInputStream;
import ClassReaderWriter.ClassComponent;
import java.io.DataOutputStream;

/**
 * The class for the {@code InnerClasses} attribute.
 * The {@code InnerClasses} attribute has the following format:
 *
 * <pre>
 *    InnerClasses_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *        u2 number_of_classes;
 *        {  u2 inner_class_info_index;
 *           u2 outer_class_info_index;
 *           u2 inner_name_index;
 *           u2 inner_class_access_flags;
 *        } classes[number_of_classes];
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#79996">
 * VM Spec: The InnerClasses Attribute
 * </a>
 */
public class AttributeInnerClasses extends AttributeInfo {

    private transient final u2 number_of_classes;
    private transient Class[] classes;

    AttributeInnerClasses(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream)
            throws IOException, Exception {
        super(nameIndex, type, posDataInputStream);

        this.number_of_classes = new u2();
        this.number_of_classes.value = posDataInputStream.readUnsignedShort();
        if (this.number_of_classes.value > 0) {
            this.classes = new Class[this.number_of_classes.value];
            for (int i = 0; i < this.number_of_classes.value; i++) {
                this.classes[i] = new Class(posDataInputStream);
            }
        }

        super.checkSize(posDataInputStream.getPos());
    }

     public void WriteAttribute(DataOutputStream dos)
       throws IOException, Exception {
        dos.writeShort((short)this.number_of_classes.value);
        
        if (this.number_of_classes.value > 0) {
           
            for (int i = 0; i < this.number_of_classes.value; i++) {
                this.classes[i].WriteClass(dos);
            }
        }

    }
    
    /**
     * Get the value of {@code number_of_classes}.
     *
     * @return The value of {@code number_of_classes}
     */
    public int getNumberOfClasses() {
        return this.number_of_classes.value;
    }

    /**
     * Get the value of {@code classes}[{@code index}].
     *
     * @param index Index of the classes
     * @return The value of {@code classes}[{@code index}]
     */
    public Class getClass(final int index) {
        Class cls = null;
        if (this.classes != null) {
            cls = this.classes[index];
        }

        return cls;
    }

    /**
     * The {@code classes} structure in {@code InnerClasses} attribute.
     *
     * @author Amos Shi
     * @since JDK 6.0
     */
    public final class Class extends ClassComponent {

        private transient final u2 inner_class_info_index;
        private transient final u2 outer_class_info_index;
        private transient final u2 inner_name_index;
        private transient final u2 inner_class_access_flags;

        private Class(final PosDataInputStream posDataInputStream)
                throws IOException {
            this.startPos = posDataInputStream.getPos();
            this.length = 8;

            this.inner_class_info_index = new u2();
            this.inner_class_info_index.value = posDataInputStream.readUnsignedShort();
            this.outer_class_info_index = new u2();
            this.outer_class_info_index.value = posDataInputStream.readUnsignedShort();
            this.inner_name_index = new u2();
            this.inner_name_index.value = posDataInputStream.readUnsignedShort();
            this.inner_class_access_flags = new u2();
            this.inner_class_access_flags.value = posDataInputStream.readUnsignedShort();
        }

       public void WriteClass(DataOutputStream dos)
       throws IOException, Exception {
            dos.writeShort((short)this.inner_class_info_index.value);
            dos.writeShort((short)this.outer_class_info_index.value);
            dos.writeShort((short)this.inner_name_index.value);
            dos.writeShort((short)this.inner_class_access_flags.value);
        }
        
        /**
         * Get the value of {@code inner_class_info_index}.
         *
         * @return The value of {@code inner_class_info_index}
         */
        public int getInnerClassInfoIndex() {
            return this.inner_class_info_index.value;
        }

        /**
         * Get the value of {@code outer_class_info_index}.
         *
         * @return The value of {@code outer_class_info_index}
         */
        public int getOuterClassInfoIndex() {
            return this.outer_class_info_index.value;
        }

        /**
         * Get the value of {@code inner_name_index}.
         *
         * @return The value of {@code inner_name_index}
         */
        public int getInnerNameIndex() {
            return this.inner_name_index.value;
        }

        /**
         * Get the value of {@code inner_class_access_flags}.
         *
         * @return The value of {@code inner_class_access_flags}
         */
        public int getInnerClassAccessFlags() {
            return this.inner_class_access_flags.value;
        }
    }
}