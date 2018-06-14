package com.company;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Main {
    public static double getDiskInfo() {
        StringBuffer sb=new StringBuffer();
        File[] roots = File.listRoots();// 获取磁盘分区列表
        for (File file : roots) {
            long totalSpace=file.getTotalSpace();
            long freeSpace=file.getFreeSpace();
            long usableSpace=file.getUsableSpace();
            double size=0,free=0;
            if(totalSpace>0){
                sb.append(file.getPath() + "(总计：");
                size=(((double)totalSpace/ (1024*1024*1024))*100/100.0);
                free=((((double)usableSpace/ (1024*1024*1024))*100)/100.0);
                return free;
                }
            }return 0;
        }




    public static void main (String args[]) throws Exception{
//        Properties prop = System.getProperties();
//        prop.setProperty("http.proxyHost", "192.168.137.1");
//        prop.setProperty("http.proxyPort", "1080");
//        prop.setProperty("https.proxyHost", "192.168.137.1");
//        prop.setProperty("https.proxyPort", "1080");
        //String encoding = "UTF-8";
        File file = new File("/root/123.txt");
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        String magnet,size,cache,downloaded="";
        double Size,disk;
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            downloaded=new String(filecontent);
            Document doc = null;
            Document doc1 = null;
            System.out.println(downloaded);
            for(int i=1;i<=10;i++){
                doc = Jsoup.connect("https://www.javbus.com/page/"+i).get();
                Elements links = ((Element) doc).select("a[href]");
                int b=0,ID=1234;
                for(Element link : links){
                    String linkHref =(link.attr("href"));
                    String a=OpenUrl(linkHref);
                    if((a!=null)&&(a.indexOf("javascript")==-1)&&(a.indexOf("page")==-1)&&(a.indexOf("-")!=-1)){
                        if (b>=17&&(b<=46)){
                            System.out.println(a);
                            if(downloaded.indexOf(a) == -1){
                                cache=Magnet.Magnet(a);
                                int intIndex = cache.indexOf("~");
                                size=cache.substring(0,intIndex-2);
                                magnet=cache.substring(intIndex+1,cache.length());
                                Size=Double.parseDouble(size);
                                disk=getDiskInfo();
                                while((Size+1)>=disk){
                                    Thread.sleep(100000);
                                    //System.out.println(1000);
                                    disk=getDiskInfo();
                                }
                                RPC.addrpc(magnet,ID);
                                RPC.tellrpc(ID);
                                ID++;
                                downloaded=a+downloaded;
                                FileWriter fw = new FileWriter("/root/123.txt", true);
                                BufferedWriter bw = new BufferedWriter(fw);
                                bw.write(a);
                                bw.close();
                                fw.close();
                            }
                        }
                        b++;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("以上地址未获取到页面");
            e.printStackTrace();
        }

    }
    public static String OpenUrl(String str){
        if ((str.length()>=24)){
            return str;
        }
        else return null;

    }

}
