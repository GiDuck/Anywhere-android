package org.androidtown.anywhere.any_VO.before_VO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 2017-07-17.
 */

public class BoardVO implements Serializable {

    private String title;
    private String writer;
    private String content;
    private Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
