package com.company;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Magnet {

    public static String Magnet(String args) throws Exception{
        String url = args;
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("net.sourceforge.htmlunit").setLevel(java.util.logging.Level.OFF);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.waitForBackgroundJavaScript(600*1000);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        final HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(1000*3);
        webClient.setJavaScriptTimeout(300);
        Thread.sleep(1000 *3L);
        String js = "javascript:checkShowFollow('271942','2');";
        ScriptResult sr = page.executeJavaScript(js);
        HtmlPage newPage = (HtmlPage) sr.getNewPage();
        String a=page.asXml();
        Document doc = Jsoup.parse(a);
        Elements links = doc.select("a[href]"); //带有href属性的a元素
        String w=null;
        String w1=null;
        String magnet=null;
        double size=0;
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            if(linkText.length()>=3) {
                w = linkText.substring(linkText.length() - 2, linkText.length());
                if ((linkText.length() >= 3) && w.equals("GB")) {
                    w1 = linkText.substring(0, linkText.length() - 2);
                    if (Double.parseDouble(w1) >= size) {
                        size = Double.parseDouble(w1);
                        magnet = (linkText+"~"+linkHref);
                    }
                }
            }
        } return magnet;
    }
}




