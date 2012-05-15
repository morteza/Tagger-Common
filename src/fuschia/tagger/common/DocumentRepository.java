package fuschia.tagger.common;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DocumentRepository {
	private HashMap<String, Document> docs = null;

	public DocumentRepository() {
		docs = new HashMap<String, Document>();
	}

	public void setDocuments(HashMap<String, Document> docs) {
		this.docs.clear();
		this.docs.putAll(docs);
	}

	@SuppressWarnings("unchecked")
	public static DocumentRepository get911() {
		try{
			DocumentRepository repo = new DocumentRepository();
			InputStream is = repo.getClass().getResourceAsStream("911.cmap.gz");
			GZIPInputStream gzipis = new GZIPInputStream(is);
			ObjectInputStream oisCompressed = new ObjectInputStream(gzipis);

			Object obj = oisCompressed.readObject();

			if (obj instanceof HashMap<?, ?>) {
				repo.setDocuments((HashMap<String, Document>)obj);
			} else {
				throw new Exception("Invalid object format");
			}

			return repo;					
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<String, Document> getAll() {
		return docs;
	}

	public Document getDocumentById(String documentId) {
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

	@SuppressWarnings("unchecked")
	public void loadFromFile(String strFilePath) throws Exception {
		FileInputStream fis = new FileInputStream(new File(strFilePath));
		GZIPInputStream gzipis = new GZIPInputStream(fis);
		ObjectInputStream oisCompressed = new ObjectInputStream(gzipis);

		docs.clear();
		docs = null;

		Object obj = oisCompressed.readObject();

		if (obj instanceof HashMap<?, ?>) {
			docs = (HashMap<String, Document>)obj;
		} else {
			throw new Exception("Invalid object format");
		}
	}

	public void clear() {
		docs.clear();
	}
}
