package hu.ponte.hr.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import hu.ponte.hr.services.ImageStore;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
@RestController()
@RequestMapping("api/images")
public class ImagesController {

    @Autowired
    private ImageStore imageStore;

    @GetMapping("meta")
    public Iterable<ImageMeta> listImages() {
		return imageStore.findAll();
    }

    @GetMapping("preview/{id}")
    public void getImage(@PathVariable("id") String id, HttpServletResponse response) throws IOException {

        try {
            ImageMeta image = imageStore.findById(Long.valueOf(id));
            response.setStatus(HttpServletResponse.SC_OK);
            //String json = new ObjectMapper().writeValueAsString(image);
            response.get
            response.getWriter().
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        }


        response.flushBuffer();

    }

}
