package hu.ponte.hr.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import hu.ponte.hr.repository.ImageMetaJpaRepository;
import hu.ponte.hr.services.ImageMetaStore;
import hu.ponte.hr.services.ImageStore;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.NoSuchElementException;


@Log4j2
@RestController()
@RequestMapping("api/images")
@AllArgsConstructor
public class ImagesController {

    private ImageStore imageStore;

    private ImageMetaStore imageMetaStore;


    @GetMapping("meta")
    public List<ImageMeta> listImages() {
		return imageMetaStore.findAll();
    }

    @GetMapping("preview/{id}")
    public void getImage(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        OutputStream os = response.getOutputStream();
        try {
            Image image = imageStore.findById(Long.valueOf(id));
            response.setContentLength(image.getData().length);
            os.write(image.getData(), 0, image.getData().length);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NoSuchElementException e) {
            log.error("",e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch (Exception e) {
            log.error("",e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
         finally {
            os.close();
        }
    }

}
