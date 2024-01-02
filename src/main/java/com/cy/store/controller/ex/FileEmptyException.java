package com.cy.store.controller.ex;

/** 上傳文件為空的異常，例如沒有選擇文件就按上傳，或是容量大小為0的檔案 */
public class FileEmptyException extends FileUploadException {
    public FileEmptyException() {
        super();
    }

    public FileEmptyException(String message) {
        super(message);
    }

    public FileEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileEmptyException(Throwable cause) {
        super(cause);
    }

    protected FileEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
