// StylizedTextParser.java
// Class with methods to parse a message with markdown elements and
// convert to HTML stylized text 

package codeu.controller;

import java.lang.StringBuffer; 
import java.lang.String; 
import java.util.Stack; 
import java.util.Hashtable; 

public class StylizedTextParser {

    /* Maps markdown symbol to array of HTML opening and closing stylized syntax */
    private static final Hashtable<Character, String> MarkdownHTMLMapping = new Hashtable<Character, String>(); 

    /* constructor */
    public StylizedTextParser() {
        MarkdownHTMLMapping.put('*', "b");
        MarkdownHTMLMapping.put('_', "i"); 
    }

    /* converts markdown to HTML elements */
    public String parse(String markdownMessage) {

        /* holds final HTML message */
        StringBuffer HTMLMessage = new StringBuffer(); 

        /* message length */
        int stringLength = markdownMessage.length(); 

        /* contains potential stylized text */
        StringBuffer messageBuffer = new StringBuffer(); 

        /* marks potential beginning of stylized text */
        Stack<Character> styleMarker = new Stack<Character>(); 

        for (int i = 0; i < stringLength; i++) {
            char currChar = markdownMessage.charAt(i); 
            if (MarkdownHTMLMapping.containsKey(currChar)) {
 
                /* found possible beginning of stylized text */
                if (styleMarker.isEmpty() || styleMarker.peek() != currChar) { 
                    styleMarker.push(currChar);
                }
                else { /* found end of stylized text */
                    String HTMLTag = MarkdownHTMLMapping.get(styleMarker.pop()); 
                    HTMLMessage.append("<" + HTMLTag + ">" + messageBuffer + "</" + HTMLTag + ">"); 
                    messageBuffer = new StringBuffer(); // TODO change to handle different embedded markdown elements 
                }
            }
            else {

                /* if in potential stylized text, add to buffer; otherwise add to final message */
                if (styleMarker.isEmpty()) {
                    HTMLMessage.append(currChar); 
                }
                else {
                    messageBuffer.append(currChar); 
                }
            }
        }

        if (messageBuffer.capacity() != 0) {
            HTMLMessage.append(messageBuffer);
        } 

        return HTMLMessage.toString(); 
    }

    public static void main(String[] args) {
        StylizedTextParser testParser = new StylizedTextParser(); 

        /* TODO use assert statements for testing */
        System.out.println(testParser.parse("testing BOLD: *bold this*"));
        System.out.println(testParser.parse("*do not bold"));
        System.out.println(testParser.parse("do not bold*"));

        System.out.println(testParser.parse("testing ITALICS: _italicize this_"));
        System.out.println(testParser.parse("testing EMBEDDING: *bold*, _italicize_, *_both_*"));
    }
}
