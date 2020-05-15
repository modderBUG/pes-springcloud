package me.xiaowei.modules.pes.rest;

import io.swagger.annotations.ApiOperation;
import me.xiaowei.aop.log.Log;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2019/12/2821:06
 * Version:1.0
 * Desc:
 */
@RequestMapping("/pes/api")
@RestController
public class WaterFallController {

    @Value("${server.addr}")
    String SERVER_HOST;

    @Value("${server.port}")
    String SERVER_PORT;

    @Value("${file.imgcontent}")
    String imgcontent;

    @Value("${file.imgthum}")
    String imgthum;

    @Value("${file.imgcontent2}")
    String imgcontent2;

    public volatile static Integer SINGLE_TREAD_FLAG = 0;


    @Log("获取图片")
    @ApiOperation("获取图片")
    @Deprecated
    @GetMapping("/getAllPic")
    public List<HashMap<String, String>> getAllPic(HttpServletRequest request, HttpServletResponse response) {
        String localPath = "C:\\Users\\modderBUG\\Pictures\\碧蓝航线\\pixiv";
        String serverHost = "http://"+SERVER_HOST;
        String vartaul = "/imgcontent/";
        return getPicturesByPath(request, localPath, serverHost, vartaul);
    }


    @Log("获取图片—多路径")
    @ApiOperation("获取图片—多路径")
    @Deprecated
    @GetMapping("/getAllPic2/{index}")
    public List<HashMap<String, String>> getAllPicName(HttpServletRequest request, @PathVariable("index") Integer index) {
        List<HashMap<String, String>> list = new ArrayList<>();

        System.out.println(imgcontent2.replace("\\", "\\\\"));
        list.addAll(getPicturesByPath(request, imgcontent.replace("\\", "\\\\"), SERVER_HOST, "/imgcontent/"));
//        list.addAll(getPicturesByPath(request, "F:/迅雷下载/[波萝社]BOL062夏美酱毛衣合集/", SERVER_HOST, "/imgcontent/"));
//        list.addAll(getPicturesByPath(request, "F:/迅雷下载/[PDLONE]铂金刊VOL.22/图包/", SERVER_HOST, "/imgcontent/"));
        pageSelect(list, index);
        return list;
    }

    @Log("获取图片—缓存")
    @ApiOperation("获取图片—缓存")
    @GetMapping("/getPictureThum/{index}")
    public List<HashMap<String, String>> getPictureThum(HttpServletRequest request, @PathVariable("index") Integer index) {
        List<HashMap<String, String>> list = new ArrayList<>();
        List<String> pathList = new ArrayList<>();
        pathList.add(imgcontent2.replace("\\", "\\\\"));
//        pathList.add("F:/迅雷下载/[波萝社]BOL062夏美酱毛衣合集/");
//        pathList.add("F:/迅雷下载/[PDLONE]铂金刊VOL.22/图包/");
        list.addAll(getPicturesThum(request, SERVER_HOST, "/imgcontent/", pathList, index));

        return list;
    }


    @Log("获取图片名字按索引")
    @ApiOperation("获取图片名字按索引")
    @GetMapping("/getPicturesByIndex/{index}")
    public List<HashMap<String, String>> getPicturesByIndex(HttpServletRequest request, @PathVariable("index") Integer index) {

        //String localPath="F:\\迅雷下载\\apanwonder";

        List<String> pathList = new ArrayList<>();



        System.out.println(imgcontent.replace("\\", "\\\\"));
        pathList.add(imgcontent.replace("\\", "\\\\"));

        String serverHost = SERVER_HOST;
        String vartaul = "/imgcontent/";
        //List<HashMap<String, String>> data = getPicturesByPath(request,localPath,serverHost,vartaul);
        List<HashMap<String, String>> data = getPicturesThum(request, SERVER_HOST, "/imgcontent/", pathList, index);

        //data=pageSelect(data,index);

        return data;
    }

    /**
     * 返回分页参数
     *
     * @Aothor modderBUG
     * @Param
     * @Return 对象数组
     * @Lastupdate 2019.12.29
     **/
    public List<HashMap<String, String>> pageSelect(List list, Integer index) {
        int pageSize = list.size() / 10;    //19
        int last = list.size() - pageSize * 10;  //2
        if (index == pageSize) {
            list = list.subList(index * 10, index * 10 + last);
        } else if (index < pageSize) {
            list = list.subList(index * 10, index * 10 + 10);
        } else {
            list = null;
        }
        return list;
    }

    public List<String> pageSelect2(List list, Integer index) {
        int pageSize = list.size() / 10;    //19
        int last = list.size() - pageSize * 10;  //2
        if (index == pageSize) {
            list = list.subList(index * 10, index * 10 + last);
        } else if (index < pageSize) {
            list = list.subList(index * 10, index * 10 + 10);
        } else {
            list = null;
        }
        return list;
    }


    /**
     * 给定参数，返回服务器资源地址，需要在适配器里映射虚拟路径。
     *
     * @Aothor modderBUG
     * @Param
     * @Return 对象数组
     * @Lastupdate 2019.12.29
     **/
    public List<HashMap<String, String>> getPicturesByPath(HttpServletRequest request, String localPath, String serverHost, String virtualPath) {
        List<HashMap<String, String>> list = new ArrayList<>();

        getFilesName(localPath).forEach(fileName -> {
            if (fileName != null) {
                HashMap map = new HashMap();
                map.put("src", serverHost +":"+ request.getServerPort() + virtualPath + fileName);
                map.put("href", serverHost +":"+ request.getServerPort() + virtualPath + fileName);
                map.put("info", "阿潘:" + fileName);
                list.add(map);
            }
        });
        return list;
    }


    /**
     * 对于缓存，要先获取页数，在处理图片。
     * 先索引 后缓存
     **/

    public List<HashMap<String, String>> getPicturesThum(HttpServletRequest request,
                                                         String serverHost,
                                                         String virtualPath,
                                                         List<String> pathList,
                                                         Integer index) {
        List<String> fileList = new ArrayList<>();
        pathList.forEach(path -> {
            fileList.addAll(getFilesThum(path));
        });
        //开辟新线程去生成缩略图，而不是需要加载时才生成。
        if (SINGLE_TREAD_FLAG == 0) {
            SINGLE_TREAD_FLAG = 1;
            new Thread(() -> {
                fileList.forEach(path -> {
                    String fileName = path.replace("\\", "/");
                    String fileType = path.substring(fileName.lastIndexOf("."));
                    if (!new File(imgthum.replace("\\", "\\\\") + path.hashCode() + fileType).exists()) {
                        try {
                            Thumbnails.of(path)
                                    .size(200, 300)
                                    .toFile(imgthum.replace("\\", "\\\\") + path.hashCode() + fileType);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                SINGLE_TREAD_FLAG = 0;
            }).start();
        }
        List<HashMap<String, String>> list = new ArrayList<>();
        List<String> filelist;
        if (pageSelect2(fileList, index) != null) {
            filelist = pageSelect2(fileList, index);
        } else {
            return list;
        }
        filelist.forEach(path -> {
            String fileName = path.replace("\\", "/");
            String fileType = path.substring(fileName.lastIndexOf("."));
            HashMap<String, String> map = new HashMap<>();
            try {
                if (!new File(imgthum.replace("\\", "\\\\") + path.hashCode() + fileType).exists()) {
                    Thumbnails.of(path)
                            .size(200, 300)
                            .toFile(imgthum.replace("\\", "\\\\") + path.hashCode() + fileType);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fileType.equals(".mp4") || fileType.equals(".avi") || fileType.equals(".flv") || fileType.equals(".wav") || fileType.equals(".wmv")) {
                map.put("src", serverHost +":"+ request.getServerPort() + "/imgthum/" + "000000000.jpg");
            } else {
                map.put("src", serverHost +":"+ request.getServerPort() + "/imgthum/" + path.hashCode() + fileType);
            }
            map.put("href", serverHost +":"+ request.getServerPort() + virtualPath + fileName.substring(fileName.lastIndexOf("/") + 1));
            map.put("info", "" + fileName.substring(fileName.lastIndexOf("/") + 1));
            list.add(map);

        });
        return list;
    }


    /**
     * 获取目录内全部文件名
     *
     * @Aothor modderBUG
     * @Param filePath 文件路径
     * @Return fileList 文件名列表
     * @Lastupdate 2019.12.29
     **/

    public static ArrayList<String> getFilesName(String filePath) {
        ArrayList<String> fileList = new ArrayList<String>();
        File root = new File(filePath);
        File[] files = root.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getFilesName(file.getAbsolutePath());
                    //fileList.add(file.getName());
                } else {
                    String picPathStr = file.getName();
                    fileList.add(picPathStr);
                }
            }
        }
        return fileList;
    }

    /**
     * 获取目录内全部文件路径，绝对路径 F:/File/1.jpg
     *
     * @Aothor modderBUG
     * @Param filePath 文件路径
     * @Return fileList 文件路径列表
     * @Lastupdate 2019.12.29
     **/
    public static ArrayList<String> getFiles(String filePath) {
        ArrayList<String> fileList = new ArrayList<String>();
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                /*
                 * 递归调用
                 */
                getFiles(file.getAbsolutePath());
                fileList.add(file.getAbsolutePath());
            } else {
                String picPathStr = file.getAbsolutePath();
//              String picPathStr = file.getAbsolutePath().replaceAll("\\\\","//");
                fileList.add(picPathStr);
            }
        }
        /*for(String str:fileList){
            System.out.println(str);
        }*/
        return fileList;
    }

    /**
     * 获取缩略图
     *
     * @Aothor modderBUG
     * @Param filePath 文件路径
     * @Return fileList 文件路径列表
     * @Lastupdate 2019.12.29
     **/
    public static ArrayList<String> getFilesThum(String filePath) {
        ArrayList<String> fileList = new ArrayList<String>();
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                getFilesThum(file.getAbsolutePath());
                //fileList.add(file.getAbsolutePath());
            } else {
                String picPathStr = file.getAbsolutePath();
                fileList.add(picPathStr);
            }
        }
        return fileList;
    }


}