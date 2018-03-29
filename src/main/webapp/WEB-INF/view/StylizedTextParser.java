// StylizedTextParser.java
// Class with methods to parse a message with markdown elements and
// convert to HTML stylized text 

//package codeu.controller;

import java.lang.StringBuffer; 
import java.lang.String; 
import java.util.Stack; 
import java.util.Hashtable; 

public class StylizedTextParser {

    /* Maps markdown symbol to array of HTML opening and closing stylized syntax */
    private Hashtable<Character, String> styleTable; 

    /* constructor */
    public StylizedTextParser() {
        styleTable = new Hashtable<Character, String>(); 
        fill(styleTable); 
    }

    /* Fills styleTable with markdown elements to be parsed */
    private static void fill(Hashtable<Character, String> styleTable) {
        styleTable.put('*', "b");
        styleTable.put('_', "i");  
    }

    /* converts markdown to HTML elements */
    public String parse(String markdownMessage) {

        StringBuffer HTMLMessage = new StringBuffer(); /* holds final HTML message */
        int stringLength = markdownMessage.length(); /* message length */
        StringBuffer messageBuffer = new StringBuffer(); /* contains potential stylized text */
        Stack<Character> styleMarker = new Stack<Character>(); /* marks potential beginning of stylized text */

        for (int i = 0; i < stringLength; i++) {
            char currChar = markdownMessage.charAt(i); 
            if (styleTable.containsKey(currChar)) {
 
                /* found possible beginning of stylized text */
                if (styleMarker.isEmpty() || styleMarker.peek() != currChar) { 
                    styleMarker.push(currChar);
                }
                else { /* found end of stylized text */
                    String HTMLTag = styleTable.get(styleMarker.pop()); 
                    HTMLMessage.append("<" + HTMLTag + ">"); 
                    HTMLMessage.append(messageBuffer); 
                    HTMLMessage.append("</" + HTMLTag + ">"); 
                    messageBuffer = new StringBuffer(); // TODO change to handle different embedded markdown elements 
                }
            }
            else {

                /* if in potential stylized text, add to buffer; otherwise add to final message */
                if (styleMarker.isEmpty()) 
                    HTMLMessage.append(currChar); 
                else 
                    messageBuffer.append(currChar); 
            }
        }

        if (messageBuffer.capacity() != 0) 
            HTMLMessage.append(messageBuffer); 

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
