package eme.properties;

/**
 * This class manages the extraction properties in the user.properties file.
 * @author Timur Saglam
 */
public class ExtractionProperties extends AbstractProperties { // TODO (MEDIUM) add setters
    private static final String FILE_COMMENT = "Use this file to configure the Ecore metamodel extraction.";
    private static final String FILE_NAME = "user.properties";

    public static void main(String[] args) {
        new ExtractionProperties(); // TODO (LOW) remove main method.
    }

    /**
     * Basic constructor. Calls the super class constructor which manages the file.
     */
    public ExtractionProperties() {
        super(FILE_NAME, FILE_COMMENT);
    }

    /**
     * Returns the value of the property DefaultPackageName.
     * @return the value.
     */
    public String getDefaultPackageName() {
        return properties.getProperty("DefaultPackageName", "DEFAULT");
    }

    /**
     * Returns the value of the property ExtractAbstractMethods.
     * @return the value.
     */
    public boolean getExtractAbstractMethods() {
        return Boolean.parseBoolean(properties.getProperty("ExtractAbstractMethods", "false"));
    }

    /**
     * Returns the value of the property ExtractEmptyPackages.
     * @return the value.
     */
    public boolean getExtractEmptyPackages() {
        return Boolean.parseBoolean(properties.getProperty("ExtractEmptyPackages", "true"));
    }

    /**
     * Returns the value of the property ExtractNestedTypes.
     * @return the value.
     */
    public boolean getExtractNestedTypes() {
        return Boolean.parseBoolean(properties.getProperty("ExtractNestedTypes", "true"));
    }

    /**
     * Returns the value of the property ExtractStaticAttributes.
     * @return the value.
     */
    public boolean getExtractStaticAttributes() {
        return Boolean.parseBoolean(properties.getProperty("ExtractStaticAttributes", "false"));
    }

    /**
     * Returns the value of the property ExtractStaticMethods.
     * @return the value.
     */
    public boolean getExtractStaticMethods() {
        return Boolean.parseBoolean(properties.getProperty("ExtractStaticMethods", "false"));
    }

    /**
     * Returns the value of the property SavingStrategy.
     * @return the value.
     */
    public String getSavingStrategy() {
        return properties.getProperty("SavingStrategy", "OutputProject");
    }

    @Override
    protected void setDefaultValues() {
        properties.setProperty("DefaultPackageName", "DEFAULT");
        properties.setProperty("ExtractEmptyPackages", "true");
        properties.setProperty("ExtractNestedTypes", "true");
        properties.setProperty("ExtractAbstractMethods", "false");
        properties.setProperty("ExtractStaticMethods", "false");
        properties.setProperty("ExtractStaticAttributes", "false");
        properties.setProperty("SavingStrategy", "OutputProject");
    }
}