package org.androidtown.anywhere.httpcontrol;

import static org.androidtown.anywhere.httpcontrol_retrofitController.URL_setup.ROOT_URL;

/**
 * Created by gdtbg on 2017-07-22.
 */

public class ImageURLParser {

    private static final String SERVERS_PATH = ROOT_URL+"controller/resources/upload/";

    private static final String ICON_PATH = ROOT_URL+"controller/resources/upload/facility/";

    public static final String imageURLParser(String origin_url){


        return SERVERS_PATH+origin_url;

    }

    public static final String iconURLParser(String origin_url){


        return ICON_PATH+"android"+origin_url+".png";

    }



}
