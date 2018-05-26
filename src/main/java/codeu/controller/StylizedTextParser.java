// StylizedTextParser.java
// Class with methods to parse a message with markdown elements and
// convert to HTML stylized text. Currently supports bold, italicized, monospaced,
// strikethrough, and underline text. 

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
        MarkdownHTMLMapping.put('\'', "code");
        MarkdownHTMLMapping.put('~', "s");
        MarkdownHTMLMapping.put('-', "u"); 
    }

    /* converts markdown to HTML elements */
    public String parse(String markdownMessage) {
        /* holds final HTML message */
        StringBuffer HTMLMessage = new StringBuffer(); 

        /* contains potential stylized text */
        StringBuffer messageBuffer = new StringBuffer(); 

        /* marks potential beginning of stylized text */
        Stack<Character> styleMarker = new Stack<Character>(); 

        // return empty string if null message is passed in 
        if (markdownMessage == null) 
            return ""; 

        for (int i = 0; i < markdownMessage.length(); i++) {
            char currChar = markdownMessage.charAt(i); 
            if (MarkdownHTMLMapping.containsKey(currChar)) {
 
                /* found possible beginning of stylized text */
                if (styleMarker.isEmpty() || styleMarker.peek() != currChar) { 
                    styleMarker.push(currChar);
                }
                else { /* found end of stylized text */
                    String HTMLTag = MarkdownHTMLMapping.get(styleMarker.pop()); 
                    HTMLMessage.append("<" + HTMLTag + ">" + messageBuffer + "</" + HTMLTag + ">"); 
                    messageBuffer = new StringBuffer(); 
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

}