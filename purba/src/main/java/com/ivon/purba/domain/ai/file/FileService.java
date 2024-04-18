package com.ivon.purba.domain.ai.file;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileService {
    String storeFile(MultipartFile file);
}
