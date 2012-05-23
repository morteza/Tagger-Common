package fuschia.tagger.common;

public class TestRepository {

	public static void main(String[] args) {
		DocumentRepository repo = DocumentRepository.get911();
		System.out.println(repo.getAll().size());
		System.out.println("GU002-Q1 "+repo.getDocumentById("GU001-Q1").getText());
	}

}
