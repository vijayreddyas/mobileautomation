package helpers;

import java.io.FileReader;
import java.util.Properties;

public class LoadProperties {
    private static Properties properties=new Properties();
    public static String propertiesFilePath;
    /**
     *
     * @return
     */
    public static Properties getAllProperties(String filePath){
        try {
            properties.load(new FileReader(filePath));
            return properties;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProperty(String filePath, String key, String defaultValue){
        if (getAllProperties(filePath).containsKey(key))
            return getProperty(filePath, key);
        else if(!properties.containsKey(key) && (!defaultValue.equalsIgnoreCase("") || defaultValue!=""||!defaultValue.isEmpty()||defaultValue!=null))
            return defaultValue;
        else if (!properties.containsKey(key) && (defaultValue.equalsIgnoreCase("") || defaultValue==""||defaultValue.isEmpty()||defaultValue==null))
            return null;
        else
            return null;
    }

    /**
     *
     * @param key
     * @return
     */
    public static String getProperty(String filePath, String key){
        if(getAllProperties(filePath).containsKey(key))
            return getAllProperties(filePath).get(key).toString();
        else
            return null;
    }

}