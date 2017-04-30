package test.puigames.courseofhistory.framework.engine.resourceloading;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Jordan on 25/10/2016.
 */

public interface FileIO
{
    InputStream readAsset(String fileName) throws IOException;

    InputStream readFile(String fileName) throws IOException;

    OutputStream writeFile(String fileName) throws IOException;
}
