package com.blumbit.compras_ventas.service.spec;

import java.io.File;

import com.blumbit.compras_ventas.common.dto.file.FileDownloadResponse;
import com.blumbit.compras_ventas.common.dto.file.FileRequest;
import com.blumbit.compras_ventas.common.dto.file.FileResponse;

public interface FileService {

    FileResponse createFile(FileRequest fileRequest);

    File retrieveFile(FileResponse fileResponse);

    FileDownloadResponse fileDownload(String filePath);
}
