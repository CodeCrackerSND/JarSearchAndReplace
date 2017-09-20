/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package maingui;

import ClassReaderWriter.AttributeCode;
import ClassReaderWriter.ClassReader;
import ClassReaderWriter.ClassWriter;
import ClassReaderWriter.utils;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JFileChooser;

/**
 *
 * @author Mihai
 */
public class SearchAndReplaceFrame extends javax.swing.JFrame {

    /**
     * Creates new form DissasmbleFrame
     */
    public SearchAndReplaceFrame() {
        initComponents();
        this.setTitle("Search and replace");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        StatusLab = new javax.swing.JLabel();

        jLabel1.setText("Jar name:");

        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Decompile all jar");

        jLabel2.setText("Class name:");

        jButton2.setText("...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel3.setText("Search for (instructions or hex bytes):");

        jLabel4.setText("Replace with (instructions or hex bytes):");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton4.setText("Replace");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        StatusLab.setText("Status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox1)
                                    .addComponent(jLabel1))
                                .addGap(216, 216, 216))
                            .addComponent(jTextField1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2)))
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(StatusLab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(StatusLab))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    File Directory = null;
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        ExtensionFileFilter eff = new ExtensionFileFilter("java jar",
        new String[]{".jar"});
        j.setFileFilter(eff);
        
        if (Directory!=null)
        j.setCurrentDirectory(Directory);
        
        j.setDialogTitle("Choose target jar class:");
        Integer opt = j.showOpenDialog(this);

        if (opt == JFileChooser.APPROVE_OPTION)
        {
            
            File file = j.getSelectedFile();
            String filePath = file.getAbsolutePath();
            File sdirectory = j.getCurrentDirectory();
            if (sdirectory.exists()&&sdirectory.isDirectory())
            Directory = sdirectory;
            jTextField1.setText(filePath);

        }  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

try
 {
           
if (jTextField1.getText().toLowerCase().endsWith(".jar")||
        jTextField1.getText().toLowerCase().endsWith(".zip"))
{
List<String> list = new java.util.ArrayList<>();
        try
        {
        
        ZipFile zf = new ZipFile(jTextField1.getText());
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

if (!selector.GetSelectedName().isEmpty())
jTextField2.setText(selector.GetSelectedName());

}
else
{
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        ExtensionFileFilter eff = new ExtensionFileFilter("java class",
        new String[]{".class"});
        j.setFileFilter(eff);
        
        if (Directory!=null)
        j.setCurrentDirectory(Directory);
        
        j.setDialogTitle("Choose target java class:");
        Integer opt = j.showOpenDialog(this);

        if (opt == JFileChooser.APPROVE_OPTION)
        {
            
            File file = j.getSelectedFile();
            String filePath = file.getAbsolutePath();
            File sdirectory = j.getCurrentDirectory();
            if (sdirectory.exists()&&sdirectory.isDirectory())
            Directory = sdirectory;
            jTextField2.setText(filePath);

        } 
}
}
catch(Exception exc)
{
   
}


            
    }//GEN-LAST:event_jButton2ActionPerformed

    public void SetJarPath(String jar_path)
    {
    if (jar_path!=null)
    jTextField1.setText(jar_path);
    }
    
    public void SetClassPath(String class_path)
    {
    if (class_path!=null)
    jTextField2.setText(class_path);
    }
    
ClassReaderWriter.Opcode opcode = null;
String fullbody = "";

    public String GetMethodString(ClassReader clsread,int methodindex)
    {
    
     byte[] methodbytes = GetMethodBytes(clsread, methodindex);
        if (methodbytes!=null)
        {

opcode = new ClassReaderWriter.Opcode();
fullbody = opcode.parseCode(methodbytes, clsread);

int size = opcode.sizes.size();
for (int j=0;j<size;j++)
{
// sb.append(opcode.opcodes.get(j));
// sb.append('\n');
}

        
        }
        
        return "";
    }
    
    public byte[] GetMethodBytes(ClassReader clsread,int methodindex)
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
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

g_patch_count = 0;

if (jTextField1.getText().toLowerCase().endsWith(".jar")||
        jTextField1.getText().toLowerCase().endsWith(".zip"))
{
       
    try
    {
        ZipFile zf = new ZipFile(jTextField1.getText());
        Enumeration<? extends ZipEntry> entries = zf.entries();
        
        while (entries.hasMoreElements())
        {
        ZipEntry entry = entries.nextElement();
        if (entry == null)
        break;
        
        if (entry.getName().endsWith(".class")&&(jCheckBox1.isSelected()||
            entry.getName().equals(jTextField2.getText())))
        {
ZipEntry zentry = zf.getEntry(entry.getName());
InputStream instream = zf.getInputStream(zentry);
ByteArrayOutputStream bos = new ByteArrayOutputStream();
int next = instream.read();
while (next > -1)
{
    bos.write(next);
    next = instream.read();
}
bos.flush();
byte[] array = bos.toByteArray();

ClassReader clsread = new ClassReader();
boolean isok = clsread.init(array);
if (!isok)
clsread = null;
else
PatchClass(clsread, entry.getName());

}
        }
        zf.close();

}
catch(Exception exc)
{

}

}


File file1 = new File(jTextField2.getText());
if (file1.exists())
{
   
try
{
byte[] bytes = utils.getBytesFromFile(file1);
ClassReader clsread = new ClassReader();
boolean isok = clsread.init(bytes);
if (!isok)
clsread = null;
else
PatchClass(clsread, jTextField2.getText());

}
catch(Exception exc)
{

}

}


StatusLab.setText(Integer.toString(g_patch_count)+" patched made!");
    }//GEN-LAST:event_jButton4ActionPerformed

int g_patch_count = 0;
    
private void SearchAndReplaceInstructions() {                                         
        // TODO add your handling code here:

g_patch_count = 0;

if (jTextField1.getText().toLowerCase().endsWith(".jar")||
        jTextField1.getText().toLowerCase().endsWith(".zip"))
{
       
    try
    {
        ZipFile zf = new ZipFile(jTextField1.getText());
        Enumeration<? extends ZipEntry> entries = zf.entries();
        
        while (entries.hasMoreElements())
        {
        ZipEntry entry = entries.nextElement();
        if (entry == null)
        break;
        
        if (entry.getName().endsWith(".class")&&(jCheckBox1.isSelected()||
            entry.getName().equals(jTextField2.getText())))
        {
ZipEntry zentry = zf.getEntry(entry.getName());
InputStream instream = zf.getInputStream(zentry);
ByteArrayOutputStream bos = new ByteArrayOutputStream();
int next = instream.read();
while (next > -1)
{
    bos.write(next);
    next = instream.read();
}
bos.flush();
byte[] array = bos.toByteArray();

ClassReader clsread = new ClassReader();
boolean isok = clsread.init(array);
if (!isok)
clsread = null;
else
SearchPatchClass(clsread, entry.getName());

}
        }
        zf.close();

}
catch(Exception exc)
{

}

}


File file1 = new File(jTextField2.getText());
if (file1.exists())
{
   
try
{
byte[] bytes = utils.getBytesFromFile(file1);
ClassReader clsread = new ClassReader();
boolean isok = clsread.init(bytes);
if (!isok)
clsread = null;
else
SearchPatchClass(clsread, jTextField2.getText());

}
catch(Exception exc)
{

}

}


StatusLab.setText(Integer.toString(g_patch_count)+" patched made!");
    }


    public void SearchPatchClass(ClassReader clsread, String classname)
    {

for (int i=0;i<clsread.methods.length;i++)
{
byte[] method_bytes = GetMethodBytes(clsread, i);
int patches_count = 0;

        if (method_bytes!=null)
        {

opcode = new ClassReaderWriter.Opcode();
fullbody = opcode.parseCode(method_bytes, clsread);

int size = opcode.sizes.size();
for (int j=0;j<size;j++)
{
String copcode = (String)opcode.opcodes.get(j);

if (copcode.startsWith("As"))
{

}

}
      
}
   


}



    }

byte char2byte(char input)
{
  if (input >= '0' && input <= '9')
    return (byte)(input - '0');
  if (input >= 'A' && input <= 'F')
    return (byte)(input - 'A' + 10);
  if (input >= 'a' && input <= 'f')
    return (byte)(input - 'a' + 10);
  
  // if (input == '?') return 0;
  
  return 0;
}

    
    public void PatchClass(ClassReader clsread, String classname)
    {
String tofind = jTextArea1.getText();
if (!ClassReaderWriter.Opcode.isHexadecimal(tofind))
{
tofind = ClassReaderWriter.Opcode.InstructionsToBytes(tofind, clsread);
}
else
{
tofind = tofind.replace(" ", "");
tofind = tofind.replace("\n", "");
}

String replacewith = jTextArea2.getText();
if (!ClassReaderWriter.Opcode.isHexadecimal(replacewith))
{
replacewith = ClassReaderWriter.Opcode.InstructionsToBytes(replacewith, clsread);
}
else
{
replacewith = replacewith.replace(" ", "");
replacewith = replacewith.replace("\n", "");
}

if (!tofind.isEmpty()&&!replacewith.isEmpty())
{
for (int i=0;i<clsread.methods.length;i++)
{
byte[] method_bytes = GetMethodBytes(clsread, i);
int patches_count = 0;

for (int j=0;j<method_bytes.length-(tofind.length()/2)+1;j++)
{
       boolean found = true;
       if (tofind.isEmpty()) found = false;
       
       for(int k = 0; k < tofind.length(); k+=2)
       {
           byte first_tetrade = (byte)(char2byte(tofind.charAt(k))<<4);
           byte second_tetrade = char2byte(tofind.charAt(k+1));
           
           byte first_test = (byte)(method_bytes[j+(k/2)]&0xF0);
           if (tofind.charAt(k)!='?'&&first_test!=first_tetrade)
           {
               found = false;
               break;
           }
           
           byte second_test = (byte)(method_bytes[j+(k/2)]&0x0F);
           if (tofind.charAt(k+1)!='?'&&second_test!=second_tetrade)
           {
               found = false;
               break;
           }
        }
       
       if (found)
       {
       g_patch_count++;
       patches_count++;
       
       for(int k = 0; k < replacewith.length(); k+=2)
       {
           byte first_tetrade = (byte)(char2byte(replacewith.charAt(k))<<4);
           if (replacewith.charAt(k)=='?')
           first_tetrade = (byte)(method_bytes[j+(k/2)]&0xF0);
           
           byte second_tetrade = char2byte(replacewith.charAt(k+1));
           if (replacewith.charAt(k)=='?')
           second_tetrade = (byte)(method_bytes[j+(k/2)]&0x0F);
           
           method_bytes[j+(k/2)] = (byte)(first_tetrade|second_tetrade);

        }
             
       }
    
}

if (patches_count>0)
{

try
{
SetMethodBytes(clsread, i, method_bytes);
clsread.MethodsFromArray();
ClassWriter clswrite = new ClassWriter();
byte[] classbytes = clswrite.GetClassBytes(clsread);
MainJFrame.patches.put(classname, classbytes);
}
catch(Exception exc)
{

}
}    


}

}

    }
    
    public void SetMethodBytes(ClassReader clsread, int methodindex, byte[] newbytes)
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
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchAndReplaceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchAndReplaceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchAndReplaceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchAndReplaceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchAndReplaceFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel StatusLab;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
