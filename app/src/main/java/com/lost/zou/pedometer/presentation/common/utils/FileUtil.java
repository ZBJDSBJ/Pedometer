package com.lost.zou.pedometer.presentation.common.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author zoubo
 *         文件工具类
 */
public class FileUtil {
    private final static String ITEM_SPACE = "#";

    /**
     * 指定路径文件是否存在
     *
     * @param filePath
     */
    public static boolean isFileExist(String filePath) {
        boolean result = false;
        try {
            File file = new File(filePath);
            result = file.exists();
            file = null;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }


    public static void readInputStream(String filename, InputStream inputStream) {
        // 1.建立通道对象
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 2.定义存储空间
        byte[] buffer = new byte[1024];
        // 3.开始读文件
        int len = -1;
        try {
            if (inputStream != null) {
                while ((len = inputStream.read(buffer)) != -1) {
                    // 将Buffer中的数据写到outputStream对象中
                    outputStream.write(buffer, 0, len);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4.关闭流
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建文件夹
     */
    public static void createNewFile(String path) {
        File destDir = new File(path);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }

    /**
     * sd卡是否可读写
     */
    public static boolean isSDCardAvaiable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean saveByteToSDFile(byte[] byteData, String filePathName) {
        boolean result = false;
        FileOutputStream fileOutputStream = null;

        try {
            File e = createNewFile(filePathName, false);
            fileOutputStream = new FileOutputStream(e);
            fileOutputStream.write(byteData);
            fileOutputStream.flush();
            result = true;
        } catch (FileNotFoundException var19) {
            var19.printStackTrace();
        } catch (SecurityException var20) {
            var20.printStackTrace();
        } catch (IOException var21) {
            var21.printStackTrace();
        } catch (Exception var22) {
            var22.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException var18) {
                    var18.printStackTrace();
                }
            }

        }

        return result;
    }

    public static File createNewFile(String path, boolean append) {
        File newFile = new File(path);
        File e;
        if (!append) {
            if (newFile.exists()) {
                newFile.delete();
            } else {
                e = new File(path + ".png");
                if (e != null && e.exists()) {
                    e.delete();
                }
            }
        }

        if (!newFile.exists()) {
            try {
                e = newFile.getParentFile();
                if (e != null && !e.exists()) {
                    e.mkdirs();
                }

                newFile.createNewFile();
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

        return newFile;
    }


    public static final boolean saveBitmap(Bitmap bmp, String bmpName, Bitmap.CompressFormat format) {
        if (bmp == null) {
            Log.i("BitmapUtility", "save bitmap to file bmp is null");
            return false;
        } else {
            FileOutputStream stream = null;

            try {
                File e = new File(bmpName);
                boolean bCreate;
                boolean bOk;
                if (e.exists()) {
                    bCreate = e.delete();
                    if (!bCreate) {
                        Log.i("BitmapUtility", "delete src file fail");
                        return false;
                    }
                } else {
                    File bCreate1 = e.getParentFile();
                    if (bCreate1 == null) {
                        Log.i("BitmapUtility", "get bmpName parent file fail");
                        return false;
                    }

                    if (!bCreate1.exists()) {
                        bOk = bCreate1.mkdirs();
                        if (!bOk) {
                            Log.i("BitmapUtility", "make dir fail");
                            return false;
                        }
                    }
                }

                bCreate = e.createNewFile();
                if (!bCreate) {
                    Log.i("BitmapUtility", "create file fail");
                    return false;
                }

                stream = new FileOutputStream(e);
                bOk = bmp.compress(format, 100, stream);
                if (bOk) {
                    return true;
                }

                Log.i("BitmapUtility", "bitmap compress file fail");
                return false;
            } catch (Exception var20) {
                Log.i("BitmapUtility", var20.toString());
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Exception var19) {
                        Log.i("BitmapUtility", "close stream " + var19.toString());
                    }
                }

            }

            return false;
        }
    }


    /**
     * 根据给定路径参数删除单个文件的方法 私有方法，供内部其它方法调用
     *
     * @param filePath 要删除的文件路径
     * @return 成功返回true, 失败返回false
     */
    public static boolean deleteFile(String filePath) {
        // 定义返回结果
        boolean result = false;
        // //判断路径参数是否为空
        // if(filePath == null || "".equals(filePath)) {
        // //如果路径参数为空
        // System.out.println("文件路径不能为空~！");
        // } else {
        // //如果路径参数不为空
        // File file = new File(filePath);
        // //判断给定路径下要删除的文件是否存在
        // if( !file.exists() ) {
        // //如果文件不存在
        // System.out.println("指定路径下要删除的文件不存在~！");
        // } else {
        // //如果文件存在，就调用方法删除
        // result = file.delete();
        // }
        // }

        if (filePath != null && !"".equals(filePath.trim())) {
            File file = new File(filePath);
            if (file.exists()) {
                result = file.delete();
            }
        }
        return result;
    }

    public static String readFileContent(String fileName) {
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(fileName);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            Log.d("zou", " sendError The File doesn't not exist.");
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while ((line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }
                    instream.close();
                }
            } catch (FileNotFoundException e) {
                Log.d("zou", "sendError The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("zou", e.getMessage());
            }
        }
        return content;
    }

    public static String getFileName(String filePath) {
        String[] names = filePath.split("/");
        if (names != null && names.length > 0) {
            return names[names.length - 1];
        }
        return null;
    }
}
