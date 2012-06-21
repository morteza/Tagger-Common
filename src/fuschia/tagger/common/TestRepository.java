package fuschia.tagger.common;

public class TestRepository {

	public static void main(String[] args) {
		DocumentRepository repo = DocumentRepository.get911();
		System.out.println(repo.getAll().size());
		System.out.println("GU002-Q1 "+repo.getDocumentById("GU001-Q1").getText());
		System.out.println(repo.getParticipantMap().containsKey("GU058"));
		System.out.println(repo.getParticipantMap().getS1("GU058"));
		System.out.println(repo.getParticipantMap().getS1("GU176B"));
		System.out.println(repo.getParticipantMap().getS1("GU060C"));
		
	}

}
