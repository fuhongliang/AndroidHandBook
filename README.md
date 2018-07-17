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

