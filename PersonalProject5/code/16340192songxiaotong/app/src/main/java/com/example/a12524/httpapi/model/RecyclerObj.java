package com.example.a12524.httpapi.model;

public class RecyclerObj {
    private boolean status;
    public Data data;
    public static class Data  {
        private int aid;
        private int state;
        private String cover;
        private String title;
        private String content;
        private int play;
        private String duration;
        private int video_review;
        private String create;
        private String rec;
        private int count;
        public int getAid(){ return aid; }
        public int getState(){ return state; }
        public int getPlay(){ return play; }
        public int getVideo_review(){ return video_review; }
        public int getCount(){ return count; }
        public String getCover(){ return cover; }
        public String getTitle(){ return title; }
        public String getContent(){ return content; }
        public String getDuration(){ return duration; }
        public String getCreate(){ return create; }
        public String getRec(){ return rec; }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setState(int state) {
            this.state = state;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setCreate(String create) {
            this.create = create;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setPlay(int play) {
            this.play = play;
        }

        public void setRec(String rec) {
            this.rec = rec;
        }

        public void setVideo_review(int video_review) {
            this.video_review = video_review;
        }
    }


    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
