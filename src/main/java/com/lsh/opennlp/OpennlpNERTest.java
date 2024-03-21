package com.lsh.opennlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OpennlpNERTest {

    public static void main(String[] args) throws Exception{
//        personNameFinder();
        dateNerFinder();
    }

    public static void dateNerFinder() throws Exception{
        TokenNameFinderModel model = new TokenNameFinderModel(new File("/Users/liushihao/IdeaProjects/opennlp-java-demo/model/en-ner-date.bin"));
        NameFinderME nameFinder = new NameFinderME(model);
        String [] sentence = new String[]{
                "2024年03月11日17:32:32",
                "2024-03-11 17:32:36",
                "2024年03月11日",
                "2024-03-11",
                "二〇二四年〇三月十一日",
                "20240311",
                "March 11 2024",
                "03/11/2024"
        };
        Span nameSpans[] = nameFinder.find(sentence);

        for(Span s: nameSpans){
            System.out.println(s.toString()+"  "+sentence[s.getStart()]);
        }
    }

    public static void personNameFinder() throws IOException {
        TokenNameFinderModel model = new TokenNameFinderModel(new File("/Users/liushihao/IdeaProjects/opennlp-java-demo/model/en-ner-person.bin"));
        NameFinderME nameFinder = new NameFinderME(model);
        String [] sentence = new String[]{
                "Mike",
                "and",
                "Smith",
                "are",
                "good",
                "friends",
                "zhangsan",
                "张三",
                "ZhangSan",
                "李四",
                "Joey",
                "Jack"
        };
        //Finding the names in the sentence
        Span nameSpans[] = nameFinder.find(sentence);

        //Printing the spans of the names in the sentence
        for(Span s: nameSpans){
            System.out.println(s.toString()+"  "+sentence[s.getStart()]);
        }
        /**
         * [0..1) person  Mike
         * [2..3) person  Smith
         * [10..12) person  Joey
         */

    }
}
