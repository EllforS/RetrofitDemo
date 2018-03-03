package com.ellfors.dagger2.http.config;


import com.ellfors.dagger2.http.model.BaseCallModel;
import com.ellfors.dagger2.model.Girl;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit 构造请求Service
 */
public interface HttpApi {
    /*
     *  @GET        表示这是一个GET请求
     *  @POST       表示这个一个POST请求
     *  @PUT        表示这是一个PUT请求
     *  @DELETE     表示这是一个DELETE请求
     *  @HEAD       表示这是一个HEAD请求
     *  @OPTIONS    表示这是一个OPTION请求
     *  @PATCH      表示这是一个PAT请求
     */

    @GET("/api/data/福利/{limit}/{page}")
    Flowable<BaseCallModel<List<Girl>>> getGirlList(
            @Path("limit") int limit,
            @Path("page") int page
    );

    /**********************下面是记录***************************************/

//    /* Get请求 */
//    //第一种---直接请求---无参
//    @GET("xxx")
//    Call<TestModel> getGet();
//
//    //第二种---组合式直接请求 如/result/{id}写法如下:
//    @GET("/result/{id}")
//    Call<TestModel> getGet2(@Path("id") String id);
//
//    //第三种---有参
//    @GET("XXX")
//    Call<TestModel> getGet3(@Query("name") String name,@Query("age") String age);
//
//    /* Post请求 */
//    //第一种
//    @FormUrlEncoded
//    @POST("xxx")
//    Call<TestModel> getPost(@Field("name") String name);
//
//    //第二种
//    @FormUrlEncoded
//    @POST("XXX")
//    Call<TestModel> getPost2(@FieldMap Map<String,String> map);
//
//    //第三种---body 用于JSON上传
//    Call<TestModel> getPost3(@Body TestModel str);
//
//    //第四种---带Header
//    @FormUrlEncoded
//    @POST("/info")
//    Call<TestModel> updateInfo(
//            @Header("device") String device, @Header("version") int version, @Field("id") String id);
//
//    /* 上传文件 */
//    /**
//     * 单文件上传
//     *
//     * 此处@Part(“file\”; filename=\”avatar.png\”“)
//     * 注释的含义是该RequestBody 的名称为file，
//     * 上传的文件名称为avatar.png。
//     *  @Path 注解中的filename与上传文件的真实名称可以不匹配。
//     */
//    @POST("/file")
//    @Multipart
//    Observable<TestModel> uploadFile(@Part("file\"; filename=\"avatar.png\"") RequestBody file);
//
//    /**
//     * 多文件上传
//     *
//     * 此处使用@PartMap注释，传递多个Part，以实现多文件上传。
//     */
//    @Multipart
//    @POST("/files")
//    Call<TestModel> uploadFiles(@PartMap Map<String, RequestBody> params);
//
//    /**
//     * 混合上传
//     *
//     * 现实使用中，一般会有一种需求是，传文件的同时需要上传一些其他的字段
//     */
//    @POST("/file")
//    @Multipart
//    Observable<TestModel> uploadFile1(
//            @Part("file\"; filename=\"avatar.png\"") RequestBody file,@Part("nickName") RequestBody nickName);
//
//    /**
//     * 另一种混合上传----实际测试过,可用
//     */
//    @Multipart
//    @POST("/api/imageupload.php?type=upload")
//    Observable<String> uploadImgToMap(@Part List<MultipartBody.Part> partList);
//
//    /**
//     * java代码中的写法
//     *
//     * MediaType.parse()中可接受的格式：
//     *      multipart/form-data、image/png、image/jpg
//     */
//    File file = ExBitmapCompressUtils.compressImage("需要压缩的图片地址","压缩之后保存的位置");
//    MultipartBody.Builder builder = new MultipartBody.Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart("参数1",uid)
//            .addFormDataPart("参数2",token)
//            .addFormDataPart("服务器定义的图片字段名",file.getName(),RequestBody.create(MediaType.parse("image/png"),file));
//    //提交网络
//    RetrofitManager manager = new RetrofitManager();
//    manager.getHttpService()
//            .uploadImgToMap(builder.build().parts())
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new ProgressSubscriber<String>(mContext)
//    {
//        @Override
//        public void onNext(String s)
//        {
//            L.json(s);
//        }
//    });
}
