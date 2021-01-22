package org.example;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

public class TestCrawler extends WebCrawler{

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(ctextss|js|gif|jpg|png|mp3|mp4|zip|gz|pdf|xml))$");
    static FileWriter writer;

    static {
        try {
            writer = new FileWriter("/home/dimuthuw/MSC/crawl-data/cnn.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int pageNumber = 0;


    /**
     * Specify whether the given url should be crawled or not based on
     * the crawling logic. Here URLs with extensions css, js etc will not be visited
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();

        return !FILTERS.matcher(href).matches();
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by the program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String title = htmlParseData.getTitle();
            pageNumber++;

            //Write content to file
            String textWritten = "<DOC> <DOCNO> " + pageNumber + " </DOCNO> " + title + " </DOC>\n";
            try {
                writer.write(textWritten);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
