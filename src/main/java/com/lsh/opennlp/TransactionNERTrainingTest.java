package com.lsh.opennlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class TransactionNERTrainingTest {

    public static void main(String[] args) {
        try {
            // 读取模型
            InputStream tokenizerModelIn = new FileInputStream("/Users/liushihao/IdeaProjects/opennlp-java-demo/model/en-token.bin");
            TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelIn);
            TokenizerME tokenizer = new TokenizerME(tokenizerModel);

            InputStream nerModelIn = new FileInputStream("/Users/liushihao/IdeaProjects/opennlp-java-demo/transaction_ner_model.bin");
            TokenNameFinderModel nerModel = new TokenNameFinderModel(nerModelIn);
            NameFinderME nerFinder = new NameFinderME(nerModel);

            // 训练数据
            String[] transactions = {
                    "User wants to transfer 10 dollars to Joey.",
                    "I want to send 100 dollars to Tom."
                    // Add more training examples
            };

            // 提取实体
//            for (String transaction : transactions) {
//                String[] tokens = tokenizer.tokenize(transaction);
//                Span[] spans = nerFinder.find(tokens);
//                String[] entities = Span.spansToStrings(spans, tokens);
//                for (int i = 0; i < spans.length; i++) {
//                    System.out.println("Entity: " + entities[i] + " Type: " + spans[i].getType() +
//                            " Start: " + spans[i].getStart() + " End: " + spans[i].getEnd());
//                }
//            }

            String text = "Please transfer 100 dollars from account 1234567890 to account 0987654322 on March 1st.";
            String[] tokens = SimpleTokenizer.INSTANCE.tokenize(text);

            // 使用模型进行识别
            Span[] spans = nerFinder.find(tokens);

            // 解析识别结果
            for (Span span : spans) {
                String entity = String.join(" ", Arrays.copyOfRange(tokens, span.getStart(), span.getEnd()));
                String entityType = span.getType();
                System.out.println("Entity: " + entity);
                System.out.println("Type: " + entityType);
            }

            // 关闭输入流
            tokenizerModelIn.close();
            nerModelIn.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
