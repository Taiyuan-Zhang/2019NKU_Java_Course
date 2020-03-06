import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;


/**
 * @author song
 * @description:
 * 依赖jar：Lucene-core，lucene-analyzers-common，lucene-queryparser
 * 作用：简单的索引建立
 */
public class CreateIndex {
    /**
     * 建立索引
     */
    /**public static class FileDocument {
       public static Document Document(File file) throws IOException {
           Document document = null;
           document = new Document();
           document.add(new StringField("path", file.getPath(),Field.Store.YES));
           System.out.println(file.getName());
           document.add(new StringField("name", file.getName(),Field.Store.YES));
           InputStream stream = Files.newInputStream(Paths.get(file.toString()));
           document.add(new TextField("content", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));//textField内容会进行分词
           //document.add(new TextField("content", new FileReader(file)));  如果不用utf-8编码的话直接用这个就可以了
           return  document;
       }
    }
    public static void indexDocs(IndexWriter writer,File file)
            throws IOException {
        if (file.canRead()) {
            // 如果file是一个目录(该目录下面可能有文件、目录文件、空文件三种情况)
            if (file.isDirectory()) {
                // 获取file目录下的所有文件(包括目录文件)File对象，放到数组files里
                String[] files = file.list();
                if (files != null) {// 如果files!=null
                    // 对files数组里面的File对象递归索引，通过广度遍历
                    for (int i = 0; i < files.length; i++) {
                        indexDocs(writer, new File(file, files[i]));
                    }
                }
            } else { // 到达叶节点时，说明是一个File，而不是目录，则建立索引
                System.out.println("adding " + file);
                try {
                    writer.addDocument(FileDocument.Document(file));
                } catch (FileNotFoundException fnfe) {
                    ;
                }
            }
        }
    }
    public static void createIndex(){
        IndexWriter writer = null;
        try {
            //1、创建Directory
            //Directory directory = new RAMDirectory();//创建内存directory
            Directory directory = FSDirectory.open(Paths.get("G:\\360MoveData\\Users\\Imaginer\\Desktop\\JAVA\\Index1"));//在硬盘上生成Directory00
            //2、创建IndexWriter
            IndexWriterConfig iwConfig = new IndexWriterConfig(new StandardAnalyzer());
            writer = new IndexWriter(directory, iwConfig);
            //3、创建document对象
            //Document document = null;
            //4、为document添加field对象
            File f = new File("G:\\360MoveData\\Users\\Imaginer\\Desktop\\JAVA\\maildir");//索引源文件位置
            indexDocs(writer, f);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //6、使用完成后需要将writer进行关闭
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException
    {
        createIndex();
    }
}
*/
    public static void indexDocs(File file)
            throws IOException {
        if (file.canRead()) {
            // 如果file是一个目录(该目录下面可能有文件、目录文件、空文件三种情况)
            if (file.isDirectory()) {
                // 获取file目录下的所有文件(包括目录文件)File对象，放到数组files里
                String[] files = file.list();
                if (files != null) {// 如果files!=null
                    // 对files数组里面的File对象递归索引，通过广度遍历
                    for (int i = 0; i < files.length; i++) {
                        indexDocs(new File(file, files[i]));
                    }
                }
            } else { // 到达叶节点时，说明是一个File，而不是目录，则建立索引
                try {
                    indexFile(file);
                } catch (FileNotFoundException fnfe) {
                    ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static IndexWriter writer;
    public CreateIndex(String indexDir) throws IOException {
        //得到索引所在目录的路径
        Directory directory = FSDirectory.open(Paths.get(indexDir));
        // 标准分词器
        Analyzer analyzer = new StandardAnalyzer();
        //保存用于创建IndexWriter的所有配置。
        IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        //实例化IndexWriter
        writer = new IndexWriter(directory, iwConfig);
    }
    public void close() throws IOException {
        writer.close();
    }

    public int index(String dataDir) throws Exception {
        File f= new File(dataDir);
            //索引指定文件
            indexDocs(f);
        //返回索引了多少个文件
        return writer.numRamDocs();

    }
    private static void indexFile(File f) throws Exception {
        //输出索引文件的路径
        System.out.println("索引文件：" + f.getCanonicalPath());
        //获取文档，文档里再设置每个字段
        Document doc = getDocument(f);
        //开始写入,就是把文档写进了索引文件里去了；
        writer.addDocument(doc);
    }
    private static Document getDocument(File f) throws Exception {

        Long fileLengthLong = f.length();
        byte[] fileContent = new byte[fileLengthLong.intValue()];
        try {
            FileInputStream inputStream = new FileInputStream(f);
            inputStream.read(fileContent);
            inputStream.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        String contents_string = new String(fileContent);

        // 截取到的Subject(信件标题)
        int subject_begin = contents_string.indexOf("Subject:") + 8;
        int subject_end = contents_string.indexOf("\r\n", subject_begin);
        String subject_string = contents_string.substring(subject_begin, subject_end);

        // 截取到的From(发信人)
        int from_begin = contents_string.indexOf("From:") + 5;
        int from_end = contents_string.indexOf("\r\n", from_begin);
        String from_string = contents_string.substring(from_begin, from_end);

        //截取到的To（收信人）
        int to_begin = contents_string.indexOf("To:")+5;
        int to_end=contents_string.indexOf("\r\n",to_begin);
        String to_string =contents_string.substring(to_begin,to_end);

        // 截取到的body(信件内容)
        int body_begin = contents_string.indexOf("\r\n\r\n") + 4;
        String body_string = contents_string.substring(body_begin);

        Document doc = new Document();
        //把设置好的索引加到Document里，以便在确定被索引文档
        //Field.Store.YES：把文件名存索引文件里，为NO就说明不需要加到索引文件里去
        doc.add(new TextField("contents", contents_string, Field.Store.YES));
        doc.add(new TextField("body", body_string, Field.Store.YES));
        doc.add(new TextField("from", from_string, Field.Store.YES));
        doc.add(new TextField("to", to_string, Field.Store.YES));
        doc.add(new TextField("subject", subject_string, Field.Store.YES));

        //把完整路径存在索引文件里
        doc.add(new TextField("fileName", f.getName(), Field.Store.YES));
        doc.add(new TextField("fullPath", f.getCanonicalPath(), Field.Store.YES));

        return doc;
    }

    public static void main(String[] args) {
        //索引指定的文档路径
        String indexDir = ".\\Index3";
        ////被索引数据的路径
        String dataDir = ".\\maildir";
        CreateIndex indexer = null;
        int numIndexed = 0;
        //索引开始时间
        long start = System.currentTimeMillis();
        try {
            indexer = new CreateIndex(indexDir);
            numIndexed = indexer.index(dataDir);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                indexer.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //索引结束时间
        long end = System.currentTimeMillis();
        System.out.println("共索引：" + numIndexed + " 个文件 花费了" + (end - start)/1000.0 + " s");
    }
}