package ClassReaderWriter;

import java.io.IOException;
import ClassReaderWriter.PosDataInputStream;

/**
 * Direct superinterfaces of this {@code class} or {@code interface} type.
 * It is the {@code interfaces[]} in {@code ClassFile} structure.
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see ClassFile#getInterfaces()
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#74353">
 * VM Spec: The ClassFile Structure
 * </a>
 */
public class Interface extends U2ClassComponent {

    private Interface() {
    }

    public Interface(final PosDataInputStream posDataInputStream)
            throws IOException {
        super(posDataInputStream);
    }
}
