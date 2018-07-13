package org.androidtown.anywhere.header;

import java.util.regex.Pattern;

/**
 * Created by gdtbg on 2017-07-21.
 */

public class RegularExpression {

    public static final Pattern                                                                                                                                                                                                                                                                                                                                                                                                                        VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE); //이메일 정규식

    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM =
            Pattern.compile("^.*(?=.{4,16})(?=.*[0-9])(?=.*[a-zA-Z]).*$"); // 4자리 ~ 16자리까지 가능

    public static final Pattern VALID_ONLY_NUMBER =
            Pattern.compile("/^[0-9]+$/"); // 숫자만





}
