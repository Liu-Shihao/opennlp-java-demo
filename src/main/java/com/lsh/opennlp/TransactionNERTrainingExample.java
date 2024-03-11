package com.lsh.opennlp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import opennlp.tools.namefind.*;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.featuregen.*;

public class TransactionNERTrainingExample {

    public static void main(String[] args) {
        try {

            InputStreamFactory inputStreamFactory = new InputStreamFactory() {
                public InputStream createInputStream() throws IOException {
                    FileInputStream fileInputStream = new FileInputStream("/Users/liushihao/IdeaProjects/opennlp-java-demo/example/transfers_train.txt");
                    return fileInputStream;
                }
            };

            // 读取训练数据

            ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, "UTF-8");
            ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);

            // 初始化特征生成器
            AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(
                    new AdaptiveFeatureGenerator[] {
                            new WindowFeatureGenerator(new TokenFeatureGenerator(), 2, 2),
                            new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 2, 2),
                            new OutcomePriorFeatureGenerator(),
                            new PreviousMapFeatureGenerator(),
                            new BigramNameFeatureGenerator()
                    });

            // 训练参数
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, "100");
            params.put(TrainingParameters.CUTOFF_PARAM, "5");

            // 训练模型
            TokenNameFinderModel model = NameFinderME.train("en", "transaction", sampleStream,
                    params, new TokenNameFinderFactory());

            // 保存模型
            model.serialize(new FileOutputStream("transaction_ner_model.bin"));

            System.out.println("Transaction NER model trained and saved successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
