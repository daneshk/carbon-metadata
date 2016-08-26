package org.wso2.carbon.metadata.solr.store.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EmbeddedSolrClient extends AbstractSolrClient {
    private static final Log log = LogFactory.getLog(SolrClient.class);
    private static final String SOLR_CONFIG_FILES_CONTAINER = "solr_configuration_files.properties";
    private static final String DEFAULT_SOLR_SERVER_CORE = "registry-indexing";
    private static final String SOLR_HOME_SYSTEM_PROPERTY = "solr.solr.home";
    // Constant to identify the file path. file maps to this value should go under home directory
    private static final String SOLR_HOME = "home/";
    // Constant to identify the file path. file maps to this value should go under home/core directory
    private static final String SOLR_CORE = "home/core/";
    // Constant to identify the file path. file maps to this value should go under home/core/conf/lang directory
    private static final String SOLR_CONF_LANG = "home/core/conf/lang";
    // Solr core properties filename
    private static final String CORE_PROPERTIES = "core.properties";
    private File solrHome, confDir, langDir;
    private String solrCore = null;
    private Map<String, String> filePathMap = new HashMap<String, String>();

    public EmbeddedSolrClient() throws IOException {
        super();
    }


    public void initialize() throws IOException {

        // Default solr core is set to registry-indexing
        solrCore = DEFAULT_SOLR_SERVER_CORE;
        if (log.isDebugEnabled()) {
            log.debug("Solr server core is set as: " + solrCore);
        }

        // Create the solr home path defined in SOLR_HOME_FILE_PATH : carbon_home/repository/conf/solr
        solrHome = new File("/home/daneshk/solr");
        if (!solrHome.exists() && !solrHome.mkdirs()) {
            throw new IOException("Solr home directory could not be created. path: " + solrHome);
        }

        // Create the configuration folder inside solr core : carbon_home/repository/conf/solr/<solrCore>/conf
        confDir = new File(solrHome, solrCore + File.separator + "conf");
        if (!confDir.exists() && !confDir.mkdirs()) {
            throw new IOException("Solr conf directory could not be created! Path: " + confDir);
        }

        // Create lang directory inside conf to store language specific stop words
        // commons-io --> file utils
        langDir = new File(confDir, "lang");
        if (!langDir.exists() && !langDir.mkdirs()) {
            throw new IOException("Solf lang directory could not be created! Path: " + langDir);
        }

        // Read the configuration file name and there destination path and stored in filePathMap
        readConfigurationFilePaths();
        // Read the content of the files in filePathMap and copy them into destination path
        copyConfigurationFiles();
        // Set the solr home path
        System.setProperty(SOLR_HOME_SYSTEM_PROPERTY, solrHome.getPath());

        CoreContainer coreContainer = new CoreContainer(solrHome.getPath());
        coreContainer.load();
        this.solrClient = new EmbeddedSolrServer(coreContainer, solrCore);
    }

    /**
     * Reads sourceFilePath and destFilePath from solr_configuration_files.properties file
     * e.g: protwords.txt = home/core/conf
     * protword.txt is the resource file name
     * home/core/conf is destination file path, this will go to solr-home/<solr-core>/conf directory.
     * @throws IOException
     */
    private void readConfigurationFilePaths() throws IOException {
        InputStream resourceAsStream = null;
        filePathMap = new HashMap<String, String>();
        try {
            resourceAsStream = getClass().getClassLoader()
                    .getResourceAsStream(SOLR_CONFIG_FILES_CONTAINER);
            Properties fileProperties = new Properties();
            fileProperties.load(resourceAsStream);

            for (Map.Entry<Object, Object> entry : fileProperties.entrySet()) {
                if (entry.getValue() != null) {
                    String[] fileNames = entry.getValue().toString().split(",");
                    for (String fileName : fileNames) {
                        filePathMap.put(fileName, (String) entry.getKey());
                    }
                }
            }
        } finally {
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
        }
    }

    /**
     * Copy solr configuration files in resource folder to solr home folder.
     * @throws IOException
     */
    private void copyConfigurationFiles() throws IOException {
        for (Map.Entry<String, String> entry : filePathMap.entrySet()) {
            String sourceFileName = entry.getKey();
            String fileDestinationPath = entry.getValue();
            File file;

            if (SOLR_HOME.equals(fileDestinationPath)) {
                file = new File(solrHome, sourceFileName);
            } else if (SOLR_CORE.equals(fileDestinationPath)) {
                file = new File(confDir.getParentFile(), sourceFileName);
            } else if (SOLR_CONF_LANG.equals(fileDestinationPath)) {
                file = new File(langDir, sourceFileName);
            } else {
                file = new File(confDir, sourceFileName);
            }

            if (!file.exists()) {
                write2File(sourceFileName, file);
            }
        }
    }

    private void write2File(String sourceFileName, File dest) throws IOException {
        byte[] buf = new byte[1024];
        InputStream in = null;
        OutputStream out = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream(sourceFileName);
            out = new FileOutputStream(dest);

            if (CORE_PROPERTIES.equals(sourceFileName)) {
                Properties coreProperties = new Properties();
                coreProperties.load(in);
                coreProperties.setProperty("name", solrCore);
                coreProperties.store(out, null);
            } else {
                int read;
                while ((read = in.read(buf)) >= 0) {
                    out.write(buf, 0, read);
                }
            }

        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }
}
