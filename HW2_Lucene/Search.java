import java.io.*;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
public class Search{
    //全文搜索
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    public static void search(String indexDir, String q) throws Exception {

        // 得到读取索引文件的路径
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        // 通过dir得到的路径下的所有的文件
        IndexReader reader = DirectoryReader.open(dir);
        // 建立索引查询器
        IndexSearcher is = new IndexSearcher(reader);
        // 实例化分析器
        Analyzer analyzer = new StandardAnalyzer();
        // 建立查询解析器
        //第一个参数是要查询的字段； 第二个参数是分析器Analyzer
        QueryParser parser = new QueryParser("contents", analyzer);
        // 根据传进来的p查找
        Query query = parser.parse(q);
        // 计算索引开始时间
        long start = System.currentTimeMillis();
        // 开始查询
        /**
         * 第一个参数是通过传过来的参数来查找得到的query； 第二个参数是要出查询的行数
         */
        TopDocs hits = is.search(query, 10);

        //高亮设置
        //用于高亮查询,query是Lucene的查询对象Query，对命中结果进行评分操作
        QueryScorer scorer=new QueryScorer(query);
        //即拆分器，把原始文本拆分成一个个高亮片段。
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
        //以红色字体标记关键词
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        //创建一个高亮器
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
        //高亮匹配到的文档片段
        highlighter.setTextFragmenter(fragmenter);

        // 计算索引结束时间
        long end = System.currentTimeMillis();
        System.out.println("检索： " + q + " ，总共耗时" + (end - start)/1000.0 + " s" + " 查询到" + hits.totalHits + "个记录");
        // 遍历hits.scoreDocs，得到scoreDoc
        /**
         * ScoreDoc:得分文档,即得到文档 scoreDocs:代表的是topDocs这个文档数组
         *
         * @throws Exception
         */

        BufferedWriter out = new BufferedWriter(new FileWriter("result.html", true));
        int count=0;
        System.out.println("搜索时间："+df.format(new Date()));// new Date()为获取当前系统时间
        out.write("搜索时间："+df.format(new Date())+"\r");
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            count++;
            Document doc = is.doc(scoreDoc.doc);
            System.out.println("("+count+")."+doc.get("fullPath"));
            String content = doc.get("contents");
            String highlighter_string ="";
            if(content!=null){
                try {
                    TokenStream tokenStream = null;

                    tokenStream=analyzer.tokenStream("contents", content);
                    // System.out.println(highlighter.getBestFragment(tokenStream, content));
                    out.write("("+count+")."+doc.get("fullPath"));

                    highlighter_string = highlighter.getBestFragment(tokenStream, content).toString();
                    out.write("<br>");
                    out.write(highlighter_string);
                    out.write("<br>");
                    out.write("<br>");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        out.close();
        // 关闭reader
        reader.close();
    }
    //信件标题
    public static void search1(String indexDir, String q) throws Exception {

        // 得到读取索引文件的路径
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        // 通过dir得到的路径下的所有的文件
        IndexReader reader = DirectoryReader.open(dir);
        // 建立索引查询器
        IndexSearcher is = new IndexSearcher(reader);
        // 实例化分析器
        Analyzer analyzer = new StandardAnalyzer();
        // 建立查询解析器
        //第一个参数是要查询的字段； 第二个参数是分析器Analyzer
        QueryParser parser = new QueryParser("subject", analyzer);
        // 根据传进来的p查找
        Query query = parser.parse(q);
        // 计算索引开始时间
        long start = System.currentTimeMillis();
        // 开始查询
        /**
         * 第一个参数是通过传过来的参数来查找得到的query； 第二个参数是要出查询的行数
         */
        TopDocs hits = is.search(query, 10);

        //高亮设置
        //用于高亮查询,query是Lucene的查询对象Query，对命中结果进行评分操作
        QueryScorer scorer=new QueryScorer(query);
        //即拆分器，把原始文本拆分成一个个高亮片段。
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
        //以红色字体标记关键词
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        //创建一个高亮器
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
        //高亮匹配到的文档片段
        highlighter.setTextFragmenter(fragmenter);

        // 计算索引结束时间
        long end = System.currentTimeMillis();
        System.out.println("检索： " + q + " ，总共耗时" + (end - start)/1000.0 + " s" + " 查询到" + hits.totalHits + "个记录");
        // 遍历hits.scoreDocs，得到scoreDoc
        /**
         * ScoreDoc:得分文档,即得到文档 scoreDocs:代表的是topDocs这个文档数组
         *
         * @throws Exception
         */

        BufferedWriter out = new BufferedWriter(new FileWriter("result.html", true));
        int count=0;
        System.out.println("搜索时间："+df.format(new Date()));// new Date()为获取当前系统时间
        out.write("搜索时间："+df.format(new Date())+"\r");
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            count++;
            Document doc = is.doc(scoreDoc.doc);
            System.out.println("("+count+")."+doc.get("fullPath"));
            String content = doc.get("contents");
            String highlighter_string ="";
            if(content!=null){
                try {
                    TokenStream tokenStream = null;

                    tokenStream=analyzer.tokenStream("subject", content);
                    // System.out.println(highlighter.getBestFragment(tokenStream, content));
                    out.write("("+count+")."+doc.get("fullPath"));

                    highlighter_string = highlighter.getBestFragment(tokenStream, content).toString();
                    out.write("<br>");
                    out.write(highlighter_string);
                    out.write("<br>");
                    out.write("<br>");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        out.close();
        // 关闭reader
        reader.close();
    }
    //收信人
    public static void search2(String indexDir, String q) throws Exception {

        // 得到读取索引文件的路径
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        // 通过dir得到的路径下的所有的文件
        IndexReader reader = DirectoryReader.open(dir);
        // 建立索引查询器
        IndexSearcher is = new IndexSearcher(reader);
        // 实例化分析器
        Analyzer analyzer = new StandardAnalyzer();
        // 建立查询解析器
        //第一个参数是要查询的字段； 第二个参数是分析器Analyzer
        QueryParser parser = new QueryParser("from", analyzer);
        // 根据传进来的p查找
        Query query = parser.parse(q);
        // 计算索引开始时间
        long start = System.currentTimeMillis();
        // 开始查询
        /**
         * 第一个参数是通过传过来的参数来查找得到的query； 第二个参数是要出查询的行数
         */
        TopDocs hits = is.search(query, 10);

        //高亮设置
        //用于高亮查询,query是Lucene的查询对象Query，对命中结果进行评分操作
        QueryScorer scorer=new QueryScorer(query);
        //即拆分器，把原始文本拆分成一个个高亮片段。
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
        //以红色字体标记关键词
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        //创建一个高亮器
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
        //高亮匹配到的文档片段
        highlighter.setTextFragmenter(fragmenter);

        // 计算索引结束时间
        long end = System.currentTimeMillis();
        System.out.println("检索： " + q + " ，总共耗时" + (end - start)/1000.0 + " s" + " 查询到" + hits.totalHits + "个记录");
        // 遍历hits.scoreDocs，得到scoreDoc
        /**
         * ScoreDoc:得分文档,即得到文档 scoreDocs:代表的是topDocs这个文档数组
         *
         * @throws Exception
         */

        BufferedWriter out = new BufferedWriter(new FileWriter("result.html", true));
        int count=0;
        System.out.println("搜索时间："+df.format(new Date()));// new Date()为获取当前系统时间
        out.write("搜索时间："+df.format(new Date())+"\r");
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            count++;
            Document doc = is.doc(scoreDoc.doc);
            System.out.println("("+count+")."+doc.get("fullPath"));
            String content = doc.get("contents");
            String highlighter_string ="";
            if(content!=null){
                try {
                    TokenStream tokenStream = null;

                    tokenStream=analyzer.tokenStream("from", content);
                    // System.out.println(highlighter.getBestFragment(tokenStream, content));
                    out.write("("+count+")."+doc.get("fullPath"));

                    highlighter_string = highlighter.getBestFragment(tokenStream, content).toString();
                    out.write("<br>");
                    out.write(highlighter_string);
                    out.write("<br>");
                    out.write("<br>");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        out.close();
        // 关闭reader
        reader.close();
    }

    //发信人
    public static void search3(String indexDir, String q) throws Exception {

        // 得到读取索引文件的路径
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        // 通过dir得到的路径下的所有的文件
        IndexReader reader = DirectoryReader.open(dir);
        // 建立索引查询器
        IndexSearcher is = new IndexSearcher(reader);
        // 实例化分析器
        Analyzer analyzer = new StandardAnalyzer();
        // 建立查询解析器
        //第一个参数是要查询的字段； 第二个参数是分析器Analyzer
        QueryParser parser = new QueryParser("to", analyzer);
        // 根据传进来的p查找
        Query query = parser.parse(q);
        // 计算索引开始时间
        long start = System.currentTimeMillis();
        // 开始查询
        /**
         * 第一个参数是通过传过来的参数来查找得到的query； 第二个参数是要出查询的行数
         */
        TopDocs hits = is.search(query, 10);

        //高亮设置
        //用于高亮查询,query是Lucene的查询对象Query，对命中结果进行评分操作
        QueryScorer scorer=new QueryScorer(query);
        //即拆分器，把原始文本拆分成一个个高亮片段。
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
        //以红色字体标记关键词
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        //创建一个高亮器
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
        //高亮匹配到的文档片段
        highlighter.setTextFragmenter(fragmenter);

        // 计算索引结束时间
        long end = System.currentTimeMillis();
        System.out.println("检索： " + q + " ，总共耗时" + (end - start)/1000.0 + " s" + " 查询到" + hits.totalHits + "个记录");
        // 遍历hits.scoreDocs，得到scoreDoc
        /**
         * ScoreDoc:得分文档,即得到文档 scoreDocs:代表的是topDocs这个文档数组
         *
         * @throws Exception
         */

        BufferedWriter out = new BufferedWriter(new FileWriter("result.html", true));
        int count=0;
        System.out.println("搜索时间："+df.format(new Date()));// new Date()为获取当前系统时间
        out.write("搜索时间："+df.format(new Date())+"\r");
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            count++;
            Document doc = is.doc(scoreDoc.doc);
            System.out.println("("+count+")."+doc.get("fullPath"));
            String content = doc.get("contents");
            String highlighter_string ="";
            if(content!=null){
                try {
                    TokenStream tokenStream = null;

                    tokenStream=analyzer.tokenStream("to", content);
                    // System.out.println(highlighter.getBestFragment(tokenStream, content));
                    out.write("("+count+")."+doc.get("fullPath"));

                    highlighter_string = highlighter.getBestFragment(tokenStream, content).toString();
                    out.write("<br>");
                    out.write(highlighter_string);
                    out.write("<br>");
                    out.write("<br>");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        out.close();
        // 关闭reader
        reader.close();
    }
    //信件内容
    public static void search4(String indexDir, String q) throws Exception {

        // 得到读取索引文件的路径
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        // 通过dir得到的路径下的所有的文件
        IndexReader reader = DirectoryReader.open(dir);
        // 建立索引查询器
        IndexSearcher is = new IndexSearcher(reader);
        // 实例化分析器
        Analyzer analyzer = new StandardAnalyzer();
        // 建立查询解析器
        //第一个参数是要查询的字段； 第二个参数是分析器Analyzer
        QueryParser parser = new QueryParser("body", analyzer);
        // 根据传进来的p查找
        Query query = parser.parse(q);
        // 计算索引开始时间
        long start = System.currentTimeMillis();
        // 开始查询
        /**
         * 第一个参数是通过传过来的参数来查找得到的query； 第二个参数是要出查询的行数
         */
        TopDocs hits = is.search(query, 10);

        //高亮设置
        //用于高亮查询,query是Lucene的查询对象Query，对命中结果进行评分操作
        QueryScorer scorer=new QueryScorer(query);
        //即拆分器，把原始文本拆分成一个个高亮片段。
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
        //以红色字体标记关键词
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        //创建一个高亮器
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
        //高亮匹配到的文档片段
        highlighter.setTextFragmenter(fragmenter);

        // 计算索引结束时间
        long end = System.currentTimeMillis();
        System.out.println("检索： " + q + " ，总共耗时" + (end - start)/1000.0 + " s" + " 查询到" + hits.totalHits + "个记录");
        // 遍历hits.scoreDocs，得到scoreDoc
        /**
         * ScoreDoc:得分文档,即得到文档 scoreDocs:代表的是topDocs这个文档数组
         *
         * @throws Exception
         */

        BufferedWriter out = new BufferedWriter(new FileWriter("result.html", true));
        int count=0;
        System.out.println("搜索时间："+df.format(new Date()));// new Date()为获取当前系统时间
        out.write("搜索时间："+df.format(new Date())+"\r");
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            count++;
            Document doc = is.doc(scoreDoc.doc);
            System.out.println("("+count+")."+doc.get("fullPath"));
            String content = doc.get("contents");
            String highlighter_string ="";
            if(content!=null){
                try {
                    TokenStream tokenStream = null;

                    tokenStream=analyzer.tokenStream("body", content);
                    out.write("("+count+")."+doc.get("fullPath"));

                    highlighter_string = highlighter.getBestFragment(tokenStream, content).toString();
                    out.write("<br>");
                    out.write(highlighter_string);
                    out.write("<br>");
                    out.write("<br>");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        out.close();
        // 关闭reader
        reader.close();
    }

    public static Scanner sc = new Scanner(System.in);
    public static InputStreamReader ir = new InputStreamReader(System.in);//创建输入流对象
    public static BufferedReader in = new BufferedReader(ir);
    public static void mainMenu() throws Exception {
        String indexDir = ".\\Index3";
        System.out.println("*************欢迎使用信件检索系统*************");
        System.out.println("请选择关键字 1.全文搜索  2.信件标题  3.发信人  4.收信人  5.信件内容   6.清除历史搜索记录  7.退出系统");
        String choice = sc.next();
        switch (choice.trim()) {
            case "1":
                System.out.println("请输入搜索内容：");
                String s=in.readLine();
                try {
                    search(indexDir, s);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mainMenu();
                break;
            case "2":
                System.out.println("请输入搜索内容：");
                String s1=in.readLine();
                try {
                    search1(indexDir, s1);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mainMenu();
                break;
            case "3":
                System.out.println("请输入搜索内容：");
                String s2=in.readLine();
                try {
                    search2(indexDir, s2);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mainMenu();
                break;
            case "4":
                System.out.println("请输入搜索内容：");
                String s3=in.readLine();
                try {
                    search3(indexDir, s3);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mainMenu();
                break;
            case "5":
                System.out.println("请输入搜索内容：");
                String s4=in.readLine();
                try {
                    search4(indexDir, s4);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mainMenu();
                break;
            case "6":
                File file =new File("result.html");
                FileWriter fileWriter =new FileWriter(file);
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
                System.out.println("搜索历史记录于  "+df.format(new Date())+"  已清除");
                mainMenu();
                break;
            case"7":
                System.out.println("谢谢使用");
                System.exit(0);
                break;
            default:
                System.out.println("您的输入有误！");
                mainMenu();
                break;
        }
    }
    public static void main(String[] args) throws Exception {
        while(true) {
            mainMenu();
        }
    }
}
