package fuschia.tagger.common;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.imageio.stream.FileImageInputStream;


public class DocumentRepository {
	private HashMap<String, Document> docs;
	
	public DocumentRepository() {
		docs = new HashMap<String, Document>();
	}
	
	public void setDocuments(HashMap<String, Document> docs) {
		this.docs.clear();
		this.docs.putAll(docs);
	}
	
	
	
	public static DocumentRepository get911() {
		try{
			DocumentRepository repo = new DocumentRepository();
			InputStream is = repo.getClass().getResourceAsStream("911.cmap.gz");
			GZIPInputStream gzipis = new GZIPInputStream(is);
			ObjectInputStream oisCompressed = new ObjectInputStream(gzipis);
			
			repo.setDocuments((HashMap<String, Document>)oisCompressed.readObject());
			
			return repo;					
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<String, Document> getAll() {
		return docs;
	}
	
	public Document getDocument(String documentId) {
		return docs.get(documentId);
	}
	
	public void addDocument(String documentId, Document document) {
		docs.put(documentId, document);
	}
	
	public void saveToFile(String strFilePath) throws Exception{
		FileOutputStream fout = new FileOutputStream(new File(strFilePath), false);
		GZIPOutputStream gzipos = new GZIPOutputStream(fout) {
			{
				def.setLevel(Deflater.BEST_COMPRESSION);
			}
		};

		ObjectOutputStream oosCompressed = new ObjectOutputStream(gzipos);
		oosCompressed.writeObject(docs);
		oosCompressed.flush();
		oosCompressed.close();
		fout.flush();
		fout.close();		
	}
	
	public void loadFromFile(String strFilePath) throws Exception {
		 FileInputStream fis = new FileInputStream(new File(strFilePath));
		 GZIPInputStream gzipis = new GZIPInputStream(fis);
		 ObjectInputStream oisCompressed = new ObjectInputStream(gzipis);
		 
		 docs.clear();
		 docs = null;
		 docs = (HashMap<String, Document>)oisCompressed.readObject();
	}
	
	public void clear() {
		docs.clear();
	}
}
