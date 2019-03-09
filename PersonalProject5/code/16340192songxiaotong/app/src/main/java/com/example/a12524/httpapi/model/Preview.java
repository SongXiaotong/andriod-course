package com.example.a12524.httpapi.model;

import java.util.List;

public class Preview {
    private int code;
    private String message;
    private int ttl;
    public Data data;
    public static class Data{
        private String pvdata;
        private int img_x_len;
        private int img_y_len;
        private int img_x_size;
        private int img_y_size;
        private List<String> image;
        private List<Integer> index;

        public String getPvdata(){
            return pvdata;
        }

        public int getImg_x_len() {
            return img_x_len;
        }

        public int getImg_x_size() {
            return img_x_size;
        }

        public int getImg_y_len() {
            return img_y_len;
        }

        public int getImg_y_size() {
            return img_y_size;
        }

        public String getImage(int i) {
            return image.get(i);
        }
        public int getImageSize() {
            return image.size();
        }
        public List<Integer> getIndex(){
            return index;
        }
    }
    public String getPvdata(){
        return data.getPvdata();
    }

}
