package com.zhongjh.app.constant

/**
 * 文件名称
 *
 *
 * 外部存储
 * context.getExternalFilesDir(dir)	路径为:/mnt/sdcard/Android/data/<packageName>/files/…
 * context.getExternalCacheDir()	路径为:/mnt/sdcard/Android/data/<packageName>/cache/…
 * 内部存储
 * context.getFilesDir()	路径是:/data/data/<packageName>/files/…
 * context.getCacheDir()	路径是:/data/data/<packageName>/cache/…
 *
 * 直接创建文件：
 * String dirPath = context.getExternalFilesDir(null).getAbsolutePath();
 * String filePath = direPath + "/recording";
 * audioFile = new File(filePath);
 *
 * getExternalStorageDirectory 已经舍弃
 *
 *
 * @author zhongjh
 * @date 2021/5/13
 */
object FilePaths {

}