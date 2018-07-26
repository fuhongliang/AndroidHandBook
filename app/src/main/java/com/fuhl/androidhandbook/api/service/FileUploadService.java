package com.fuhl.androidhandbook.api.service;

import com.fuhl.androidhandbook.api.BaseEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author tony
 * @date 2018/7/17
 */
public interface FileUploadService {
    @Multipart
    @POST("files/avatars")
    public abstract Observable<BaseEntity<FileModel>> uploads(@Part() MultipartBody.Part file);

    class FileModel {

        /**
         * filename : 23370e2c8b1866e87cc66a0930d6aa47.pdf
         * size : 176909
         * url : https://s3-ap-southeast-1.amazonaws.com/88-upload-qa/user/23370e2c8b1866e87cc66a0930d6aa47.pdf
         */

        private String filename;
        private int size;
        private String url;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
