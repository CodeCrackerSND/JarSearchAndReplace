/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maingui;

import ClassReaderWriter.AttributeCode;
import ClassReaderWriter.ClassReader;
import ClassReaderWriter.ClassWriter;
import ClassReaderWriter.utils;
import java.io.File;
import java.util.Enumeration;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;

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
import ClassReaderWriter.ConstantPoolTypes.ConstantInvokeDynamicInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantMethodHandleInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantMethodTypeInfo;
import ClassReaderWriter.ConstantPoolTypes.ConstantNameAndTypeInfo;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Mihai
 */
public class MainJFrame extends javax.swing.JFrame {
public String originaltitle = "JarSearchAndReplace v1.0";
    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
        initComponents();
        this.setTitle(originaltitle);
        this.jTree1.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.RemoveMethods();
        if (MainJFrame.classfilename!=null&&!MainJFrame.classfilename.equals(""))
        {
            File file = new File(MainJFrame.classfilename);
            if (!file.exists()) return;
            //j.getSelectedFile();
            filePath = file.getAbsolutePath();
            File sdirectory = file.getParentFile();
            if (sdirectory.exists()&&sdirectory.isDirectory())
            Directory = sdirectory;
            LoadFile(filePath);
        }
    }
       
    public void RemoveMethods()
    {
    TreeModel def = this.jTree1.getModel();       
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)def.getRoot();
    Enumeration children = root.children();
    if (children != null) {
      while (children.hasMoreElements()) {
        DefaultMutableTreeNode mtree = (DefaultMutableTreeNode) children.nextElement();
        if (mtree.toString().equals("Methods"))
        {
        mtree.removeAllChildren();
        break;
        }

      }
    }
    }

    public void Removecps()
    {
    TreeModel def = this.jTree1.getModel();       
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)def.getRoot();
    Enumeration children = root.children();
    if (children != null) {
      while (children.hasMoreElements()) {
        DefaultMutableTreeNode mtree = (DefaultMutableTreeNode) children.nextElement();
        if (mtree.toString().equals("constant_pool"))
        {
        mtree.removeAllChildren();
        break;
        }

      }
    }
    }
    
    public void RemoveFields()
    {
    TreeModel def = this.jTree1.getModel();       
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)def.getRoot();
    Enumeration children = root.children();
    if (children != null) {
      while (children.hasMoreElements()) {
        DefaultMutableTreeNode mtree = (DefaultMutableTreeNode) children.nextElement();
        if (mtree.toString().equals("Fields"))
        {
        mtree.removeAllChildren();
        break;
        }

      }
    }
    }
    
    public void AddMethod(String methodname,int childindex)
    {
    DefaultTreeModel model = (DefaultTreeModel)this.jTree1.getModel();
    
    TreeModel def = this.jTree1.getModel();       
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)def.getRoot();
    Enumeration children = root.children();
    if (children != null) {
      while (children.hasMoreElements()) {
        DefaultMutableTreeNode mtree = (DefaultMutableTreeNode) children.nextElement();
        if (mtree.toString().equals("Methods"))
        {
        mtree.insert(new DefaultMutableTreeNode(methodname), childindex);
        model.reload(mtree);
        break;
        }

      }
    }
    }
    
    public void AddConstantPool(int cpindex)
    {
    if (clsread.constant_pools[cpindex]==null)
    return;
    
    DefaultTreeModel model = (DefaultTreeModel)this.jTree1.getModel();
    
    TreeModel def = this.jTree1.getModel();       
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)def.getRoot();
    Enumeration children = root.children();
    DefaultMutableTreeNode cptree = null;
    if (children != null) {
      while (children.hasMoreElements()) {
        DefaultMutableTreeNode current = (DefaultMutableTreeNode) children.nextElement();
        if (current.toString().equals("constant_pool"))
        {
        cptree = current;
        break;
        }

      }
    }
    if (cptree!=null)
    {
        String nodename = String.format("%d.  %s",  cpindex, clsread.constant_pools[cpindex].getName());
        DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(nodename);
        cptree.add(dmtn);
        
        AddCPChilds(dmtn,cpindex);
        model.reload(cptree);
    }
    }
    
    public void AddCPChilds(DefaultMutableTreeNode rootNode, int cpindex)
    {
        /*
        DefaultMutableTreeNode child = new DefaultMutableTreeNode("chit");
        rootNode.insert(child, 0);
        
        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("chit2");
        tree.insert(child2, 1);
        */
                
        short tag = clsread.constant_pools[cpindex].getTag();
        rootNode.add(new DefaultMutableTreeNode(
        "tag: " + tag));
                
            switch (tag) {
            case AbstractCPInfo.CONSTANT_Utf8:
                generateTreeNode(rootNode, (ConstantUtf8Info) clsread.constant_pools[cpindex]);
                break;

            case AbstractCPInfo.CONSTANT_Integer:
                generateTreeNode(rootNode, (ConstantIntegerInfo) clsread.constant_pools[cpindex]);
                break;

            case AbstractCPInfo.CONSTANT_Float:
                generateTreeNode(rootNode, (ConstantFloatInfo) clsread.constant_pools[cpindex]);
                break;

            case AbstractCPInfo.CONSTANT_Long:
                generateTreeNode(rootNode, (ConstantLongInfo) clsread.constant_pools[cpindex]);
                break;

            case AbstractCPInfo.CONSTANT_Double:
                generateTreeNode(rootNode, (ConstantDoubleInfo) clsread.constant_pools[cpindex]);
                break;

            case AbstractCPInfo.CONSTANT_Class:
                generateTreeNode(rootNode, (ConstantClassInfo) clsread.constant_pools[cpindex]);
                break;

            case AbstractCPInfo.CONSTANT_String:
                generateTreeNode(rootNode, (ConstantStringInfo) clsread.constant_pools[cpindex]);
                break;

            case AbstractCPInfo.CONSTANT_Fieldref:
                generateTreeNode(rootNode, (ConstantFieldrefInfo) clsread.constant_pools[cpindex]);
                break;

            case AbstractCPInfo.CONSTANT_Methodref:
                generateTreeNode(rootNode, (ConstantMethodrefInfo) clsread.constant_pools[cpindex]);
                break;

            case AbstractCPInfo.CONSTANT_InterfaceMethodref:
                generateTreeNode(rootNode, (ConstantInterfaceMethodrefInfo) clsread.constant_pools[cpindex]);
                break;

            case AbstractCPInfo.CONSTANT_NameAndType:
                generateTreeNode(rootNode, (ConstantNameAndTypeInfo) clsread.constant_pools[cpindex]);
                break;

            case AbstractCPInfo.CONSTANT_MethodHandle:
                generateTreeNode(rootNode, (ConstantMethodHandleInfo) clsread.constant_pools[cpindex]);
                break;
                
            case AbstractCPInfo.CONSTANT_MethodType:
                generateTreeNode(rootNode, (ConstantMethodTypeInfo) clsread.constant_pools[cpindex]);
                break;
                
            case AbstractCPInfo.CONSTANT_InvokeDynamic:
                generateTreeNode(rootNode, (ConstantInvokeDynamicInfo) clsread.constant_pools[cpindex]);
                break;
                
            default:
                // TODO: Add exception
                break;
        }
    }
   
    public void AddField(int fieldindex)
    {
    if (clsread.fields[fieldindex]==null)
    return;
    
    DefaultTreeModel model = (DefaultTreeModel)this.jTree1.getModel();
    
    TreeModel def = this.jTree1.getModel();       
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)def.getRoot();
    Enumeration children = root.children();
    DefaultMutableTreeNode cptree = null;
    if (children != null) {
      while (children.hasMoreElements()) {
        DefaultMutableTreeNode current = (DefaultMutableTreeNode) children.nextElement();
        if (current.toString().equals("Fields"))
        {
        cptree = current;
        break;
        }

      }
    }
    if (cptree!=null)
    {
        String nodename = String.format("field %d: %s", fieldindex + 1, clsread.fields[fieldindex].getDeclaration());
        DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(nodename);
        cptree.add(dmtn);
        
        AddFieldChilds(dmtn,fieldindex);
        model.reload(cptree);
    }
    }
    
    public void AddFieldChilds(DefaultMutableTreeNode rootNode, int fieldindex)
    {

        rootNode.add(new DefaultMutableTreeNode(
                "access_flags: " + clsread.fields[fieldindex].getAccessFlags() + ", " + clsread.fields[fieldindex].getModifiers()));
        rootNode.add(new DefaultMutableTreeNode(
                "name_index: " + clsread.fields[fieldindex].getNameIndex()));
        rootNode.add(new DefaultMutableTreeNode(
                "descriptor_index: " + clsread.fields[fieldindex].getDescriptorIndex()));

    }
    
private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantUtf8Info utf8Info)
{
        final int bytesLength = utf8Info.getBytesLength();
        rootNode.add(new DefaultMutableTreeNode("length: " + bytesLength));
        rootNode.add(new DefaultMutableTreeNode(
                "bytes: " + utf8Info.getValue()));
    }

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantIntegerInfo integerInfo)
{
        rootNode.add(new DefaultMutableTreeNode(
                "bytes: " + integerInfo.getValue()));
    }

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantFloatInfo floatInfo)
{
        rootNode.add(new DefaultMutableTreeNode(
                "bytes: " + floatInfo.getValue()));
    }

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantLongInfo longInfo)
{
        rootNode.add(new DefaultMutableTreeNode("value: "+longInfo.getValue()));
     }

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantDoubleInfo doubleInfo)
{
        rootNode.add(new DefaultMutableTreeNode("value: "+doubleInfo.getValue()));
}

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantClassInfo classInfo)
{
        rootNode.add(new DefaultMutableTreeNode(
                "name_index: " + classInfo.getNameIndex()));
}

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantStringInfo stringInfo)
{
        rootNode.add(new DefaultMutableTreeNode(
                "string_index: " + stringInfo.getStringIndex()));
}

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantFieldrefInfo fieldrefInfo)
{
        rootNode.add(new DefaultMutableTreeNode(
                "class_index: " + fieldrefInfo.getClassIndex()));
        rootNode.add(new DefaultMutableTreeNode(
                "name_and_type_index: " + fieldrefInfo.getNameAndTypeIndex()));
    }

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantMethodrefInfo methodrefInfo)
{
        rootNode.add(new DefaultMutableTreeNode(
                "class_index: " + methodrefInfo.getClassIndex()));
        rootNode.add(new DefaultMutableTreeNode(
                "name_and_type_index: " + methodrefInfo.getNameAndTypeIndex()));
    }

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantInterfaceMethodrefInfo interfaceMethodrefInfo)
{
        rootNode.add(new DefaultMutableTreeNode(
                "class_index: " + interfaceMethodrefInfo.getClassIndex()));
        rootNode.add(new DefaultMutableTreeNode(
                "name_and_type_index: " + interfaceMethodrefInfo.getNameAndTypeIndex()));
    }

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantNameAndTypeInfo nameAndTypeInfo)
{
        rootNode.add(new DefaultMutableTreeNode(
                "name_index: " + nameAndTypeInfo.getNameIndex()));
        rootNode.add(new DefaultMutableTreeNode(
                "descriptor_index: " + nameAndTypeInfo.getDescriptorIndex()));
}

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantMethodHandleInfo MethodHandleInfo)
{
        rootNode.add(new DefaultMutableTreeNode(
                "reference_kind: " + MethodHandleInfo.getReferenceKind()));
        rootNode.add(new DefaultMutableTreeNode(
                "reference_index: " + MethodHandleInfo.getReferenceIndex()));
}

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantMethodTypeInfo MethodTypeInfo)
{
        rootNode.add(new DefaultMutableTreeNode(
                "descriptor_index: " + MethodTypeInfo.getDescriptorIndex()));
}

private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantInvokeDynamicInfo InvokeDynamicInfo)
{
        rootNode.add(new DefaultMutableTreeNode(
                "bootstrap_method_attr_index: " + InvokeDynamicInfo.getBootStrapMethodAttrIndex()));
        rootNode.add(new DefaultMutableTreeNode(
                "name_and_type_index: " + InvokeDynamicInfo.getNameAndTypeIndex()));
}

    int methodindex;
    ClassReaderWriter.Opcode opcode = null;
    String fullbody = "";
    public void UpdateTable()
    {
DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
model.setRowCount(0);  // remove existing instructions!

        byte[] methodbytes = GetMethodBytes();
        if (methodbytes!=null)
        {
opcode = new ClassReaderWriter.Opcode();
fullbody = opcode.parseCode(methodbytes, clsread);

int size = opcode.sizes.size();
for (int j=0;j<size;j++)
model.addRow(new Object[]{opcode.positions.get(j), opcode.opcodes.get(j)});

        }
    }
    
    public byte[] GetMethodBytes()
    {
        if (clsread!=null)
        {
        if (methodindex<clsread.methods.length&&
        clsread.methods[methodindex]!=null)
        {
        try
        {
     for (int i=0;i<clsread.methods[methodindex].attributes.length;i++)
     {
if (clsread.methods[methodindex].attributes[i] instanceof AttributeCode)
{
AttributeCode atribcode = (AttributeCode)clsread.methods[methodindex].attributes[i];
return atribcode.code;
}
        }
            }
                    catch (Exception exc)
                    {
                    
                    }
        }
    }
       return null;
    }
    
    
    public void SetMethodBytes(byte[] newbytes)
    {
        if (clsread!=null)
        {
        if (methodindex<clsread.methods.length&&
        clsread.methods[methodindex]!=null)
        {
        try
        {
     for (int i=0;i<clsread.methods[methodindex].attributes.length;i++)
     {
if (clsread.methods[methodindex].attributes[i] instanceof AttributeCode)
{
AttributeCode atribcode = (AttributeCode)clsread.methods[methodindex].attributes[i];
int oldlength = atribcode.code.length;
int newlength = newbytes.length;
int newvalue = atribcode.getAttributeLength()-oldlength+newlength;
atribcode.SetAttributeLenght(newvalue);
atribcode.code = newbytes;
clsread.methods[methodindex].attributes[i] = atribcode;

UpdateTable();
return;
}
        }
            }
                    catch (Exception exc)
                    {
                    
                    }
        }
    }

    }
    

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ShowOpcode = new javax.swing.JMenuItem();
        Nop = new javax.swing.JMenuItem();
        FullBodyEditor = new javax.swing.JMenuItem();
        CopyInstructions = new javax.swing.JMenuItem();
        CopyAll = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        MenuBlockStart = new javax.swing.JMenuItem();
        MenuBlockEnd = new javax.swing.JMenuItem();
        MenuNopStartEnd = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        SearchAndReplace = new javax.swing.JMenuItem();
        Dissasemble = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        ShowOpcode.setText("Show opcode");
        ShowOpcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowOpcodeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ShowOpcode);

        Nop.setText("Nop");
        Nop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NopActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Nop);

        FullBodyEditor.setText("Full Body Editor");
        FullBodyEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FullBodyEditorActionPerformed(evt);
            }
        });
        jPopupMenu1.add(FullBodyEditor);

        CopyInstructions.setText("Copy instruction");
        CopyInstructions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CopyInstructionsActionPerformed(evt);
            }
        });
        jPopupMenu1.add(CopyInstructions);

        CopyAll.setText("Copy All");
        CopyAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CopyAllActionPerformed(evt);
            }
        });
        jPopupMenu1.add(CopyAll);
        jPopupMenu1.add(jSeparator1);

        MenuBlockStart.setText("Block start");
        MenuBlockStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuBlockStartActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MenuBlockStart);

        MenuBlockEnd.setText("Block end");
        MenuBlockEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuBlockEndActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MenuBlockEnd);

        MenuNopStartEnd.setText("Nop Start-End");
        MenuNopStartEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuNopStartEndActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MenuNopStartEnd);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Class File");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("constant_pool");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Methods");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Fields");
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Offset", "Instruction"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setComponentPopupMenu(jPopupMenu1);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(90);
        }

        jLabel1.setText("method");

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Save");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Save As...");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem5.setText("Search");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        SearchAndReplace.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        SearchAndReplace.setText("Search and replace");
        SearchAndReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchAndReplaceActionPerformed(evt);
            }
        });
        jMenu1.add(SearchAndReplace);

        Dissasemble.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        Dissasemble.setText("Dissasemble");
        Dissasemble.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DissasembleActionPerformed(evt);
            }
        });
        jMenu1.add(Dissasemble);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Exit");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    File Directory = null;
    ClassReader clsread = null;
    byte[] array;
    String filePath = "";
    
public static HashMap<String, byte[]> patches = new HashMap<String, byte[]>();
        
    final String PathSeparator = "->";
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        ExtensionFileFilter eff = new ExtensionFileFilter("java class/jar",
            new String[]{".class",".jar"});
        j.setFileFilter(eff);
        
        if (Directory!=null)
        j.setCurrentDirectory(Directory);
        
        j.setDialogTitle("Choose target java class:");
        Integer opt = j.showOpenDialog(this);

        if (opt == JFileChooser.APPROVE_OPTION)
        {
            
            File file = j.getSelectedFile();
            filePath = file.getAbsolutePath();
            File sdirectory = j.getCurrentDirectory();
            if (sdirectory.exists()&&sdirectory.isDirectory())
            Directory = sdirectory;
            LoadFile(filePath);

            patches = new HashMap<String, byte[]>();
        }    
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public void LoadFile(String filename)
    {
                
            try
            {
                
if (filePath.toLowerCase().endsWith(".jar")||filePath.toLowerCase().endsWith(".zip"))
{
List<String> list = new java.util.ArrayList<>();
        try
        {
        
        ZipFile zf = new ZipFile(filePath);
        Enumeration<? extends ZipEntry> entries = zf.entries();
        
        while (entries.hasMoreElements())
        {
        ZipEntry entry = entries.nextElement();
        if (entry == null)
        break;
        
        if (entry.getName().endsWith(".class"))
        list.add(entry.getName());
        
        }
        zf.close();
        }
        catch(Exception exc)
        {
            
        }

ClassSelector selector = new ClassSelector(this,true);
selector.Initialise("Class", list);
selector.SetSelectedName("");
selector.setVisible(true);
try
{
if (!selector.GetSelectedName().isEmpty())
{
ZipFile zf = new ZipFile(filePath);
ZipEntry zentry = zf.getEntry(selector.GetSelectedName());
InputStream instream = zf.getInputStream(zentry);
ByteArrayOutputStream bos = new ByteArrayOutputStream();
int next = instream.read();
while (next > -1)
{
    bos.write(next);
    next = instream.read();
}
bos.flush();
array = bos.toByteArray();
filePath = filePath+PathSeparator+selector.GetSelectedName();
}
}
catch(Exception exc)
{
   
}



            }
            else
            {
                    File file = new File(filename);
                    array = utils.getBytesFromFile(file);
            }
                    clsread = new ClassReader();
                    boolean isok = clsread.init(array);
                    if (!isok)
                    {

                    clsread = null;
                    }
                    else
                    {
DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
model.setRowCount(0);  // remove existing instructions!
jLabel1.setText("");

                    this.Removecps();
                    if (clsread.cp_count>0)
                    {
                    for (int i = 0; i < clsread.cp_count; i++)
                    {
                    this.AddConstantPool(i);
                    }
                    }
                    this.RemoveMethods();
                    if (clsread.methods_count>0)
                    {
                    for (int i = 0; i < clsread.methods_count; i++)
                    {
                    String methodname = String.format("%d: %s", i + 1, clsread.methods[i].getDeclaration());
                    this.AddMethod(methodname, i);
                    }
                    }
                    
                    this.RemoveFields();
                    if (clsread.fields_count>0)
                    {
                    for (int i = 0; i < clsread.fields_count; i++)
                    {
                    this.AddField(i);
                    }
                    }
                    this.setTitle(originaltitle+" file: "+filePath);
                    }
            }
            catch (Exception ex)
            {
            clsread = null;
            //System.out.println(ex.toString());
            }
    }
    
    public static void CopyZipEntries(String injarpath,String outfile,
                String entryname, byte[] classbytes)
    {
    
        try
        {
        
        ZipFile zf = new ZipFile(injarpath);
        Enumeration<? extends ZipEntry> entries = zf.entries();
        
        FileOutputStream os = new FileOutputStream(new File(outfile));
        ZipOutputStream jos = new ZipOutputStream(os);
                
        while (entries.hasMoreElements())
        {
        ZipEntry entry = entries.nextElement();
        if (entry == null)
        break;
               
InputStream instream = zf.getInputStream(entry);
ByteArrayOutputStream bos = new ByteArrayOutputStream();
int next = instream.read();
while (next > -1)
{
    bos.write(next);
    next = instream.read();
}
bos.flush();
byte[] entrybytes = bos.toByteArray();
        
        if (entry.getName().equals(entryname))
        entrybytes =  classbytes;
        
        if (patches.containsKey(entry.getName()))
        entrybytes =  patches.get(entry.getName());

            ZipEntry destEntry = new ZipEntry(entry.getName());
            jos.putNextEntry(destEntry);
            jos.write(entrybytes);
            jos.closeEntry();
    }

        // Finaly close:
        jos.closeEntry();
        jos.close();
        zf.close();
        os.close();
        
        
        }
        catch(Exception exc)
        {

        }
        
    
}
    
    public String GetFileName(String inputStr)
    {
    int index = inputStr.lastIndexOf("/");
    if (index==-1) index = inputStr.lastIndexOf("\\");
    
    return inputStr.substring(index+1);
    }
        
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
if (clsread != null)
{
            try
            {
String bakfilename = utils.ClasswithouthExtension(filePath)+".bak";
utils.WriteBytesToFile(bakfilename,array);

byte[] classbytes = null;

if (patches.containsKey(filePath))
{
classbytes = patches.get(filePath);
}
else
{
clsread.MethodsFromArray();
ClassWriter clswrite = new ClassWriter();
classbytes = clswrite.GetClassBytes(clsread);
}

if (classbytes!=null&&classbytes.length>0)
{
        String saveFileName = "";
        if (filePath.contains(PathSeparator))
        {
        String JarName = filePath.substring(0, filePath.lastIndexOf(PathSeparator));
        String DirectoryName = JarName.substring(0, JarName.lastIndexOf("\\"));
        saveFileName = DirectoryName+"\\"+GetFileName(filePath);
        }
        else
        {
        saveFileName = filePath;
        }
        
utils.WriteBytesToFile(saveFileName,classbytes);  // write the new file!
}
else
{
JOptionPane.showMessageDialog(null,
"Error while writting the class!", "Fatal error!", JOptionPane.ERROR_MESSAGE);

}

            }
            catch (Exception ex)
            {

            }
}
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
if (clsread != null)
{
            try
            {
clsread.MethodsFromArray();
     
ClassWriter clswrite = new ClassWriter();
byte[] classbytes = clswrite.GetClassBytes(clsread);
if (classbytes.length>0)
{
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        ExtensionFileFilter eff = new ExtensionFileFilter("java class",
        new String[]{".class"});
        j.setFileFilter(eff);
        
        if (Directory!=null)
        j.setCurrentDirectory(Directory);
        
        j.setDialogTitle("Choose new java class file name:");
        Integer opt = j.showSaveDialog(this);

        String oldPath = filePath;
        if (opt == JFileChooser.APPROVE_OPTION)
        {
            File file = j.getSelectedFile();
            filePath = file.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".class")&&
            !filePath.toLowerCase().endsWith(".jar")&&!filePath.toLowerCase().endsWith(".zip"))
            filePath = filePath+".class";
                 
            this.setTitle(originaltitle+" file: "+filePath);
            
        try
        {

        if (oldPath.contains(PathSeparator)&&
        (filePath.toLowerCase().endsWith(".jar")||filePath.toLowerCase().endsWith(".zip")))
        {
        String old_jar_name = oldPath.substring(0,oldPath.lastIndexOf(PathSeparator));
        String entryname = oldPath.substring(oldPath.lastIndexOf(PathSeparator)+2);
        CopyZipEntries(old_jar_name,filePath, entryname, classbytes);
        filePath = filePath+PathSeparator+entryname;
        this.setTitle(originaltitle+" file: "+filePath);  
        }
        else
        {
        if (patches.containsKey(oldPath))
        classbytes =  patches.get(oldPath);
        utils.WriteBytesToFile(filePath,classbytes);  // write the new file!
        }
        }
        catch(Exception ex)
        {
                        
        }
       
        }

}
else
{
JOptionPane.showMessageDialog(null,
"Error while writting the class!", "Fatal error!", JOptionPane.ERROR_MESSAGE);

}

            }
            catch (Exception ex)
            {

            }
}
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        // TODO add your handling code here:
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        jTree1.getLastSelectedPathComponent();
        
        /* if nothing is selected */ 
        if (node == null) return;
 
        if (node.isLeaf())  // if node has no child!
        {
        TreeNode parent = node.getParent();
        DefaultMutableTreeNode parentnode =
        (DefaultMutableTreeNode) parent;
        String parentname = (String)parentnode.getUserObject();
        if (parentname.equals("Methods"))
        {
        BlockStart = -1;
        BlockEnd = -1;
        String selmethod = (String)node.getUserObject();
        if (selmethod!=null&&!selmethod.equalsIgnoreCase(""))
        {
        this.jLabel1.setText(selmethod);
        }
        methodindex = parent.getIndex(node);
        this.UpdateTable();
        }


        }

    }//GEN-LAST:event_jTree1ValueChanged

    public void UpdateMethod(String opcodestr)
    {
    byte[] newbytes = utils.BytesFromString(opcodestr);
    
    if (selected>=0)
    {
        byte[] methodbytes = GetMethodBytes();
        if (methodbytes!=null)
        {
        int position = (int)opcode.positions.get(selected);
        int size = (int)opcode.sizes.get(selected);
        int newmethodlength = methodbytes.length-size+newbytes.length;
        byte[] newmethodbody = new byte[newmethodlength];
        int i=0;
        for (i=0;i<position;i++) // copy old bytes
        newmethodbody[i]=methodbytes[i];
        
        int j;
        for (j=0;j<newbytes.length;j++) // copy new bytes
        newmethodbody[i+j]=newbytes[j];
        
        int l=0;
        for (int k=i+size;k<methodbytes.length;k++) // copy old bytes
        {
        newmethodbody[i+j+l]=methodbytes[k];
        l++;
        }
        SetMethodBytes(newmethodbody);
        }
        }
    
    }   
    
    public void FullUpdateMethod(String opcodestr)
    {
    byte[] newbytes = utils.BytesFromString(opcodestr);
    SetMethodBytes(newbytes);
    
    } 
    
    public void NopMethod()
    {  
    int cselected = this.jTable1.getSelectedRow();
            
    if (cselected>=0)
    {
        byte[] methodbytes = GetMethodBytes();
        if (methodbytes!=null)
        {
        if (opcode!=null)
        {
        selected = cselected;
        int position = (int)opcode.positions.get(selected);
        int size = (int)opcode.sizes.get(selected);
        
        int i=0;
        for (i=0;i<size;i++)
        methodbytes[i+position]=00; // nop instructions
        
        SetMethodBytes(methodbytes);
        }
        }
        }
    }
    
    SimpleOpcodeEditor soe = null;
    int selected = 0;
    private void ShowOpcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowOpcodeActionPerformed
        // TODO add your handling code here:
    onShowOpcode();
    }//GEN-LAST:event_ShowOpcodeActionPerformed

    public void onShowOpcode()
    {
        int cselected = this.jTable1.getSelectedRow();
        
        if (soe==null)
        soe = new SimpleOpcodeEditor();
        
        if (cselected>=0)
        {
        if (opcode!=null)
        {
        if (soe!=null)
        {
        byte[] methodbytes = GetMethodBytes();
        if (methodbytes!=null)
        {
        selected = cselected;
        int position = (int)opcode.positions.get(cselected);
        int size = (int)opcode.sizes.get(cselected);
        byte[] selectedopcode = new byte[size];
        for (int i=0;i<size;i++)
        selectedopcode[i]=methodbytes[i+position];
        
        String opcodestr = utils.getByteDataHexView(selectedopcode);
        soe.mainframe = this;
        soe.setTitle(soe.originaltitle+" at offset "+Integer.toString(position));
        soe.SetText(opcodestr);
        soe.setVisible(true);

        
        }
        }
        }
        
        }
    }

    FullOpcodeEditor foe;
    public void onFullOpcode()
    {        
        if (foe==null)
        foe = new FullOpcodeEditor();
        
        if (foe!=null)
        {
        byte[] methodbytes = GetMethodBytes();
        if (methodbytes!=null)
        {        
        String opcodestr = utils.getByteDataHexView(methodbytes);
        foe.mainframe = this;
        foe.setTitle(foe.originaltitle);
        foe.SetText(opcodestr);
        foe.setVisible(true);
        
        }
        }

        
        
    }
    
    
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2)
        {
         onShowOpcode();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void CopyInstructionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopyInstructionsActionPerformed
        // TODO add your handling code here:
        int selected = this.jTable1.getSelectedRow();
        if (selected>=0)
        {
        Object element = this.jTable1.getValueAt(selected, 1);
        if (element instanceof String)
        {
        String tobeset = (String)element;
        TextTransfer trans = new TextTransfer();
        trans.setClipboardContents(tobeset);
        }
        }

    }//GEN-LAST:event_CopyInstructionsActionPerformed

    private void FullBodyEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FullBodyEditorActionPerformed
        // TODO add your handling code here:
        onFullOpcode();
    }//GEN-LAST:event_FullBodyEditorActionPerformed

    private void NopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NopActionPerformed
        // TODO add your handling code here:
        NopMethod();
    }//GEN-LAST:event_NopActionPerformed

    private void CopyAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopyAllActionPerformed
        // TODO add your handling code here:
        if (!fullbody.isEmpty())
        {
        TextTransfer trans = new TextTransfer();
        trans.setClipboardContents(fullbody);
        }
    }//GEN-LAST:event_CopyAllActionPerformed
    SearchMemberName smn;
    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        if (smn==null)
        smn = new SearchMemberName();
        
        if (smn!=null)
        {
        smn.mainframe = this;
        smn.setTitle(smn.originaltitle);
        smn.setVisible(true);

        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    
    int BlockStart = -1;
    int BlockEnd = -1;
    private void MenuBlockStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuBlockStartActionPerformed
        // TODO add your handling code here:
    BlockStart = this.jTable1.getSelectedRow();
    }//GEN-LAST:event_MenuBlockStartActionPerformed

    private void MenuBlockEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuBlockEndActionPerformed
        // TODO add your handling code here:
    BlockEnd = this.jTable1.getSelectedRow();
    }//GEN-LAST:event_MenuBlockEndActionPerformed

    private void MenuNopStartEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuNopStartEndActionPerformed
        // TODO add your handling code here:
        
    if (BlockStart>=0&&BlockEnd>=0)
    {
        int keeper = 0;
        if (BlockStart>BlockEnd)
        {  // switch these two variables!
        keeper = BlockStart;
        BlockStart = BlockEnd;
        BlockEnd = keeper;
        }  
        
        byte[] methodbytes = GetMethodBytes();
        if (methodbytes!=null)
        {
        if (opcode!=null)
        {
        int positionStart = (int)opcode.positions.get(BlockStart);
        int positionEnd = (int)opcode.positions.get(BlockEnd);
        
        int end_size = (int)opcode.sizes.get(BlockEnd); // including margins!
        int size = (positionEnd-positionStart)+end_size;  // including margins!
        
        for (int i=0;i<size;i++)
        methodbytes[i+positionStart]=00; // nop instructions
        
        SetMethodBytes(methodbytes);
        }
        }
        }
    }//GEN-LAST:event_MenuNopStartEndActionPerformed

    SearchAndReplaceFrame srf;
    private void SearchAndReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchAndReplaceActionPerformed
        // TODO add your handling code here:
        
        if (srf==null)
        srf = new SearchAndReplaceFrame();
        
        if (srf!=null)
        {
        String jar_path = "";
        String class_path = filePath;
        if (filePath.contains(PathSeparator))
        {
        String[] splited = filePath.split(PathSeparator);
        jar_path = splited[0];
        if (splited.length>1)
        class_path = splited[1];
        }
        srf.SetJarPath(jar_path);
        srf.SetClassPath(class_path);
        srf.setVisible(true);
        }
    }//GEN-LAST:event_SearchAndReplaceActionPerformed

    DissasembleFrame disf;
    private void DissasembleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DissasembleActionPerformed
        // TODO add your handling code here:
        if (disf==null)
        disf = new DissasembleFrame();
        
        if (disf!=null)
        {
        String jar_path = "";
        String class_path = filePath;
        if (filePath.contains(PathSeparator))
        {
        String[] splited = filePath.split(PathSeparator);
        jar_path = splited[0];
        if (splited.length>1)
        class_path = splited[1];
        }
        disf.SetJarPath(jar_path);
        disf.SetClassPath(class_path);
        disf.setVisible(true);
        }

    }//GEN-LAST:event_DissasembleActionPerformed

      public DefaultMutableTreeNode searchNode(String nodeStr) {
    DefaultMutableTreeNode node = null;
    TreeModel def = this.jTree1.getModel();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)def.getRoot();
    Enumeration e = root.breadthFirstEnumeration();
    while (e.hasMoreElements()) {
      node = (DefaultMutableTreeNode) e.nextElement();
      if (node.getUserObject().toString().contains(nodeStr)) {
        return node;
      }
    }
    return null;
  }
      
    public DefaultMutableTreeNode searchNextNode(String nodeStr) {
DefaultMutableTreeNode selectednode = null;
if (this.jTree1.getSelectionPath()!=null)
selectednode =(DefaultMutableTreeNode)this.jTree1.getSelectionPath().getLastPathComponent();

    if (selectednode==null) return searchNode(nodeStr);
    
    DefaultMutableTreeNode node = null;
    TreeModel def = this.jTree1.getModel();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)def.getRoot();
    Enumeration e = root.breadthFirstEnumeration();
    boolean isfinded = false;
    while (e.hasMoreElements()) {
      node = (DefaultMutableTreeNode) e.nextElement();

      if (isfinded&&node.getUserObject().toString().contains(nodeStr)) {
        return node;
      }
      
      if (node==selectednode)
      isfinded = true;
      
    }
    return null;
  }
    public void SetNode(DefaultMutableTreeNode node) {
    TreeModel def = this.jTree1.getModel();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)def.getRoot();
    DefaultTreeModel m_model = new DefaultTreeModel(root);
    TreeNode[] nodes = m_model.getPathToRoot(node);
    TreePath path = new TreePath(nodes);
    this.jTree1.scrollPathToVisible(path);
    this.jTree1.setSelectionPath(path);
    }
    
    public static String classfilename = "";
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        classfilename = "";
        if (args.length>0)
        classfilename = args[0];
        //for (String s: args) {
        //  System.out.println(s);
        //}
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
boolean test1 = ClassReaderWriter.Opcode.IsDecimalNumber("65535ABC");
        
String test = ClassReaderWriter.Opcode.UshortToHexString("65535");
String test2 = ClassReaderWriter.Opcode.GetValueFromString("as=1","as=");
int kaka = 1;
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem CopyAll;
    private javax.swing.JMenuItem CopyInstructions;
    private javax.swing.JMenuItem Dissasemble;
    private javax.swing.JMenuItem FullBodyEditor;
    private javax.swing.JMenuItem MenuBlockEnd;
    private javax.swing.JMenuItem MenuBlockStart;
    private javax.swing.JMenuItem MenuNopStartEnd;
    private javax.swing.JMenuItem Nop;
    private javax.swing.JMenuItem SearchAndReplace;
    private javax.swing.JMenuItem ShowOpcode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables



}
