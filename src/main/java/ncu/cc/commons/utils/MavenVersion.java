package ncu.cc.commons.utils;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;

public interface MavenVersion {
    default Model getMavenVersion() throws IOException, XmlPullParserException {
        return new MavenXpp3Reader().read(new FileReader("pom.xml"));
    }
}
