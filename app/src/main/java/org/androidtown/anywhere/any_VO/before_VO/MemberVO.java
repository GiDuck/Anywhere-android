package org.androidtown.anywhere.any_VO.before_VO;

import java.util.Date;

/**
 * Created by user on 2017-07-17.
 */

public class MemberVO {

    public String member_email;
    private String member_nick;
    private String member_password;
    private Date member_date;
    private int member_division;
    private String member_business_num;
    private String member_business_name;
    private String member_business_phone;
    private int member_warning;
    private int member_black;


    public String getMember_email() {
        return member_email;
    }
    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }
    public String getMember_nick() {
        return member_nick;
    }
    public void setMember_nick(String member_nick) {
        this.member_nick = member_nick;
    }
    public String getMember_password() {
        return member_password;
    }
    public void setMember_password(String member_password) {
        this.member_password = member_password;
    }
    public Date getMember_date() {
        return member_date;
    }
    public void setMember_date(Date member_date) {
        this.member_date = member_date;
    }
    public int getMember_division() {
        return member_division;
    }
    public void setMember_division(int member_division) {
        this.member_division = member_division;
    }
    public String getMember_business_num() {
        return member_business_num;
    }
    public void setMember_business_num(String member_business_num) {
        this.member_business_num = member_business_num;
    }
    public String getMember_business_name() {
        return member_business_name;
    }
    public void setMember_business_name(String member_business_name) {
        this.member_business_name = member_business_name;
    }
    public String getMember_business_phone() {
        return member_business_phone;
    }
    public void setMember_business_phone(String member_business_phone) {
        this.member_business_phone = member_business_phone;
    }
    public int getMember_warning() {
        return member_warning;
    }
    public void setMember_warning(int member_warning) {
        this.member_warning = member_warning;
    }
    public int getMember_black() {
        return member_black;
    }
    public void setMember_black(int member_black) {
        this.member_black = member_black;
    }


}
