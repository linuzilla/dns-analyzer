package ncu.cc.digger.services;

import ncu.cc.commons.utils.MavenVersion;
import ncu.cc.digger.constants.Constants;
import org.apache.maven.model.Model;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class VersionServiceImpl implements VersionService, MavenVersion {
    private final Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);
    private final String version;

    public VersionServiceImpl() {
        String version = null;
        try {
            Model mavenVersion = getMavenVersion();
            version  = String.format("%s %s",
                    mavenVersion.getArtifactId(),
                    mavenVersion.getVersion());
            logger.info(version);
        } catch (IOException|XmlPullParserException e) {
            version = Constants.VERSION;
            logger.info(e.getMessage());
        }
        this.version = version;
    }

    @Override
    public String getVersion() {
        return version;
    }
}
