package org.aicool.study.interview.qianmi;

public class Message {

    private String seqNo;
    private String type;
    private String id;
    private String content;

    public Message(String seqNo, String type, String id, String content) {
        this.seqNo = seqNo;
        this.type = type;
        this.id = id;
        this.content = content;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "seqNo='" + seqNo + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
