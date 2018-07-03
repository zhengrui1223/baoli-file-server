package com.baoli.util;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-07-03 17:37
 ************************************************************/

public class VideoUtil {
    /**
     * 获取指定视频的帧并保存为图片至指定目录
     *
     * @param videoFile 源视频文件路径
     * @param frameFile 截取帧的图片存放路径
     * @throws Exception
     */
    public static void fetchFrame(String videoFile, String frameFile)
            throws Exception {
        long start = System.currentTimeMillis();
        File targetFile = new File(frameFile);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoFile);
        ff.start();
        int length = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < length) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            f = ff.grabFrame();
            if ((i > 5) && (f.image != null)) {
                break;
            }
            i++;
        }
        opencv_core.IplImage img = f.image;
        int oldWidth = img.width();
        int oldHeight = img.height();
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / oldWidth) * oldHeight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null);
        ImageIO.write(bi, "jpg", targetFile);
        //ff.flush();
        ff.stop();
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void main(String[] args) {
        try {
            fetchFrame("C:\\Users\\Administrator\\Desktop\\测试图片视频\\球酷早报打样（最终版）.mp4", "D:\\test.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
