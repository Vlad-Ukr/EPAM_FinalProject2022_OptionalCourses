package PhotoManager;

import com.example.optionalcoursesfp.photo.PhotoManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PhotoManagerTest {
    private final String IMAGE_DIRECTORY_PATH="C:\\Users\\sasha\\apache-tomcat-9.0.58\\usersAvatars";
    @Test
    public  void getByteStringOfUploadedPhotoTest(){
        PhotoManager photoManager=new PhotoManager(IMAGE_DIRECTORY_PATH);
        String result="";
        String waited ="iVBORw0KGgoAAAANSUhEUgAAADUAAAAlCAYAAADiMKHrAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAA/SURBVFhH7c8BDQAwEMSg+Td908GnOODtoFKKUopSilKKUopSilKKUopSilKKUopSilKKUopSilKKUopShu0DZfCHH1VvmsIAAAAASUVORK5CYII=";
        try {
           result=photoManager.getByteStringOfUploadedPhoto("test.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(waited,result);
    }
}
