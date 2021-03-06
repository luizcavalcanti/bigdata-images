package br.edu.ufam.icomp.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

/**
 * Conversor de imagens para arquivo sequencial do HDFS.
 * 
 * @author luiz
 */
public class ImageToSequenceFile {

    static Configuration confHadoop = new Configuration();

    public static void main(String args[]) throws Exception {
        if (args.length != 2) {
            System.err.println("argumentos: dir-de-entrada arquivo-de-saida");
            System.exit(1);
        }

        FileSystem fs = FileSystem.get(confHadoop);
        Path inPath = new Path(args[0]);
        Path outPath = new Path(args[1]+"/dataset");
        FSDataInputStream in = null;
        SequenceFile.Writer writer = null;
        List<Path> files = listFiles(inPath, jpegFilter);
        try {
            writer = SequenceFile.createWriter(fs, confHadoop, outPath, Text.class, BytesWritable.class);
            for (Path p : files) {
                in = fs.open(p);
                byte buffer[] = new byte[in.available()];
                in.readFully(buffer);
                writer.append(new Text(p.getName()), new BytesWritable(buffer));
                in.close();
            }
        } finally {
            IOUtils.closeStream(writer);
        }
    }

    private static List<Path> listFiles(Path path, PathFilter filter) throws IOException {
        ArrayList<Path> files = new ArrayList<Path>();
        FileSystem fs = FileSystem.get(confHadoop);
        FileStatus[] status = fs.listStatus(path);
        for (int i = 0; i < status.length; i++) {
            Path p = status[i].getPath();
            if (filter.accept(p)) {
                files.add(p);
            }
        }
        return files;
    }

    private static PathFilter jpegFilter = new PathFilter() {
        @Override
        public boolean accept(Path path) {
            return path.getName().endsWith("jpg");
        }
    };

}
