package client.image;

import java.net.URL;
/**
 * <p>Title: essp system</p>
 * <p>Description: essp</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author wenhuike@wistronits.com
 * @version 1.0
 */

public class ComImage {
    //get image URL by imageName
    public static URL getImage(String imageName){
        return ComImage.class.getResource(imageName);
    }

}
