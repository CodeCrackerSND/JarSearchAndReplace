package ClassReaderWriter;

import java.io.IOException;
import ClassReaderWriter.u2;
import java.io.IOException;
import ClassReaderWriter.ConstantPoolTypes.AbstractCPInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantUtf8Info;
import ClassReaderWriter.AttributeConstantValue;
import ClassReaderWriter.AttributeInnerClasses;
import ClassReaderWriter.AttributeSourceFile;
import ClassReaderWriter.AttributeLineNumberTable;
import ClassReaderWriter.AttributeDeprecated;
import ClassReaderWriter.AttributeExtended;
import java.io.DataOutputStream;

/**
 * Super class for attributes in class file. All attributes have the following format:
 *
 * <pre>
 *    attribute_info {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *        u1 info[attribute_length];
 *    }
 * </pre>
 *
 * The contents in {@code info} is determined by {@code attribute_name_index}.
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#43817">
 * VM Spec: Attributes
 * </a>
 */
public class AttributeInfo extends ClassComponent {

    /**
     * The name for {@code ConstantValue} attribute type.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1405">
     * VM Spec: The ConstantValue Attribute
     * </a>
     */
    public static final String TypeConstantValue = "ConstantValue";
    /**
     * The name for {@code Code} attribute type.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1546">
     * VM Spec: The Code Attribute
     * </a>
     */
    public static final String TypeCode = "Code";
    /**
     * The name for {@code Exceptions} attribute type.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#3129">
     * VM Spec: The Exceptions Attribute
     * </a>
     */
    public static final String TypeExceptions = "Exceptions";
    /**
     * The name for {@code InnerClasses} attribute type.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#79996">
     * VM Spec: The InnerClasses Attribute
     * </a>
     */
    public static final String TypeInnerClasses = "InnerClasses";
    /**
     * The name for {@code Synthetic} attribute type.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#80128">
     * VM Spec: The Synthetic Attribute
     * </a>
     */
    public static final String TypeSynthetic = "Synthetic";
    /**
     * The name for {@code SourceFile} attribute type.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#79868">
     * VM Spec: The SourceFile Attribute
     * </a>
     */
    public static final String TypeSourceFile = "SourceFile";
    /**
     * The name for {@code LineNumberTable} attribute type.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#22856">
     * VM Spec: The LineNumberTable Attribute
     * </a>
     */
    public static final String TypeLineNumberTable = "LineNumberTable";
    /**
     * The name for {@code LocalVariableTable} attribute type.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#5956">
     * VM Spec: The LocalVariableTable Attribute
     * </a>
     */
    public static final String TypeLocalVariableTable = "LocalVariableTable";
    /**
     * The name for {@code Deprecated} attribute type.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#78232">
     * VM Spec: The Deprecated Attribute
     * </a>
     */
    public static final String TypeDeprecated = "Deprecated";
    /**
     * Non-standard attributes.
     * All of the attributes which are not defined in the VM Spec.
     */
    public static final String Extended = "[Extended Attr.] ";
    transient private String type;
    transient u2 attribute_name_index;
    transient u4 attribute_length;

    AttributeInfo() {
    }

    AttributeInfo(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream)
            throws IOException, Exception {
        this.startPos = posDataInputStream.getPos() - 2;

        this.type = type;

        this.attribute_name_index = new u2();
        this.attribute_name_index.value = nameIndex.value;
        this.attribute_length = new u4();
        this.attribute_length.value = posDataInputStream.readInt();

        this.length = this.attribute_length.value + 6;
    }
    
    public int GetAttributeLenght()
    {
    return this.attribute_length.value;
    }

    public void SetAttributeLenght(int newvvalue)
    {
    this.attribute_length.value = newvvalue;
    }
    
    static AttributeInfo parse(final PosDataInputStream posDataInputStream, final AbstractCPInfo[] cp)
            throws IOException, Exception {
        AttributeInfo attr = new AttributeInfo();

        final u2 attrNameIndex = new u2();
        attrNameIndex.value = posDataInputStream.readUnsignedShort();
        if (AbstractCPInfo.CONSTANT_Utf8 == cp[attrNameIndex.value].tag.value) {
            final String type = ((ConstantUtf8Info) cp[attrNameIndex.value]).getValue();
            if (TypeConstantValue.equals(type)) {
                attr = new AttributeConstantValue(attrNameIndex, type, posDataInputStream);
            } else if (TypeCode.equals(type)) {
                attr = new AttributeCode(attrNameIndex, type, posDataInputStream, cp);
            } else if (TypeExceptions.equals(type)) {
                attr = new AttributeExceptions(attrNameIndex, type, posDataInputStream);
            } else if (TypeInnerClasses.equals(type)) {
                attr = new AttributeInnerClasses(attrNameIndex, type, posDataInputStream);
            } else if (TypeSynthetic.equals(type)) {
                attr = new AttributeSynthetic(attrNameIndex, type, posDataInputStream);
            } else if (TypeSourceFile.equals(type)) {
                attr = new AttributeSourceFile(attrNameIndex, type, posDataInputStream);
            } else if (TypeLineNumberTable.equals(type)) {
                attr = new AttributeLineNumberTable(attrNameIndex, type, posDataInputStream);
            } else if (TypeLocalVariableTable.equals(type)) {
                attr = new AttributeLocalVariableTable(attrNameIndex, type, posDataInputStream);
            } else if (TypeDeprecated.equals(type)) {
                attr = new AttributeDeprecated(attrNameIndex, type, posDataInputStream);
            } else {
                attr = new AttributeExtended(attrNameIndex, Extended + type, posDataInputStream);
            }
        } else {
            throw new Exception(String.format("Attribute name_index is not CONSTANT_Utf8. Constant index = %d, type = %d.", attrNameIndex.value, cp[attrNameIndex.value].tag.value));
        }

        return attr;
    }

        public void WriteAttrib(DataOutputStream dos)
            throws IOException, Exception {

        dos.writeShort((short)this.attribute_name_index.value);
        dos.writeInt((int)this.attribute_length.value);
        
                if (this instanceof AttributeConstantValue)
                {
                AttributeConstantValue attrcv = (AttributeConstantValue)this;
                attrcv.WriteAttribute(dos);
                }
                else if (this instanceof AttributeCode)
                {
                AttributeCode attrc = (AttributeCode)this;
                attrc.WriteAttribute(dos);
                }
                else if (this instanceof AttributeExceptions)
                {
                AttributeExceptions attre = (AttributeExceptions)this;
                attre.WriteAttribute(dos);
                }
                else if (this instanceof AttributeInnerClasses)
                {
                AttributeInnerClasses attric = (AttributeInnerClasses)this;
                attric.WriteAttribute(dos); 
                }
                else if (this instanceof AttributeSynthetic)
                {
                AttributeSynthetic attrs = (AttributeSynthetic)this;
                attrs.WriteAttribute(dos);
                }
                else if (this instanceof AttributeSourceFile)
                {
                AttributeSourceFile attrsf = (AttributeSourceFile)this;
                attrsf.WriteAttribute(dos);
                }
                else if (this instanceof AttributeLineNumberTable)
                {
                AttributeLineNumberTable attrnt = (AttributeLineNumberTable)this;
                attrnt.WriteAttribute(dos);
                }
                else if (this instanceof AttributeLocalVariableTable)
                {
                AttributeLocalVariableTable attrlvt = (AttributeLocalVariableTable)this;
                attrlvt.WriteAttribute(dos);
                }
                else if (this instanceof AttributeDeprecated)
                {
                AttributeDeprecated attrd = (AttributeDeprecated)this;
                attrd.WriteAttribute(dos);
                
                }
                else if (this instanceof AttributeExtended)
                {
                AttributeExtended attre = (AttributeExtended)this;
                attre.WriteAttribute(dos);
                }
                
                
        /*
        if (AbstractCPInfo.CONSTANT_Utf8 == cp[attrNameIndex.value].tag.value) {
            final String type = ((ConstantUtf8Info) cp[attrNameIndex.value]).getValue();
            if (TypeConstantValue.equals(type)) {
                attr = new AttributeConstantValue(attrNameIndex, type, posDataInputStream);
            } else if (TypeCode.equals(type)) {
                attr = new AttributeCode(attrNameIndex, type, posDataInputStream, cp);
            } else if (TypeExceptions.equals(type)) {
                attr = new AttributeExceptions(attrNameIndex, type, posDataInputStream);
            } else if (TypeInnerClasses.equals(type)) {
                attr = new AttributeInnerClasses(attrNameIndex, type, posDataInputStream);
            } else if (TypeSynthetic.equals(type)) {
                attr = new AttributeSynthetic(attrNameIndex, type, posDataInputStream);
            } else if (TypeSourceFile.equals(type)) {
                attr = new AttributeSourceFile(attrNameIndex, type, posDataInputStream);
            } else if (TypeLineNumberTable.equals(type)) {
                attr = new AttributeLineNumberTable(attrNameIndex, type, posDataInputStream);
            } else if (TypeLocalVariableTable.equals(type)) {
                attr = new AttributeLocalVariableTable(attrNameIndex, type, posDataInputStream);
            } else if (TypeDeprecated.equals(type)) {
                attr = new AttributeDeprecated(attrNameIndex, type, posDataInputStream);
            } else {
                attr = new AttributeExtended(attrNameIndex, Extended + type, posDataInputStream);
            }
        } else {
            throw new Exception(String.format("Attribute name_index is not CONSTANT_Utf8. Constant index = %d, type = %d.", attrNameIndex.value, cp[attrNameIndex.value].tag.value));
        }
        */ 


    }
    
    /**
     * Verify the current class file input stream position is correct.
     *
     * @param endPos Current postion
     * @throws org.freeinternals.classfile.core.ClassFormatException
     */
    protected void checkSize(final int endPos)
            throws Exception {
        if (this.startPos + this.length != endPos) {
            throw new Exception(String.format("Attribute analysis failed. type='%s', startPos=%d, length=%d, endPos=%d", this.getName(), this.startPos, this.length, endPos));
        }
    }

    /**
     * Get the name of the attribute.
     *
     * Attributes are used in the {@code ClassFile}, {@code field_info}, {@code method_info},
     * and {@code Code_attribute} structures of the class file format.  
     *
     * @return A string of the attribute name
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#43817">
     * VM Spec: Attributes
     * </a>
     */
    public String getName() {
        return (this.type != null) ? this.type : "";
    }

    /**
     * Get the value of {@code attribute_name_index}.
     *
     * @return The value of {@code attribute_name_index}
     */
    public int getNameIndex() {
        return this.attribute_name_index.value;
    }

    /**
     * Get the value of {@code attribute_length}.
     *
     * @return The value of {@code attribute_length}
     */
    public int getAttributeLength() {
        return this.attribute_length.value;
    }
}
