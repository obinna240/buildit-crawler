package com.buildit.crawler;

import com.buildit.crawler.exceptions.InvalidCrawlURLException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.buildit.crawler.exceptions.InvalidDepthException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CrawlerTest {

    Crawler crawler;

    String testURL1 = "http://www.bbc.co.uk";
    String testURL2 = "http://www.dummy.co.uk";
    String linksWithFileTypes = "https://crawler-test.com/urls/links_to_non_html_filetypes";

    @BeforeEach
    void init(){
        crawler = new Crawler();
    }

//    @Test
//    @DisplayName("Links with files)
//    public void willReturnLinksWithNonHtmlFileTypes() {
//        crawler.craw
//        crawler.crawl(linksWithFileTypes);
//
//    }

//    @Test
//    @DisplayName("Invalid url test")
//    public void willReturnFalseForInvalidURL(){
//        UrlValidator urlValidator = new UrlValidator();
//        boolean isValid = urlValidator.isValid("bbc.co.uk");
//        assertThat(isValid).isTrue();
//    }
//
//    @Test
//    @DisplayName("Invalid depth exception")
//    public void willReturnExceptionForInvalidDepth() {
//        assertThrows(InvalidDepthException.class, () -> {
//            crawler.crawl(testURL1, -1);
//        });
//    }

//    @Test
//    @DisplayName("Invalid depth exception")
//    public void willWriteCrawledPageToJsonAndReturnFile(){
//       crawler.crawl("dummyUrl", 10);
//       assertEquals(crawler.getDepth(), 3);
//    }

    @Test
    @DisplayName("Returns url with filetypes")
    public void willReturnNonURLFileTypes() {
        Map<String, Set> indexOfCrawledLinks = crawler.crawl(linksWithFileTypes, 0);
        List<String> staticContent = Arrays.asList("https://crawler-test.com/", "https://crawler-test.com/images/logo_small.jpg", "https://crawler-test.com/images/logo_small.JPG", "https://crawler-test.com/pdf_open_parameters.pdf", "https://crawler-test.com/pdf_open_parameters.PDF", "https://crawler-test.com/Dashboard/Charts/FCF_Column2D.swf", "https://crawler-test.com/Dashboard/Charts/FCF_Column2D.SWF");
        Set<String> indexedElements = indexOfCrawledLinks.get("https://crawler-test.com/urls/links_to_non_html_filetypes");
        assertThat(indexedElements.size()).isEqualTo(7);
        assertThat(indexedElements.containsAll(staticContent));
    }



    @Test
    public void willReturnExceptionForInvalidOrNullURL() {
        assertThrows(InvalidCrawlURLException.class, () -> {
            crawler.crawl("invalidURL", 1);
        });
    }

    @Test
    public void willReturnGoogleAsDomainName() throws Exception {
        String test = "http://www.google.co.uk#1234";
        URI uri = new URI(test);
        String domain = uri.getHost();
        String dd = domain.startsWith("www.")?domain.substring(4): domain;
        String domainName = StringUtils.substringBefore(dd, ".");
        assertEquals("google", domainName);
    }

    @Test
    public void willReturnFalseOnCheckingThatDomainNamesAreNotEqual() throws Exception {
        String url1 = "http://www.google.co.uk#1234";
        String url2 = "https://www.abcnews.com";
        URI uri = new URI(url1);
        String domain = uri.getHost();
        String dd = domain.startsWith("www.")?domain.substring(4): domain;
        String domainName = StringUtils.substringBefore(dd, ".");

        URI uri2 = new URI(url2);
        String domain2 = uri2.getHost();
        String dd2= domain2.startsWith("www.")?domain2.substring(4): domain2;
        String domainName2 = StringUtils.substringBefore(dd2, ".");

        boolean compare = domainName.equalsIgnoreCase(domainName2);
        assertThat(compare).isFalse();
    }

    @Test
    public void compareDomains() throws Exception {
        String url1 = "http://www.google.co.uk#1234";
        String url2 = "https://www.abcnews.com";
        assertThat(crawler.compareDomainName(url1, url2)).isFalse();
    }

    @Test
    public void compareDomainsWhenAnInternalLinkIsUsed() throws Exception {
        String url1 = "http://www.google.co.uk#1234";
        String url2 = "/";
        assertThat(crawler.compareDomainName(url1, url2)).isTrue();
    }

    public void willReturnMapOfLinks(){
//        assertEquals(3, crawler.crawl("https://crawler-test.com")).size()) ;
    }




}
