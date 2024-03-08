要使用 OpenNLP 实现文档分类，你可以遵循以下步骤：

1. **准备数据**：
    - 准备一个包含已标记文档和它们对应类别的训练数据集。每个文档应该有一个与之相关的类别或标签。

2. **训练模型**：
    - 使用 OpenNLP 提供的文档分类器来训练模型。文档分类器通常基于朴素贝叶斯或最大熵模型。
    - 通过提供训练数据集和适当的参数进行训练。
    - 示例代码如下：

```java
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

import java.io.*;

public class DocumentClassifier {

    public static void main(String[] args) throws IOException {
        // 读取训练数据
        InputStream dataIn = new FileInputStream("training_data.txt");
        ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
        ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

        // 定义训练参数
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 100);
        params.put(TrainingParameters.CUTOFF_PARAM, 5);

        // 训练文档分类器
        DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
        sampleStream.close();

        // 保存模型
        OutputStream modelOut = new BufferedOutputStream(new FileOutputStream("model.bin"));
        model.serialize(modelOut);
        modelOut.close();
    }
}
```

3. **使用模型**：
    - 加载训练好的模型文件。
    - 对新的文档进行分类。
    - 示例代码如下：

```java
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DoccatModel;

import java.io.*;

public class DocumentClassifier {

    public static void main(String[] args) throws IOException {
        // 加载模型
        InputStream modelIn = new FileInputStream("model.bin");
        DoccatModel model = new DoccatModel(modelIn);

        // 创建文档分类器
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);

        // 待分类的文档
        String document = "This is a test document.";

        // 进行分类
        double[] outcomes = categorizer.categorize(document.split(" "));
        String category = categorizer.getBestCategory(outcomes);

        System.out.println("Category: " + category);
    }
}
```

在这个示例中，我们首先加载了训练好的模型文件，然后创建了一个 `DocumentCategorizerME` 对象来进行文档分类。接着，我们提供了一个待分类的文档，并调用 `categorize()` 方法对其进行分类。最后，我们使用 `getBestCategory()` 方法获取最佳的分类结果。