import edu.rit.annotation.StreamsFactory;
import edu.rit.stream.StreamQueries;

@StreamsFactory(
	query="select * from do_nothing",
	name= "doNothing")
public class HelloAnnotationProcessor {

	public static void main(String[] args) {
		StreamQueries s = new StreamQueries();
		s.getdoNothing();
	}

}
