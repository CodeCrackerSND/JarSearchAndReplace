package maingui;

import javax.swing.filechooser.FileFilter;
import java.io.File;


/**
 * This <code>FileFilter</code> accepts files that end in one of the given
 * extensions.
 *
 * @author Eric Lafortune
 */
final class ExtensionFileFilter extends FileFilter
{
    private final String   description;
    private final String[] extensions;


    /**
     * Creates a new ExtensionFileFilter.
     * @param description a description of the filter.
     * @param extensions  an array of acceptable extensions.
     */
    public ExtensionFileFilter(String description, String[] extensions)
    {
        this.description = description;
        this.extensions  = extensions;
    }


    // Implemntations for FileFilter

    public String getDescription()
    {
        return description;
    }


    public boolean accept(File file)
    {
        if (file.isDirectory())
        {
            return true;
        }

        String fileName = file.getName().toLowerCase();

        for (int index = 0; index < extensions.length; index++)
        {
            if (fileName.endsWith(extensions[index]))
            {
                return true;
            }
        }

        return false;
    }
}
