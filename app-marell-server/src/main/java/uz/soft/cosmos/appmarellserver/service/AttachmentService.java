package uz.soft.cosmos.appmarellserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.soft.cosmos.appmarellserver.entity.Attachment;
import uz.soft.cosmos.appmarellserver.entity.AttachmentContent;
import uz.soft.cosmos.appmarellserver.repository.AttachmentContentRepository;
import uz.soft.cosmos.appmarellserver.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by Sherlock on 15.04.2020.
 */
@Service
public class AttachmentService {
    @Autowired
    private AttachmentContentRepository attachmentContentRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;

    @Transactional
    public Attachment saveFile(MultipartHttpServletRequest request) {
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        Attachment image = new Attachment();

//        String oldAttachmentId = request.getParameter("oldAttachmentId")!=null?request.getParameter("oldAttachmentId"):"";

        while (itr.hasNext()) {
            try {
                mpf = request.getFile(itr.next());
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                image.setName(UUID.randomUUID().toString());
                image.setOriginalName(mpf.getOriginalFilename());
                image.setSize(mpf.getSize());
                image.setContentType(mpf.getContentType());
                outputStream.close();
                image = attachmentRepository.save(image);


                AttachmentContent content = new AttachmentContent();
                content.setContent(mpf.getBytes());
                content.setAttachment(image);
                attachmentContentRepository.save(content);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void getFile(HttpServletResponse response, String id) {
        try {
            AttachmentContent file = attachmentContentRepository.getByAttachment(attachmentRepository.getOne(UUID.fromString(id)));
            response.setContentType(file.getAttachment().getContentType());
//            response.setHeader("Content-disposition", "attachment; filename=\"" + file.getAttachment().getOriginalName() + "\"");
            FileCopyUtils.copy(file.getContent(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
