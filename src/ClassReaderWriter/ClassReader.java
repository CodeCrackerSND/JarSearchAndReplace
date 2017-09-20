package ClassReaderWriter;
import java.io.*;
import java.util.*;
import ClassReaderWriter.*;
import ClassReaderWriter.PosByteArrayInputStream;
import ClassReaderWriter.PosDataInputStream;
import ClassReaderWriter.ConstantPoolTypes.AbstractCPInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantUtf8Info;
import ClassReaderWriter.ConstantPoolTypes.ConstantIntegerInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantFloatInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantLongInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantDoubleInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantClassInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantStringInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantFieldrefInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantMethodrefInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantInterfaceMethodrefInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantNameAndTypeInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantInvokeDynamicInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantMethodHandleInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantMethodTypeInfo;

public class ClassReader {
    byte[] classarray;
    public PosByteArrayInputStream bis;
    public PosDataInputStream ois;
    public int ClassLenght;
    public int magic;
    public short minor_version;
    public short major_version;
    public short cp_count;
    public AbstractCPInfo[] constant_pools;
    public byte[] cpbytes;

    public short access_flags;
    public short this_class;
    public short super_class;
    public short interfaces_count;
    public Interface[] interfaces;
    public byte[] interfacesbytes;

    public short fields_count;
    public FieldInfo[] fields;
    public byte[] fieldsbytes;

    public short methods_count;
    public MethodInfo[] methods;
    public byte[] methodsbytes;

    public short attributes_count;
    private AttributeInfo[] attributes;
    public byte[] attributesbytes;


    public boolean init(byte[] bytearray) throws IOException,Exception
    {
try
{
    classarray = bytearray;
    bis = new PosByteArrayInputStream(classarray);
    ois = new PosDataInputStream(bis);
    ClassFileVersion();
    ConstantPool();
    ClassDeclaration();
    readFields();
    readMethods();
    readAttributes();
ClassLenght = ois.getPos();

return true;
}
catch(Exception ex)
{
return false;
}


    }


    public short WriteString(String thestring) throws IOException,Exception
    {
    short utfIndex = (short)(cp_count);
    short StringIndex = (short)(cp_count+1);
    byte[] stringbytes = thestring.getBytes("US-ASCII");

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(bos);  
    dos.write(cpbytes, 0, cpbytes.length);

    dos.writeByte((byte)(01)); // tag - 01 for UTF8!
    dos.writeShort((short)(stringbytes.length));  // string length
    dos.write(stringbytes, 0, stringbytes.length);
    cp_count++;

    dos.writeByte((byte)(8)); // tag - 08 for string!
    dos.writeShort((short)(utfIndex));  // string index
    cp_count++;

    byte[] bytes = bos.toByteArray();
    cpbytes = bytes;
    dos.close();
    bos.close();

    return StringIndex;
    }

    public short WriteUTF8(String thestring) throws IOException,Exception
    {
    short utfIndex = (short)(cp_count);
    byte[] stringbytes = thestring.getBytes("US-ASCII");

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(bos);  
    dos.write(cpbytes, 0, cpbytes.length);

    dos.writeByte((byte)(01)); // tag - 01 for UTF8!
    dos.writeShort((short)(stringbytes.length));  // string length
    dos.write(stringbytes, 0, stringbytes.length);
    cp_count++;

    byte[] bytes = bos.toByteArray();
    cpbytes = bytes;
    dos.close();
    bos.close();

return utfIndex;
    }
    
    public void MethodsFromArray() throws IOException
    {
try
{
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(bos);  
    
    methods_count = (short)methods.length;
            if (methods_count > 0)
            {
                for (int i = 0; i < methods_count; i++)
                {
                    MethodInfo minfo = methods[i];
                    dos.writeShort((short)minfo.access_flags.value);
                    dos.writeShort((short)minfo.name_index.value);
                    dos.writeShort((short)minfo.descriptor_index.value);
                    short atttribcount = (short)minfo.attributes_count.value;
                    dos.writeShort(atttribcount);

                    
            if (atttribcount > 0) {
            for (int j = 0; j < atttribcount; j++)
            minfo.attributes[j].WriteAttrib(dos);                    
            }

                }
            }
    byte[] bytes = bos.toByteArray();
    int newone = bytes.length;
    int oldone = methodsbytes.length;
    methodsbytes = bytes;
    dos.close();
    bos.close();

}
catch(Exception exc)
{

}
    }
    
public void ClassFileVersion() throws IOException,Exception
{
    magic = ois.readInt();
    if (magic != 0xCAFEBABE)
    {
    throw new Exception("The magic number of the byte array is not 0xCAFEBABE");
    }
    minor_version = ois.readShort();
    major_version = ois.readShort();
    cp_count = ois.readShort();
}

public void ConstantPool() throws IOException,Exception
{
    int cpstart = ois.getPos();

    constant_pools = new AbstractCPInfo[cp_count];
    short tag;
    for (int i = 1; i < cp_count; i++) {
     tag = (short) ois.readUnsignedByte();

                switch (tag) {
                    case AbstractCPInfo.CONSTANT_Utf8:
                        constant_pools[i] = new ConstantUtf8Info(ois);
                        break;

                    case AbstractCPInfo.CONSTANT_Integer:
                        constant_pools[i] = new ConstantIntegerInfo(ois);
                        break;

                    case AbstractCPInfo.CONSTANT_Float:
                        constant_pools[i] = new ConstantFloatInfo(ois);
                        break;

                    case AbstractCPInfo.CONSTANT_Long:
                        constant_pools[i] = new ConstantLongInfo(ois);
                        i++;
                        break;

                    case AbstractCPInfo.CONSTANT_Double:
                        constant_pools[i] = new ConstantDoubleInfo(ois);
                        i++;
                        break;

                    case AbstractCPInfo.CONSTANT_Class:
                        constant_pools[i] = new ConstantClassInfo(ois);
                        break;

                    case AbstractCPInfo.CONSTANT_String:
                        constant_pools[i] = new ConstantStringInfo(ois);
                        break;

                    case AbstractCPInfo.CONSTANT_Fieldref:
                        constant_pools[i] = new ConstantFieldrefInfo(ois);
                        break;

                    case AbstractCPInfo.CONSTANT_Methodref:
                        constant_pools[i] = new ConstantMethodrefInfo(ois);
                        break;

                    case AbstractCPInfo.CONSTANT_InterfaceMethodref:
                        constant_pools[i] = new ConstantInterfaceMethodrefInfo(ois);
                        break;

                    case AbstractCPInfo.CONSTANT_NameAndType:
                        constant_pools[i] = new ConstantNameAndTypeInfo(ois);
                        break;
                        
                    case AbstractCPInfo.CONSTANT_MethodHandle:
                        constant_pools[i] = new ConstantMethodHandleInfo(ois);
                        break;

                    case AbstractCPInfo.CONSTANT_MethodType:
                        constant_pools[i] = new ConstantMethodTypeInfo(ois);
                        break;
                        
                    case AbstractCPInfo.CONSTANT_InvokeDynamic:
                        constant_pools[i] = new ConstantInvokeDynamicInfo(ois);
                        break;
                        
                    default:
                    throw new Exception("Unreconizable constant pool type found.");

                }
    }

    int cpend = ois.getPos();
    int cplen = cpend - cpstart;
    cpbytes = new byte[cplen];
    System.arraycopy(classarray, cpstart, cpbytes, 0, cpbytes.length );

}

public void ClassDeclaration() throws IOException
{
    interfacesbytes = new byte[0];
    interfaces = new Interface[0];
    access_flags = ois.readShort();
    this_class = ois.readShort();
    super_class = ois.readShort();
    interfaces_count = ois.readShort();


    if (interfaces_count > 0) {
    int interfacesstart = ois.getPos();

        interfaces = new Interface[interfaces_count];
                for (int i = 0; i < interfaces_count; i++)
                {
                interfaces[i] = new Interface(ois);
                }
        int interfacesend = ois.getPos();
        int interfaceslen = interfacesend - interfacesstart;
        interfacesbytes = new byte[interfaceslen];
        System.arraycopy(classarray, interfacesstart, interfacesbytes, 0, interfacesbytes.length );
            }
}

public void readFields() throws IOException, Exception
{
    fieldsbytes = new byte[0];
    fields = new FieldInfo[0];
    fields_count = ois.readShort();
    int fieldsstart = ois.getPos();

            if (fields_count > 0) {
                fields = new FieldInfo[fields_count];
                for (int i = 0; i < fields_count; i++) {
                    fields[i] = new FieldInfo(ois, constant_pools);
               String type = null; 
               try {
                    type = SignatureConvertor.signature2Type(this.getConstantUtf8Value(fields[i].getDescriptorIndex()));
                } catch (Exception se) {
                    type = "[Unexpected signature type]: " + this.getConstantUtf8Value(fields[i].getDescriptorIndex());
                    //System.err.println(se.toString());
                }
                fields[i].setDeclaration(String.format("%s %s %s",
                        fields[i].getModifiers(),
                        type,
                        this.getConstantUtf8Value(fields[i].getNameIndex())));
            }
                
        int fieldsend = ois.getPos();
        int fieldslen = fieldsend - fieldsstart;
        fieldsbytes = new byte[fieldslen];
        System.arraycopy(classarray, fieldsstart, fieldsbytes, 0, fieldsbytes.length );
            }
    
}

public void readMethods() throws IOException, Exception
{
    methodsbytes = new byte[0];
    methods = new MethodInfo[0];
    methods_count = ois.readShort();
    int methodsstart = ois.getPos();

            if (methods_count > 0) {
                methods = new MethodInfo[methods_count];
                for (int i = 0; i < methods_count; i++) {
                    methods[i] = new MethodInfo(ois, constant_pools);
                    
                    String mtdReturnType = null;
                    String mtdParameters = null;
                try {
                    mtdReturnType = SignatureConvertor.parseMethodReturnType(this.getConstantUtf8Value(methods[i].getDescriptorIndex()));
                } catch (Exception se) {
                    mtdReturnType = String.format("[Unexpected method return type: %s]", this.getConstantUtf8Value(methods[i].getDescriptorIndex()));
                    //System.err.println(se.toString());
                }
                try {
                    mtdParameters = SignatureConvertor.parseMethodParameters(this.getConstantUtf8Value(methods[i].getDescriptorIndex()));
                } catch (Exception se)
                {
                mtdParameters = String.format("[Unexpected method parameters: %s]", this.getConstantUtf8Value(methods[i].getDescriptorIndex()));
                    //System.err.println(se.toString());
                }
                
                methods[i].setDeclaration(String.format("%s %s %s %s",
                        methods[i].getModifiers(),
                        mtdReturnType,
                        this.getConstantUtf8Value(methods[i].getNameIndex()),
                        mtdParameters));
                }
        int methodsend = ois.getPos();
        int methodslen = methodsend - methodsstart;
        methodsbytes = new byte[methodslen];
        System.arraycopy(classarray, methodsstart, methodsbytes, 0, methodsbytes.length );
            }


}
// this.startPos = posDataInputStream.getPos() - 2;

    private String getConstantUtf8Value(final int cpIndex)
            throws Exception {
        String returnValue = null;

        if ((cpIndex == 0) || (cpIndex >= this.cp_count)) {
            throw new Exception(String.format(
                    "Constant Pool index is out of range. CP index cannot be zero, and should be less than CP count (=%d). CP index = %d.",
                    this.cp_count,
                    cpIndex));
        }

        if (this.constant_pools[cpIndex].tag.value == AbstractCPInfo.CONSTANT_Utf8) {
            final ConstantUtf8Info utf8Info = (ConstantUtf8Info) this.constant_pools[cpIndex];
            returnValue = utf8Info.getValue();
        } else {
            throw new Exception(String.format(
                    "Unexpected constant pool type: Utf8(%d) expected, but it is '%d'.",
                    AbstractCPInfo.CONSTANT_Utf8,
                    this.constant_pools[cpIndex].tag.value));
        }

        return returnValue;
    }

public void readAttributes() throws IOException, Exception
{
    attributesbytes = new byte[0];
    attributes_count = ois.readShort();
    int attributesstart = ois.getPos();

            if (attributes_count > 0) {
                attributes = new AttributeInfo[attributes_count];
                for (int i = 0; i < attributes_count; i++) {
                    attributes[i] = AttributeInfo.parse(ois, constant_pools);
                }
            }

        int attributesend = ois.getPos();
        int attributeslen = attributesend - attributesstart;
        attributesbytes = new byte[attributeslen];
        System.arraycopy(classarray, attributesstart, attributesbytes, 0, attributesbytes.length );

}

    public void CloseStream() throws IOException
    {
    ois.close();
    bis.close();
    }
    
    
        /**
     * Returns a string of the constant pool item at the specified
     * {@code index}.
     *
     * @param index Index in the constant pool
     * @return String of the constant pool item at {@code index}
     */
    public String getCPDescription(final int index) {
        // Invalid index
        if (index >= ClassReader.this.cp_count) {
            return null;
        }

        // Special index: empty
        if (index == 0) {
            return null;
        }

        return new CPDescr().getCPDescr(index);
    }

        private static enum Descr_NameAndType {

        RAW(1), FIELD(2), METHOD(3);
        private final int enum_value;

        Descr_NameAndType(final int value) {
            this.enum_value = value;
        }

        public int value() {
            return this.enum_value;
        }
    }
        
        private class CPDescr {

        CPDescr() {
        }

        public String getCPDescr(final int index) {
            final StringBuilder sb = new StringBuilder(40);

            switch (ClassReader.this.constant_pools[index].getTag()) {
                case AbstractCPInfo.CONSTANT_Utf8:
                    sb.append("Utf8: ");
                    sb.append(this.getDescr_Utf8((ConstantUtf8Info) ClassReader.this.constant_pools[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Integer:
                    sb.append("Integer: ");
                    sb.append(this.getDescr_Integer((ConstantIntegerInfo) ClassReader.this.constant_pools[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Float:
                    sb.append("Float: ");
                    sb.append(this.getDescr_Float((ConstantFloatInfo) ClassReader.this.constant_pools[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Long:
                    sb.append("Long: ");
                    sb.append(this.getDescr_Long((ConstantLongInfo) ClassReader.this.constant_pools[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Double:
                    sb.append("Double: ");
                    sb.append(this.getDescr_Double((ConstantDoubleInfo) ClassReader.this.constant_pools[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Class:
                    sb.append("Class: ");
                    sb.append(this.getDescr_Class((ConstantClassInfo) ClassReader.this.constant_pools[index]));
                    break;
                case AbstractCPInfo.CONSTANT_String:
                    sb.append("String: ");
                    sb.append(this.getDescr_String((ConstantStringInfo) ClassReader.this.constant_pools[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Fieldref:
                    sb.append("Fieldref: ");
                    sb.append(this.getDescr_Fieldref((ConstantFieldrefInfo) ClassReader.this.constant_pools[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Methodref:
                    sb.append("Methodref: ");
                    sb.append(this.getDescr_Methodref((ConstantMethodrefInfo) ClassReader.this.constant_pools[index]));
                    break;
                case AbstractCPInfo.CONSTANT_InterfaceMethodref:
                    sb.append("InterfaceMethodref: ");
                    sb.append(this.getDescr_InterfaceMethodref((ConstantInterfaceMethodrefInfo) ClassReader.this.constant_pools[index]));
                    break;
                case AbstractCPInfo.CONSTANT_NameAndType:
                    sb.append("NameAndType: ");
                    sb.append(this.getDescr_NameAndType(
                            (ConstantNameAndTypeInfo) ClassReader.this.constant_pools[index],
                            ClassReader.Descr_NameAndType.RAW));
                    break;
                default:
                    sb.append("!!! Un-supported CP type.");
                    break;
            }

            return sb.toString();
        }

        private String getDescr_Utf8(final ConstantUtf8Info info) {
            return info.getValue();
        }

        private String getDescr_Integer(final ConstantIntegerInfo info) {
            return String.valueOf(info.getValue());
        }

        private String getDescr_Float(final ConstantFloatInfo info) {
            return String.valueOf(info.getValue());
        }

        private String getDescr_Long(final ConstantLongInfo info) {
            return String.valueOf(info.getValue());
        }

        private String getDescr_Double(final ConstantDoubleInfo info) {
            return String.valueOf(info.getValue());
        }

        private String getDescr_Class(final ConstantClassInfo info) {
            // The value of the name_index item must be a valid index into the constant_pool table. 
            // The constant_pool entry at that index must be a CONSTANT_Utf8_info structure 
            // representing a valid fully qualified class or interface name encoded in internal form.
            return SignatureConvertor.parseClassSignature(this.getDescr_Utf8(
                    (ConstantUtf8Info) ClassReader.this.constant_pools[info.getNameIndex()]));
        }

        private String getDescr_String(final ConstantStringInfo info) {
            // The value of the string_index item must be a valid index into the constant_pool table. 
            // The constant_pool entry at that index must be a CONSTANT_Utf8_info (.4.7) structure 
            // representing the sequence of characters to which the String object is to be initialized.
            return SignatureConvertor.parseClassSignature(this.getDescr_Utf8(
                    (ConstantUtf8Info) ClassReader.this.constant_pools[info.getStringIndex()]));
        }

        private String getDescr_Fieldref(final ConstantFieldrefInfo info) {
            return this.getDescr_ref(
                    info.getClassIndex(),
                    info.getNameAndTypeIndex(),
                    ClassReader.Descr_NameAndType.FIELD);
        }

        private String getDescr_Methodref(final ConstantMethodrefInfo info) {
            return this.getDescr_ref(
                    info.getClassIndex(),
                    info.getNameAndTypeIndex(),
                    ClassReader.Descr_NameAndType.METHOD);
        }

        private String getDescr_InterfaceMethodref(final ConstantInterfaceMethodrefInfo info) {
            return this.getDescr_ref(
                    info.getClassIndex(),
                    info.getNameAndTypeIndex(),
                    ClassReader.Descr_NameAndType.METHOD);
        }

        private String getDescr_ref(final int classindex, final int natindex, final ClassReader.Descr_NameAndType type) {
            final StringBuilder sb = new StringBuilder();
            sb.append(this.getDescr_Class((ConstantClassInfo) ClassReader.this.constant_pools[classindex]));
            sb.append(".");
            sb.append(this.getDescr_NameAndType((ConstantNameAndTypeInfo) ClassReader.this.constant_pools[natindex], type));

            return sb.toString();
        }

        private String getDescr_NameAndType(final ConstantNameAndTypeInfo info, final ClassReader.Descr_NameAndType format) {
            final StringBuilder sb = new StringBuilder();
            String type;

            sb.append(this.getDescr_Utf8((ConstantUtf8Info) ClassReader.this.constant_pools[info.getNameIndex()]));
            sb.append(", ");
            type = this.getDescr_Utf8((ConstantUtf8Info) ClassReader.this.constant_pools[info.getDescriptorIndex()]);

            switch (format) {
                case RAW:
                    sb.append(type);
                    break;

                case FIELD:
                    try {
                        sb.append("type = ");
                        sb.append(SignatureConvertor.signature2Type(type));
                    } catch (Exception ex) {
                        //Logger.getLogger(ClassReader.class.getName()).log(Level.SEVERE, null, ex);

                        sb.append(type);
                        sb.append(" !!! Un-recognized type");
                    }
                    break;

                case METHOD:
                    final StringBuilder sb_mtd = new StringBuilder();
                    try {
                        sb_mtd.append("parameter = ");
                        sb_mtd.append(SignatureConvertor.parseMethodParameters(type));
                        sb_mtd.append(", returns = ");
                        sb_mtd.append(SignatureConvertor.parseMethodReturnType(type));

                        sb.append(sb_mtd);
                    } catch (Exception ex) {
                        //Logger.getLogger(ClassReader.class.getName()).log(Level.SEVERE, null, ex);

                        sb.append(type);
                        sb.append(" !!! Un-recognized type");
                    }
                    break;
                default:
                    break;
            }

            return sb.toString();
        }
    }
}
