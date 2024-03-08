# analysis user intent 利用nlp工具分析用户意图



# Document Categorizer 文档分类器
https://opennlp.apache.org/docs/2.3.2/manual/opennlp.html#tools.doccat.training

```java
DoccatModel model = null;
try {
  ObjectStream<String> lineStream =
		new PlainTextByLineStream(new MarkableFileInputStreamFactory(new File("en-sentiment.train")), StandardCharsets.UTF_8);

  ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

  model = DocumentCategorizerME.train("eng", sampleStream,
      TrainingParameters.defaultParams(), new DoccatFactory());
} catch (IOException e) {
  e.printStackTrace();
}


try (OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(modelFile))) {
model.serialize(modelOut);
}

```