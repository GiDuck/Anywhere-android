package org.androidtown.anywhere.httpcontrol;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.ipaulpro.afilechooser.utils.FileUtils;

import org.androidtown.anywhere.httpcontrol_retrofitController.ImageUploadController;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.androidtown.anywhere.httpcontrol_retrofitController.URL_setup.API_SUPPLIER_URL;
import static org.androidtown.anywhere.httpcontrol_retrofitController.URL_setup.API_URL;

/**
 * 이미지와 객체를 함께 전송하는 메소드
 */

public class HttpImageAndObjectUpload {

    private Retrofit retrofit; //레트로핏 객체
    private ImageUploadController apiService; //apiService 인터페이스
    private String jsonString; //json으로 전송 받은 결과를 받을 필드
    private Call<ResponseBody> call;
    private List<MultipartBody.Part> parts;
    private MultipartBody.Part part;
    Activity activity;


    private Gson gson;
    public static final String ERROR_TEXT = "error";


    public HttpImageAndObjectUpload(Activity activity) {
        this.activity = activity;

    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }


    /*--------------------------------- file 형식의 uri를 content 타입의 uri로 파싱해주는 메서드 -----------------------------*/

    public static Uri getImageContentUri(Context context, File imageFile) throws FileNotFoundException {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

        /*--------------------------------- 파일 전송을 위해 준비 작업을 하는 메서드 -----------------------------*/


    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) throws FileNotFoundException {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        File file = FileUtils.getFile(activity, fileUri); //aFileChooser 라이브러리를 사용해 파일을 들고와서 파일 객체에 저장함.

        ContentResolver cr = activity.getContentResolver(); //mime 타입 추출을 위한 메소드(getType)를 호출하기 위해 ContentResolver를 사용함.
        Uri castedUri = getImageContentUri(activity, file); //이미지 전송을 위해서는 mime 타입을 추출하는 작업이 필요한데,
        // mime 타입을 얻으려면 file path 타입의 uri 말고 content path 타입의 uri가 필요하기 때문에 메소드를 통해 캐스팅을 해줌


        Log.d("imageTest", file.getPath());


        // create RequestBody instance from file
        try {

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse(cr.getType(castedUri)), file); //RequestBody 인터페이스 타입의 변수 requestFile에, .create 메서드를 통해 리턴받은 RequestBody 추상클래스 객체를 저장함.
            // MultipartBody.Part is used to send also the actual file name

            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile); //파일 전송 포맷 객체를 생성해 리턴해줌

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }






    /*--------------------------------- 이미지와 object를 같이 전송하는 메소드 -----------------------------*/
    /*--------------------------------- 인자로 선택한 이미지 uri path 가 담긴 List를 전달 받음(file path) -----------------------------*/


    public void uploadImages(List<Uri> fileUris, String imageName, Object object) {
        retrofit = createRetrofitObject();
        apiService = createApiserverObject();
        parts = new ArrayList<>();


        for (int i = 0; i < fileUris.size(); i++) {

            try {

                parts.add(prepareFilePart("files", fileUris.get(i))); //MultipartBody.Part 클래스 타입의 ArrayList에, file uri를 인자로 넘겨주고 리턴받은 파일 전송 포맷 객체를 저장함

            } catch (Exception e) {

                e.printStackTrace();
            }

        }

        call = apiService.uploadImageAndObject(
                createPartFromString(imageName),
                parts, object);


        try {
            jsonString = new HttpImageUploadAsync().execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {
            e.printStackTrace();

        }


    }





/*    --------------------------------------------- (가게 등록)사진 여러개, 메인 사진 1개, 객체 한개 보내는 메소드 --------------------------------------  */

    public void uploadMultipleImagesAndObject(List<Uri> fileUris, Uri fileUri, String imageName, Object object) {

        parts = new ArrayList<>();

        try {
            part = prepareFilePart("main", fileUri);

            for (int i = 0; i < fileUris.size(); i++) {
                parts.add(prepareFilePart("sub", fileUris.get(i))); //MultipartBody.Part 클래스 타입의 ArrayList에, file uri를 인자로 넘겨주고 리턴받은 파일 전송 포맷 객체를 저장함
                Log.d("httpTest", fileUris.get(i).toString());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }


        call = apiService.uploadMultiImageAndObject(
                createPartFromString(imageName), parts,
                part, object);


        try {
            jsonString = new HttpImageUploadAsync().execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {
            e.printStackTrace();

        }


    }


    /*    --------------------------------------------- (가게 수정) 사진 여러개, 메인 사진 1개, 객체 한개 보내는 메소드 --------------------------------------  */

    public void updateMultipleImagesAndObject(List<Uri> fileUris, Uri fileUri, String imageName, Object object) {

        Log.d("httpTest", "" + 1);

        try {
            Log.d("httpTest", "" + 2);

            parts = new ArrayList<>();

            Log.d("httpTest", "" + 3);
            if (fileUri != null) {
                part = prepareFilePart("main", fileUri);
                Log.d("httpTest", fileUri.toString());
            }else{
                part=null;
            }

            if (fileUris.size() != 0) {
                for (int i = 0; i < fileUris.size(); i++) {
                    parts.add(prepareFilePart("sub", fileUris.get(i))); //MultipartBody.Part 클래스 타입의 ArrayList에, file uri를 인자로 넘겨주고 리턴받은 파일 전송 포맷 객체를 저장함
                }
            }else{

                parts=null;
            }
        } catch (Exception e) {

            e.printStackTrace();
            Log.d("httpTest", "error");
        }


        call = apiService.updateMultiImageAndObject(
                createPartFromString(imageName), parts,
                part, object);


        try {
            jsonString = new HttpImageUploadAsync().execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {
            e.printStackTrace();

        }


    }



   /* 스레드로 요청 작업 */


    private class HttpImageUploadAsync extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {

            try {

                String result = call.execute().body().string();

                return result;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            super.onPostExecute(jsonObject);
        }


    }

     /*  ------------------------- 레트로핏 객체를 생성하는 메소드 ---------------------------*/


    public Retrofit createRetrofitObject() {

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }
    /*  ------------------------- retrofitController 인터페이스를 생성하는 메소드 ---------------------------*/


    public ImageUploadController createApiserverObject() {
        apiService = retrofit.create(ImageUploadController.class);

        return apiService;
    }


      /*  ------------------------- 사업자 레트로핏 객체를 생성하는 메소드 ---------------------------*/


    public Retrofit createRetrofitObject_Supplier() {

        retrofit = new Retrofit.Builder()
                .baseUrl(API_SUPPLIER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }

  /*  -------------------------  하나의 문자열을 리턴하는 메소드 (key값 필요) ---------------------------*/

    public String parsingStringFunc(String key) {

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject == null) {

            } else {


                String str = jsonObject.getString(key);
                return str;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }


    public Object parsingFunc(Type type) {


        try {
            gson = new Gson();
            return gson.fromJson(jsonString, type);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }


}
