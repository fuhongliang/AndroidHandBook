# AndroidHandBook
整理个人工作中用到的Android 通用功能

# 网络请求
Retrofit+okhttp+RX
https://github.com/square/retrofit
http://square.github.io/retrofit/
## 使用步骤：
1. 集成
``` java
/**
 * retrofit2相关
 */
compile "io.reactivex.rxjava2:rxjava:2.1.13"
compile 'io.reactivex.rxjava2:rxandroid:2.0.2'
compile 'com.squareup.retrofit2:retrofit:2.4.0'
compile 'com.squareup.retrofit2:converter-gson:2.3.0'
compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
compile 'com.squareup.okhttp3:logging-interceptor:3.8.1'
/**
 * stetho 调试相关
 */
compile 'com.facebook.stetho:stetho:1.4.1'
compile 'com.facebook.stetho:stetho-urlconnection:1.4.1'
compile 'com.facebook.stetho:stetho-okhttp3:1.4.1'
``` 

2. 将api文件夹复制  

![图片](https://images-cdn.shimo.im/TVP1azpTmBwbp0mZ/image.png)

3. 参考下面例子进行网络请求
Json接口请求  

![图片](https://images-cdn.shimo.im/qViUTzcxc5YOD5Bq/carbon.png)

文件上传

![图片](https://images-cdn.shimo.im/SKCx8seI2yoBJiQ7/carbon_1_.png)

# [Toast提示](https://github.com/fuhongliang/AndroidHandBook/tree/master/app/src/main/java/com/fuhl/androidhandbook/toast)
Toast分为五种类型
1. 横条Toast  

![](./pic/h_toast.gif)

2. 成功Toast  

![](./pic/success_toast.gif)

3. 失败Toast  

![](./pic/fail_toast.gif)

4. 警告Toast  

![](./pic/warn_toast.gif)

5. 普通文本Toast  

![](./pic/txt_toast.gif)

可自定义高，只需要更换对应布局文件即可自定义想要的Toast效果
使用上也很方便  

``` java
 public void showSuccessToast() {
        ToastHelper.makeText("关注成功", Toast.LENGTH_SHORT,ToastHelper.SUCCESSWITHICONTOAST).show();
    }
 ``` 
 

